package com.chess;

import com.ai.Action;
import com.chess.movementrules.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PositionLegalMovesService {

    public static Set<Move> getLegalMoves(Position position, Square square) {
        Piece piece = position.getPieceAtSquare(square);
        Set<Move> legalMoves = Collections.emptySet();
        if (piece != null) {

            switch (piece.getPieceType()) {
                case BISHOP:
                    legalMoves = BishopRuleMovement.getLegalMoves(position, square);
                    break;
                case ROOK:
                    legalMoves = RookRuleMovement.getLegalMoves(position, square);
                    break;
                case QUEEN:
                    legalMoves = QueenRuleMovement.getLegalMoves(position, square);
                    break;
                case KNIGHT:
                    legalMoves = KnightRuleMovement.getLegalMoves(position, square);
                    break;
                case PAWN:
                    legalMoves = PawnRuleMovement.getLegalMoves(position, square);
                    break;
                case KING:
                    legalMoves = KingRuleMovement.getLegalMoves(position, square);
                    break;
                default:
                    throw new RuntimeException("Piece not recognized!");
            }
        }
        return legalMoves
                .stream()
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(move))
                .collect(Collectors.toSet());
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
