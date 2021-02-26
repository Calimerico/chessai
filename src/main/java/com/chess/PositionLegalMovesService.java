package com.chess;

import com.chess.movementrules.*;

import java.util.Set;
import java.util.stream.Collectors;

public class PositionLegalMovesService {

    public static Set<Move> getLegalMoves(Position position, Square square) {
        Set<Square> possibleEndSquaresAfterMove;
        switch (position.getPieces().get(square).getPieceType()) {
            case BISHOP:
                possibleEndSquaresAfterMove = BishopRuleMovement.getAttackingSquares(position, square);
                break;
            case ROOK:
                possibleEndSquaresAfterMove = RookRuleMovement.getAttackingSquares(position, square);
                break;
            case QUEEN:
                possibleEndSquaresAfterMove = QueenRuleMovement.getAttackingSquares(position, square);
                break;
            case KNIGHT:
                possibleEndSquaresAfterMove = KnightRuleMovement.getAttackingSquares(position, square);
                break;
            case PAWN:
                possibleEndSquaresAfterMove = PawnRuleMovement.getAttackingSquares(position, square);
                break;
            case KING:
                possibleEndSquaresAfterMove = KingRuleMovement.getAttackingSquares(position, square);
                break;
            default:
                throw new RuntimeException("Piece not recognized!");
        }
        return possibleEndSquaresAfterMove.stream().map(endSquare -> new Move(square, endSquare))
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(position, move))
                .collect(Collectors.toSet());
    }
}
