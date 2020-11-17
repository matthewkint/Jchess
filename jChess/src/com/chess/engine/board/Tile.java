package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;

/*
Declares if there is a piece on the tile or if it is empty
takes a tile coordinate
methods if tile is occupied or not, retrieves piece on tile
*/

public abstract class Tile {
	
	
	//immutable
	protected final int tileCoordinate;
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossbileEmptyTiles();
	
	private static Map<Integer, EmptyTile> createAllPossbileEmptyTiles() {
		
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i=0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i,  new EmptyTile(i));
		}
		
		return Collections.unmodifiableMap(emptyTileMap);
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}  
	
	private Tile(final int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	// Tile is empty 
	public static final class EmptyTile extends Tile{
		
		private EmptyTile(final int coordinate){
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		@Override
		public boolean isTileOccupied(){
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	// tile is occupied
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(int tileCoordiante, final Piece pieceOnTile) {
			super(tileCoordiante);
			this.pieceOnTile = pieceOnTile;
		}
		
		
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
				getPiece().toString();
		}
		
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
	}
}
