package com.chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.CastleRight;
import com.github.bhlangonijr.chesslib.Side;

import java.util.HashMap;
import java.util.Map;

public class PositionGenerator {

    public static Position fromFEN(String FEN) {
        Board board = new Board();
        board.loadFromFen(FEN);
        Color playerToMove = Color.fromSide(board.getSideToMove());
        Map<Square, Piece> pieces = new HashMap<>();
        CastleRight whiteCastleRight = board.getCastleRight().get(Side.WHITE);
        CastleRight blackCastleRight = board.getCastleRight().get(Side.BLACK);
        boolean whiteCanCastleQueenSide = whiteCastleRight == CastleRight.KING_AND_QUEEN_SIDE || whiteCastleRight == CastleRight.QUEEN_SIDE;
        boolean whiteCanCastleKingSide = whiteCastleRight == CastleRight.KING_AND_QUEEN_SIDE || whiteCastleRight == CastleRight.KING_SIDE;
        boolean blackCanCastleQueenSide = blackCastleRight == CastleRight.KING_AND_QUEEN_SIDE || blackCastleRight == CastleRight.QUEEN_SIDE;;
        boolean blackCanCastleKingSide = blackCastleRight == CastleRight.KING_AND_QUEEN_SIDE || blackCastleRight == CastleRight.KING_SIDE;;

        Square whiteKingPosition = null;
        Square blackKingPosition = null;
        for (com.github.bhlangonijr.chesslib.Square square : com.github.bhlangonijr.chesslib.Square.values()) {
            Square mySquare = Square.fromLibSquare(square);
            com.github.bhlangonijr.chesslib.Piece piece = board.getPiece(square);
            switch (piece) {
                case NONE:
                    break;
                case BLACK_KING:
                    blackKingPosition = mySquare;
                    pieces.put(mySquare, new Piece(Color.BLACK,mySquare, PieceType.KING));
                    break;
                case BLACK_BISHOP:
                    pieces.put(mySquare, new Piece(Color.BLACK,mySquare, PieceType.BISHOP));
                    break;
                case BLACK_KNIGHT:
                    pieces.put(mySquare, new Piece(Color.BLACK,mySquare, PieceType.KNIGHT));
                    break;
                case BLACK_PAWN:
                    pieces.put(mySquare, new Piece(Color.BLACK,mySquare, PieceType.PAWN));
                    break;
                case BLACK_QUEEN:
                    pieces.put(mySquare, new Piece(Color.BLACK,mySquare, PieceType.QUEEN));
                    break;
                case BLACK_ROOK:
                    pieces.put(mySquare, new Piece(Color.BLACK,mySquare, PieceType.ROOK));
                    break;
                case WHITE_KING:
                    whiteKingPosition = mySquare;
                    pieces.put(mySquare, new Piece(Color.WHITE,mySquare, PieceType.KING));
                    break;
                case WHITE_BISHOP:
                    pieces.put(mySquare, new Piece(Color.WHITE,mySquare, PieceType.BISHOP));
                    break;
                case WHITE_KNIGHT:
                    pieces.put(mySquare, new Piece(Color.WHITE,mySquare, PieceType.KNIGHT));
                    break;
                case WHITE_PAWN:
                    pieces.put(mySquare, new Piece(Color.WHITE,mySquare, PieceType.PAWN));
                    break;
                case WHITE_QUEEN:
                    pieces.put(mySquare, new Piece(Color.WHITE,mySquare, PieceType.QUEEN));
                    break;
                case WHITE_ROOK:
                    pieces.put(mySquare, new Piece(Color.WHITE,mySquare, PieceType.ROOK));
                    break;
                default:
                    throw new RuntimeException("Piece not recognized: " + piece);
            }
        }

        return new Position(
                pieces,
                playerToMove,
                null,
                new CastleEntity(
                        whiteCanCastleQueenSide,
                        whiteCanCastleKingSide,
                        blackCanCastleQueenSide,
                        blackCanCastleKingSide
                ),
                whiteKingPosition,
                blackKingPosition
        );
    }

