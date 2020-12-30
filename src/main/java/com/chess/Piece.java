package com.chess;

import com.chess.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Piece {
    private final Color color;
    private Square square;
    private PieceType pieceType;

    public Piece(Color color, Square square, PieceType pieceType) {
        this.color = color;
        this.square = square;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Color getColor() {
        return color;
    }

    public Square getSquare() {
        return square;
    }

    protected boolean leavesKingInCheck(Move move, Position position) {

    }

    public Set<Square> getAttackedSquares() {

    }

    public Set<Square> getLegalMoves(Position position) {

    }
}
