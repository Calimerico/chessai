package com.chess;

import com.github.bhlangonijr.chesslib.Side;

public enum Color {
    WHITE,
    BLACK;

    public static Color fromSide(Side side) {
        return side == Side.BLACK ? Color.BLACK : Color.WHITE;
    }
}
