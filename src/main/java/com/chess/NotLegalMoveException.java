package com.chess;

public class NotLegalMoveException extends Exception {

    public NotLegalMoveException(String message) {
        super(message);
    }
}
