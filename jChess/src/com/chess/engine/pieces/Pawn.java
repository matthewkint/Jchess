package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;

public class Pawn extends Piece {

	private final static int[] CANDIDTAE_MOVE_COORDIATE = {8, 16, 7, 9};
	
	public Pawn(final Alliance pieceAlliance, final int piecePosition) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculatedLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset: CANDIDTAE_MOVE_COORDIATE) {
			final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
			if(! BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				//TODO switch to pawn move
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				
			} else if(currentCandidateOffset == 16 && this.isFirstMove && 
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) || 
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())) {
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()* 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				}
				/*
				 * if on 7th column and white cannot attack off of board
				 * if on 1st column and black cannot attack off of board
				 */
			}else if(currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
					 (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) ))  {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//TODO new move attack
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
				
				
			}else if(currentCandidateOffset == 9 &&
					!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
					(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) )) { 
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//TODO new move attack
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}

}
