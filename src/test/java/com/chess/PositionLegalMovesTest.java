package com.chess;

import com.ai.Action;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

public class PositionLegalMovesTest {

    @Test
    void legalMovesTest() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        Set<Action> actions = position.getActions();
        //then

        //king moves
        Assertions.assertThat(actions).contains(
                new Move(Square.C2, Square.B1, 0, false),
                new Move(Square.C2, Square.C1, 0, false),
                new Move(Square.C2, Square.D1, 0, false),
                new Move(Square.C2, Square.B2, 0, false),
                new Move(Square.C2, Square.D2, 0, false),
                new Move(Square.C2, Square.B3, 0, false),
                new Move(Square.C2, Square.C3, 0, false),
                new Move(Square.C2, Square.D3, 0, false),
                new Move(Square.C4, Square.A3, 0, false),
                new Move(Square.C4, Square.A5, 0, true),
                new Move(Square.C4, Square.B6, 0, false),
                new Move(Square.C4, Square.B2, 0, false),
                new Move(Square.C4, Square.D6, 0, true),
                new Move(Square.C4, Square.D2, 0, false),
                new Move(Square.C4, Square.E5, 0, false),
                new Move(Square.C4, Square.E3, 0, false),
                new Move(Square.G7, Square.G8, 0, false)
        );

        Assertions.assertThat(actions.size()).isEqualTo(17);

    }
}
