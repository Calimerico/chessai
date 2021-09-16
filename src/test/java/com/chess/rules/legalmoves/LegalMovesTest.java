package com.chess.rules.legalmoves;

import com.ai.Action;
import com.chess.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
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
        Assertions.assertThat(position1.getActions().contains(new Move(Square.G5, Square.F6, position1, true, PieceType.QUEEN)));
        Position position2 = position1.newState(new Move(Square.G5, Square.F6, position1, true, PieceType.QUEEN));
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

    @ParameterizedTest
    @MethodSource("numberOfLegalMovesByPosition")
    void testNumberOfLegalMovesInPosition(String fen, long numberOfExpectedLegalMoves, int depth) {
        //given
        Position position = PositionGenerator.fromFEN(fen);
        //when
        long numberOfLegalMoves = calculateNumberOfMoves(position, depth);


        for (Action action : new ArrayList<>(position.getActions())) {
            Set<Action> actions1 = position.newState(action).getActions();
            Square startingSquare = ((Move) action).getStartingSquare();
            Square endingSquare = ((Move) action).getEndingSquare();
            System.out.println(" " + startingSquare + endingSquare + ": " + actions1.size());
        }



//        for (Action action : new ArrayList<>(position.getActions())) {
//            Position position1 = position.newState(action);
//            Set<Action> actions1 = position1.getActions();
//            int innerCounter = 0;
//            for (Action action1 : actions1) {
//                Square startingSquare1 = ((Move) action1).getStartingSquare();
//                Square endingSquare1 = ((Move) action1).getEndingSquare();
//                Position position2 = position1.newState(new Move(startingSquare1, endingSquare1, position1));
//                innerCounter += position2.getActions().size();
//            }
//            Square startingSquare = ((Move) action).getStartingSquare();
//            Square endingSquare = ((Move) action).getEndingSquare();
//            System.out.println(" " + startingSquare + endingSquare + ": " + innerCounter);
//        }

        //then
        Assertions.assertThat(numberOfLegalMoves).isEqualTo(numberOfExpectedLegalMoves);
    }

    public long calculateNumberOfMoves(Position position, int depth) {
        Set<Action> actions = position.getActions();
        long sizeOnCurrentLevel = actions.size();
        if (depth == 1) {
            return sizeOnCurrentLevel;
        } else {
            long sizeOnDeeperLevels = 0;
            for (Action action : actions) {
                long sizeOnDeeperLevels1 = calculateNumberOfMoves(position.newState(action), depth - 1);
                sizeOnDeeperLevels += sizeOnDeeperLevels1;
            }
            return sizeOnDeeperLevels;
        }
    }

    private static Stream<Arguments> numberOfLegalMovesByPosition() {
        return Stream.of(
                //todo too slow but should work!
//                Arguments.of(
//                        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
//                        4865609,
//                        5
//                ),
                Arguments.of(
                        "rnbqkbnr/pppp1ppp/8/4p3/4P3/P7/1PPP1PPP/RNBQKBNR b KQkq - 0 2",
                        810,
                        2
                ),
                Arguments.of(
                        "rnbqk1nr/pppp1ppp/8/4p3/1b2P3/P7/1PPP1PPP/RNBQKBNR w KQkq - 1 3",
                        26,
                        1
                ),
                Arguments.of(
                        "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3",
                        835,
                        2
                ),
                Arguments.of(
                        "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8  ",
                        1486,
                        2
                ),
                Arguments.of(
                        "rnNq1k1r/pp2bppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R b KQ - 0 8",
                        1607,
                        2
                ),
                Arguments.of(
                        "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2",
                        24825,
                        3
                ),
                Arguments.of(
                        "rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 2",
                        1103,
                        2
                ),
                Arguments.of(
                        "rnbqkbnr/pppp1ppp/8/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2",
                        1120,
                        2
                ),
                Arguments.of(
                        "rnNq1k1r/pp2bppp/2p5/8/2B5/8/PPP1N1PP/RNBnK2R w KQ - 0 9",
                        37,
                        1
                ),
                Arguments.of(
                        "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8  ",
                        62379,
                        3
                )
                //                Arguments.of(//todo under promote
//                        "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ",
//                        4085603,
//                        4
//                ),
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
                                new Move(Square.G7, Square.G8, position, false, PieceType.KNIGHT),
                                new Move(Square.G7, Square.G8, position, false, PieceType.ROOK),
                                new Move(Square.G7, Square.G8, position, false, PieceType.BISHOP),
                                new Move(Square.G7, Square.G8, position, false, PieceType.QUEEN)
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
                                new Move(Square.G7, Square.G8,position, false, PieceType.KNIGHT),
                                new Move(Square.G7, Square.G8,position, false, PieceType.ROOK),
                                new Move(Square.G7, Square.G8,position, false, PieceType.BISHOP),
                                new Move(Square.G7, Square.G8,position, false, PieceType.QUEEN)
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
