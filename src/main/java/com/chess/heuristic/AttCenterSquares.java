package com.chess.heuristic;

import com.chess.Color;
import com.chess.Move;
import com.chess.Position;
import com.chess.Square;
import com.chess.moveorder.MoveOrderService;

import java.util.Map;

public class AttCenterSquares implements Heuristic, MoveOrderService {
    @Override
    public double getHeuristic(Position position) {
        double sum = 0;
        for (Map.Entry<Square, Integer> squareIntegerEntry : position.getAttackingSquaresByPlayer(Color.WHITE).entrySet()) {
            if (squareIntegerEntry.getKey().isNarrowCenter()) {
                sum += squareIntegerEntry.getValue() * 0.1;
            }
        }
        for (Map.Entry<Square, Integer> squareIntegerEntry : position.getAttackingSquaresByPlayer(Color.BLACK).entrySet()) {
            if (squareIntegerEntry.getKey().isNarrowCenter()) {
                sum -= squareIntegerEntry.getValue() * 0.1;
            }
        }
        return sum;
    }

    @Override
    public double getOrder(Move move) {
        return getHeuristic(move.getPosition()) * 300000;
    }
}
