/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    private final edu.princeton.cs.algs4.WeightedQuickUnionUF uf;
    private final int length;
    private boolean[][] grid;
    private int numOpen = 0;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        length = n;
        grid = new boolean[length + 1][length + 1]; // 1-indexed
        uf = new edu.princeton.cs.algs4.WeightedQuickUnionUF((n * n) + 2);
        // CREATE AND CONNECT TOP/BOTTOM VIRTUAL SITE
        for (int i = 1; i <= length; i++) {
            uf.union(xyTo1D(1, i), 0); // top virtual site
            uf.union(xyTo1D(length, i), (n * n)); // bottom virtual site
        }
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * length + (col);
    }

    public void open(int row, int col) {
        checkIllegalArguments(row, col);
        grid[row][col] = true;
        numOpen++;
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
            throw new IllegalArgumentException("in (" + row + ", " + col + ")");

        }
    }

    public boolean isOpen(int row, int col) {
        checkIllegalArguments(row, col);
        return grid[row][col];
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return uf.connected(0, length * length);
    }

    public boolean isFull(int row, int col) {
        checkIllegalArguments(row, col);
        if (isOpen(row, col)) {
            return uf.connected(0, xyTo1D(row, col));
        }
        return false;
    }
}
