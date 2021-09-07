package com.chess.moveorder;

import com.chess.Move;
import com.chess.PieceType;
import com.chess.Position;
import com.chess.Square;

public class QueenInTheCenter implements MoveOrderService {

    @Override
    public int getOrder(Move move) {
        Position position = move.getPosition();
        if (position.getPieceAtSquare(move.getStartingSquare()).getPieceType() == PieceType.QUEEN) {
            if (move.getEndingSquare().isNarrowCenter()) {
                return 500;
            }
            if (move.getEndingSquare().isEdge()) {
                return -500;
            }
        }
        return 0;
    }
}
