package com.chess;

import com.ai.Action;
import com.ai.BreakTest;
import com.ai.MiniMax;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    @Test
    void promotion() {
        //given
        Position position = PositionGenerator.fromFEN("8/4k1P1/8/8/3p4/3K4/8/8 w - - 0 1");
        //when
        MiniMax miniMax = new MiniMax(new BreakTest(6));
        Action action = miniMax.search(position);
        //then
        Assertions.assertThat(((Move) action).getStartingSquare()).isEqualTo(Square.G7);
        Assertions.assertThat(((Move) action).getEndingSquare()).isEqualTo(Square.G8);
    }
}
