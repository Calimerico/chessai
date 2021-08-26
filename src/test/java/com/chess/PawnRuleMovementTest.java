package com.chess;

import com.chess.movementrules.KingRuleMovement;
import com.chess.movementrules.PawnRuleMovement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PawnRuleMovementTest {

    @Test
    void name() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        Set<Move> actions = PawnRuleMovement.getLegalMoves(position, Square.G7);
        Assertions.assertThat(actions).contains(
                new Move(Square.G7, Square.G8, 0, false)
        );
    }

    @Test
    void promotionWorks() {
        //given
        Position position = PositionGenerator.fromFEN("8/1k4P1/8/8/8/3K4/8/8 w - - 0 1");
        //when
        Position newPosition = position.newState(new Move(Square.G7, Square.G8));
        Assertions.assertThat(newPosition.getHeuristicFunction()).isEqualTo(9);
    }
}
