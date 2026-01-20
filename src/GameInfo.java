package assignments.Ex3;

import exe.ex3.game.PacManAlgo;

/**
 * This class contains all the needed parameters for the Pacman game.
 * Make sure you update your details below!
 */
public class GameInfo {

	public static final String MY_ID = "214756645";
	public static final int CASE_SCENARIO = 4;
	public static final long RANDOM_SEED = 31;
	public static final boolean CYCLIC_MODE = true;
	public static final int DT = 200;
	public static final double RESOLUTION_NORM = 1.1;

	private static PacManAlgo _manualAlgo = new ManualAlgo();
	private static PacManAlgo _myAlgo = new Ex3Algo();


	public static final PacManAlgo ALGO = _myAlgo;


}
