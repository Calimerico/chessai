package com.chess;

public enum Square {
    A1(0,0, false, true),
    A2(0, 1, false, true),
    A3(0, 2, false, true),
    A4(0, 3, false, true),
    A5(0, 4, false, true),
    A6(0, 5, false, true),
    A7(0, 6, false, true),
    A8(0, 7, false, true),

    B1(1, 0, false, true),
    B2(1, 1, false, false),
    B3(1, 2, false, false),
    B4(1, 3, false, false),
    B5(1, 4, false, false),
    B6(1, 5, false, false),
    B7(1, 6, false, false),
    B8(1, 7, false, true),

    C1(2, 0, false, true),
    C2(2, 1, false, false),
    C3(2, 2, false, false),
    C4(2, 3, false, false),
    C5(2, 4, false, false),
    C6(2, 5, false, false),
    C7(2, 6, false, false),
    C8(2, 7, false, true),

    D1(3, 0, false, true),
    D2(3, 1, false, false),
    D3(3, 2, false, false),
    D4(3, 3, true, false),
    D5(3, 4, true, false),
    D6(3, 5, false, false),
    D7(3, 6, false, false),
    D8(3, 7, false, true),

    E1(4, 0, false, true),
    E2(4, 1, false, false),
    E3(4, 2, false, false),
    E4(4, 3, true, false),
    E5(4, 4, true, false),
    E6(4, 5, false, false),
    E7(4, 6, false, false),
    E8(4, 7, false, true),

    F1(5, 0, false, true),
    F2(5, 1, false, false),
    F3(5, 2, false, false),
    F4(5, 3, false, false),
    F5(5, 4, false, false),
    F6(5, 5, false, false),
    F7(5, 6, false, false),
    F8(5, 7, false, true),

    G1(6, 0, false,  true),
    G2(6, 1, false,  false),
    G3(6, 2, false,  false),
    G4(6, 3, false,  false),
    G5(6, 4, false,  false),
    G6(6, 5, false,  false),
    G7(6, 6, false,  false),
    G8(6, 7, false,  true),

    H1(7, 0, false, true),
    H2(7, 1, false, true),
    H3(7, 2, false, true),
    H4(7, 3, false, true),
    H5(7, 4, false, true),
    H6(7, 5, false, true),
    H7(7, 6, false, true),
    H8(7, 7, false, true);

    private final int file;
    private final int rank;
    private final boolean narrowCenter;
    private final boolean edge;

    Square(int file, int rank, boolean narrowCenter, boolean edge) {
        this.file = file;
        this.rank = rank;
        this.narrowCenter = narrowCenter;
        this.edge = edge;
    }

    public boolean isNarrowCenter() {
        return narrowCenter;
    }

    public boolean isEdge() {
        return edge;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public static Square fromLibSquare(com.github.bhlangonijr.chesslib.Square square) {
        return Square.calculateSquareFromCoordinates(square.getFile().ordinal(), square.getRank().ordinal());
    }

    public static Square calculateSquareFromCoordinates(int file, int rank) {
        int i = 8 * file + rank;

        if (i >= 64 || file > 7 || rank > 7 || file < 0 || rank < 0) {
            return null;
        }
        return Square.values()[i];
    }
}
