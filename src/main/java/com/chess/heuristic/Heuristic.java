package com.chess.heuristic;

import com.chess.Move;
import com.chess.Position;

public interface Heuristic {
    double getHeuristic(Position position);
}
