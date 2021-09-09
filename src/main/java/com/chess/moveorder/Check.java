package com.chess.moveorder;

import com.chess.Move;
import com.chess.Position;
import com.chess.movementrules.CheckRuleMovement;

public class Check implements MoveOrderService {

    @Override
    public int getOrder(Move move) {
        Position position = move.getPosition();
        return CheckRuleMovement.isKingInCheckAfterMove(position, move, position.getPlayerToMove().opposite()) ? 50000000 : 0;
    }
}
