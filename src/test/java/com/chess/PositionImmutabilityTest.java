package com.chess;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionImmutabilityTest {

    @Test
    void test() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        Position startPosition = ((Position) position.deepCopy());
        //when
        position.newState(new Move(Square.C4, Square.A3, position));
        Assertions.assertThat(position.equals(startPosition));
    }
}
