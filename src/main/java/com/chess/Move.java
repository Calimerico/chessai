package com.chess;

public class Move {
    private Square startingSquare;
    private Square endingSquare;

    public Move(Square startingSquare, Square endingSquare) {
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
    }

    public Square getStartingSquare() {
        return startingSquare;
    }

    public Square getEndingSquare() {
        return endingSquare;
    }
}
