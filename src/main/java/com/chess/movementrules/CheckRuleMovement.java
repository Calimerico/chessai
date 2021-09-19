package com.chess.movementrules;

import com.chess.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckRuleMovement {

    public static boolean isKingInCheckAfterMove(Position position, Move move, Color color) {
        PerformanceMonitor.isKingInCheckStart();
        boolean isKingInCheckAfterMove;
        Position newPosition = position.newState(move);

        Piece piece = newPosition.getPieces().get(newPosition.getKingPosition(color));
        isKingInCheckAfterMove = newPosition.getAttackingSquaresByPlayer(color.opposite()).containsKey(piece.getSquare());

        //check castle check
        Castle castle = newPosition.getIsLastMoveCastle();
        if (castle == Castle.KING || castle == Castle.QUEEN) {
            if (newPosition.getPlayerToMove() == Color.BLACK) {
                Map<Square, Integer> blackAttacking = newPosition.getAttackingSquaresByPlayer(Color.BLACK);
                if (castle == Castle.KING) {
                    isKingInCheckAfterMove = blackAttacking.containsKey(Square.E1) || blackAttacking.containsKey(Square.F1) || blackAttacking.containsKey(Square.G1);
                } else {
                    isKingInCheckAfterMove = blackAttacking.containsKey(Square.E1) || blackAttacking.containsKey(Square.D1) || blackAttacking.containsKey(Square.C1);

                }
            } else {
                Map<Square, Integer> whiteAttacking = newPosition.getAttackingSquaresByPlayer(Color.WHITE);
                if (castle == Castle.KING) {
                    isKingInCheckAfterMove = whiteAttacking.containsKey(Square.E8) || whiteAttacking.containsKey(Square.F8) || whiteAttacking.containsKey(Square.G8);
                } else {
                    isKingInCheckAfterMove = whiteAttacking.containsKey(Square.E8) || whiteAttacking.containsKey(Square.D8) || whiteAttacking.containsKey(Square.C8);

                }
            }
        }
        PerformanceMonitor.isKingInCheckEnd();
        return isKingInCheckAfterMove;
    }

    public static boolean isKingInCheckAfterMove(Move move) {
        Position position = move.getPosition();
        return isKingInCheckAfterMove(position, move, position.getPlayerToMove());
    }
}
