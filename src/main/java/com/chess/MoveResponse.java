package com.chess;

import com.ai.Action;
import lombok.Value;

@Value
public class MoveResponse {
    Square startingSquare;
    Square endingSquare;
    boolean resign;
    boolean offerDraw;
}
