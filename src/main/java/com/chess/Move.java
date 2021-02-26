package com.chess;

import com.ai.Action;
import lombok.Value;

@Value
public class Move implements Action {
    Square startingSquare;
    Square endingSquare;
}
