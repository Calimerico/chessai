package com.chess.movementrules;

import com.chess.*;

import java.util.HashSet;
import java.util.Set;

public class RookRuleMovement {


    public static Set<Square> getMovingSquares(Position position, Square currentSquare) {
        return getAttackingSquares(position, currentSquare);
    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> legalMoves = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        for(int r = rank + 1; r < 8; r++) {
            if (checkEndingSquare(position, myColor, legalMoves, r, file)) break;
        }

        for(int r = rank - 1; r >= 0; r--) {
            if (checkEndingSquare(position, myColor, legalMoves, r, file)) break;
        }

        for(int f = file + 1; f < 8; f++) {
            if (checkEndingSquare(position, myColor, legalMoves, rank, f)) break;
        }

        for(int f = file - 1; f >= 0; f--) {
            if (checkEndingSquare(position, myColor, legalMoves, rank, f)) break;
        }

        return legalMoves;
    }


    private static boolean checkEndingSquare(Position position, Color myColor, Set<Square> legalMoves, int r, int f) {
        Square endingSquare = Square.calculateSquareFromCoordinates(f, r);

        Piece pieceOnEndingSquare = position.getPieces().get(endingSquare);
        if (pieceOnEndingSquare != null) {
            if (pieceOnEndingSquare.getColor() != myColor) {
                legalMoves.add(endingSquare);
            }
            return true;
        } else {
            legalMoves.add(endingSquare);
        }
        return false;
    }
}
