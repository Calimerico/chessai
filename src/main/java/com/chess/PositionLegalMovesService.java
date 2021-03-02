package com.chess;

import com.chess.movementrules.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class PositionLegalMovesService {

    public static Set<Move> getLegalMoves(Position position, Square square) {
        Piece piece = position.getPieceAtSquare(square);
        if (piece != null) {

            switch (piece.getPieceType()) {
                case BISHOP:
                    return BishopRuleMovement.getLegalMoves(position, square);
                case ROOK:
                    return RookRuleMovement.getLegalMoves(position, square);
                case QUEEN:
                    return QueenRuleMovement.getLegalMoves(position, square);
                case KNIGHT:
                    return KnightRuleMovement.getLegalMoves(position, square);
                case PAWN:
                    return PawnRuleMovement.getLegalMoves(position, square);
                case KING:
                    return KingRuleMovement.getLegalMoves(position, square);
                default:
                    throw new RuntimeException("Piece not recognized!");
            }
        }
        return Collections.emptySet();
    }

    public static Set<Square> getAttackingSquares(Position position, Square square) {
        Piece piece = position.getPieceAtSquare(square);
        if (piece != null) {
            switch (piece.getPieceType()) {
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
        return Collections.emptySet();
    }
}
