package com.chess;

public class CannotCaptureYourOwnPieceException extends NotLegalMoveException {

    public CannotCaptureYourOwnPieceException(String message) {
        super(message);
    }
}
