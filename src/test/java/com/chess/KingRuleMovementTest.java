package com.chess;

import com.chess.movementrules.KingRuleMovement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class KingRuleMovementTest {

    @Test
    void name() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        Set<Move> actions = KingRuleMovement.getLegalMoves(position, Square.C2);
        Assertions.assertThat(actions).contains(
                new Move(Square.C2, Square.B1, 0, false),
                new Move(Square.C2, Square.C1, 0, false),
                new Move(Square.C2, Square.D1, 0, false),
                new Move(Square.C2, Square.B2, 0, false),
                new Move(Square.C2, Square.D2, 0, false),
                new Move(Square.C2, Square.B3, 0, false),
                new Move(Square.C2, Square.C3, 0, false),
                new Move(Square.C2, Square.D3, 0, false)
        );
    }
}
