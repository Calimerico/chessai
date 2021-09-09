package com.chess;

import com.ai.ZobristValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.Set;

@ToString
@EqualsAndHashCode
public class Piece implements ZobristValue {
    Color color;
    Square square;
    PieceType pieceType;

    public Piece(Color color, Square square, PieceType pieceType) {
        this(color, square, pieceType, PieceType.QUEEN);
    }

    public Piece(Color color, Square square, PieceType pieceType, PieceType promoteTo) {
        this.color = color;
        this.square = square;
        if (pieceType == PieceType.PAWN) {
            if ((color == Color.WHITE && square.getRank() == 7) || (color == Color.BLACK && square.getRank() == 0)) {
                this.pieceType = promoteTo;
            } else {
                this.pieceType = pieceType;
            }
        } else {
            this.pieceType = pieceType;
        }
    }

    public Set<Move> getLegalMoves(Position position) {
        return PositionLegalMovesService.getLegalMoves(position, square);
    }

    public Set<Square> getAttackingSquares(Position position) {
        return PositionLegalMovesService.getAttackingSquares(position, square);
    }

    public Color getColor() {
        return color;
    }

    public Square getSquare() {
        return square;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
