package com.chess.movementrules;

import com.chess.Move;
import com.chess.Position;
import com.chess.Square;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class QueenRuleMovement {

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
        Set<Square> squares = EnumSet.noneOf(Square.class);
        Set<Square> bishop = BishopRuleMovement.getAttackingSquares(position, currentSquare);
        Set<Square> rook = RookRuleMovement.getAttackingSquares(position, currentSquare);
        squares.addAll(bishop);
        squares.addAll(rook);
        return squares;
    }
}
