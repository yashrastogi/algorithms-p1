/* *****************************************************************************
 *  Name: Solver.java
 *  Date: 25-03-2020
 *  Description: 8-puzzle solver
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final int moves;
    private final boolean isSolvable;
    private final SearchNode goalSN;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> searchNodes = new MinPQ<>(
                (o1, o2) -> Integer.compare(o1.manhattanPriorityVal, o2.manhattanPriorityVal));
        searchNodes.insert(new SearchNode(initial, 0, null, true));
        searchNodes.insert(new SearchNode(initial.twin(), 0, null, false));
        SearchNode sn = searchNodes.delMin();

        while (sn == null || !sn.currentBoard.isGoal()) {
            for (Board b : sn.currentBoard.neighbors()) {
                // critical optimization
                if (sn.previousSN != null) {
                    if (!b.equals(sn.previousSN.currentBoard))
                        searchNodes
                                .insert(new SearchNode(b, sn.numberOfMoves + 1, sn, sn.fromBoard));
                }
                else
                    searchNodes.insert(new SearchNode(b, sn.numberOfMoves + 1, sn, sn.fromBoard));
            }
            sn = searchNodes.delMin();
        }

        if (!sn.fromBoard) {
            isSolvable = false;
            moves = -1;
        }
        else {
            isSolvable = true;
            moves = sn.numberOfMoves;
        }
        goalSN = sn;
    }

    private static <T> void print(T s) {
        System.out.println("" + s);
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
        Stack<Board> solutionPath = new Stack<>();
        SearchNode currSN = goalSN;
        if (!isSolvable) return null;
        while (currSN.previousSN != null) {
            solutionPath.push(currSN.currentBoard);
            currSN = currSN.previousSN;
        }
        solutionPath.push(currSN.currentBoard);
        return solutionPath;
    }

    private class SearchNode {
        private final Board currentBoard;
        private final int numberOfMoves;
        private final SearchNode previousSN;
        private final int manhattanPriorityVal;
        private final boolean fromBoard;

        public SearchNode(Board current, int numberOfMoves, SearchNode previousSN,
                          boolean fromBoard) {
            this.currentBoard = current;
            this.numberOfMoves = numberOfMoves;
            this.previousSN = previousSN;
            this.fromBoard = fromBoard;
            manhattanPriorityVal = current.manhattan() + numberOfMoves;
        }

        public String toString() {
            return "{mp: " + manhattanPriorityVal + "; moves: " + numberOfMoves + "; fromBoard: "
                    + fromBoard + "}";
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In("puzzle36.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        // tiles = new int[][] { { 1,2,3 }, { 8,6,7 }, { 4,0,5 } };
        Solver sv = new Solver(new Board(tiles));
        print("isSolvable: " + sv.isSolvable());
        print("moves: " + sv.moves());
        // print("Moves: " + sv.moves());
        // print("Path:");
        for (Board b : sv.solution()) {
            print(b);
        }
    }

}
