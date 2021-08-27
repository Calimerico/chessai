package com.chess;

import com.github.bhlangonijr.chesslib.Side;

public enum Color {
    WHITE {
        @Override public Color opposite() {
            return Color.BLACK;
        }
    },
    BLACK {
        @Override public Color opposite() {
            return Color.WHITE;
        }
    };

    public abstract Color opposite();

    public static Color fromSide(Side side) {
        return side == Side.BLACK ? Color.BLACK : Color.WHITE;
    }
}
