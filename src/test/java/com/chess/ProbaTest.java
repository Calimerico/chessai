package com.chess;

import com.ai.MiniMax;
import com.ai.ZobristHashing;
import org.junit.jupiter.api.Test;

public class ProbaTest {

    @Test
    void name() {
        Position position = PositionGenerator.fromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Position position1 = position.newState(new Move(Square.D2, Square.D4, position));
        Position position2 = position.newState(new Move(Square.H7, Square.H6, position1));
        Position position3 = position.newState(new Move(Square.G1, Square.F3, position2));
        MiniMax miniMax = new MiniMax(new ChessBreakTest(6), new ZobristHashing());
        System.out.println(miniMax.search(position3));
    }
}
