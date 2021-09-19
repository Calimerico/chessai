package com.chess.moveorder;

import com.chess.Move;

import java.util.Arrays;
import java.util.List;

public class MoveOrderManager {
    private final List<MoveOrderService> moveOrderServices = Arrays.asList(
            new Check(),
            new Takes(),
            new QueenInTheCenter()
//            new GivingFreePiece()
    );

    public int getOrder(Move move) {
        return moveOrderServices.stream().mapToInt(moveOrderService -> moveOrderService.getOrder(move)).sum();
    }
}
