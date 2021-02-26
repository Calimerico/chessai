package com.chess;

import com.ai.Action;
import com.ai.DummyAction;
import com.ai.State;

import java.util.*;

public class Position implements State {
    private final Map<Square, Piece> pieces;
    private final boolean whiteCanCastleQueenSide;
    private final boolean whiteCanCastleKingSide;
    private final boolean blackCanCastleQueenSide;
    private final boolean blackCanCastleKingSide;
    private final Color playerToMove;
    private final Move lastPlayedMove;

    public Position(Map<Square, Piece> pieces, boolean whiteCanCastleQueenSide, boolean whiteCanCastleKingSide, boolean blackCanCastleQueenSide, boolean blackCanCastleKingSide, Color playerToMove, Move lastPlayedMove) {
        this.pieces = pieces;
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        this.playerToMove = playerToMove;
        this.lastPlayedMove = lastPlayedMove;
    }

    public Map<Square, Piece> getPieces() {
        return pieces;
    }

    @Override
    public Set<Action> getActions() {
        Set<Action> moves = new HashSet<>();
        for (Square square : Square.values()) {
            Piece piece = pieces.get(square);
            if (piece != null && piece.getColor() == playerToMove) {
                moves.addAll(piece.getLegalMoves(this));
            }
        }
        return moves;
    }

    @Override
    public Position newState(Action action) {
        Move move = (Move) action;
        Square startingSquare = move.getStartingSquare();
        HashMap<Square, Piece> piecesInNewPosition = new HashMap<>(this.pieces);
        boolean newWhiteCanCastleKingSide = whiteCanCastleKingSide;
        boolean newWhiteCanCastleQueenSide = whiteCanCastleQueenSide;
        boolean newBlackCanCastleKingSide = blackCanCastleKingSide;
        boolean newBlackCanCastleQueenSide = blackCanCastleQueenSide;
        piecesInNewPosition.put(move.getEndingSquare(), this.pieces.get(startingSquare));
        piecesInNewPosition.put(startingSquare,null);
        if (startingSquare == Square.E1) {
            //white king moving, loosing right to castle
            newWhiteCanCastleKingSide = false;
            newWhiteCanCastleQueenSide = false;
        }
        if (startingSquare == Square.E8) {
            //black king moving, loosing right to castle
            newBlackCanCastleKingSide = false;
            newBlackCanCastleQueenSide = false;
        }
        if (startingSquare == Square.A1) {
            //A1 rook moves, white loose right to castle queenside
            newWhiteCanCastleQueenSide = false;
        }
        if (startingSquare == Square.H1) {
            //A8 rook moves, white loose right to castle kingside
            newWhiteCanCastleKingSide = false;
        }
        if (startingSquare == Square.A8) {
            //H1 rook moves, black loose right to castle queenside
            newBlackCanCastleQueenSide = false;
        }
        if (startingSquare == Square.H8) {
            //H8 rook moves, black loose right to castle kingside
            newBlackCanCastleKingSide = false;
        }
        return new Position(
                piecesInNewPosition,
                newWhiteCanCastleQueenSide,
                newWhiteCanCastleKingSide,
                newBlackCanCastleQueenSide,
                newBlackCanCastleKingSide,
                this.playerToMove == Color.WHITE ? Color.BLACK : Color.WHITE,
                move
        );
    }

    @Override
    public double getHeuristicFunction() {
        return 0;
    }

    @Override
    public State deepCopy() {
        return new Position(
                new HashMap<>(pieces),
                whiteCanCastleQueenSide,
                whiteCanCastleKingSide,
                blackCanCastleQueenSide,
                blackCanCastleKingSide,
                playerToMove,
                lastPlayedMove
        );
    }
}
