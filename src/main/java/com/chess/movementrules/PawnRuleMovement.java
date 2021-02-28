package com.chess.movementrules;

import com.chess.*;

import java.util.HashSet;
import java.util.Set;

public class PawnRuleMovement {


    public static Set<Square> getMovingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        Set<Square> movingSquares = new HashSet<>(getAttackingSquares(position, currentSquare));

        //can I move forward
        if (myColor == Color.WHITE) {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank + 1);
            if (position.getPieces().get(endingSquare) != null) {
                movingSquares.add(endingSquare);
            }
        } else {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank - 1);
            if (position.getPieces().get(endingSquare) != null) {
                movingSquares.add(endingSquare);
            }
        }

        //en passant

        Move lastPlayedMove = position.getLastPlayedMove();
        if (lastPlayedMove != null) {
            Square lastPlayedMoveEndingSquare = lastPlayedMove.getEndingSquare();
            Square lastPlayedMoveStartingSquare = lastPlayedMove.getStartingSquare();
            if (lastPlayedMoveEndingSquare.getRank() == currentSquare.getRank() && position.getPieces().get(lastPlayedMoveEndingSquare).getPieceType() == PieceType.PAWN) {
                if (myColor == Color.WHITE) {
                    Square enPassantSquareRight = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() + 1, lastPlayedMoveEndingSquare.getRank() + 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareRight) {
                        movingSquares.add(enPassantSquareRight);
                    }
                    Square enPassantSquareLeft = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() - 1, lastPlayedMoveEndingSquare.getRank() + 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareLeft) {
                        movingSquares.add(enPassantSquareLeft);
                    }
                } else {
                    Square enPassantSquareRight = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() + 1, lastPlayedMoveEndingSquare.getRank() - 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareRight) {
                        movingSquares.add(enPassantSquareRight);
                    }
                    Square enPassantSquareLeft = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() - 1, lastPlayedMoveEndingSquare.getRank() - 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareLeft) {
                        movingSquares.add(enPassantSquareLeft);
                    }
                }
            }
        }

        return movingSquares;
    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> attackingSquares = new HashSet<>();

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
        if (pieceToCapture != null && pieceToCapture.getColor() != myColor) {
            attackingSquares.add(endingSquare);
        }
    }
}
