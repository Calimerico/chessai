package com.chess;

import com.ai.Action;
import com.ai.MiniMaxState;
import com.ai.State;
import com.ai.ZobristValue;
import com.chess.heuristic.HeuristicManager;
import com.chess.heuristic.InsufficientMaterial;
import com.chess.moveorder.MoveOrderManager;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter //todo wtf ddd getter
public class Position implements MiniMaxState {
    private static final HeuristicManager heuristicManager = new HeuristicManager();
    Map<Square, Piece> pieces;
    CastleEntity castleEntity;
    Color playerToMove;
    Move lastPlayedMove;
    Square whiteKingPosition;
    Square blackKingPosition;
    int numberOfPieces;
    Set<Square> attSquaresByBlack;
    Set<Square> attSquaresByWhite;
    Set<Action> actions;
    Integer actionsSize;

    public Position(
            Map<Square, Piece> pieces,
            Color playerToMove,
            Move lastPlayedMove,
            CastleEntity castleEntity,
            Square whiteKingPositionInPreviousPosition,
            Square blackKingPositionInPreviousPosition
    ) {
        this.pieces = pieces;
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
        setActions(actions == null);
        return actions;
    }

    public int getActionsSize() {
        setActions(actionsSize == null);
        return actionsSize;
    }

    private void setActions(boolean shouldSetActions) {
        if (shouldSetActions) {
            actions = new TreeSet<>();
            pieces.values().forEach(piece -> {
                if (piece.getColor() == playerToMove) {
                    Set<Action> legalMoves = piece.getLegalMoves(this).stream().map(move -> ((Action) move)).collect(Collectors.toSet());
                    actions.addAll(legalMoves);
                }
            });
            actionsSize = actions.size();
        }
    }

    @Override
    public Position newState(Action action) {
        PerformanceMonitor.newPosition();
        Move move = (Move) action;

        Square startingSquare = move.getStartingSquare();
        Square endingSquare = move.getEndingSquare();
        EnumMap<Square, Piece> piecesInNewPosition = new EnumMap<>(getPieces());

        Piece pieceOnOldSquare = getPieces().get(startingSquare);
        piecesInNewPosition.put(move.getEndingSquare(), new Piece(pieceOnOldSquare.getColor(), move.getEndingSquare(), pieceOnOldSquare.getPieceType(), move.getPromoteTo()));
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



    public Set<Square> getAttackingSquaresByPlayer(Color color) {
        if (color == Color.WHITE) {
            if (attSquaresByWhite == null) {
                attSquaresByWhite = EnumSet.noneOf(Square.class);
                pieces.values().forEach(piece -> {
                    if (piece.getColor() == color) {
                        attSquaresByWhite.addAll(piece.getAttackingSquares(this));
                    }
                });
            }
            return attSquaresByWhite;
        } else {
            if (attSquaresByBlack == null) {
                attSquaresByBlack = EnumSet.noneOf(Square.class);
                pieces.values().forEach(piece -> {
                    if (piece.getColor() == color) {
                        attSquaresByBlack.addAll(piece.getAttackingSquares(this));
                    }
                });
            }
            return attSquaresByBlack;
        }
    }

    @Override
    public double getHeuristicFunction() {
        if (InsufficientMaterial.isInsufficient(this)) {
            return 0;
        }
        if (getActionsSize() == 0) {
            if (isKingInCheck()) {
                if (playerToMove == Color.WHITE) {
                    return -900000;
                } else {
                    return 900000;
                }
            } else {
                return 0;
            }
        }//todo extract somewhere

        return heuristicManager.getHeuristic(this);
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
        Piece pieceAtSquare = getPieceAtSquare(square);
        return pieceAtSquare == null ? null : pieceAtSquare.getColor();
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
