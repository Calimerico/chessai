package com.chess;

import com.ai.Action;
import com.ai.MiniMaxState;
import com.ai.State;
import com.ai.ZobristValue;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter //todo wtf ddd getter
public class Position implements MiniMaxState {
    Map<Square, Piece> pieces;
    CastleEntity castleEntity;
    Color playerToMove;
    Move lastPlayedMove;
    Square whiteKingPosition;
    Square blackKingPosition;
    int numberOfPieces;

    public Position(
            Map<Square, Piece> pieces,
            Color playerToMove,
            Move lastPlayedMove,
            CastleEntity castleEntity,
            Square whiteKingPositionInPreviousPosition,
            Square blackKingPositionInPreviousPosition
    ) {
        this.pieces = Collections.unmodifiableMap(pieces);
        this.castleEntity = castleEntity;
        this.playerToMove = playerToMove;
        this.lastPlayedMove = lastPlayedMove;
        this.whiteKingPosition = calculateKingPosition(whiteKingPositionInPreviousPosition, lastPlayedMove);
        this.blackKingPosition = calculateKingPosition(blackKingPositionInPreviousPosition, lastPlayedMove);
        this.numberOfPieces = this.pieces.size();
    }

    private Square calculateKingPosition(Square kingPositionInPreviousPosition, Move lastPlayedMove) {
        if (lastPlayedMove == null) {
            return kingPositionInPreviousPosition;
        }
        if (lastPlayedMove.getStartingSquare().equals(kingPositionInPreviousPosition)) {
            return lastPlayedMove.getEndingSquare();
        } else {
            return kingPositionInPreviousPosition;
        }
    }

    @Override
    public Set<Action> getActions() {
        Set<Action> moves = new TreeSet<>();
        pieces.values().forEach(piece -> {
            if (piece.getColor() == playerToMove) {
                Set<Action> legalMoves = piece.getLegalMoves(this).stream().map(move -> ((Action) move)).collect(Collectors.toSet());
                moves.addAll(legalMoves);
            }
        });
        return moves;
    }

    @Override
    public Position newState(Action action) {
        PerformanceMonitor.newPosition();
        Move move = (Move) action;

        Square startingSquare = move.getStartingSquare();
        Square endingSquare = move.getEndingSquare();
        HashMap<Square, Piece> piecesInNewPosition = new HashMap<>(getPieces());

        Piece pieceOnOldSquare = getPieces().get(startingSquare);
        piecesInNewPosition.put(move.getEndingSquare(), new Piece(pieceOnOldSquare.getColor(), move.getEndingSquare(), pieceOnOldSquare.getPieceType()));
        piecesInNewPosition.remove(startingSquare);
        if (move.isEnPassant()) {
            if (playerToMove == Color.WHITE) {
                piecesInNewPosition.remove(Square.calculateSquareFromCoordinates(move.getEndingSquare().getFile(), move.getEndingSquare().getRank() - 1));
            } else {
                piecesInNewPosition.remove(Square.calculateSquareFromCoordinates(move.getEndingSquare().getFile(), move.getEndingSquare().getRank() + 1));
            }
        }
        CastleEntity newCastleEntity = new CastleEntity(startingSquare, endingSquare, this, piecesInNewPosition);
        return new Position(
                piecesInNewPosition,
                this.playerToMove.opposite(),
                move,
                newCastleEntity,
                whiteKingPosition,
                blackKingPosition
        );
    }


    private boolean isKingInCheck() {
        return getAttackingSquaresByPlayer(playerToMove.opposite()).contains(getKingPosition(playerToMove));
    }



    public List<Square> getAttackingSquaresByPlayer(Color color) {
        List<Square> squares = new ArrayList<>();
        pieces.values().forEach(piece -> {
            if (piece.getColor() == color) {
                squares.addAll(piece.getAttackingSquares(this));
            }
        });
        return squares;
    }

