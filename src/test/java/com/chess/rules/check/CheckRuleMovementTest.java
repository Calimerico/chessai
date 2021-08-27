package com.chess.rules.check;

import com.chess.Move;
import com.chess.Position;
import com.chess.PositionGenerator;
import com.chess.Square;
import com.chess.movementrules.CheckRuleMovement;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

public class CheckRuleMovementTest {

    @Test
    void noCheck() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        boolean kingInCheckAfterMove = CheckRuleMovement.isKingInCheckAfterMove(position, new Move(Square.C2, Square.B3,0, false), position.getPlayerToMove());
        //then
        AssertionsForClassTypes.assertThat(kingInCheckAfterMove).isFalse();
    }

    @Test
    void noCheck1() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        boolean kingInCheckAfterMove = CheckRuleMovement.isKingInCheckAfterMove(position, new Move(Square.C2, Square.C1, 0, false), position.getPlayerToMove());
        //then
        AssertionsForClassTypes.assertThat(kingInCheckAfterMove).isFalse();
    }

    @Test
    void noCheck2() {
        //given
        Position position = PositionGenerator.fromFEN("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1");
        //when
        boolean kingInCheckAfterMove = CheckRuleMovement.isKingInCheckAfterMove(position, new Move(Square.C4, Square.D6, 0, true), position.getPlayerToMove());
        //then
        AssertionsForClassTypes.assertThat(kingInCheckAfterMove).isFalse();
    }
}
