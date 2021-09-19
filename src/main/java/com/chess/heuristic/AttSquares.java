package com.chess.heuristic;

import com.chess.Move;
import com.chess.Position;
import com.chess.moveorder.MoveOrderService;

import java.util.Collection;

public class AttSquares implements Heuristic, MoveOrderService {
    @Override
    public double getHeuristic(Position position) {
        int sum = 0;
        Collection<Integer> values = position.getAttackingSquaresByPlayer(position.getPlayerToMove()).values();
        for(Integer v : values) {
            sum += v;
        }
        return sum;
    }

    @Override
    public int getOrder(Move move) {
        return (int) (getHeuristic(move.getPosition()) * 300000);
    }
}
