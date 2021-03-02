package com.chess.movementrules;

import com.chess.*;

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
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(position,move))
                .collect(Collectors.toSet());
        //castle
        if (currentSquare == Square.E1) {
            if (position.isWhiteCanCastleKingSide()) {
                legalMoves.add(new Move(currentSquare, Square.G1, 0,CheckRuleMovement.isKingInCheckAfterMove(position,new Move(currentSquare, Square.G1))));
            }
            if (position.isWhiteCanCastleQueenSide()) {
                legalMoves.add(new Move(currentSquare, Square.C1, 0,CheckRuleMovement.isKingInCheckAfterMove(position,new Move(currentSquare, Square.C1))));

            }
        }
        if (currentSquare == Square.E8) {
            if (position.isBlackCanCastleKingSide()) {
                legalMoves.add(new Move(currentSquare, Square.G8, 0,CheckRuleMovement.isKingInCheckAfterMove(position,new Move(currentSquare, Square.G8))));
            }
            if (position.isBlackCanCastleQueenSide()) {
                legalMoves.add(new Move(currentSquare, Square.C8, 0,CheckRuleMovement.isKingInCheckAfterMove(position,new Move(currentSquare, Square.C8))));
            }
        }
        return legalMoves;

    }

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {

            Color myColor = position.getPieceColorOnSquare(currentSquare);
        Set<Square> attackingSquares = new HashSet<>();

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
