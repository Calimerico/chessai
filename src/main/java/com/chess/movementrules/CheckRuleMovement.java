package com.chess.movementrules;

import com.chess.*;

import java.util.Arrays;
import java.util.List;

public class CheckRuleMovement {

    public static boolean isKingInCheckAfterMove(Position position, Move move, Color color) {
        PerformanceMonitor.isKingInCheckStart();
        boolean isKingInCheckAfterMove;
        Position newPosition = position.newState(move);

        Piece piece = newPosition.getPieces().get(newPosition.getKingPosition(color));
        isKingInCheckAfterMove = newPosition.getAttackingSquaresByPlayer(color.opposite()).contains(piece.getSquare());

        //check castle check
        Castle castle = newPosition.getIsLastMoveCastle();
        if (castle == Castle.KING || castle == Castle.QUEEN) {
            if (newPosition.getPlayerToMove() == Color.BLACK) {
                List<Square> blackAttacking = newPosition.getAttackingSquaresByPlayer(Color.BLACK);
                if (castle == Castle.KING) {
                    isKingInCheckAfterMove = blackAttacking.contains(Square.E1) || blackAttacking.contains(Square.F1) || blackAttacking.contains(Square.G1);
                } else {
                    isKingInCheckAfterMove = blackAttacking.contains(Square.E1) || blackAttacking.contains(Square.D1) || blackAttacking.contains(Square.C1);

                }
            } else {
                List<Square> whiteAttacking = newPosition.getAttackingSquaresByPlayer(Color.WHITE);
                if (castle == Castle.KING) {
                    isKingInCheckAfterMove = whiteAttacking.contains(Square.E8) || whiteAttacking.contains(Square.F8) || whiteAttacking.contains(Square.G8);
                } else {
                    isKingInCheckAfterMove = whiteAttacking.contains(Square.E8) || whiteAttacking.contains(Square.D8) || whiteAttacking.contains(Square.C8);

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
