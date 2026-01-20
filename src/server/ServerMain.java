package assignments.Ex3.server;

import assignments.Ex3.Ex3Algo;

public class ServerMain {
    public static void main(String[] args) throws Exception {

        MyPacmanGame game = new MyPacmanGame();
        MyGameGUI gui = new MyGameGUI();
        Ex3Algo algo = new Ex3Algo();

        // init + play
        game.init(0, "", false, 0L, 0.02, 25, 25);
        game.play();

        while (true) {
            int dir = algo.move(game);   // Algo decides
            game.move(dir);              // server applies
            gui.draw(game.getGame(0), game.getPacX(), game.getPacY()); // draw

            Thread.sleep(30);
        }
    }
}
