package com.chess.engine.board;

public class BoardUtils {

	public static final boolean[] FIRST_COLUMN = null;
	public static final boolean[] SECOND_COLUMN = null;
	public static final boolean[] SEVENTH_COLUMN = null;
	public static final boolean[] EIGHT_COLUMN = null;
	


	private BoardUtils() {
		
		throw new RuntimeException("You cannot instaniate me!");
	}
	
	
	public static boolean isValidTileCoordinaet(int coordinate) {
		return coordinate >=0 && coordinate < 64;
		
	}
}