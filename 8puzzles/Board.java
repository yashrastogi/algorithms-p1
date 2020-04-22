/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    private final int[][] tiles;
    private int manhattan = -1;
    // private int isSolvable = -1;
    private Board goalBoard = null;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copy(tiles);
    }

    private static int[][] copy(int[][] arr) {
        int[][] tempArr = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                tempArr[i][j] = arr[i][j];
            }
        }
        return tempArr;
    }

    // public boolean isSolvable() {
    //     if (isSolvable == 0) return false;
    //     if (isSolvable == 1) return true;
    //
    //     int inversions = 0;
    //     for(int i=0; i<tiles.length; i++) {
    //         for(int j=0; j<tiles.length; j++) {
    //             if(tiles[i][j] > goalBoard().tiles[i][j] && tiles[i][j] != 0) {
    //                 inversions += tiles[i][j] - goalBoard().tiles[i][j];
    //             }
    //         }
    //     }
    //
    //     System.out.println("Inversions: "+inversions);
    //     return true;
    // }

    public static void main(String[] args) {
        // In in = new In("puzzle04.txt");
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         tiles[i][j] = in.readInt();
        //     }
        // }
        int[][] tiles = { { 5, 2, 8 }, { 4, 1, 7 }, { 0, 3, 6 } };
        Board board = new Board(tiles);
        System.out.println("" + board);
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Twin: " + board.twin());
        System.out.println("Neighbors: ");
        // for (int i = 0; i < 100; i++) System.out.println(board.manhattan());
        // for (Board b : board.neighbors()) {
        //     System.out.println(b);
        // }
        // board.isSolvable();
        // 4 1 3
        // 0 2 5
        // 7 8 6

        // 0 1 3
        // 4 2 5
        // 7 8 6
    }

    // number of tiles out of place
    public int hamming() {
        int incorrCount = 0;
        if (goalBoard == null) goalBoard = goalBoard();
        for (int i = 0; i < tiles.length; ++i)
            for (int j = 0; j < tiles.length; ++j)
                if (tiles[i][j] != goalBoard.tiles[i][j])
                    if (tiles[i][j] != 0)
                        incorrCount++;
        return incorrCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan != -1) return manhattan;
        manhattan = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                // if(tiles[i][j] == 0) break;
                manhattan += findUnitManhattan(new Point(i, j));
            }
        }
        return manhattan;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Point first = null, second = null;
        int random1 = 0, random2 = 0;
        while (first == null || second == null) {
            while (tiles[random1][random2] == 0) {
                random2++;
            }
            first = (first == null ? new Point(random1, random2) : first);
            second = new Point(random1, random2);
            random1++;
            if (first.equals(second)) second = null;
        }

        int[][] tilesTemp = copy(tiles);
        swap(tilesTemp, first, second);
        return new Board(tilesTemp);
    }

    private Board goalBoard() {
        int[][] tilesGoal = new int[this.tiles.length][this.tiles.length];
        int count = 1;
        for (int i = 0; i < tilesGoal.length; i++)
            for (int j = 0; j < tilesGoal.length; j++) {
                if (count == this.tiles.length * this.tiles.length) break;
                tilesGoal[i][j] = count++;
            }
        return new Board(tilesGoal);
    }

    private int findUnitManhattan(Point currPt) {
        if (tiles[currPt.getX()][currPt.getY()] == 0) return 0;
        if (goalBoard == null) goalBoard = goalBoard();
        Point goalPt = new Point(0, 0);
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles.length; j++)
                if (goalBoard.tiles[i][j] == tiles[currPt.getX()][currPt.getY()]) {
                    goalPt = new Point(i, j);
                    break;
                }
        int absXdiff = Math.abs(currPt.getX() - goalPt.getX()),
                absYdiff = Math.abs(currPt.getY() - goalPt.getY());
        return absXdiff + absYdiff;
    }

    private void swap(int[][] swapTiles, Point firstPoint, Point secondPoint) {
        int tileTemp = swapTiles[firstPoint.getX()][firstPoint.getY()];
        swapTiles[firstPoint.getX()][firstPoint.getY()] = swapTiles[secondPoint.getX()][secondPoint
                .getY()];
        swapTiles[secondPoint.getX()][secondPoint.getY()] = tileTemp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Point zeroPt = null;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    zeroPt = new Point(i, j);
                    break;
                }
            }
        }
        List<Board> boards = new ArrayList<>();
        int[][] tilesTemp;
        for (Point pt : trueNeighbors(zeroPt)) {
            tilesTemp = copy(this.tiles);
            swap(tilesTemp, pt, zeroPt);
            boards.add(new Board(tilesTemp));
        }
        return boards;
    }

    private List<Point> trueNeighbors(Point from) {
        List<Point> nb = new ArrayList<>();
        nb.add(new Point(from.getX() + 1, from.getY()));
        nb.add(new Point(from.getX(), from.getY() + 1));
        nb.add(new Point(from.getX() - 1, from.getY()));
        nb.add(new Point(from.getX(), from.getY() - 1));
        List<Point> validNB = new ArrayList<>(nb.subList(0, nb.size()));
        for (Point a : nb) {
            if (a.getX() < 0 || a.getY() < 0) validNB.remove(a);
            if (a.getX() >= tiles.length || a.getY() >= tiles.length) validNB.remove(a);
        }
        return validNB;
    }

    // private boolean max(int x) {
    //     return x == tiles.length - 1;
    // }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (goalBoard == null) goalBoard = goalBoard();
        return this.equals(goalBoard);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) return false;

        for (int i = 0; i < this.tiles.length; i++)
            for (int j = 0; j < this.tiles.length; j++)
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
        return true;
    }

    // string representation of this board
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("" + tiles.length + "\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                // if(tiles[i][j] == 0) buf.append("*" + "\t");
                // else
                    buf.append(tiles[i][j] + "\t");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    private static class Point {
        private final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int hashCode() {
            return Objects.hash(x, y);
        }

        public boolean equals(Object second) {
            if (second == null) return false;
            // if (this == null) return false;
            if (this.getClass() != second.getClass()) return false;
            Point that = (Point) second;
            if (x == that.x && this.y == that.y) return true;
            return false;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
