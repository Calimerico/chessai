package com.chess;

import com.ai.Action;
import com.ai.DummyAction;
import com.ai.State;
import lombok.Getter;
import lombok.Value;

import java.util.*;

@Value
public class Position implements State {
    Map<Square, Piece> pieces;
    boolean whiteCanCastleQueenSide;
    boolean whiteCanCastleKingSide;
    boolean blackCanCastleQueenSide;
    boolean blackCanCastleKingSide;
    Color playerToMove;
    Move lastPlayedMove;
    Castle isLastMoveCastle;

    public Position(Map<Square, Piece> pieces, boolean whiteCanCastleQueenSide, boolean whiteCanCastleKingSide, boolean blackCanCastleQueenSide, boolean blackCanCastleKingSide, Color playerToMove, Move lastPlayedMove, Castle isLastMoveCastle) {
        this.pieces = Collections.unmodifiableMap(pieces);
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        this.playerToMove = playerToMove;
        this.lastPlayedMove = lastPlayedMove;
        this.isLastMoveCastle = isLastMoveCastle;
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
        Square endingSquare = move.getEndingSquare();
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
        if (endingSquare == Square.A1) {
            //queens rook captured, white cannot castle on that side
            newWhiteCanCastleQueenSide = false;
        }
        if (endingSquare == Square.A8) {
            //king rook captured, white cannot castle on that side
            newWhiteCanCastleKingSide = false;
        }
        if (endingSquare == Square.H1) {
            //queens rook captured, black cannot castle on that side
            newBlackCanCastleQueenSide = false;
        }
        if (endingSquare == Square.H8) {
            //king rook captured, black cannot castle on that side
            newBlackCanCastleKingSide = false;
        }
        Castle isLastMoveCastle = Castle.NO_CASTLE;
        if (isCastle(startingSquare, endingSquare)) {
            if (endingSquare == Square.C1) {
                isLastMoveCastle = Castle.QUEEN;
                piecesInNewPosition.put(Square.D1, new Piece(Color.WHITE, Square.D1, PieceType.ROOK));
                piecesInNewPosition.put(Square.A1, null);
            }
            if (endingSquare == Square.G1) {
                isLastMoveCastle = Castle.KING;
                piecesInNewPosition.put(Square.F1, new Piece(Color.WHITE, Square.F1, PieceType.ROOK));
                piecesInNewPosition.put(Square.H1, null);
            }
            if (endingSquare == Square.C8) {
                isLastMoveCastle = Castle.QUEEN;
                piecesInNewPosition.put(Square.D8, new Piece(Color.BLACK, Square.D8, PieceType.ROOK));
                piecesInNewPosition.put(Square.A8, null);
            }
            if (endingSquare == Square.G8) {
                isLastMoveCastle = Castle.KING;
                piecesInNewPosition.put(Square.F8, new Piece(Color.BLACK, Square.F8, PieceType.ROOK));
                piecesInNewPosition.put(Square.H8, null);
            }
        }
        return new Position(
                piecesInNewPosition,
                newWhiteCanCastleQueenSide,
                newWhiteCanCastleKingSide,
                newBlackCanCastleQueenSide,
                newBlackCanCastleKingSide,
                this.playerToMove == Color.WHITE ? Color.BLACK : Color.WHITE,
                move,
                isLastMoveCastle
        );
    }

    private boolean isCastle(Square startingSquare, Square endingSquare) {
        return (pieces.get(startingSquare).getPieceType() == PieceType.KING && startingSquare == Square.E1 && endingSquare == Square.G1 || endingSquare == Square.C1) ||
                (pieces.get(startingSquare).getPieceType() == PieceType.KING && startingSquare == Square.E8 && endingSquare == Square.G8 || endingSquare == Square.C8);
    }

    public List<Square> getAttackingSquaresByPlayer(Color color) {
        List<Square> squares = new ArrayList<>();
        Arrays.stream(Square.values())
                .map(pieces::get)
                .filter(piece -> piece != null && piece.getColor() == color).forEach(piece -> squares.addAll(piece.getAttackingSquares(this)));
        return squares;
    }

    @Override
    public double getHeuristicFunction() {
        double sum = 0.0;
        for (Square square : Square.values()) {
            Piece piece = pieces.get(square);
            if (piece != null) {
                if (piece.getColor() == Color.WHITE) {
                    sum+= piece.getPieceType().getValue();
                } else {
                    sum-= piece.getPieceType().getValue();
                }
            }
        }
        return sum;
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
                lastPlayedMove,
                isLastMoveCastle
        );
    }
}
