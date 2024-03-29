package com.chess.puzzles;

import com.ai.Action;
import com.ai.BreakTest;
import com.ai.MiniMax;
import com.ai.ZobristHashing;
import com.chess.*;
import com.chess.movementrules.KnightRuleMovement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PuzzleTest {

    @ParameterizedTest
    @MethodSource("positions")
    void test(String positionFen, Square startingSquare, Square endingSquare, int depth) {
//        PerformanceMonitor.start();
        //given
        Position position = PositionGenerator.fromFEN(positionFen);
        //when
        MiniMax miniMax = new MiniMax(new ChessBreakTest(depth), new ZobristHashing());
        Action action = miniMax.search(position);
        //then
        System.out.println("Number of positions: " + PerformanceMonitor.getTotalNumberOfPositions());
        Assertions.assertThat(((Move) action).getStartingSquare()).isEqualTo(startingSquare);
        Assertions.assertThat(((Move) action).getEndingSquare()).isEqualTo(endingSquare);
    }

    private static Stream<Arguments> positions() {
        return Stream.of(
//                Arguments.of("1k6/1pp5/3r4/8/8/2N2R2/5PP1/6K1 w - - 0 1", Square.F3, Square.F8, 6),
//                Arguments.of("5R2/kpp5/3r4/8/8/2N5/5PP1/6K1 w - - 2 2", Square.C3, Square.B5, 6),
//                Arguments.of("6k1/5ppp/8/8/4n3/8/PP6/1K3R2 b - - 0 1", Square.E4, Square.D2, 6),
//                Arguments.of("4r3/1k4P1/8/8/2N5/8/2K5/8 w - - 0 1", Square.C4, Square.D6, 6),//todo
//                Arguments.of("8/4k1P1/8/8/3p4/3K4/8/8 w - - 0 1", Square.G7, Square.G8, 6),
//                Arguments.of("r1b2r1k/ppq3pp/2pp4/5p2/3bP3/1BP2QP1/PP3PK1/R6R w - - 0 21", Square.H1, Square.H7, 6)
                Arguments.of("2rbr1k1/1b1q1pp1/p2R1n1p/2pPB3/2B5/2N4P/1PQ2PP1/4R1K1 b - - 4 26", Square.E8, Square.E5, 6)
                //--------------------------------------------------------------------
//                Arguments.of("6k1/2r2p1p/5Pp1/6Q1/8/8/5PPP/6K1 w - - 0 1", Square.G5, Square.H6)
        );
    }
}
