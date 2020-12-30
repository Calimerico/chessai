package com.chess;

import java.util.Set;

public class RuleMovement {
    public static Set<Square> getLegalMoves(Position position, Square square) {
        switch (position.getPieces().get(square).getPieceType()) {
            case BISHOP:
                return BishopRuleMovement.getLegalMoves(square,position);//todo finish this switch
            default:
                throw new RuntimeException("Piece not recognized!");
        }
    }
}
