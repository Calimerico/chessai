package com.chess;

import com.ai.Action;
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
        PerformanceMonitor.getAttackingSquaresStart();
        Piece piece = position.getPieceAtSquare(square);
        if (piece != null) {
            switch (piece.getPieceType()) {
                case BISHOP:
                    Set<Square> attackingSquares = BishopRuleMovement.getAttackingSquares(position, square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares;
                case ROOK:
                    Set<Square> attackingSquares1 = RookRuleMovement.getAttackingSquares(position, square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares1;
                case QUEEN:
                    Set<Square> attackingSquares2 = QueenRuleMovement.getAttackingSquares(position, square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares2;
                case KNIGHT:
                    Set<Square> attackingSquares3 = KnightRuleMovement.getAttackingSquares(position, square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares3;
                case PAWN:
                    Set<Square> attackingSquares4 = PawnRuleMovement.getAttackingSquares(position, square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares4;
                case KING:
                    Set<Square> attackingSquares5 = KingRuleMovement.getAttackingSquares(position, square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares5;
                default:
                    throw new RuntimeException("Piece not recognized!");
            }
        }
        return Collections.emptySet();
    }
}
