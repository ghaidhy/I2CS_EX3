package assignments.Ex3;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

import java.awt.*;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 */
public class Ex3Algo implements PacManAlgo{
    int ans = 0; int pinkState = 1;  int monsterState = 2; int greenState = 3;
    int blue = Game.getIntColor(Color.BLUE, 0);
    int pink = Game.getIntColor(Color.PINK, 0);
    int black = Game.getIntColor(Color.BLACK, 0);
    int green = Game.getIntColor(Color.GREEN, 0);
    private int _count;
    public Ex3Algo() {_count=0;}

    public String getInfo() {
        return "Safer BFS: choose safe food, else run";
    }

    @Override
    public int move(PacmanGame game) {
        int wallColor = 1;
        int pinkFood = 3;
        int greenFood = 5;

        int[][] board = game.getGame(0);
        GhostCL [] gs = game.getGhosts(0);

        String pos = String.valueOf(game.getPos(0));
        String[] p = pos.replace(" ", "").split(",");
        int px = Integer.parseInt(p[0]);
        int py = Integer.parseInt(p[1]);

        Pixel2D pac = new Index2D(px, py);
        Map2D m = new Map(board);

        // distance from pacman to all cells
        Map2D distFromPac = m.allDistance(pac, wallColor);

        // Build distance maps from each "danger" ghost (not eatable now)
        Map2D[] dangerGhostDists = new Map2D[0];
        Pixel2D closestDangerGhost = null;
        int closestDangerLen = Integer.MAX_VALUE;

        if (gs != null && gs.length > 0) {
            dangerGhostDists = new Map2D[gs.length];
            for (int i = 0; i < gs.length; i++) {
                GhostCL g0 = gs[i];
                if (g0 == null) continue;

                // ignore eatable ghosts (they are not danger right now)
                if (g0.remainTimeAsEatable(0) > 0) continue;

                String gpos = String.valueOf(g0.getPos(0));
                String[] parts = gpos.replace(" ", "").split(",");
                Pixel2D gp = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

                // distance map from this ghost
                dangerGhostDists[i] = m.allDistance(gp, wallColor);

                // closest danger ghost by path length
                Pixel2D[] path = m.shortestPath(pac, gp, wallColor);
                if (path != null && path.length < closestDangerLen) {
                    closestDangerLen = path.length;
                    closestDangerGhost = gp;
                }
            }
        }

        // If danger ghost is extremely close -> immediate run
        if (closestDangerGhost != null && closestDangerLen <= 4) {
            return RunAway(pac, closestDangerGhost, m, wallColor);
        }

        // Choose a SAFE target (pink or green)
        Pixel2D bestTarget = null;
        int bestScore = Integer.MIN_VALUE;

        // safety margin: how far a target cell must be from closest danger ghost
        int SAFE_MARGIN = 5;   // increase to be safer, decrease to be more aggressive
        int NEAR_MARGIN = 3;   // if target is within 3 of a danger ghost, avoid strongly

        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                int cell = m.getPixel(x, y);
                if (cell != pinkFood && cell != greenFood) continue;

                int dPac = distFromPac.getPixel(x, y);
                if (dPac < 0) continue; // unreachable

                // compute min distance from any danger ghost to this cell
                int minDG = Integer.MAX_VALUE;
                boolean hasDanger = false;

                if (dangerGhostDists != null && dangerGhostDists.length > 0) {
                    for (int i = 0; i < dangerGhostDists.length; i++) {
                        Map2D dg = dangerGhostDists[i];
                        if (dg == null) continue;
                        hasDanger = true;
                        int dd = dg.getPixel(x, y);
                        if (dd >= 0 && dd < minDG) minDG = dd;
                    }
                }

                // If there are danger ghosts, enforce safety
                if (hasDanger) {
                    if (minDG <= NEAR_MARGIN) continue; // too risky
                } else {
                    // no danger ghosts right now -> treat as very safe
                    minDG = 999;
                }

                // Prefer green slightly (bonus), but never at the cost of suicide
                int bonus = (cell == greenFood) ? 2 : 0;

                // Score: higher is better
                // want: far from ghosts (minDG big), close to pacman (dPac small)
                // score = 10*minDG - 3*dPac + bonus
                int score = 10 * minDG - 3 * dPac + bonus;

                // Require a minimal safety margin when danger exists
                if (hasDanger && minDG < SAFE_MARGIN) {
                    // not safe enough unless it's super close and no better options exist
                    score -= 50;
                }

                if (score > bestScore) {
                    bestScore = score;
                    bestTarget = new Index2D(x, y);
                }
            }
        }

        // If we found a good target, go there using shortest path
        if (bestTarget != null) {
            int dir = nextMove(pac, bestTarget, m, wallColor);

            // last safety check: if next step is close to a danger ghost, override to run
            int nx = pac.getX(), ny = pac.getY();
            if (dir == Game.UP) ny += 1;
            else if (dir == Game.DOWN) ny -= 1;
            else if (dir == Game.RIGHT) nx += 1;
            else if (dir == Game.LEFT) nx -= 1;

            if (m.isCyclic()) {
                nx = (nx + m.getWidth()) % m.getWidth();
                ny = (ny + m.getHeight()) % m.getHeight();
            }

            if (nx >= 0 && nx < m.getWidth() && ny >= 0 && ny < m.getHeight() && m.getPixel(nx, ny) != wallColor) {
                int minDGNext = Integer.MAX_VALUE;
                boolean hasDanger = false;

                if (dangerGhostDists != null && dangerGhostDists.length > 0) {
                    for (int i = 0; i < dangerGhostDists.length; i++) {
                        Map2D dg = dangerGhostDists[i];
                        if (dg == null) continue;
                        hasDanger = true;
                        int dd = dg.getPixel(nx, ny);
                        if (dd >= 0 && dd < minDGNext) minDGNext = dd;
                    }
                }

                if (hasDanger && minDGNext <= 2 && closestDangerGhost != null) {
                    return RunAway(pac, closestDangerGhost, m, wallColor);
                }
            }

            return dir;
        }

        // No safe food found -> run from closest danger ghost, else random
        if (closestDangerGhost != null) {
            return RunAway(pac, closestDangerGhost, m, wallColor);
        }
        return randomDir();
    }

    private static int nextMove(Pixel2D pos, Pixel2D target, Map2D map, int wallColor) {
        Pixel2D[] path = map.shortestPath(pos, target, wallColor);
        if (path == null || path.length < 2) return randomDir();
        Pixel2D next = path[1];

        int dx = next.getX() - pos.getX();
        int dy = next.getY() - pos.getY();

        if (dx < -1) return Game.RIGHT;
        if (dx > 1)  return Game.LEFT;
        if (dx == 1)  return Game.RIGHT;
        if (dx == -1) return Game.LEFT;

        if (dy < -1) return Game.DOWN;
        if (dy > 1)  return Game.UP;
        if (dy == 1)  return Game.UP;
        if (dy == -1) return Game.DOWN;

        return randomDir();
    }

    public static int RunAway (Pixel2D pos, Pixel2D ghostPos, Map2D map, int wallColor) {
        Map2D Dis = map.allDistance(ghostPos, wallColor);

        int bestDir = randomDir();
        int bestVal = -1;

        int[][] moves = {
                {0, 1, Game.UP},
                {0, -1, Game.DOWN},
                {1, 0, Game.RIGHT},
                {-1, 0, Game.LEFT}
        };

        int w = map.getWidth();
        int h = map.getHeight();

        for (int[] mv : moves) {
            int nx = pos.getX() + mv[0];
            int ny = pos.getY() + mv[1];

            if (map.isCyclic()) {
                nx = (nx + w) % w;
                ny = (ny + h) % h;
            } else {
                if (nx < 0 || nx >= w || ny < 0 || ny >= h) continue;
            }

            if (map.getPixel(nx, ny) == wallColor) continue;

            int val = Dis.getPixel(nx, ny);
            if (val > bestVal) {
                bestVal = val;
                bestDir = mv[2];
            }
        }
        return bestDir;
    }

    private static int randomDir() {
        int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
        int ind = (int)(Math.random()*dirs.length);
        return dirs[ind];
    }
}
