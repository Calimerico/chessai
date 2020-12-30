package com.chess;

import java.util.Map;
import java.util.Set;

public class Position {
    private final Map<Square, Piece> pieces;
    private boolean whiteCanCastleQueenSide;
    private boolean whiteCanCastleKingSide;
    private boolean blackCanCastleQueenSide;
    private boolean blackCanCastleKingSide;
    private Color playerToMove;
    private Move lastPlayedMove;

    public Position(Map<Square, Piece> pieces, boolean whiteCanCastleQueenSide, boolean whiteCanCastleKingSide, boolean blackCanCastleQueenSide, boolean blackCanCastleKingSide, Color playerToMove, Move lastPlayedMove) {
        this.pieces = pieces;
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        this.playerToMove = playerToMove;
        this.lastPlayedMove = lastPlayedMove;
    }

    public Position makeMove(Move move) {
        Square startingSquare = move.getStartingSquare();
        Piece pieceOnStartingSquare = pieces.get(move.getStartingSquare());
        if (pieceOnStartingSquare == null) {
            throw new RuntimeException("There is no piece on square " + startingSquare);
        }
        Set<Move> legalMoves = pieceOnStartingSquare.getLegalMoves(this);
        if (legalMoves.contains(move)) {
            applyMove(move);
        }
        return this;
    }

    public Map<Square, Piece> getPieces() {
        return pieces;
    }

    public Position getPositionAfterMove(Move move) {
        Position currPosition = new Position(
                pieces,
                whiteCanCastleQueenSide,
                whiteCanCastleKingSide,
                blackCanCastleQueenSide,
                blackCanCastleKingSide,
                playerToMove == Color.WHITE ? Color.BLACK : Color.WHITE,
                move
        );
        return currPosition.makeMove(move);
    }

    private void applyMove(Move move) {
        Square startingSquare = move.getStartingSquare();
        pieces.put(move.getEndingSquare(),pieces.get(startingSquare));
        pieces.put(startingSquare,null);
        playerToMove = this.playerToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
        if (startingSquare == Square.E1) {
            //white king moving, loosing right to castle
            this.whiteCanCastleKingSide = false;
            this.whiteCanCastleQueenSide = false;
        }
        if (startingSquare == Square.E8) {
            //black king moving, loosing right to castle
            this.blackCanCastleKingSide = false;
            this.blackCanCastleQueenSide = false;
        }
        if (startingSquare == Square.A1) {
            //A1 rook moves, white loose right to castle queenside
            this.whiteCanCastleQueenSide = false;
        }
        if (startingSquare == Square.A8) {
            //A8 rook moves, white loose right to castle kingside
            this.whiteCanCastleKingSide = false;
        }
        if (startingSquare == Square.H1) {
            //H1 rook moves, black loose right to castle queenside
            this.blackCanCastleQueenSide = false;
        }
        if (startingSquare == Square.H8) {
            //H8 rook moves, black loose right to castle kingside
            this.blackCanCastleKingSide = false;
        }
        this.lastPlayedMove = move;
    }
}
