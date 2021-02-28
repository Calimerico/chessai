package com.chess;

import com.chess.movementrules.*;

import java.util.Set;
import java.util.stream.Collectors;

public class PositionLegalMovesService {

    public static Set<Move> getLegalMoves(Position position, Square square) {
        Set<Square> possibleEndSquaresAfterMove;
        switch (position.getPieces().get(square).getPieceType()) {
            case BISHOP:
                possibleEndSquaresAfterMove = BishopRuleMovement.getMovingSquares(position, square);
                break;
            case ROOK:
                possibleEndSquaresAfterMove = RookRuleMovement.getMovingSquares(position, square);
                break;
            case QUEEN:
                possibleEndSquaresAfterMove = QueenRuleMovement.getMovingSquares(position, square);
                break;
            case KNIGHT:
                possibleEndSquaresAfterMove = KnightRuleMovement.getMovingSquares(position, square);
                break;
            case PAWN:
                possibleEndSquaresAfterMove = PawnRuleMovement.getMovingSquares(position, square);
                break;
            case KING:
                possibleEndSquaresAfterMove = KingRuleMovement.getMovingSquares(position, square);
                break;
            default:
                throw new RuntimeException("Piece not recognized!");
        }
        return possibleEndSquaresAfterMove.stream().map(endSquare -> new Move(square, endSquare))
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(position, move))
                .collect(Collectors.toSet());
    }

    public static Set<Square> getAttackingSquares(Position position, Square square) {
        switch (position.getPieces().get(square).getPieceType()) {
            case BISHOP:
                return BishopRuleMovement.getAttackingSquares(position, square);
            case ROOK:
                return RookRuleMovement.getAttackingSquares(position, square);
            case QUEEN:
                return QueenRuleMovement.getAttackingSquares(position, square);
            case KNIGHT:
                return KnightRuleMovement.getAttackingSquares(position, square);
            case PAWN:
                return PawnRuleMovement.getAttackingSquares(position, square);
            case KING:
                return KingRuleMovement.getAttackingSquares(position, square);
            default:
                throw new RuntimeException("Piece not recognized!");
        }
    }
}
