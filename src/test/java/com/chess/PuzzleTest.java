package com.chess;

import com.ai.Action;
import com.ai.BreakTest;
import com.ai.MiniMax;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PuzzleTest {

    @Test
    void name() {
        //given
        Position position = PositionGenerator.fromFEN("1k6/1pp5/3r4/8/8/2N2R2/5PP1/6K1 w - - 0 1");
        //when
        MiniMax miniMax = new MiniMax(new BreakTest());
        Action action = miniMax.search(position);
        //then
        Assertions.assertThat(((Move) action).getStartingSquare()).isEqualTo(Square.F3);
        Assertions.assertThat(((Move) action).getEndingSquare()).isEqualTo(Square.F8);

        //when
        Position newPosition = position.newState(action).newState(new Move(Square.B8, Square.A7));
        Action action2 = miniMax.search(newPosition);
        //then
        Assertions.assertThat(((Move) action2).getStartingSquare()).isEqualTo(Square.C3);
        Assertions.assertThat(((Move) action2).getEndingSquare()).isEqualTo(Square.B5);
    }

    @Test
    void name2() {
        //given
        Position position = PositionGenerator.fromFEN("3R4/kpp5/8/8/8/2N5/5PP1/6K1 w - - 1 1");
        //when
        MiniMax miniMax = new MiniMax(new BreakTest());
        Action action = miniMax.search(position);
        //then
        System.out.println("pera");
    }
}
