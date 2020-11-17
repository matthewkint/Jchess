package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class Bishop extends Piece {

	private final static int[] CANDIDATE_MOVE_VETOR_COORDINATES = {-9, -7, 7, 9};
	
	public Bishop(final Alliance pieceAlliance, final int piecePosition) {
		super(piecePosition, pieceAlliance);
		
	}

	@Override
	public Collection<Move> calculatedLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		/* 
		 checks if piece is on valid tile
		 then applies offset and checks if it is valid tile
		 if enemy is on tile then it breaks out of loop 
		 */
		for(final int candidateCoordidateOffset: CANDIDATE_MOVE_VETOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordidateOffset) || 
						isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordidateOffset)) {
					break;
				}
				candidateDestinationCoordinate += candidateCoordidateOffset;
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					if(!candidateDestinationTile.isTileOccupied()){
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
			
		}
		
		return  Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
	}
	
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
	}
}
