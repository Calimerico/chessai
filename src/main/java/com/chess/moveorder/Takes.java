package com.chess.moveorder;

import com.chess.Move;
import com.chess.PieceType;
import com.chess.Position;
import com.chess.Square;

public class Takes implements MoveOrderService {

    @Override
    public double getOrder(Move move) {
        Position position = move.getPosition();

        Square endingSquare = move.getEndingSquare();
        Square startingSquare = move.getStartingSquare();
        double pieceValueOnEndingSquare = position.getPieceValueOnSquare(endingSquare);
        double pieceValueOnStartingSquare = position.getPieceValueOnSquare(startingSquare);
        if (pieceValueOnEndingSquare == 0) {
            return 0;
        }
        //cant take king
        if (position.getPieceTypeOnSquare(endingSquare) == PieceType.KING) {
            return 0;
        }
        if (position.getPieceTypeOnSquare(startingSquare) == PieceType.KING) {
            return (int) (pieceValueOnEndingSquare * 300000);
        }
        int order = 0;
        order += pieceValueOnEndingSquare * 300000;
        order += (10 - pieceValueOnStartingSquare) * 300000;
        double diff = pieceValueOnEndingSquare - position.getPieceValueOnSquare(startingSquare);
        //if ending square is defended
//        boolean endingSquareDefended = position.getAttackingSquaresByPlayer(position.getPlayerToMove().opposite()).contains(endingSquare);
//        if (endingSquareDefended && diff > 0) {
//            return (int) (diff*300000);
//        }
//        if (!endingSquareDefended) {
//            return (int) (diff*300000);
//        }
        return order;
    }
}
