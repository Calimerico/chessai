package com.chess.movementrules;

import com.chess.Move;
import com.chess.Position;
import com.chess.Square;

import java.util.HashSet;
import java.util.Set;

public class QueenRuleMovement {


    public static Set<Square> getMovingSquares(Position position, Square currentSquare) {
        return getAttackingSquares(position, currentSquare);
    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Set<Square> squares = new HashSet<>();
        Set<Square> bishop = BishopRuleMovement.getAttackingSquares(position, currentSquare);
        Set<Square> rook = RookRuleMovement.getAttackingSquares(position, currentSquare);
        squares.addAll(bishop);
        squares.addAll(rook);
        return squares;
    }
}
