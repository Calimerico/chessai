package com.chess;

import java.util.HashMap;

public class PositionGenerator {

    public static Position fromFEN(String FEN) {
        throw new UnsupportedOperationException("Chess960 not supported yet");
    }

    public static Position initialChess960Position(int startPosition) {
        throw new UnsupportedOperationException("Chess960 not supported yet");
    }

    public static Position initialChess960Position() {
        throw new UnsupportedOperationException("Chess960 not supported yet");
    }

    public static Position initialPosition() {
        HashMap<Square, Piece> pieces = new HashMap<>();
        pieces.put(Square.A1, new Rook(Color.WHITE,Square.A1));
        pieces.put(Square.B1, new Knight(Color.WHITE,Square.B1));
        pieces.put(Square.C1, new Bishop(Color.WHITE,Square.C1));
        pieces.put(Square.D1, new Queen(Color.WHITE,Square.D1));
        pieces.put(Square.E1, new King(Color.WHITE,Square.E1));
        pieces.put(Square.F1, new Bishop(Color.WHITE,Square.F1));
        pieces.put(Square.G1, new Knight(Color.WHITE,Square.G1));
        pieces.put(Square.H1, new Rook(Color.WHITE,Square.H1));

        pieces.put(Square.A2, new Pawn(Color.WHITE,Square.A2));
        pieces.put(Square.B2, new Pawn(Color.WHITE,Square.B2));
        pieces.put(Square.C2, new Pawn(Color.WHITE,Square.C2));
        pieces.put(Square.D2, new Pawn(Color.WHITE,Square.D2));
        pieces.put(Square.E2, new Pawn(Color.WHITE,Square.E2));
        pieces.put(Square.F2, new Pawn(Color.WHITE,Square.F2));
        pieces.put(Square.G2, new Pawn(Color.WHITE,Square.G2));
        pieces.put(Square.H2, new Pawn(Color.WHITE,Square.H2));

        pieces.put(Square.A8, new Rook(Color.BLACK, Square.A8));
        pieces.put(Square.B8, new Knight(Color.BLACK, Square.B8));
        pieces.put(Square.C8, new Bishop(Color.BLACK, Square.C8));
        pieces.put(Square.D8, new Queen(Color.BLACK, Square.D8));
        pieces.put(Square.E8, new King(Color.BLACK, Square.E8));
        pieces.put(Square.F8, new Bishop(Color.BLACK, Square.F8));
        pieces.put(Square.G8, new Knight(Color.BLACK, Square.G8));
        pieces.put(Square.H8, new Rook(Color.BLACK, Square.H8));

        pieces.put(Square.A7, new Pawn(Color.BLACK, Square.A7));
        pieces.put(Square.B7, new Pawn(Color.BLACK, Square.B7));
        pieces.put(Square.C7, new Pawn(Color.BLACK, Square.C7));
        pieces.put(Square.D7, new Pawn(Color.BLACK, Square.D7));
        pieces.put(Square.E7, new Pawn(Color.BLACK, Square.E7));
        pieces.put(Square.F7, new Pawn(Color.BLACK, Square.F7));
        pieces.put(Square.G7, new Pawn(Color.BLACK, Square.G7));
        pieces.put(Square.H7, new Pawn(Color.BLACK, Square.H7));

        return new Position(
                pieces, true,true,true,true,Color.WHITE, null

        );
    }
}
