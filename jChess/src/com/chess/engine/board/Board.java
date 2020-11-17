package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class Board {
	
	
	private static List<Tile> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	
	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	
	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
		
		final List<Move> legalMoves= new ArrayList<>();
		
		for(final Piece piece: pieces) {
			legalMoves.addAll(piece.calculatedLegalMoves(this));
		}
		return Collections.unmodifiableList(legalMoves);
	}


	private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard2, final Alliance alliance) {
		
		final List<Piece> activePieces = new ArrayList<>();
		
		for(final Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
			}
		}
		return Collections.unmodifiableList(activePieces);
	}


	public Tile getTile(final int tilecoordinate) {
		return gameBoard.get(tilecoordinate);
	}
	
	private static List<Tile> createGameBoard(final Builder builder){
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for(int i =0; i< BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i,builder.boardConfig.get(i));
		}
		return Collections.unmodifiableList(Arrays.asList(tiles));
	}
	
	
	public static Board createStandardBoard() {
		final Builder builder = new Builder();
		//Black layout
		builder.setPiece(new Rook(Alliance.BLACK, 0));
		builder.setPiece(new Knight(Alliance.BLACK, 1));
		builder.setPiece(new Bishop(Alliance.BLACK, 2));
		builder.setPiece(new Queen(Alliance.BLACK, 3));
		builder.setPiece(new King(Alliance.BLACK, 4));
		builder.setPiece(new Bishop(Alliance.BLACK, 5));
		builder.setPiece(new Knight(Alliance.BLACK, 6));
		builder.setPiece(new Rook(Alliance.BLACK, 7));
		builder.setPiece(new Pawn(Alliance.BLACK, 8));
		builder.setPiece(new Pawn(Alliance.BLACK, 9));
		builder.setPiece(new Pawn(Alliance.BLACK, 10));
		builder.setPiece(new Pawn(Alliance.BLACK, 11));
		builder.setPiece(new Pawn(Alliance.BLACK, 12));
		builder.setPiece(new Pawn(Alliance.BLACK, 13));
		builder.setPiece(new Pawn(Alliance.BLACK, 14));
		builder.setPiece(new Pawn(Alliance.BLACK, 15));
		
		//White layout
		builder.setPiece(new Rook(Alliance.WHITE, 48));
		builder.setPiece(new Knight(Alliance.WHITE, 49));
		builder.setPiece(new Bishop(Alliance.WHITE, 50));
		builder.setPiece(new Queen(Alliance.WHITE, 51));
		builder.setPiece(new King(Alliance.WHITE, 52));
		builder.setPiece(new Bishop(Alliance.WHITE, 53));
		builder.setPiece(new Knight(Alliance.WHITE, 54));
		builder.setPiece(new Rook(Alliance.WHITE, 55));
		builder.setPiece(new Pawn(Alliance.WHITE, 56));
		builder.setPiece(new Pawn(Alliance.WHITE, 57));
		builder.setPiece(new Pawn(Alliance.WHITE, 58));
		builder.setPiece(new Pawn(Alliance.WHITE, 59));
		builder.setPiece(new Pawn(Alliance.WHITE, 60));
		builder.setPiece(new Pawn(Alliance.WHITE, 61));
		builder.setPiece(new Pawn(Alliance.WHITE, 62));
		builder.setPiece(new Pawn(Alliance.WHITE, 63));
		
		//white to move
		builder.setMoveMaker(Alliance.WHITE);
		
		return builder.build();
	}
	
	
	public static class Builder{
		
		Map<Integer, Piece> boardConfig;
		Alliance nextMoveMaker;
		
		public Builder() {
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker(final Alliance nextMoverMaker) {
			this.nextMoveMaker = nextMoverMaker;
			return this;
		}
		
		
		public Board build() {
			return new Board(this);
		}
		
	}
	
}
