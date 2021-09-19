package com.chess.heuristic;

import com.chess.Color;
import com.chess.PiecesInfo;
import com.chess.Position;

public class InsufficientMaterial {

    public boolean isInsufficient(Position position) {
        PiecesInfo piecesInfo = new PiecesInfo(position);
        long numberOfPieces = piecesInfo.getNumberOfPieces();
        return
                numberOfPieces == 2 ||
                        (numberOfPieces == 3 && piecesInfo.containsBishop()) ||
                        (numberOfPieces == 3 && piecesInfo.containsKnight()) ||
                        (
                                piecesInfo.getNumberOfPieces(Color.WHITE) == 2 &&
                                        piecesInfo.getNumberOfPieces(Color.BLACK) == 2 &&
                                        (
                                                piecesInfo.containsKnight(Color.WHITE) ||
                                                piecesInfo.containsBishop(Color.WHITE)
                                        ) &&
                                        (
                                                piecesInfo.containsKnight(Color.BLACK) ||
                                                piecesInfo.containsBishop(Color.BLACK))
                        );
    }




}
