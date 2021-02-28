package com.chess.movementrules;

import com.chess.*;

import java.util.HashSet;
import java.util.Set;

public class KnightRuleMovement {


    public static Set<Square> getMovingSquares(Position position, Square currentSquare) {
        return getAttackingSquares(position, currentSquare);
    }


    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> attackingSquares = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank + 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank - 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank + 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank -2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 2, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 2, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 2, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 2, rank - 1));
        return attackingSquares;
    }

    private static void addEndingSquareIfAppropriate(Position position, Color myColor, Set<Square> legalMoves, Square endingSquare) {
        if (endingSquare != null) {
            Piece pieceOnEndingSquare = position.getPieces().get(endingSquare);
            if (pieceOnEndingSquare == null || pieceOnEndingSquare.getColor() != myColor) {
                legalMoves.add(endingSquare);
            }
        }
    }
}
