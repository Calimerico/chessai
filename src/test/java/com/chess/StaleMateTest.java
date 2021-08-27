package com.chess;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StaleMateTest {

    @Test
    void name() {
        Position position = PositionGenerator.fromFEN("3k4/3P4/3K4/8/8/8/8/8 b - - 0 1");
        Assertions.assertThat(position.getHeuristicFunction()).isEqualTo(0);
    }
}
