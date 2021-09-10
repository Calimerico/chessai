package com.chess.heuristic;

import com.chess.Color;
import com.chess.Piece;
import com.chess.Position;

import java.util.Collection;

public class StaticValue implements Heuristic {

    @Override
    public double getHeuristic(Position position) {
        double sum = 0.0;
        Collection<Piece> values = position.getPieces().values();
        for (Piece piece : values) {
            if (piece.getColor() == Color.WHITE) {
                sum+= piece.getPieceType().getValue();
            } else {
                sum-= piece.getPieceType().getValue();
            }
        }
        return sum;
    }
}
