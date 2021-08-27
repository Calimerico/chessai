package com.chess.heuristic;

import com.chess.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class HeuristicFunctionTest {

    @ParameterizedTest
    @MethodSource("positions")
    void positionHeuristicTest(String positionFen, double heuristic) {
        //given
        Position position = PositionGenerator.fromFEN(positionFen);
        //when
        double heuristicFunction = position.getHeuristicFunction();
        //then
        assertThat(heuristicFunction).isEqualTo(heuristic);
    }

    private static Stream<Arguments> positions() {
        return Stream.of(
                Arguments.of(
                        "2R3k1/5ppp/6qq/7q/8/8/5PPP/6K1 b - - 0 1",
                        -900000
                ),
                Arguments.of(
                        "3k4/3P4/3K4/8/8/8/8/8 b - - 0 1",
                        0
                ),
                Arguments.of(
                        "8/2k5/8/8/8/8/3K2B1/8 w - - 0 1",
                        0
                )
        );
    }
}
