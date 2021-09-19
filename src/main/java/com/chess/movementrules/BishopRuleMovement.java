package com.chess.movementrules;

import com.chess.*;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BishopRuleMovement {

    public static Set<Move> getQuaziLegalMoves(Position position, Square currentSquare) {
        return getAttackingSquares(position, currentSquare)
                .stream()
                .map(square -> new Move(
                        currentSquare,
                        square,
                        position
                ))
                .collect(Collectors.toSet());
    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Set<Square> legalMoves = EnumSet.noneOf(Square.class);

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        for(int r = rank + 1, f = file + 1; r < 8 && f < 8; r++,f++) {
            if (checkEndingSquare(position, legalMoves, r, f)) break;
        }

        for(int r = rank - 1, f = file - 1; r >= 0 && f >= 0; r--,f--) {
            if (checkEndingSquare(position, legalMoves, r, f)) break;
        }

        for(int r = rank - 1, f = file + 1; r >= 0 && f < 8; r--,f++) {
            if (checkEndingSquare(position, legalMoves, r, f)) break;
        }

        for(int r = rank + 1, f = file - 1; r < 8 && f >= 0; r++,f--) {
            if (checkEndingSquare(position, legalMoves, r, f)) break;
        }
        return legalMoves;
    }

    private static boolean checkEndingSquare(Position position,Set<Square> legalMoves, int r, int f) {
        Square endingSquare = Square.calculateSquareFromCoordinates(f, r);
        Piece pieceOnEndingSquare = position.getPieceAtSquare(endingSquare);
        legalMoves.add(endingSquare);
        return pieceOnEndingSquare != null;
    }
}