    @Override
    public double getHeuristicFunction() {
        if (insufficientMaterial()) {
            return 0;
        }
        if (getActions().isEmpty()) {
            if (isKingInCheck()) {
                return -900000;
            } else {
                return 0;
            }
        }//todo extract somewhere
        double sum = 0.0;
        for (Piece piece : pieces.values()) {
            if (piece.getColor() == Color.WHITE) {
                sum+= piece.getPieceType().getValue();
            } else {
                sum-= piece.getPieceType().getValue();
            }
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return castleEntity.isWhiteCanCastleQueenSide() == position.getCastleEntity().isWhiteCanCastleQueenSide() &&
                castleEntity.isWhiteCanCastleKingSide() == position.getCastleEntity().isWhiteCanCastleKingSide() &&
                castleEntity.isBlackCanCastleQueenSide() == position.getCastleEntity().isBlackCanCastleQueenSide() &&
                castleEntity.isBlackCanCastleKingSide() == position.getCastleEntity().isBlackCanCastleKingSide() &&
                numberOfPieces == position.numberOfPieces &&
                pieces.equals(position.pieces) &&
                playerToMove == position.playerToMove &&
                Objects.equals(lastPlayedMove, position.lastPlayedMove) &&
                castleEntity.getIsLastMoveCastle() == position.getCastleEntity().getIsLastMoveCastle() &&
                whiteKingPosition == position.whiteKingPosition &&
                blackKingPosition == position.blackKingPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                pieces,
                castleEntity,
                playerToMove,
                lastPlayedMove,
                whiteKingPosition,
                blackKingPosition,
                numberOfPieces
        );
    }

    @Override
    public boolean maxPlayer() {
        return playerToMove == Color.WHITE;
    }

    @Override
    public List<ZobristValue> getAllZobristValues() {
        List<ZobristValue> zobristValues = new ArrayList<>(768);
        for (Square square : Square.values()) {
            for (PieceType pieceType : PieceType.values()) {
                for (Color color : Color.values()) {
                    zobristValues.add(new Piece(color, square, pieceType));
                }
            }
        }
        return Collections.unmodifiableList(zobristValues);
    }

    @Override
    public List<ZobristValue> getCurrentZobristValues() {
        return pieces.values().stream().map(squarePieceEntry -> ((ZobristValue) squarePieceEntry)).collect(Collectors.toList());
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

//    public boolean isNotCheckForCastle(Castle castle, Color color) {
//        if (color == Color.WHITE) {
//            if (castle == Castle.KING) {
//                return
//                        isSquareAttackedBy(Color.BLACK, Square.E1) ||
//                        isSquareAttackedBy(Color.BLACK, Square.F1) ||
//                        isSquareAttackedBy(Color.BLACK, Square.G1);
//            } else {
//                return
//                        isSquareAttackedBy(Color.BLACK, Square.E1) ||
//                                isSquareAttackedBy(Color.BLACK, Square.D1) ||
//                                isSquareAttackedBy(Color.BLACK, Square.C1);
//            }
//        } else {
//            if (castle == Castle.KING) {
//                return
//                        isSquareAttackedBy(Color.WHITE, Square.E8) ||
//                                isSquareAttackedBy(Color.WHITE, Square.F8) ||
//                                isSquareAttackedBy(Color.WHITE, Square.G8);
//            } else {
//                return
//                        isSquareAttackedBy(Color.WHITE, Square.E8) ||
//                                isSquareAttackedBy(Color.WHITE, Square.D8) ||
//                                isSquareAttackedBy(Color.WHITE, Square.C8);
//            }
//        }
//    }

    private boolean containsKnight() {
        return getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.KNIGHT);
    }

    private boolean containsBishop() {
        return getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.BISHOP);
    }

    private boolean containsKnight(Color color) {
        return getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.KNIGHT && piece.getColor() == color);
    }

    private boolean containsBishop(Color color) {
        return getPieces()
                .values()
                .stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.BISHOP && piece.getColor() == color);
    }

    private long getNumberOfPieces(Color color) {
        return getPieces().values().stream().filter(piece -> piece.getColor() == color).count();
    }


    public boolean insufficientMaterial() {
        return
                        getNumberOfPieces() == 2 ||
                        (getNumberOfPieces() == 3 && containsBishop()) ||
                        (getNumberOfPieces() == 3 && containsKnight()) ||
                        (
                                getNumberOfPieces(Color.WHITE) == 2 &&
                                getNumberOfPieces(Color.BLACK) == 2 &&
                                (containsKnight(Color.WHITE) || containsBishop(Color.WHITE)) && (containsKnight(Color.BLACK) || containsBishop(Color.BLACK))
                        )

                ;
    }

    public boolean isWhiteCanCastleKingSide() {
        return getCastleEntity().isWhiteCanCastleKingSide();
    }

    public boolean isWhiteCanCastleQueenSide() {
        return getCastleEntity().isWhiteCanCastleQueenSide();
    }

    public boolean isBlackCanCastleQueenSide() {
        return getCastleEntity().isBlackCanCastleQueenSide();
    }

    public boolean isBlackCanCastleKingSide() {
        return getCastleEntity().isBlackCanCastleKingSide();
    }

    public Castle getIsLastMoveCastle() {
        return getCastleEntity().getIsLastMoveCastle();
    }
}
