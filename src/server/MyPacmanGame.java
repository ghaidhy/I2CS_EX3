package assignments.Ex3.server;

import exe.ex3.game.GhostCL;
import exe.ex3.game.PacmanGame;

public class MyPacmanGame implements PacmanGame {

    // status
    private int status = INIT;

    // board codes expected by your Ex3Algo:
    // wallColor=1, pinkFood=3, greenFood=5
    private static final int EMPTY = 0;
    private static final int WALL  = 1;
    private static final int PINK  = 3;
    private static final int GREEN = 5;

    // board size
    private int W = 25, H = 25;

    // pacman position in grid coordinates (INT!)
    private int px = 1, py = 1;

    // board
    private int[][] board;

    // food remaining
    private int foodLeft = 0;

    // cyclic behavior
    private boolean cyclic = false;

    // ---- helpers for GUI / ServerMain ----
    public int getPacX() { return px; }
    public int getPacY() { return py; }

    // ---------------- PacmanGame API ----------------

    @Override
    public Character getKeyChar() {
        return null;
    }

    @Override
    public String getPos(int id) {
        // IMPORTANT: your Ex3Algo uses Integer.parseInt => must be "x,y" ints
        return px + "," + py;
    }

    @Override
    public GhostCL[] getGhosts(int id) {
        // Minimal server: no ghosts for now (still valid and stable)
        return new GhostCL[0];
    }

    @Override
    public int[][] getGame(int id) {
        return board;
    }

    @Override
    public String move(int dir) {
        if (status != PLAY) return getPos(0);

        int nx = px, ny = py;

        if (dir == LEFT)  nx--;
        if (dir == RIGHT) nx++;
        if (dir == UP)    ny++;
        if (dir == DOWN)  ny--;

        if (cyclic) {
            nx = (nx + W) % W;
            ny = (ny + H) % H;
        }

        // legal move: inside bounds + not wall
        if (nx >= 0 && nx < W && ny >= 0 && ny < H && board[ny][nx] != WALL) {
            px = nx; py = ny;

            // eat food
            if (board[py][px] == PINK || board[py][px] == GREEN) {
                board[py][px] = EMPTY;
                foodLeft--;
                if (foodLeft <= 0) status = DONE;
            }
        }

        return getPos(0);
    }

    @Override
    public void play() {
        if (status == INIT || status == PAUSE) status = PLAY;
    }

    @Override
    public String end(int id) {
        status = DONE;
        return "DONE";
    }

    @Override
    public String getData(int id) {
        return "foodLeft=" + foodLeft + ", status=" + status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public boolean isCyclic() {
        return cyclic;
    }

    @Override
    public String init(int id, String map, boolean cyclic, long time, double speed, int rows, int cols) {
        this.cyclic = cyclic;
        if (rows > 5) H = rows;
        if (cols > 5) W = cols;

        buildSimpleBoard();
        status = INIT;
        return "INIT";
    }

    // ---------------- helpers ----------------

    private void buildSimpleBoard() {
        board = new int[H][W];
        foodLeft = 0;

        // border walls, inside = pink
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (x == 0 || y == 0 || x == W - 1 || y == H - 1) {
                    board[y][x] = WALL;
                } else {
                    board[y][x] = PINK;
                    foodLeft++;
                }
            }
        }

        // simple internal wall line
        int midY = H / 2;
        for (int x = 2; x < W - 2; x++) {
            if (x % 4 == 0) {
                if (board[midY][x] == PINK) foodLeft--;
                board[midY][x] = WALL;
            }
        }

        // green foods (power)
        putGreen(2, 2);
        putGreen(W - 3, 2);
        putGreen(2, H - 3);
        putGreen(W - 3, H - 3);

        // spawn pacman
        px = 1; py = 1;
        if (board[py][px] == WALL) { px = 2; py = 2; }

        // if spawn on food, eat it
        if (board[py][px] == PINK || board[py][px] == GREEN) {
            board[py][px] = EMPTY;
            foodLeft--;
        }
    }

    private void putGreen(int x, int y) {
        if (x <= 0 || y <= 0 || x >= W - 1 || y >= H - 1) return;
        if (board[y][x] == WALL) return;
        if (board[y][x] == PINK) {
            board[y][x] = GREEN; // still counts as food
        } else if (board[y][x] == EMPTY) {
            board[y][x] = GREEN;
            foodLeft++;
        }
    }
}
