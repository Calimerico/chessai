package com.chess;

import lombok.Getter;

import java.util.Map;

@Getter
public class PiecesInfo {
    long numberOfWhitePieces;
    long numberOfBlackPieces;
    boolean containsWhiteKnight;
    boolean containsWhiteBishop;
    boolean containsBlackKnight;
    boolean containsBlackBishop;

    public PiecesInfo(Position position) {
        Map<Square, Piece> pieces = position.getPieces();
        this.numberOfWhitePieces = pieces.values().stream().filter(piece -> piece.getColor() == Color.WHITE).count();
        this.numberOfBlackPieces = pieces.size() - numberOfWhitePieces;
        containsWhiteKnight = pieces.values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.KNIGHT && piece.getColor() == Color.WHITE);
        containsWhiteBishop = pieces.values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.BISHOP && piece.getColor() == Color.WHITE);
        containsBlackKnight = pieces.values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.KNIGHT && piece.getColor() == Color.BLACK);
        containsBlackBishop = pieces.values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.BISHOP && piece.getColor() == Color.BLACK);
    }

    public boolean containsKnight() {
        return containsBlackKnight || containsWhiteKnight;
    }

    public boolean containsBishop() {
        return  containsBlackBishop || containsWhiteBishop;
    }

    public boolean containsKnight(Color color) {
        return color == Color.WHITE ? containsWhiteKnight : containsBlackKnight;
    }

    public boolean containsBishop(Color color) {
        return color == Color.WHITE ? containsWhiteBishop : containsBlackBishop;
    }

    public long getNumberOfPieces(Color color) {
        return color == Color.WHITE ? numberOfWhitePieces : numberOfBlackPieces;
    }

    public long getNumberOfPieces() {
        return  numberOfWhitePieces + numberOfBlackPieces;
    }
}
