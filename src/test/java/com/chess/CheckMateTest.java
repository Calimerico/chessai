package com.chess;

import com.ai.Action;
import com.ai.BreakTest;
import com.ai.MiniMax;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckMateTest {

    @Test
    void name() {
        Position position = PositionGenerator.fromFEN("2R3k1/5ppp/6qq/7q/8/8/5PPP/6K1 b - - 0 1");
        Assertions.assertThat(position.getHeuristicFunction()).isLessThan(-200);
    }
}
