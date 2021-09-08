package com.chess;

import com.ai.Action;
import com.chess.moveorder.MoveOrderManager;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

public class Move implements Action, Comparable<Move> {
    private static final MoveOrderManager moveOrderManager = new MoveOrderManager();
    private final Square startingSquare;
    private final Square endingSquare;
    @Getter
    private final Position position;
    private final int order;//todo
    private final boolean isEnPassant;

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare, Position position, boolean isEnPassant) {
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
        this.position = position;
        this.isEnPassant = isEnPassant;
        order = moveOrderManager.getOrder(this) + startingSquare.ordinal() + endingSquare.ordinal();
    }

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare, Position position) {
        this(startingSquare, endingSquare, position, false);
    }

    @Override
    public int compareTo(Move move) {
        return Integer.compare(move.getOrder(), order);
    }

    public int getOrder() {
        return order;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public Square getStartingSquare() {
        return startingSquare;
    }

    public Square getEndingSquare() {
        return endingSquare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return order == move.order &&
                startingSquare == move.startingSquare &&
                endingSquare == move.endingSquare;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingSquare, endingSquare, order);
    }

    @Override
    public String toString() {
        return "Move{" +
                "startingSquare=" + startingSquare +
                ", endingSquare=" + endingSquare +
                '}';
    }
}