    public static Position initialChess960Position(int startPosition) {
        throw new UnsupportedOperationException("Chess960 not supported yet");
    }

    public static Position initialChess960Position() {
        throw new UnsupportedOperationException("Chess960 not supported yet");
    }

    public static Position initialPosition() {
        HashMap<Square, Piece> pieces = new HashMap<>();
        pieces.put(Square.A1, new Piece(Color.WHITE,Square.A1, PieceType.ROOK));
        pieces.put(Square.B1, new Piece(Color.WHITE,Square.B1, PieceType.KNIGHT));
        pieces.put(Square.C1, new Piece(Color.WHITE,Square.C1, PieceType.BISHOP));
        pieces.put(Square.D1, new Piece(Color.WHITE,Square.D1, PieceType.QUEEN));
        pieces.put(Square.E1, new Piece(Color.WHITE,Square.E1, PieceType.KING));
        pieces.put(Square.F1, new Piece(Color.WHITE,Square.F1, PieceType.BISHOP));
        pieces.put(Square.G1, new Piece(Color.WHITE,Square.G1, PieceType.KNIGHT));
        pieces.put(Square.H1, new Piece(Color.WHITE,Square.H1, PieceType.ROOK));


        pieces.put(Square.A2, new Piece(Color.WHITE,Square.A2, PieceType.PAWN));
        pieces.put(Square.B2, new Piece(Color.WHITE,Square.B2, PieceType.PAWN));
        pieces.put(Square.C2, new Piece(Color.WHITE,Square.C2, PieceType.PAWN));
        pieces.put(Square.D2, new Piece(Color.WHITE,Square.D2, PieceType.PAWN));
        pieces.put(Square.E2, new Piece(Color.WHITE,Square.E2, PieceType.PAWN));
        pieces.put(Square.F2, new Piece(Color.WHITE,Square.F2, PieceType.PAWN));
        pieces.put(Square.G2, new Piece(Color.WHITE,Square.G2, PieceType.PAWN));
        pieces.put(Square.H2, new Piece(Color.WHITE,Square.H2, PieceType.PAWN));

        pieces.put(Square.A8, new Piece(Color.BLACK, Square.A8, PieceType.ROOK));
        pieces.put(Square.B8, new Piece(Color.BLACK, Square.B8, PieceType.KNIGHT));
        pieces.put(Square.C8, new Piece(Color.BLACK, Square.C8, PieceType.BISHOP));
        pieces.put(Square.D8, new Piece(Color.BLACK, Square.D8, PieceType.QUEEN));
        pieces.put(Square.E8, new Piece(Color.BLACK, Square.E8, PieceType.KING));
        pieces.put(Square.F8, new Piece(Color.BLACK, Square.F8, PieceType.BISHOP));
        pieces.put(Square.G8, new Piece(Color.BLACK, Square.G8, PieceType.KNIGHT));
        pieces.put(Square.H8, new Piece(Color.BLACK, Square.H8, PieceType.ROOK));

        pieces.put(Square.A7, new Piece(Color.BLACK, Square.A7, PieceType.PAWN));
        pieces.put(Square.B7, new Piece(Color.BLACK, Square.B7, PieceType.PAWN));
        pieces.put(Square.C7, new Piece(Color.BLACK, Square.C7, PieceType.PAWN));
        pieces.put(Square.D7, new Piece(Color.BLACK, Square.D7, PieceType.PAWN));
        pieces.put(Square.E7, new Piece(Color.BLACK, Square.E7, PieceType.PAWN));
        pieces.put(Square.F7, new Piece(Color.BLACK, Square.F7, PieceType.PAWN));
        pieces.put(Square.G7, new Piece(Color.BLACK, Square.G7, PieceType.PAWN));
        pieces.put(Square.H7, new Piece(Color.BLACK, Square.H7, PieceType.PAWN));

        return new Position(
                pieces,
                Color.WHITE,
                null,
                new CastleEntity(
                        true,
                        true,
                        true,
                        true
                        ),
                Square.E1,
                Square.E8
        );
    }
}
