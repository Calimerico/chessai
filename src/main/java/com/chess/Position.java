package com.chess;

import com.ai.Action;
import com.ai.MiniMaxState;
import com.ai.State;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter //todo wtf ddd getter
public class Position implements MiniMaxState {
    Map<Square, Piece> pieces;
    boolean whiteCanCastleQueenSide;
    boolean whiteCanCastleKingSide;
    boolean blackCanCastleQueenSide;
    boolean blackCanCastleKingSide;
    Color playerToMove;
    Move lastPlayedMove;
    Castle isLastMoveCastle;
    Square whiteKingPosition;
    Square blackKingPosition;
    int numberOfPieces;

    public Position(Map<Square, Piece> pieces, boolean whiteCanCastleQueenSide, boolean whiteCanCastleKingSide, boolean blackCanCastleQueenSide, boolean blackCanCastleKingSide, Color playerToMove, Move lastPlayedMove, Castle isLastMoveCastle) {
        this.pieces = Collections.unmodifiableMap(pieces);
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        this.playerToMove = playerToMove;
        this.lastPlayedMove = lastPlayedMove;
        this.isLastMoveCastle = isLastMoveCastle;
        this.whiteKingPosition = Arrays.stream(Square.values()).filter(square -> {
            Piece piece = pieces.get(square);
            return piece != null && piece.getColor() == Color.WHITE && piece.getPieceType() == PieceType.KING;
        }).findFirst().orElseThrow(() -> {
            RuntimeException runtimeException = new RuntimeException("There must be white king on he board! Pieces present: " + pieces);
            return runtimeException;
        });
        this.blackKingPosition = Arrays.stream(Square.values()).filter(square -> {
            Piece piece = pieces.get(square);
            return piece != null && piece.getColor() == Color.BLACK && piece.getPieceType() == PieceType.KING;
        }).findFirst().orElseThrow(() -> new RuntimeException("There must be black king on he board! Pieces present: " + pieces));
        this.numberOfPieces = this.pieces.size();
    }

