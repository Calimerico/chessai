package com.chess;

public class PerformanceMonitor {

    public static void start() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    System.out.println("---------------------------");

                    System.out.println("Total number of positions: " + getTotalNumberOfPositions());
                    System.out.println("\n");

                    System.out.println("Number of calls to isKingInCheck: " + getIsKingInCheckCounter());
                    System.out.println("Time spent in isKingInCheck in ms: " + getIsKingInCheckTime());
                    System.out.println("Avg time for isKingInCheck in ns: " + getIsKingInCheckAvgTime());
                    System.out.println("\n");

                    System.out.println("Number of calls to getAttackingSquares: " + getGetAttackingSquaresCounter());
                    System.out.println("Time spent in getAttackingSquares in ms: " + getGetAttackingSquaresTime());
                    System.out.println("Avg time for getAttackingSquares in ns: " + getGetAttackingSquaresAvgTime());

                    System.out.println("---------------------------");
                    totalNumberOfPositions = 0;

                    isKingInCheckStart = 0;
                    isKingInCheckCounter = 0;
                    isKingInCheckTime = 0;

                    getAttackingSquaresStart = 0;
                    getAttackingSquaresCounter = 0;
                    getAttackingSquaresTime = 0;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static long totalNumberOfPositions;

    public static void newPosition() {
        totalNumberOfPositions++;
    }

    public static long getTotalNumberOfPositions() {
        return totalNumberOfPositions;
    }





    private static long isKingInCheckStart;
    private static long isKingInCheckCounter;
    private static long isKingInCheckTime;

    public static void isKingInCheckStart() {
        isKingInCheckStart = System.nanoTime();
    }

    public static void isKingInCheckEnd() {
        isKingInCheckCounter++;
        isKingInCheckTime += System.nanoTime() - isKingInCheckStart;
    }

    public static long getIsKingInCheckStart() {
        return isKingInCheckStart;
    }

    public static long getIsKingInCheckCounter() {
        return isKingInCheckCounter;
    }

    public static long getIsKingInCheckTime() {
        return isKingInCheckTime/1000000;
    }

    public static long getIsKingInCheckAvgTime() {
        return isKingInCheckTime/isKingInCheckCounter;
    }






    private static long getAttackingSquaresStart;
    private static long getAttackingSquaresCounter;
    private static long getAttackingSquaresTime;

    public static void getAttackingSquaresStart() {
        getAttackingSquaresStart = System.nanoTime();
    }

    public static void getAttackingSquaresEnd() {
        getAttackingSquaresCounter++;
        getAttackingSquaresTime += System.nanoTime() - getAttackingSquaresStart;
    }

    public static long getGetAttackingSquaresStart() {
        return getAttackingSquaresStart;
    }

    public static long getGetAttackingSquaresCounter() {
        return getAttackingSquaresCounter;
    }

    public static long getGetAttackingSquaresTime() {
        return getAttackingSquaresTime/1000000;
    }

    public static long getGetAttackingSquaresAvgTime() {
        return getAttackingSquaresTime/getAttackingSquaresCounter;
    }
}
