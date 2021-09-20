package com.chess.heuristic;

import com.chess.Color;
import com.chess.Move;
import com.chess.Position;
import com.chess.moveorder.MoveOrderService;

import java.util.Collection;

public class AttSquares implements Heuristic, MoveOrderService {
    @Override
    public double getHeuristic(Position position) {
        double sum = 0;
        Collection<Integer> values = position.getAttackingSquaresByPlayer(Color.WHITE).values();
        int sumWhite = values.stream().mapToInt(integer -> integer).sum();
        Collection<Integer> values1 = position.getAttackingSquaresByPlayer(Color.BLACK).values();
        int sumBlack = values1.stream().mapToInt(integer -> integer).sum();
        sum += sumWhite * 0.02;
        sum -= sumBlack * 0.02;
        return sum;
    }

    @Override
    public double getOrder(Move move) {
        return getHeuristic(move.getPosition()) * 300000;
    }
}
