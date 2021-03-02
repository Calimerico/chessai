package com.chess;

import com.chess.movementrules.KingRuleMovement;
import com.chess.movementrules.KnightRuleMovement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class KnightRuleMovementTest {

    @Test
    void name() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        Set<Move> actions = KnightRuleMovement.getLegalMoves(position, Square.C4);
        Assertions.assertThat(actions).contains(
                new Move(Square.C4, Square.A3, 0, false),
                new Move(Square.C4, Square.A5, 0, true),
                new Move(Square.C4, Square.B6, 0, false),
                new Move(Square.C4, Square.B2, 0, false),
                new Move(Square.C4, Square.D6, 0, true),
                new Move(Square.C4, Square.D2, 0, false),
                new Move(Square.C4, Square.E5, 0, false),
                new Move(Square.C4, Square.E3, 0, false)
        );
    }
}
