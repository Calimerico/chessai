package com.chess;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsufficientMaterialTest {

    @Test
    void name() {
        Position position = PositionGenerator.fromFEN("8/2k5/8/8/8/8/3K2B1/8 w - - 0 1");
        Assertions.assertThat(position.getHeuristicFunction()).isEqualTo(0);
    }
}
