package com.chess.movementrules;

import com.chess.Color;
import com.chess.Move;
import com.chess.Position;
import com.chess.Square;

import java.util.HashSet;
import java.util.Set;

public class RookRuleMovement {

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> legalMoves = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();
    }

    public static Set<Move> getLegalMoves(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> legalMoves = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();
    }
}
