package com.chess.heuristic;

import com.chess.Move;
import com.chess.Position;
import com.chess.Square;
import com.chess.moveorder.MoveOrderService;

import java.util.Map;

public class AttCenterSquares implements Heuristic, MoveOrderService {
    @Override
    public double getHeuristic(Position position) {
        int sum = 0;
        for (Map.Entry<Square, Integer> squareIntegerEntry : position.getAttackingSquaresByPlayer(position.getPlayerToMove()).entrySet()) {
            if (squareIntegerEntry.getKey().isNarrowCenter()) {
                sum += squareIntegerEntry.getValue();
            }
        }
        return sum;
    }

    @Override
    public int getOrder(Move move) {
        return (int) (getHeuristic(move.getPosition()) * 300000);
    }
}