    @Override
    public Set<Action> getActions() {
        Set<Action> moves = new TreeSet<>();
        for (Square square : Square.values()) {
            Piece piece = pieces.get(square);
            if (piece != null && piece.getColor() == playerToMove) {
                Set<Action> legalMoves = piece.getLegalMoves(this).stream().map(move -> ((Action) move)).collect(Collectors.toSet());
                moves.addAll(legalMoves);
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
        Piece pieceOnOldSquare = this.pieces.get(startingSquare);
        piecesInNewPosition.put(move.getEndingSquare(), new Piece(pieceOnOldSquare.getColor(), move.getEndingSquare(), pieceOnOldSquare.getPieceType()));
        piecesInNewPosition.remove(startingSquare);
        if (getPieceTypeOnSquare(startingSquare) == PieceType.KING && getPieceColorOnSquare(startingSquare) == Color.WHITE) {
            //white king moving, loosing right to castle
            newWhiteCanCastleKingSide = false;
            newWhiteCanCastleQueenSide = false;
        }
        if (getPieceTypeOnSquare(startingSquare) == PieceType.KING && getPieceColorOnSquare(startingSquare) == Color.BLACK) {
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
                piecesInNewPosition.remove(Square.A1);
            }
            if (endingSquare == Square.G1) {
                isLastMoveCastle = Castle.KING;
                piecesInNewPosition.put(Square.F1, new Piece(Color.WHITE, Square.F1, PieceType.ROOK));
                piecesInNewPosition.remove(Square.H1);
            }
            if (endingSquare == Square.C8) {
                isLastMoveCastle = Castle.QUEEN;
                piecesInNewPosition.put(Square.D8, new Piece(Color.BLACK, Square.D8, PieceType.ROOK));
                piecesInNewPosition.remove(Square.A8);
            }
            if (endingSquare == Square.G8) {
                isLastMoveCastle = Castle.KING;
                piecesInNewPosition.put(Square.F8, new Piece(Color.BLACK, Square.F8, PieceType.ROOK));
                piecesInNewPosition.remove(Square.H8);
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
        return (getPieceTypeOnSquare(startingSquare) == PieceType.KING && startingSquare == Square.E1 && (endingSquare == Square.G1 || endingSquare == Square.C1)) ||
                (getPieceTypeOnSquare(startingSquare) == PieceType.KING && startingSquare == Square.E8 && (endingSquare == Square.G8 || endingSquare == Square.C8));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return whiteCanCastleQueenSide == position.whiteCanCastleQueenSide &&
                whiteCanCastleKingSide == position.whiteCanCastleKingSide &&
                blackCanCastleQueenSide == position.blackCanCastleQueenSide &&
                blackCanCastleKingSide == position.blackCanCastleKingSide &&
                numberOfPieces == position.numberOfPieces &&
                pieces.equals(position.pieces) &&
                playerToMove == position.playerToMove &&
                Objects.equals(lastPlayedMove, position.lastPlayedMove) &&
                isLastMoveCastle == position.isLastMoveCastle &&
                whiteKingPosition == position.whiteKingPosition &&
                blackKingPosition == position.blackKingPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces, whiteCanCastleQueenSide, whiteCanCastleKingSide, blackCanCastleQueenSide, blackCanCastleKingSide, playerToMove, lastPlayedMove, isLastMoveCastle, whiteKingPosition, blackKingPosition, numberOfPieces);
    }

    @Override
    public boolean maxPlayer() {
        return playerToMove == Color.WHITE;
    }

    public Piece getPieceAtSquare(Square square) {
        return getPieces().get(square);
    }

    public Color getPieceColorOnSquare(Square square) {
        return getPieceAtSquare(square).getColor();
    }

    public PieceType getPieceTypeOnSquare(Square square) {
        Piece pieceAtSquare = getPieceAtSquare(square);
        return pieceAtSquare == null ? null : pieceAtSquare.getPieceType();
    }

    public double getPieceValueOnSquare(Square square) {
        PieceType pieceTypeOnSquare = getPieceTypeOnSquare(square);
        return pieceTypeOnSquare == null ? 0 : pieceTypeOnSquare.getValue();
    }

    public Square getKingPosition(Color color) {
        return color == Color.WHITE ? whiteKingPosition : blackKingPosition;
    }

    public boolean isSquareEmpty(Square square) {
        return getPieces().get(square) == null;
    }

    public boolean isSquareAttackedBy(Color color, Square square) {
        //todo improve performance, we need to cache which squares are attacked by which player in position!
        return getAttackingSquaresByPlayer(color).contains(square);
    }

    public boolean isNotCheckForCastle(Castle castle, Color color) {
        if (color == Color.WHITE) {
            if (castle == Castle.KING) {
                return
                        isSquareAttackedBy(Color.BLACK, Square.E1) ||
                        isSquareAttackedBy(Color.BLACK, Square.F1) ||
                        isSquareAttackedBy(Color.BLACK, Square.G1);
            } else {
                return
                        isSquareAttackedBy(Color.BLACK, Square.E1) ||
                                isSquareAttackedBy(Color.BLACK, Square.D1) ||
                                isSquareAttackedBy(Color.BLACK, Square.C1);
            }
        } else {
            if (castle == Castle.KING) {
                return
                        isSquareAttackedBy(Color.WHITE, Square.E8) ||
                                isSquareAttackedBy(Color.WHITE, Square.F8) ||
                                isSquareAttackedBy(Color.WHITE, Square.G8);
            } else {
                return
                        isSquareAttackedBy(Color.WHITE, Square.E8) ||
                                isSquareAttackedBy(Color.WHITE, Square.D8) ||
                                isSquareAttackedBy(Color.WHITE, Square.C8);
            }
        }
    }


}
