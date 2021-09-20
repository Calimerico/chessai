package com.chess.moveorder;

import com.chess.Move;
import com.chess.heuristic.AttCenterSquares;
import com.chess.heuristic.AttSquares;

import java.util.Arrays;
import java.util.List;

public class MoveOrderManager {
    private final List<MoveOrderService> moveOrderServices = Arrays.asList(
            new Check(),
            new Takes(),
            new QueenInTheCenter(),
            new AttCenterSquares(),
            new AttSquares()
//            new GivingFreePiece()
    );

    public double getOrder(Move move) {
        return moveOrderServices.stream().mapToDouble(moveOrderService -> moveOrderService.getOrder(move)).sum();
    }
}
