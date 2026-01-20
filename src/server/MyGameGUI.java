package assignments.Ex3.server;

import exe.ex3.game.StdDraw;

public class MyGameGUI {

    // expected codes
    private static final int WALL  = 1;
    private static final int PINK  = 3;
    private static final int GREEN = 5;

    public void draw(int[][] board, int pacX, int pacY) {
        StdDraw.clear(0);

        if (board == null || board.length == 0 || board[0].length == 0) {
            StdDraw.show(20);
            return;
        }

        int h = board.length;
        int w = board[0].length;

        // draw cells using ONLY circle(...) to avoid missing methods
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int v = board[y][x];

                double sx = (x + 0.5) / w;
                double sy = (y + 0.5) / h;

                if (v == WALL) {
                    StdDraw.setPenColor(1);
                    StdDraw.circle(sx, sy, 0.45 / w, 1); // big filled
                } else if (v == PINK) {
                    StdDraw.setPenColor(6);
                    StdDraw.circle(sx, sy, 0.16 / w, 1);
                } else if (v == GREEN) {
                    StdDraw.setPenColor(2);
                    StdDraw.circle(sx, sy, 0.22 / w, 1);
                }
            }
        }

        // draw pacman
        double px = (pacX + 0.5) / w;
        double py = (pacY + 0.5) / h;
        StdDraw.setPenColor(3);
        StdDraw.circle(px, py, 0.33 / w, 1);

        StdDraw.show(20);
    }
}
