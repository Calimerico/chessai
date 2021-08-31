package com.chess.rules.legalmoves;

import com.ai.Action;
import com.ai.MiniMax;
import com.chess.*;
import com.chess.movementrules.KingRuleMovement;
import com.chess.movementrules.KnightRuleMovement;
import com.chess.movementrules.PawnRuleMovement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LegalMovesTest {

    @ParameterizedTest
    @MethodSource("legalMovesFromSquare")
    void legalMovesFromSquare(String positionFen,Square fromSquare, List<Move> moves) {
        //given
        Position position = PositionGenerator.fromFEN(positionFen);
        //when
        Set<Move> actions = PositionLegalMovesService.getLegalMoves(position, fromSquare);
        //then
        Assertions.assertThat(actions).containsAll(moves);
        Assertions.assertThat(actions.size()).isEqualTo(moves.size());
    }

    @ParameterizedTest
    @MethodSource("legalMovesInPosition")
    void legalMovesInPosition(String positionFen, List<Move> moves) {
        //given
        Position position = PositionGenerator.fromFEN(positionFen);
        //when
        Set<Action> actions = position.getActions();
        //then
        Assertions.assertThat(actions).containsAll(moves);
        Assertions.assertThat(actions.size()).isEqualTo(moves.size());
    }

    private static Stream<Arguments> legalMovesFromSquare() {
        String fen = "4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1";
        Position position = PositionGenerator.fromFEN(fen);
        return Stream.of(
                Arguments.of(
                        fen,
                        Square.C2,
                        Arrays.asList(
                                new Move(Square.C2, Square.B1,position),
                                new Move(Square.C2, Square.C1,position),
                                new Move(Square.C2, Square.D1,position),
                                new Move(Square.C2, Square.B2,position),
                                new Move(Square.C2, Square.D2,position),
                                new Move(Square.C2, Square.B3,position),
                                new Move(Square.C2, Square.C3,position),
                                new Move(Square.C2, Square.D3,position)
                        )),
                Arguments.of(
                        fen,
                        Square.C4,
                        Arrays.asList(
                                new Move(Square.C4, Square.A3, position),
                                new Move(Square.C4, Square.A5, position),
                                new Move(Square.C4, Square.B6, position),
                                new Move(Square.C4, Square.B2, position),
                                new Move(Square.C4, Square.D6, position),
                                new Move(Square.C4, Square.D2, position),
                                new Move(Square.C4, Square.E5, position),
                                new Move(Square.C4, Square.E3, position)
                        )),
                Arguments.of(
                        fen,
                        Square.G7,
                        Arrays.asList(
                                new Move(Square.G7, Square.G8, position)
                        ))
        );
    }

    private static Stream<Arguments> legalMovesInPosition() {
        String fen = "4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1";
        Position position = PositionGenerator.fromFEN(fen);
        return Stream.of(
                Arguments.of(
                        fen,
                        Arrays.asList(
                                new Move(Square.C2, Square.B1,position),
                                new Move(Square.C2, Square.C1,position),
                                new Move(Square.C2, Square.D1,position),
                                new Move(Square.C2, Square.B2,position),
                                new Move(Square.C2, Square.D2,position),
                                new Move(Square.C2, Square.B3,position),
                                new Move(Square.C2, Square.C3,position),
                                new Move(Square.C2, Square.D3,position),
                                new Move(Square.C4, Square.A3,position),
                                new Move(Square.C4, Square.A5,position),
                                new Move(Square.C4, Square.B6,position),
                                new Move(Square.C4, Square.B2,position),
                                new Move(Square.C4, Square.D6,position),
                                new Move(Square.C4, Square.D2,position),
                                new Move(Square.C4, Square.E5,position),
                                new Move(Square.C4, Square.E3,position),
                                new Move(Square.G7, Square.G8,position)
                        )),
                Arguments.of(
                        "8/2k2NP1/8/8/8/8/2K1r3/8 w - - 1 1",
                        Arrays.asList(
                                new Move(Square.C2, Square.B1,position),
                                new Move(Square.C2, Square.C1,position),
                                new Move(Square.C2, Square.D1,position),
                                new Move(Square.C2, Square.B3,position),
                                new Move(Square.C2, Square.C3,position),
                                new Move(Square.C2, Square.D3,position)
                        ))
        );
    }

}
