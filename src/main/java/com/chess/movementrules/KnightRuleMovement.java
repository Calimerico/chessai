package com.chess.movementrules;

import com.chess.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class KnightRuleMovement {

    public static Set<Move> getLegalMoves(Position position, Square currentSquare) {
        Set<Move> moves = getAttackingSquares(position, currentSquare)
                .stream()
                .map(square -> new Move(
                        currentSquare,
                        square,
                        position
                ))
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(position, move))
                .collect(Collectors.toSet());
        return moves;
    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieceColorOnSquare(currentSquare);
        Set<Square> attackingSquares = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank + 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 1, rank - 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank + 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 1, rank - 2));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 2, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file - 2, rank - 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 2, rank + 1));
        addEndingSquareIfAppropriate(position, myColor, attackingSquares, Square.calculateSquareFromCoordinates(file + 2, rank - 1));
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
