package com.chess.heuristic;

import com.chess.Color;
import com.chess.PieceType;
import com.chess.Position;

public class InsufficientMaterial {

    public static boolean isInsufficient(Position position) {
        int numberOfPieces = position.getNumberOfPieces();
        return
                numberOfPieces == 2 ||
                        (numberOfPieces == 3 && containsBishop(position)) ||
                        (numberOfPieces == 3 && containsKnight(position)) ||
                        (
                                getNumberOfPieces(position, Color.WHITE) == 2 &&
                                        getNumberOfPieces(position, Color.BLACK) == 2 &&
                                        (containsKnight(position, Color.WHITE) || containsBishop(position, Color.WHITE)) && (containsKnight(position, Color.BLACK) || containsBishop(position, Color.BLACK))
                        );
    }

    private static boolean containsKnight(Position position) {
        return position.getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.KNIGHT);
    }

    private static boolean containsBishop(Position position) {
        return position.getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.BISHOP);
    }

    private static boolean containsKnight(Position position, Color color) {
        return position.getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.KNIGHT && piece.getColor() == color);
    }

    private static boolean containsBishop(Position position, Color color) {
        return position.getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.BISHOP && piece.getColor() == color);
    }

    private static long getNumberOfPieces(Position position, Color color) {
        return position.getPieces().values().stream().filter(piece -> piece.getColor() == color).count();
    }


}
