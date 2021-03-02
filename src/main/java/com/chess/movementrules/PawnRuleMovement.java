package com.chess.movementrules;

import com.chess.*;

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
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(position, move))
                .collect(Collectors.toSet());

        Color myColor = position.getPieceColorOnSquare(currentSquare);

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        //can I move forward
        if (myColor == Color.WHITE) {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank + 1);
            if (endingSquare != null && position.isSquareEmpty(endingSquare)) {
                Move move = new Move(currentSquare, endingSquare, position);
                if (!CheckRuleMovement.isKingInCheckAfterMove(position, move)) {
                    legalMoves.add(move);
                }
            }
        } else {
            Square endingSquare = Square.calculateSquareFromCoordinates(file, rank - 1);
            if (endingSquare != null && position.isSquareEmpty(endingSquare)) {
                Move move = new Move(currentSquare, endingSquare, position);
                if (!CheckRuleMovement.isKingInCheckAfterMove(position, move)) {
                    legalMoves.add(move);
                }
            }
        }

        //en passant

        Move lastPlayedMove = position.getLastPlayedMove();
        if (lastPlayedMove != null) {
            Square lastPlayedMoveEndingSquare = lastPlayedMove.getEndingSquare();
            Square lastPlayedMoveStartingSquare = lastPlayedMove.getStartingSquare();
            if (lastPlayedMoveEndingSquare.getRank() == currentSquare.getRank() && position.getPieceTypeOnSquare(lastPlayedMoveEndingSquare) == PieceType.PAWN) {
                if (myColor == Color.WHITE) {
                    Square enPassantSquareRight = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() + 1, lastPlayedMoveEndingSquare.getRank() + 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareRight) {
                        Move move = new Move(currentSquare, enPassantSquareRight, position);
                        if (!CheckRuleMovement.isKingInCheckAfterMove(position, move)) {
                            legalMoves.add(move);
                        }
                    }
                    Square enPassantSquareLeft = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() - 1, lastPlayedMoveEndingSquare.getRank() + 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareLeft) {
                        Move move = new Move(currentSquare, enPassantSquareLeft, position);
                        if (!CheckRuleMovement.isKingInCheckAfterMove(position, move)) {
                            legalMoves.add(move);
                        }
                    }
                } else {
                    Square enPassantSquareRight = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() + 1, lastPlayedMoveEndingSquare.getRank() - 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareRight) {
                        Move move = new Move(currentSquare, enPassantSquareRight, position);
                        if (!CheckRuleMovement.isKingInCheckAfterMove(position, move)) {
                            legalMoves.add(move);
                        }
                    }
                    Square enPassantSquareLeft = Square.calculateSquareFromCoordinates(lastPlayedMoveEndingSquare.getFile() - 1, lastPlayedMoveEndingSquare.getRank() - 2);
                    if (lastPlayedMoveStartingSquare == enPassantSquareLeft) {
                        Move move = new Move(currentSquare, enPassantSquareLeft, position);
                        if (!CheckRuleMovement.isKingInCheckAfterMove(position, move)) {
                            legalMoves.add(move);
                        }
                    }
                }
            }
        }

        return legalMoves;
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
