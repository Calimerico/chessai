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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
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

    @Test
    void enPassantTest() {
        Position position = PositionGenerator.fromFEN("8/3k1p2/8/6P1/8/8/5K2/8 b - - 0 1");
        Position position1 = position.newState(new Move(Square.F7, Square.F5, position));
        Assertions.assertThat(position1.getActions().contains(new Move(Square.G5, Square.F6, position1, true)));
        Position position2 = position1.newState(new Move(Square.G5, Square.F6, position1, true));
        Assertions.assertThat(position2.getPieces()).doesNotContainKey(Square.F5);
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

//    @ParameterizedTest
//    @MethodSource("numberOfLegalMovesByPosition")
//    void testNumberOfLegalMovesInPosition(String fen, long numberOfExpectedLegalMoves, int depth) {
//        //given
//        Position position = PositionGenerator.fromFEN(fen);
//        //when
//        long numberOfLegalMoves = calculateNumberOfMoves(position, depth);
//
//        //then
//        Assertions.assertThat(numberOfLegalMoves).isEqualTo(numberOfExpectedLegalMoves);
//    }

    public long calculateNumberOfMoves(Position position, int depth) {
        Set<Action> actions = position.getActions();
        long sizeOnCurrentLevel = actions.size();
        if (depth == 1) {
            return sizeOnCurrentLevel;
        } else {
            long sizeOnDeeperLevels = 0;
            for (Action action : actions) {
                sizeOnDeeperLevels += calculateNumberOfMoves(position.newState(action), depth - 1);
            }
            return sizeOnCurrentLevel + sizeOnDeeperLevels;
        }
    }

    private static Stream<Arguments> numberOfLegalMovesByPosition() {
        return Stream.of(
                Arguments.of(
                        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                        4865609,
                        5
                ),
                Arguments.of(
                        "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ",
                        4085603,
                        4
                )
        );
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
