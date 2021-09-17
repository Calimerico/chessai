package com.chess;

import com.ai.BreakTest;
import com.ai.MiniMaxState;

public class ChessBreakTest implements BreakTest {

    private final int depth;

    public ChessBreakTest(int depth) {
        this.depth = depth;
    }

    @Override
    public boolean shouldEvaluate(MiniMaxState state, int depth) {
        Position position = (Position) state;
        if (depth == 0 || position.getActionsSize() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int getDepth() {
        return depth;
    }
}
