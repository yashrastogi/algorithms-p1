/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    private final edu.princeton.cs.algs4.WeightedQuickUnionUF uf;
    private final int length;
    private boolean[][] grid;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        length = n;
        grid = new boolean[length + 1][length + 1]; // 1-indexed
        uf = new edu.princeton.cs.algs4.WeightedQuickUnionUF((n * n));
    }

    public void open(int row, int col) {
        checkIllegalArguments(row, col);
        grid[row][col] = true;

        if (row - 1 != 0) {
            if (isOpen(row - 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        }
        if (col - 1 != 0) {
            if (isOpen(row, col - 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
        if (row + 1 <= length) {
            if (isOpen(row + 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }
        if (col + 1 <= length) {
            if (isOpen(row, col + 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    private void checkIllegalArguments(int row, int col) {
        int n = grid[0].length;
        if (row >= n || row < 1 || col >= n || col < 1) {
            // System.out.print(row + ", " + col);
            throw new IllegalArgumentException("in (" + row + ", " + col + ")");

        }
    }

    public boolean isOpen(int row, int col) {
        checkIllegalArguments(row, col);
        return grid[row][col];
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * length + (col - 1);
    }

    public int numberOfOpenSites() {
        int numOpen = 0;
        for (int i = 1; i <= length; i++) {
            for (int j = 1; j <= length; j++) {
                if (isOpen(i, j)) numOpen++;
            }
        }
        return numOpen;
    }

    public boolean percolates() {
        for (int i = 1; i <= length; i++) {
            if (isFull(length, i)) return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        checkIllegalArguments(row, col);
        for (int i = 1; i <= length; i++) {
            if (uf.connected(xyTo1D(row, col), xyTo1D(1, i))) {
                if (row == 1 && !isOpen(row, col)) return false;
                return true;
            }
        }
        return false;
    }
}
