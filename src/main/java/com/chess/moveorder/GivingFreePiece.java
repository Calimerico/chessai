package com.chess.moveorder;

import com.chess.Move;
import com.chess.Position;
import com.chess.Square;

public class GivingFreePiece implements MoveOrderService {

    @Override
    public int getOrder(Move move) {
        Position position = move.getPosition();
        Square endingSquare = move.getEndingSquare();
        Square startingSquare = move.getStartingSquare();
        double diff = position.getPieceValueOnSquare(endingSquare) - position.getPieceValueOnSquare(startingSquare);
        //if ending square is defended
        //todo position.getAttackingSquaresByPlayer maybe its actually not defended is piece is under the pin!
        boolean endingSquareDefended = position.getAttackingSquaresByPlayer(position.getPlayerToMove().opposite()).contains(endingSquare);
        if (endingSquareDefended && diff < 0) {
            return (int) (diff*300000);
        }
        return 0;
    }
}
