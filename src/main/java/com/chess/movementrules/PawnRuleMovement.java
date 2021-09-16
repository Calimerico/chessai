package com.chess.movementrules;

import com.chess.*;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PawnRuleMovement {


    public static Set<Move> getLegalMoves(Position position, Square currentSquare) {

        Set<Move> legalMoves = getAttackingSquares(position, currentSquare)
                .stream()
                .map(square -> new Move(
                        currentSquare,
                        square,
                        position
                ))
                .collect(Collectors.toSet());

        Color myColor = position.getPieceColorOnSquare(currentSquare);

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        //can I move forward by 1 square
        if (myColor == Color.WHITE) {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank + 1);
            if (endingSquare != null && position.isSquareEmpty(endingSquare)) {
                Move move = new Move(currentSquare, endingSquare, position);
                if (!CheckRuleMovement.isKingInCheckAfterMove(move)) {
                    legalMoves.add(move);
                }
            }
        } else {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank - 1);
            if (endingSquare != null && position.isSquareEmpty(endingSquare)) {
                Move move = new Move(currentSquare, endingSquare, position);
                if (!CheckRuleMovement.isKingInCheckAfterMove(move)) {
                    legalMoves.add(move);
                }
            }
        }

        //moving by 2 squares
        if (myColor == Color.WHITE && currentSquare.getRank() == 1) {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank + 2);
            if (endingSquare != null && position.isSquareEmpty(endingSquare) && position.isSquareEmpty(Square.calculateSquareFromCoordinates(endingSquare.getFile(), endingSquare.getRank() - 1))) {
                legalMoves.add(new Move(currentSquare, endingSquare, position));
            }
        }
        if (myColor == Color.BLACK && currentSquare.getRank() == 6) {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank - 2);
            if (endingSquare != null && position.isSquareEmpty(endingSquare) && position.isSquareEmpty(Square.calculateSquareFromCoordinates(endingSquare.getFile(), endingSquare.getRank() + 1))) {
                legalMoves.add(new Move(currentSquare, endingSquare, position));
            }
        }

        //en passant

        Move lastPlayedMove = position.getLastPlayedMove();
        if (lastPlayedMove != null) {
            Square lastPlayedMoveEndingSquare = lastPlayedMove.getEndingSquare();
            Square lastPlayedMoveStartingSquare = lastPlayedMove.getStartingSquare();
            int fileDiff = lastPlayedMoveEndingSquare.getFile() - currentSquare.getFile();
            if (
                    lastPlayedMoveEndingSquare.getRank() == currentSquare.getRank() &&
                    Math.abs(lastPlayedMoveEndingSquare.getRank() - lastPlayedMoveStartingSquare.getRank()) == 2 &&
                    Math.abs(fileDiff) == 1 &&
                    position.getPieceTypeOnSquare(lastPlayedMoveEndingSquare) == PieceType.PAWN
            ) {
                if (myColor == Color.WHITE) {
                    Move enPassant = new Move(currentSquare, Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile(), lastPlayedMoveEndingSquare.getRank() + 1), position, true, PieceType.QUEEN);
                    if (!CheckRuleMovement.isKingInCheckAfterMove(enPassant)) {
                        legalMoves.add(enPassant);
                    }
                } else {
                    Move enPassant = new Move(currentSquare, Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile(), lastPlayedMoveEndingSquare.getRank() - 1), position, true, PieceType.QUEEN);
                    if (!CheckRuleMovement.isKingInCheckAfterMove(enPassant)) {
                        legalMoves.add(enPassant);
                    }
                }
            }
        }
        Set<Move> underPromotes = new HashSet<>();
        legalMoves.forEach(move -> {
            if (myColor == Color.WHITE && move.getEndingSquare().getRank() == 7) {
                underPromotes.add(new Move(move.getStartingSquare(), move.getEndingSquare(),position, move.isEnPassant(), PieceType.KNIGHT));
                underPromotes.add(new Move(move.getStartingSquare(), move.getEndingSquare(),position, move.isEnPassant(), PieceType.ROOK));
                underPromotes.add(new Move(move.getStartingSquare(), move.getEndingSquare(),position, move.isEnPassant(), PieceType.BISHOP));
            }
            if (myColor == Color.BLACK && move.getEndingSquare().getRank() == 0) {
                underPromotes.add(new Move(move.getStartingSquare(), move.getEndingSquare(),position, move.isEnPassant(), PieceType.KNIGHT));
                underPromotes.add(new Move(move.getStartingSquare(), move.getEndingSquare(),position, move.isEnPassant(), PieceType.ROOK));
                underPromotes.add(new Move(move.getStartingSquare(), move.getEndingSquare(),position, move.isEnPassant(), PieceType.BISHOP));
            }
        });
        legalMoves.addAll(underPromotes);

        return legalMoves;
    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> attackingSquares = EnumSet.noneOf(Square.class);

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        if (myColor == Color.WHITE) {
            if (file + 1 < 8) {
                Square endingSquare = Square.calculateSquareFromCoordinates(file + 1, rank + 1);
                addCaptureIffPossible(position, myColor, attackingSquares, endingSquare);
            }

            if (file - 1 >= 0) {
                Square endingSquare = Square.calculateSquareFromCoordinates(file - 1, rank + 1);
                addCaptureIffPossible(position, myColor, attackingSquares, endingSquare);
            }
        } else {
            if (file + 1 < 8) {
                Square endingSquare = Square.calculateSquareFromCoordinates(file + 1, rank - 1);
                addCaptureIffPossible(position, myColor, attackingSquares, endingSquare);
            }

            if (file - 1 >= 0) {
                Square endingSquare = Square.calculateSquareFromCoordinates(file - 1, rank - 1);
                addCaptureIffPossible(position, myColor, attackingSquares, endingSquare);
            }
        }
        return attackingSquares;
    }

    private static void addCaptureIffPossible(Position position, Color myColor, Set<Square> attackingSquares, Square endingSquare) {
        Piece pieceToCapture = position.getPieces().get(endingSquare);
//        if (pieceToCapture != null && pieceToCapture.getColor() != myColor) {
            attackingSquares.add(endingSquare);
//        }
    }
}
