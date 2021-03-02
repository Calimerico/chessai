package com.chess;

import com.ai.Action;
import com.ai.BreakTest;
import com.ai.DummyAction;
import com.ai.MiniMax;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {


    @Test
    void name() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        MiniMax miniMax = new MiniMax(new BreakTest());
        Action action = miniMax.search(position);
        //then
        Assertions.assertThat(((Move) action).getStartingSquare()).isEqualTo(Square.C4);
        Assertions.assertThat(((Move) action).getEndingSquare()).isEqualTo(Square.D6);
        System.out.println("pera");
    }
}
