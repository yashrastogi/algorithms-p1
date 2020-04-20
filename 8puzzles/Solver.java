/* *****************************************************************************
 *  Name: Solver.java
 *  Date: 25-03-2020
 *  Description: 8-puzzle solver
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private final Board initial;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        MinPQ<SearchNode> searchNodes = new MinPQ<>((o1, o2) -> {
            return Integer.compare(o1.currentBoard.manhattan() + o1.numberOfMoves,
                                   o2.currentBoard.manhattan() + o2.numberOfMoves);
        });
        searchNodes.insert(new SearchNode(initial, 0, null));
        SearchNode sn = searchNodes.delMin();
        int count = 1;
        while (!sn.currentBoard.isGoal()) {

            for (Board b : sn.currentBoard.neighbors()) {
                searchNodes.insert(new SearchNode(b, count, sn));
            }
            count++;
            for (SearchNode s : searchNodes) {
                print("\npriority = " + (s.currentBoard.manhattan() + s.numberOfMoves) + "\n");
                print("moves = " + s.numberOfMoves + "\n");
                print("manhattan = " + s.currentBoard.manhattan() + "\n");
                print(s.currentBoard+"\n");
            }
            print("------------------------------");
            sn = searchNodes.delMin();
        }
        // print("Found goal");
    }

    private static <T> void print(T s) {
        System.out.print("" + s);
    }

    // test client (see below)
    public static void main(String[] args) {
        // In in = new In("puzzle06.txt");
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         tiles[i][j] = in.readInt();
        //     }
        // }
        int[][] tiles = {{0,1,3},{4,2,5},{7,8,6}};
        System.out.println((new Solver(new Board(tiles))).moves());

    }

    // min number of moves to solve initial board
    public int moves() {
        return initial.manhattan();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return null;
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
