package com.chess.movementrules;

import com.chess.*;

import java.util.HashSet;
import java.util.Set;

public class KingRuleMovement {


    public static Set<Square> getMovingSquares(Position position, Square currentSquare) {
        Set<Square> movingSquares = getAttackingSquares(position, currentSquare);
        //castle
        if (currentSquare == Square.E1) {
            if (position.isWhiteCanCastleKingSide()) {
                      movingSquares.add(Square.G1);
            }
            if (position.isWhiteCanCastleQueenSide()) {
                      movingSquares.add(Square.C1);
            }
        }
        if (currentSquare == Square.E8) {
            if (position.isBlackCanCastleKingSide()) {
                      movingSquares.add(Square.G8);
            }
            if (position.isBlackCanCastleQueenSide()) {
                      movingSquares.add(Square.C8);
            }
        }
        return movingSquares;

    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {

            Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> attackingSquares = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank));
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
