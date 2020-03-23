/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Board {
    public class Board {
        int[][] tiles;

        // create a board from an n-by-n array of tiles,
        // where tiles[row][col] = tile at (row, col)
        public Board(int[][] tiles) {
            this.tiles = tiles;
        }

        // string representation of this board
        public String toString() {
            String retString = "" + tiles.length + "\n";
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    retString += tiles[i][j] + " ";
                }
                retString += "\n";
            }
            return retString;
        }

        // board dimension n
        public int dimension()

        // number of tiles out of place
        public int hamming() {
            int incorrCount = 0;
            int currTile = 0;
            for(int i=0; i<tiles.length; i++)
                for(int j=0; j<tiles.length; j++) {
                    currTile+=1;
                    if(currTile == 9) break;
                    if(tiles[i][j] != currTile) {
                        incorrCount++;
                    }
                }
            return incorrCount;
        }

        // sum of Manhattan distances between tiles and goal
        public int manhattan()

        // is this board the goal board?
        public boolean isGoal()

        // does this board equal y?
        public boolean equals(Object y)

        // all neighboring boards
        public Iterable<Board> neighbors()

        // a board that is obtained by exchanging any pair of tiles
        public Board twin()

        // unit testing (not graded)
        public static void main(String[] args)

    }
}
