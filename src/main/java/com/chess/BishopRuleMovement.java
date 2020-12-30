package com.chess;

import java.util.HashSet;
import java.util.Set;

public class BishopRuleMovement {

    public static Set<Square> getAttackingSquares(Square currentSquare, Position position) {
        Set<Square> legalMoves = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        for(int r = rank + 1, f = file + 1; r < 8 && f < 8; r++,f++) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            if (position.getPieces().get(endingSquare) != null) {
                break;
            } else {
                Move move = new Move(currentSquare, endingSquare);
//                if (!leavesKingInCheck(move,position)) {
                    legalMoves.add(endingSquare);
//                }
            }
        }

        for(int r = rank - 1, f = file - 1; r >= 0 && f >= 0; r--,f--) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            if (position.getPieces().get(endingSquare) != null) {
                break;
            } else {
                Move move = new Move(currentSquare, endingSquare);
//                if (!leavesKingInCheck(move,position)) {
                legalMoves.add(endingSquare);
//                }
            }
        }

        for(int r = rank - 1, f = file + 1; r >= 0 && f < 8; r--,f++) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            if (position.getPieces().get(endingSquare) != null) {
                break;
            } else {
                Move move = new Move(currentSquare, endingSquare);
//                if (!leavesKingInCheck(move,position)) {
                legalMoves.add(endingSquare);
//                }
            }
        }

        for(int r = rank + 1, f = file - 1; r < 8 && f >= 0; r++,f--) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            if (position.getPieces().get(endingSquare) != null) {
                break;
            } else {
                Move move = new Move(currentSquare, endingSquare);
//                if (!leavesKingInCheck(move,position)) {
                legalMoves.add(endingSquare);
//                }
            }
        }
        return legalMoves;
    }
    public static Set<Square> getLegalMoves(Square currentSquare, Position position) {
        getAttackingSquares(currentSquare, position).stream().filter(square -> );
    }
}
