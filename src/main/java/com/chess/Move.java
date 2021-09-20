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
    private final double order;//todo
    private final boolean isEnPassant;
    private final PieceType promoteTo;

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare, Position position, boolean isEnPassant, PieceType promoteTo) {
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
        this.position = position;
        this.isEnPassant = isEnPassant;
        int ordinalStart = startingSquare.ordinal();
        int ordinalEnd = endingSquare.ordinal();
        this.promoteTo = promoteTo;
        order = moveOrderManager.getOrder(this) + (ordinalStart + ordinalEnd)*(ordinalStart + ordinalEnd + 1) + ordinalEnd + (int)(promoteTo.getValue() * 20000) + (promoteTo == PieceType.KNIGHT ? 1000: 0);
    }

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare, Position position) {
        this(startingSquare, endingSquare, position, false, PieceType.QUEEN);
    }

    @Override
    public int compareTo(Move move) {
        return Double.compare(move.getOrder(), order);
    }

    public double getOrder() {
        return order;
    }

    public PieceType getPromoteTo() {
        return promoteTo;
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
        return ((Move) o).compareTo(move) == 0;
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
