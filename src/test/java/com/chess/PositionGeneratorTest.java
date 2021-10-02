package com.chess;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionGeneratorTest {

    @Test
    void name() {
        Position position = PositionGenerator.fromFEN("r1b1kbnr/pp4pp/n1pqpp2/3p4/P4P1P/1P1P2P1/2PQP3/RNB1KBNR");
        Assertions.assertThat(position).isNotNull();
    }
}
