package com.chess.heuristic;

import com.chess.Position;
import java.util.Arrays;
import java.util.List;

public class HeuristicManager {
    private final List<Heuristic> heuristics = Arrays.asList(
            new StaticValue(),
            new AttCenterSquares(),
            new AttSquares()
    );

    public double getHeuristic(Position position) {
        return heuristics.stream().mapToDouble(heuristic -> heuristic.getHeuristic(position)).sum();
    }
}
