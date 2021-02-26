package com.chess.movementrules;

import com.chess.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BishopRuleMovement {

    public static Set<Square> getAttackingSquares(Position position, Square currentSquare) {
        Color myColor = position.getPieces().get(currentSquare).getColor();
        Set<Square> legalMoves = new HashSet<>();

        int rank = currentSquare.getRank();
        int file = currentSquare.getFile();

        for(int r = rank + 1, f = file + 1; r < 8 && f < 8; r++,f++) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            Piece pieceOnEndingSquare = position.getPieces().get(endingSquare);
            if (pieceOnEndingSquare != null) {
                if (pieceOnEndingSquare.getColor() != myColor) {
                    legalMoves.add(endingSquare);
                }
                break;
            } else {
                legalMoves.add(endingSquare);
            }
        }

        for(int r = rank - 1, f = file - 1; r >= 0 && f >= 0; r--,f--) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            Piece pieceOnEndingSquare = position.getPieces().get(endingSquare);
            if (pieceOnEndingSquare != null) {
                if (pieceOnEndingSquare.getColor() != myColor) {
                    legalMoves.add(endingSquare);
                }
                break;
            } else {
                legalMoves.add(endingSquare);
            }
        }

        for(int r = rank - 1, f = file + 1; r >= 0 && f < 8; r--,f++) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            Piece pieceOnEndingSquare = position.getPieces().get(endingSquare);
            if (pieceOnEndingSquare != null) {
                if (pieceOnEndingSquare.getColor() != myColor) {
                    legalMoves.add(endingSquare);
                }
                break;
            } else {
                legalMoves.add(endingSquare);
            }
        }

        for(int r = rank + 1, f = file - 1; r < 8 && f >= 0; r++,f--) {
            Square endingSquare = Square.calculateSquareFromCoorinates(f, r);

            Piece pieceOnEndingSquare = position.getPieces().get(endingSquare);
            if (pieceOnEndingSquare != null) {
                if (pieceOnEndingSquare.getColor() != myColor) {
                    legalMoves.add(endingSquare);
                }
                break;
            } else {
                legalMoves.add(endingSquare);
            }
        }
        return legalMoves;
    }

    public static Set<Move> getLegalMoves(Position position, Square currentSquare) {
        return getAttackingSquares(position, currentSquare)
                .stream()
                .map(endingSquare -> new Move(currentSquare, endingSquare))
                .filter(move -> !CheckRuleMovement.isKingInCheckAfterMove(position, move))
                .collect(Collectors.toSet());
    }
}
