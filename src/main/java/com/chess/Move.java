package com.chess;

import com.ai.Action;
import com.chess.movementrules.CheckRuleMovement;
import lombok.NonNull;

import java.util.Objects;

public class Move implements Action, Comparable<Move> {
    Square startingSquare;
    Square endingSquare;
    int order;

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare) {
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
        order = order + startingSquare.ordinal() + endingSquare.ordinal();
    }

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare, int canCaptureMaterial, boolean isCheck) {
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
        order = canCaptureMaterial * 10000;
        if (isCheck) {
            order+=20000;
        }
        order = order + startingSquare.ordinal() + endingSquare.ordinal();
    }

    public Move(@NonNull Square startingSquare, @NonNull Square endingSquare, Position position) {
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
        this.order = ((int) position.getPieceValueOnSquare(endingSquare)) * 10000;
        Move move = new Move(startingSquare, endingSquare);
        if (CheckRuleMovement.isKingInCheckAfterMove(position, move, position.getPlayerToMove().opposite())) {
            order+=20000;
        }
        order = order + startingSquare.ordinal() + endingSquare.ordinal();
    }

    @Override
    public int compareTo(Move move) {
        return Integer.compare(move.getOrder(), order);
    }

    public int getOrder() {
        return order;
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
