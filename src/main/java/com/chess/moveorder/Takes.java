package com.chess.moveorder;

import com.chess.Move;
import com.chess.PieceType;
import com.chess.Position;
import com.chess.Square;

public class Takes implements MoveOrderService {

    @Override
    public int getOrder(Move move) {
        Position position = move.getPosition();

        Square endingSquare = move.getEndingSquare();
        double pieceValueOnEndingSquareSquare = position.getPieceValueOnSquare(endingSquare);
        if (pieceValueOnEndingSquareSquare == 0) {
            return 0;
        }
        //cant take king
        if (position.getPieceTypeOnSquare(endingSquare) == PieceType.KING) {
            return 0;
        }
        Square startingSquare = move.getStartingSquare();
        if (position.getPieceTypeOnSquare(startingSquare) == PieceType.KING) {
            return (int) (pieceValueOnEndingSquareSquare * 300000);
        }
        double diff = pieceValueOnEndingSquareSquare - position.getPieceValueOnSquare(startingSquare);
        //if ending square is defended
        boolean endingSquareDefended = position.getAttackingSquaresByPlayer(position.getPlayerToMove().opposite()).contains(endingSquare);
        if (endingSquareDefended && diff > 0) {
            return (int) (diff*300000);
        }
        if (!endingSquareDefended) {
            return (int) (diff*300000);
        }
        return 0;
    }
}
