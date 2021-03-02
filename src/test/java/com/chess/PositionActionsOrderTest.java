package com.chess;

import com.ai.Action;
import com.chess.movementrules.KingRuleMovement;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

public class PositionActionsOrderTest {

    @Test
    void tryCaptureFirstThenCheckThenOtherMoves() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        TreeSet<Action> actions = (TreeSet<Action>) position.getActions();
        //then
        AssertionsForClassTypes.assertThat(actions.headSet(new Move(Square.C4, Square.D6, 0, true)));
    }
}
