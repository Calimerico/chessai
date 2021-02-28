package com.chess;

import lombok.Getter;

public enum PieceType {
    PAWN(1.0),
    KNIGHT(3.0),
    BISHOP(3.0),
    ROOK(5.0),
    QUEEN(9.0),
    KING(100000000.0);

    @Getter
    private double value;

    PieceType(double value) {
        this.value = value;
    }
}
