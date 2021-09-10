package com.chess.heuristic;

import com.chess.Move;
import com.chess.Position;
import com.chess.moveorder.*;

import java.util.Arrays;
import java.util.List;

public class HeuristicManager {
    private final List<Heuristic> heuristics = Arrays.asList(
            new StaticValue()
    );

    public double getHeuristic(Position position) {
        return heuristics.stream().mapToDouble(heuristic -> heuristic.getHeuristic(position)).sum();
    }
}
