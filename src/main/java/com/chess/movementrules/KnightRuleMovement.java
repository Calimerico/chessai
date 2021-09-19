package com.chess.movementrules;

import com.chess.*;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class KnightRuleMovement {

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
        Set<Square> attackingSquares = EnumSet.noneOf(Square.class);

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank + 2));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank - 2));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank + 2));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank - 2));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file - 2, rank + 1));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file - 2, rank - 1));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file + 2, rank + 1));
        addEndingSquareIfAppropriate(attackingSquares, Square.calculateSquareFromCoordinates(file + 2, rank - 1));
        return attackingSquares;
    }

    private static void addEndingSquareIfAppropriate(Set<Square> legalMoves, Square endingSquare) {
        if (endingSquare != null) {
            legalMoves.add(endingSquare);
        }
    }
}
