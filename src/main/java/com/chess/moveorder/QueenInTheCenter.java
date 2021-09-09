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
                return 100000;
            }
            if (move.getEndingSquare().isEdge()) {
                return -100000;
            }
        }
        return 0;
    }
}
