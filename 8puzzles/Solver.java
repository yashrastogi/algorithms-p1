/* *****************************************************************************
 *  Name: Solver.java
 *  Date: 25-03-2020
 *  Description: 8-puzzle solver
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private int moves;
    private List<Board> solutionPath;
    private boolean isSolvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> searchNodesBoard = new MinPQ<>((o1, o2) -> Integer.compare(o1.currentBoard.manhattan() + o1.numberOfMoves,
                                                                            o2.currentBoard.manhattan() + o2.numberOfMoves));
        MinPQ<SearchNode> searchNodesTwin = new MinPQ<>((o1, o2) -> Integer.compare(o1.currentBoard.manhattan() + o1.numberOfMoves,
                                                                                     o2.currentBoard.manhattan() + o2.numberOfMoves));
        SearchNode snBoard = new SearchNode(initial, 0, null);
        SearchNode snTwin = new SearchNode(initial.twin(), 0, null);
        solutionPath = new ArrayList<>();
        solutionPath.add(initial);
        int countBoard = 1;
        int countTwin = 1;
        while (!snBoard.currentBoard.isGoal() && !snTwin.currentBoard.isGoal()) {
            for (Board b : snBoard.currentBoard.neighbors()) {
                // critical optimization
                if (snBoard.previousSN != null) {
                    if (!b.equals(snBoard.previousSN.currentBoard))
                        searchNodesBoard.insert(new SearchNode(b, countBoard, snBoard));
                }
                else
                    searchNodesBoard.insert(new SearchNode(b, countBoard, snBoard));
            }
            for (Board b : snTwin.currentBoard.neighbors()) {
                // critical optimization
                if (snTwin.previousSN != null) {
                    if (!b.equals(snTwin.previousSN.currentBoard))
                        searchNodesTwin.insert(new SearchNode(b, countTwin, snTwin));
                }
                else
                    searchNodesTwin.insert(new SearchNode(b, countTwin, snTwin));
            }
            countBoard++;
            countTwin++;
            snBoard = searchNodesBoard.delMin();
            snTwin = searchNodesTwin.delMin();
            solutionPath.add(snBoard.currentBoard);
        }
        if(snTwin.currentBoard.isGoal()) isSolvable = false;
        moves = snBoard.numberOfMoves;
    }

    private static <T> void print(T s) {
        System.out.println("" + s);
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In("puzzle04.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        // int[][] tiles = {{0,1,3},{4,2,5},{7,8,6}};
        Solver sv = new Solver(new Board(tiles));
        print("isSolvable: " + sv.isSolvable());
        // print("Moves: " + sv.moves());
        // print("Path:");
        // for (Board b : sv.solution()) {
        //     print(b);
        // }
    }

    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solutionPath;
    }

    private class SearchNode {
        private final Board currentBoard;
        private final int numberOfMoves;
        private final SearchNode previousSN;

        public SearchNode(Board current, int numberOfMoves, SearchNode previousSN) {
            this.currentBoard = current;
            this.numberOfMoves = numberOfMoves;
            this.previousSN = previousSN;
        }

        public Board getCurrentBoard() {
            return currentBoard;
        }

        public int getNumberOfMoves() {
            return numberOfMoves;
        }

        public SearchNode getPrevSearchN() {
            return previousSN;
        }
    }
}
