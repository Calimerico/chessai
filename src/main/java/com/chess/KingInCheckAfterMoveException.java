package com.chess;

public class KingInCheckAfterMoveException extends NotLegalMoveException {

    public KingInCheckAfterMoveException(String message) {
        super(message);
    }
}
