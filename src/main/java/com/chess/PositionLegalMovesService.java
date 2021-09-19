package com.chess;

import com.chess.movementrules.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class PositionLegalMovesService {

    public static Set<Move> getLegalMoves(Position position, Square square) {
        Piece piece = position.getPieceAtSquare(square);
        Set<Move> quaziLegalMoves = Collections.emptySet();
        if (piece != null) {

            switch (piece.getPieceType()) {
                case BISHOP:
                    quaziLegalMoves = BishopRuleMovement.getQuaziLegalMoves(position, square);
                    break;
                case ROOK:
                    quaziLegalMoves = RookRuleMovement.getQuaziLegalMoves(position, square);
                    break;
                case QUEEN:
                    quaziLegalMoves = QueenRuleMovement.getQuaziLegalMoves(position, square);
                    break;
                case KNIGHT:
                    quaziLegalMoves = KnightRuleMovement.getQuaziLegalMoves(position, square);
                    break;
                case PAWN:
                    quaziLegalMoves = PawnRuleMovement.getQuaziLegalMoves(position, square);
                    break;
                case KING:
                    quaziLegalMoves = KingRuleMovement.getQuaziLegalMoves(position, square);
                    break;
                default:
                    throw new RuntimeException("Piece not recognized!");
            }
        }
        return quaziLegalMoves
                .stream()
                .filter(move -> {
                    Square endingSquare = move.getEndingSquare();
                    boolean notInCheckAfterMoveAndNotCaptureItsOwnPiece = !CheckRuleMovement.isKingInCheckAfterMove(move) &&
                            position.getPieceColorOnSquare(endingSquare) != position.getPlayerToMove();
                    if(position.getPieceTypeOnSquare(square) == PieceType.PAWN && endingSquare.getFile() != move.getStartingSquare().getFile()) {
                        Piece pieceAtEndingSquare = position.getPieceAtSquare(endingSquare);
                        return pieceAtEndingSquare != null && pieceAtEndingSquare.getColor() != position.getPlayerToMove() && notInCheckAfterMoveAndNotCaptureItsOwnPiece;
                    }

                    return notInCheckAfterMoveAndNotCaptureItsOwnPiece;
                })
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
                    Set<Square> attackingSquares5 = KingRuleMovement.getAttackingSquares(square);
                    PerformanceMonitor.getAttackingSquaresEnd();
                    return attackingSquares5;
                default:
                    throw new RuntimeException("Piece not recognized!");
            }
        }
        return Collections.emptySet();
    }
}
