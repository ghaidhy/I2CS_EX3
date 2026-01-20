package assignments.Ex3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
    private int[][] _map;
    private boolean _cyclicFlag = true;
    private int w;
    private int h;
    private int v;
    private int[][] map;

    /**
     * Constructs a w*h 2D raster map with an init value v.
     *
     * @param w
     * @param h
     * @param v
     */
    public Map(int w, int h, int v) {
        init(w, h, v);
    }

    /**
     * Constructs a square map (size*size).
     *
     * @param size
     */
    public Map(int size) {
        this(size, size, 0);
    }

    /**
     * Constructs a map from a given 2D array.
     *
     * @param data
     */
    public Map(int[][] data) {
        init(data);
    }

    @Override
    public void init(int w, int h, int v) {
        this.w = w;
        this.h = h;
        map = new int[w][h];
        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Invalid width or height parameter");
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                this.map[i][j] = v;
            }
        }
    }

    @Override
    public void init(int[][] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Invalid array parameter");
        }
        this.w = arr.length;
        this.h = arr[0].length;
        this.map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                map[i][j] = arr[i][j];
            }
        }
    }

    @Override
    public int[][] getMap() {
        return map;
    }

    @Override
    /////// add your code below ///////
    public int getWidth() {
        return this.w;
    }

    @Override
    /////// add your code below ///////
    public int getHeight() {
        return this.h;
    }

    @Override
    /////// add your code below ///////
    public int getPixel(int x, int y) {
        int ans = -1;
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            ans = this.map[x][y];
            return ans;
        }
        return ans;
    }

    @Override
    /////// add your code below ///////
    public int getPixel(Pixel2D p) {
        return this.getPixel(p.getX(), p.getY());
    }

    @Override
    /////// add your code below ///////
    public void setPixel(int x, int y, int v) {
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            this.map[x][y] = v;
        }
    }

    @Override
    /////// add your code below ///////
    public void setPixel(Pixel2D p, int v) {
        if (p != null) {
            int x = p.getX();
            int y = p.getY();
            if (0 <= x && x < this.w && 0 <= y && y < this.h) {
                this.map[x][y] = v;
            }
        }
    }

    @Override
    /**
     * Fills this map with the new color (new_v) starting from p.
     * https://en.wikipedia.org/wiki/Flood_fill
     */
    public int fill(Pixel2D xy, int new_v) {
        int old = getPixel(xy.getX(), xy.getY());
        int count = 1;
        Queue<Pixel2D> q = new LinkedList<>();
        q.add(xy);
        setPixel(xy, new_v);
        while (!q.isEmpty()) {
            Pixel2D p = q.poll();
            int x = p.getX();
            int y = p.getY();

            int nextX = x + 1;
            int nextY = y;
            p = getCyclicPosition(nextX, nextY);
            nextX = p.getX();
            nextY = p.getY();
            if (nextX < this.w) {
                if (getPixel(nextX, nextY) == old) {
                    setPixel(nextX, nextY, new_v);
                    q.add(p);
                    count++;
                }
            }
            int nextX2 = x - 1, nextY2 = y;
            p = getCyclicPosition(nextX2, nextY2);
            nextX2 = p.getX();
            nextY2 = p.getY();
            if (0 <= nextX2 && nextX2 < this.w) {
                if (getPixel(nextX2, nextY2) == old) {
                    setPixel(nextX2, nextY2, new_v);
                    q.add(p);
                    count++;
                }
            }
            int nextX3 = x, nextY3 = y + 1;
            p = getCyclicPosition(nextX3, nextY3);
            nextX3 = p.getX();
            nextY3 = p.getY();
            if (nextY3 < this.h) {
                if (getPixel(nextX3, nextY3) == old) {
                    setPixel(nextX3, nextY3, new_v);
                    q.add(p);
                    count++;
                }
            }
            int nextX4 = x, nextY4 = y - 1;
            p = getCyclicPosition(nextX4, nextY4);
            nextX4 = p.getX();
            nextY4 = p.getY();
            if (0 <= nextY4 && nextY4 < this.h) { // FIX: كان < this.w
                if (getPixel(nextX4, nextY4) == old) {
                    setPixel(nextX4, nextY4, new_v);
                    q.add(p);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    /**
     * BFS like shortest the computation based on iterative raster implementation of BFS, see:
     * https://en.wikipedia.org/wiki/Breadth-first_search
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
        int width = map.length;
        int height = map[0].length;

        if (map[p1.getX()][p1.getY()] == obsColor || map[p2.getX()][p2.getY()] == obsColor) {
            return null;
        }
        if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
            return new Pixel2D[]{p1};
        }

        Queue<Pixel2D> queue = new LinkedList<>();
        Pixel2D[][] parents = new Pixel2D[width][height];
        queue.add(p1);
        parents[p1.getX()][p1.getY()] = p1;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!queue.isEmpty()) {
            Pixel2D current = queue.poll();

            if (current.getX() == p2.getX() && current.getY() == p2.getY()) {
                return constructPath(parents, p1, p2);
            }

            for (int i = 0; i < 4; i++) {
                int nextX = current.getX() + dx[i];
                int nextY = current.getY() + dy[i];

                if (isCyclic()) {
                    nextX = (nextX + width) % width;
                    nextY = (nextY + height) % height;
                } else {
                    if (nextX < 0 || nextX >= width || nextY < 0 || nextY >= height) continue;
                }

                if (parents[nextX][nextY] == null && map[nextX][nextY] != obsColor) {
                    parents[nextX][nextY] = current;
                    queue.add(new Index2D(nextX, nextY));
                }
            }
        }
        return null;
    }

    @Override
    /////// add your code below ///////
    public boolean isInside(Pixel2D p) {
        boolean ans = true;
        int x = p.getX();
        int y = p.getY();
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            return ans;
        } else {
            ans = false;
        }
        return ans;
    }

    @Override
    public boolean isCyclic() {
        return _cyclicFlag;
    }

    @Override
    public void setCyclic(boolean cy) {
        this._cyclicFlag = cy;
    }

    @Override
    /////// add your code below ///////
    public Map2D allDistance(Pixel2D start, int obsColor) {
        int w = this.getWidth();
        int h = this.getHeight();
        int[][] dists = new int[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                dists[i][j] = -1;
            }
        }

        if (start == null || this.getPixel(start.getX(), start.getY()) == obsColor) {
            return new Map(dists);
        }

        Queue<Pixel2D> q = new LinkedList<>();

        dists[start.getX()][start.getY()] = 0;
        q.add(start);

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!q.isEmpty()) {
            Pixel2D curr = q.poll();
            int currDist = dists[curr.getX()][curr.getY()];

            for (int[] d : dirs) {
                int nextX = curr.getX() + d[0];
                int nextY = curr.getY() + d[1];

                if (isCyclic()) {
                    nextX = (nextX + w) % w;
                    nextY = (nextY + h) % h;
                }
                if (nextX >= 0 && nextX < w && nextY >= 0 && nextY < h) {
                    if (this.getPixel(nextX, nextY) != obsColor && dists[nextX][nextY] == -1) {
                        dists[nextX][nextY] = currDist + 1;
                        q.add(new Index2D(nextX, nextY));
                    }
                }
            }
        }
        return new Map(dists);
    }

    private Pixel2D getCyclicPosition(int x, int y) {
        if (isCyclic()) {
            if (x >= getWidth()) x = 0;
            else if (x < 0) x = getWidth() - 1;

            if (y >= getHeight()) y = 0;
            else if (y < 0) y = getHeight() - 1;
        }
        return new Index2D(x, y);
    }

    private Pixel2D[] constructPath(Pixel2D[][] parents, Pixel2D start, Pixel2D target) {
        List<Pixel2D> path = new ArrayList<>();
        Pixel2D curr = target;

        while (curr != null) {
            path.add(0, curr);
            if (curr.getX() == start.getX() && curr.getY() == start.getY()) {
                return path.toArray(new Pixel2D[0]);
            }
            curr = parents[curr.getX()][curr.getY()];
        }
        return null;
    }

    public boolean sameDimensions(Map2D p) {
        boolean ans = true;
        if (p.getWidth() != w || p.getHeight() != h) {
            return false;
        }
        return ans;
    }

    public void addMap2D(Map2D p) {
        if (p != null) {
            if (this.sameDimensions(p)) {
                for (int i = 0; i < this.w; i++) {
                    for (int j = 0; j < this.h; j++) {
                        this.map[i][j] = this.map[i][j] + p.getPixel(i, j);
                    }
                }
            }
        }
    }
}
