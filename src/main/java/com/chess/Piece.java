package com.chess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Set;

@Value
public class Piece {
    Color color;
    Square square;
    PieceType pieceType;

    public Set<Move> getLegalMoves(Position position) {
        return PositionLegalMovesService.getLegalMoves(position, square);
    }

    public Set<Square> getAttackingSquares(Position position) {
        return PositionLegalMovesService.getAttackingSquares(position, square);
    }
}
