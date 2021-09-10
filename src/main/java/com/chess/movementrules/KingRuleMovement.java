package com.chess.movementrules;

import com.chess.*;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KingRuleMovement {


    public static Set<Move> getLegalMoves(Position position, Square currentSquare) {
        Set<Move> legalMoves = getAttackingSquares(position, currentSquare)
                .stream()
                .map(square -> new Move(
                        currentSquare,
                        square,
                        position
                ))
                .collect(Collectors.toSet());
        //castle
        if (currentSquare == Square.E1) {
            if (position.isWhiteCanCastleKingSide() && position.isSquareEmpty(Square.F1) && position.isSquareEmpty(Square.G1)) {
                legalMoves.add(new Move(currentSquare, Square.G1, position));
            }
            if (position.isWhiteCanCastleQueenSide() && position.isSquareEmpty(Square.D1) && position.isSquareEmpty(Square.C1) && position.isSquareEmpty(Square.B1)) {
                legalMoves.add(new Move(currentSquare, Square.C1, position));

            }
        }
        if (currentSquare == Square.E8) {
            if (position.isBlackCanCastleKingSide() && position.isSquareEmpty(Square.F8) && position.isSquareEmpty(Square.G8)) {
                legalMoves.add(new Move(currentSquare, Square.G8, position));
            }
            if (position.isBlackCanCastleQueenSide() && position.isSquareEmpty(Square.D8) && position.isSquareEmpty(Square.C8) && position.isSquareEmpty(Square.B8)) {
                legalMoves.add(new Move(currentSquare, Square.C8, position));
            }
        }
        return legalMoves;

    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {

            Color myColor = position.getPieceColorOnSquare(currentSquare);
        Set<Square> attackingSquares = EnumSet.noneOf(Square.class);

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank));
        return attackingSquares;
    }

    private static void addEndingSquareIfAppropriate(Position position, Color myColor, Set<Square> legalMoves, Square endingSquare) {
        if (endingSquare != null) {
            Piece pieceOnEndingSquare = position.getPieceAtSquare(endingSquare);
            if (pieceOnEndingSquare == null || pieceOnEndingSquare.getColor() != myColor) {
                legalMoves.add(endingSquare);
            }
        }
    }
}
