package com.chess;

import lombok.Getter;
import java.util.Map;

@Getter
class CastleEntity {
    boolean whiteCanCastleQueenSide;
    boolean whiteCanCastleKingSide;
    boolean blackCanCastleQueenSide;
    boolean blackCanCastleKingSide;
    Castle isLastMoveCastle;

    public CastleEntity(boolean whiteCanCastleQueenSide, boolean whiteCanCastleKingSide, boolean blackCanCastleQueenSide, boolean blackCanCastleKingSide) {
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
    }

    public CastleEntity(Square startingSquare, Square endingSquare, Position position, Map<Square, Piece> pieces) {
        whiteCanCastleKingSide = position.getCastleEntity().isWhiteCanCastleKingSide();
        whiteCanCastleQueenSide = position.getCastleEntity().isWhiteCanCastleQueenSide();
        blackCanCastleKingSide = position.getCastleEntity().isBlackCanCastleKingSide();
        blackCanCastleQueenSide = position.getCastleEntity().isBlackCanCastleQueenSide();

        if (position.getPieceTypeOnSquare(startingSquare) == PieceType.KING && position.getPieceColorOnSquare(startingSquare) == Color.WHITE) {
            //white king moving, loosing right to castle
            whiteCanCastleKingSide = false;
            whiteCanCastleQueenSide = false;
        }
        if (position.getPieceTypeOnSquare(startingSquare) == PieceType.KING && position.getPieceColorOnSquare(startingSquare) == Color.BLACK) {
            //black king moving, loosing right to castle
            blackCanCastleKingSide = false;
            blackCanCastleQueenSide = false;
        }
        if (startingSquare == Square.A1) {
            //A1 rook moves, white loose right to castle queenside
            whiteCanCastleQueenSide = false;
        }
        if (startingSquare == Square.H1) {
            //A8 rook moves, white loose right to castle kingside
            whiteCanCastleKingSide = false;
        }
        if (startingSquare == Square.A8) {
            //H1 rook moves, black loose right to castle queenside
            blackCanCastleQueenSide = false;
        }
        if (startingSquare == Square.H8) {
            //H8 rook moves, black loose right to castle kingside
            blackCanCastleKingSide = false;
        }
        if (endingSquare == Square.A1) {
            //queens rook captured, white cannot castle on that side
            whiteCanCastleQueenSide = false;
        }
        if (endingSquare == Square.H1) {
            //king rook captured, white cannot castle on that side
            whiteCanCastleKingSide = false;
        }
        if (endingSquare == Square.A8) {
            //queens rook captured, black cannot castle on that side
            blackCanCastleQueenSide = false;
        }
        if (endingSquare == Square.H8) {
            //king rook captured, black cannot castle on that side
            blackCanCastleKingSide = false;
        }
        Castle isLastMoveCastle = Castle.NO_CASTLE;
        if (isCastle(position, startingSquare, endingSquare)) {
            if (endingSquare == Square.C1) {
                isLastMoveCastle = Castle.QUEEN;
                pieces.put(Square.D1, new Piece(Color.WHITE, Square.D1, PieceType.ROOK));
                pieces.remove(Square.A1);
            }
            if (endingSquare == Square.G1) {
                isLastMoveCastle = Castle.KING;
                pieces.put(Square.F1, new Piece(Color.WHITE, Square.F1, PieceType.ROOK));
                pieces.remove(Square.H1);
            }
            if (endingSquare == Square.C8) {
                isLastMoveCastle = Castle.QUEEN;
                pieces.put(Square.D8, new Piece(Color.BLACK, Square.D8, PieceType.ROOK));
                pieces.remove(Square.A8);
            }
            if (endingSquare == Square.G8) {
                isLastMoveCastle = Castle.KING;
                pieces.put(Square.F8, new Piece(Color.BLACK, Square.F8, PieceType.ROOK));
                pieces.remove(Square.H8);
            }
        }

        this.isLastMoveCastle = isLastMoveCastle;
    }

    private static boolean isCastle(Position position, Square startingSquare, Square endingSquare) {
        return (
                position.getPieceTypeOnSquare(startingSquare) == PieceType.KING &&
                        startingSquare == Square.E1 &&
                        (endingSquare == Square.G1 || endingSquare == Square.C1)
        ) ||
                (
                 position.getPieceTypeOnSquare(startingSquare) == PieceType.KING &&
                         startingSquare == Square.E8 &&
                         (endingSquare == Square.G8 || endingSquare == Square.C8));
    }


}
