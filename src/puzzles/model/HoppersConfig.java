package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The class for the HoppersConfig puzzle that generates all possible hops.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class HoppersConfig implements Configuration {
    /**
     * the lily pad where the frogs can jump
     */
    public final static char LILYPAD = '.';
    /**
     * the water where the frogs can't jump
     */
    public final static char INVALID = '*';
    /**
     * the green frog
     */
    public final static char GREEN = 'G';
    /**
     * the red frog
     */
    public final static char RED = 'R';
    /**
     * the total number of rows
     */
    private static int rows;
    /**
     * the total number of columns
     */
    private static int cols;
    /**
     * the board for the puzzle, a 2-D array of chars
     */
    private final char[][] board;
    /**
     * the cursor for the row
     */
    private int cursorRow;
    /**
     * the cursor for the column
     */
    private int cursorCol;

    /**
     * The constructor for the Hoppers puzzle that initializes the board.
     *
     * @param filename the file to read
     * @throws IOException throws IOException if a file can't be found
     */
    public HoppersConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String[] fields = in.readLine().split("\\s+");
            rows = Integer.parseInt(fields[0]);
            cols = Integer.parseInt(fields[1]);
            this.board = new char[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    board[row][col] = HoppersConfig.LILYPAD;
                }
            }

            for (int r = 0; r < rows; r++) {
                String[] line = in.readLine().split("\\s+");
                for (int i = 0; i < line.length; i++) {
                    this.board[r][i] = line[i].charAt(0);
                }
            }

            cursorRow = 0;
            cursorCol = -1;
        }
    }

    /**
     * Copy constructor that makes a copy of the board.
     *
     * @param other the other board
     */
    private HoppersConfig(HoppersConfig other) {
        this.cursorRow = other.cursorRow;
        this.cursorCol = other.cursorCol + 1;
        if (this.cursorCol == cols) {
            this.cursorRow += 1;
            this.cursorCol = 0;
        }

        this.board = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(other.board[r], 0, this.board[r],
                    0, cols);
        }
    }

    /**
     * A setter for the board.
     *
     * @param row the row
     * @param col the column
     * @param val the value to set the board at
     */
    private void setBoard(int row, int col, char val) {
        this.board[row][col] = val;
    }

    /**
     * Helper method that finds any frog on the board, whether it is a green or
     * a red frog.
     *
     * @return a set of all frogs that were found
     */
    private Set<Coordinates> findFrog() {
        Set<Coordinates> set = new HashSet<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == HoppersConfig.GREEN || board[row][col] == HoppersConfig.RED) {
                    set.add(new Coordinates(row, col));
                }
            }
        }
        return set;
    }

    /**
     * Method that generates all possible configurations (8 configs) for all
     * moves any frog can make.
     *
     * @return the set of configurations
     */
    private Set<Configuration> makeHop() {
        Set<Configuration> setHoppersConfigs = new HashSet<>();
        Set<Coordinates> frogs = findFrog();
        for (Coordinates frog : frogs) {
            char f = board[frog.row()][frog.col()];
            boolean topLeft = topLeft(frog.row(), frog.col());
            boolean bottomLeft = bottomLeft(frog.row(), frog.col());
            boolean topRight = topRight(frog.row(), frog.col());
            boolean bottomRight = bottomRight(frog.row(), frog.col());
            boolean left = left(frog.row(), frog.col());
            boolean right = right(frog.row(), frog.col());
            boolean up = up(frog.row(), frog.col());
            boolean down = down(frog.row(), frog.col());
            if (topLeft) {
                HoppersConfig hoppersConfig1 = new HoppersConfig(this);
                hoppersConfig1.board[frog.row() - 2][frog.col() - 2] = hoppersConfig1.board[frog.row()][frog.col()];
                hoppersConfig1.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig1.board[frog.row() - 1][frog.col() - 1] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig1);
            }
            if (bottomLeft) {
                HoppersConfig hoppersConfig2 = new HoppersConfig(this);
                hoppersConfig2.board[frog.row() + 2][frog.col() - 2] = hoppersConfig2.board[frog.row()][frog.col()];
                hoppersConfig2.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig2.board[frog.row() + 1][frog.col() - 1] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig2);
            }
            if (topRight) {
                HoppersConfig hoppersConfig3 = new HoppersConfig(this);
                hoppersConfig3.board[frog.row() - 2][frog.col() + 2] = hoppersConfig3.board[frog.row()][frog.col()];
                hoppersConfig3.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig3.board[frog.row() - 1][frog.col() + 1] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig3);
            }
            if (bottomRight) {
                HoppersConfig hoppersConfig4 = new HoppersConfig(this);
                hoppersConfig4.board[frog.row() + 2][frog.col() + 2] = hoppersConfig4.board[frog.row()][frog.col()];
                hoppersConfig4.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig4.board[frog.row() + 1][frog.col() + 1] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig4);
            }
            if (left) {
                HoppersConfig hoppersConfig5 = new HoppersConfig(this);
                hoppersConfig5.board[frog.row()][frog.col() - 4] = hoppersConfig5.board[frog.row()][frog.col()];
                hoppersConfig5.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig5.board[frog.row()][frog.col() - 2] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig5);
            }
            if (right) {
                HoppersConfig hoppersConfig6 = new HoppersConfig(this);
                hoppersConfig6.board[frog.row()][frog.col() + 4] = hoppersConfig6.board[frog.row()][frog.col()];
                hoppersConfig6.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig6.board[frog.row()][frog.col() + 2] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig6);
            }
            if (up) {
                HoppersConfig hoppersConfig7 = new HoppersConfig(this);
                hoppersConfig7.board[frog.row() - 4][frog.col()] = hoppersConfig7.board[frog.row()][frog.col()];
                hoppersConfig7.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig7.board[frog.row() - 2][frog.col()] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig7);
            }
            if (down) {
                HoppersConfig hoppersConfig8 = new HoppersConfig(this);
                hoppersConfig8.board[frog.row() + 4][frog.col()] = hoppersConfig8.board[frog.row()][frog.col()];
                hoppersConfig8.board[frog.row()][frog.col()] = HoppersConfig.LILYPAD;
                hoppersConfig8.board[frog.row() + 2][frog.col()] = HoppersConfig.LILYPAD;
                setHoppersConfigs.add(hoppersConfig8);
            }
        }
        return setHoppersConfigs;
    }

    /**
     * Helper method that checks if the move to the top left is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean topLeft(int row, int col) {
        if (row - 2 >= 0 && col - 2 >= 0) {
            return board[row - 1][col - 1] == HoppersConfig.GREEN && board[row - 2][col - 2] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move to the top right is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean topRight(int row, int col) {
        if (row - 2 >= 0 && col + 2 <= cols - 1) {
            return board[row - 1][col + 1] == HoppersConfig.GREEN && board[row - 2][col + 2] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move to the bottom left is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean bottomLeft(int row, int col) {
        if (row + 2 <= rows - 1 && col - 2 >= 0) {
            return board[row + 1][col - 1] == HoppersConfig.GREEN && board[row + 2][col - 2] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move to the bottom right is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean bottomRight(int row, int col) {
        if (row + 2 <= rows - 1 && col + 2 <= cols - 1) {
            return board[row + 1][col + 1] == HoppersConfig.GREEN && board[row + 2][col + 2] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move to the left is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean left(int row, int col) {
        if (col - 4 < cols && col - 4 >= 0) {
            return board[row][col - 2] == HoppersConfig.GREEN && board[row][col - 4] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move to the right is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean right(int row, int col) {
        if (col + 4 < cols && col + 4 >= 0) {
            return board[row][col + 2] == HoppersConfig.GREEN && board[row][col + 4] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move up is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean up(int row, int col) {
        if (row - 4 >= 0) {
            return board[row - 2][col] == HoppersConfig.GREEN && board[row - 4][col] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Helper method that checks if the move down is valid or not.
     *
     * @param row the row
     * @param col the column
     * @return true if the move is valid; false otherwise
     */
    private boolean down(int row, int col) {
        if (row + 4 <= rows - 1) {
            return board[row + 2][col] == HoppersConfig.GREEN && board[row + 4][col] == HoppersConfig.LILYPAD;
        }
        return false;
    }

    /**
     * Method that returns a boolean value by checking if there is only reg frog
     * found on the board. That is when we know that we have a solution.
     *
     * @return true if there is a solution; false otherwise
     */
    @Override
    public boolean isSolution() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == HoppersConfig.GREEN) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method that generates all possible configurations.
     *
     * @return hash set of all neighbors
     */
    @Override
    public Set<Configuration> getNeighbors() {
        Set<Configuration> configurations = new HashSet<>();
        configurations.addAll(makeHop());
        return configurations;
    }

    /**
     * Method that checks if the move is made on the board (if in range).
     *
     * @param row the row
     * @param col the column
     * @return true if in bounds; false otherwise
     */
    public boolean checkCoordinates(int row, int col) {
        return row < rows && row >= 0 && col < cols && col >= 0;
    }

    /**
     * Method that checks if there is a lilypad on the selected spot.
     *
     * @param row the row
     * @param col the column
     * @return true if there is a lilypad; false otherwise
     */
    public boolean containsLilypad(int row, int col) {
        return board[row][col] == HoppersConfig.LILYPAD;
    }

    /**
     * Method that checks if there is a frog, either a green or a red frog on
     * the selected spot.
     *
     * @param row the row
     * @param col the column
     * @return true if there is a frog; false otherwise
     */
    public boolean containsFrog(int row, int col) {
        return board[row][col] == HoppersConfig.GREEN || board[row][col] == HoppersConfig.RED;
    }

    /**
     * Method that checks if the move is valid or not.
     *
     * @param startRow  the starting row
     * @param startCol  the starting column
     * @param finishRow the finish row
     * @param finishCol the finish column
     * @return the coordinates of the frog
     */
    public Coordinates isValidMove(int startRow, int startCol, int finishRow, int finishCol) {
        if (board[finishRow][finishCol] == HoppersConfig.LILYPAD) {
            // left and right
            if (startRow == finishRow && startCol > finishCol) {
                // left
                boolean bounds = finishCol == startCol - 4;
                if (bounds && board[finishRow][startCol - 2] == HoppersConfig.GREEN) {
                    return new Coordinates(finishRow, startCol - 2);
                }
            }
            if (startRow == finishRow && startCol < finishCol) {
                // right
                boolean bounds = finishCol == startCol + 4;
                if (bounds && board[finishRow][startCol + 2] == HoppersConfig.GREEN) {
                    return new Coordinates(finishRow, startCol + 2);
                }
            }

            // up and down
            if (startRow > finishRow && startCol == finishCol) {
                // up
                boolean bounds = finishRow == startRow - 4;
                if (bounds && board[startRow - 2][finishCol] == HoppersConfig.GREEN) {
                    return new Coordinates(startRow - 2, finishCol);
                }
            }
            if (startRow < finishRow && startCol == finishCol) {
                // down
                boolean bounds = finishRow == startRow + 4;
                if (bounds && board[startRow + 2][startCol] == HoppersConfig.GREEN) {
                    return new Coordinates(startRow + 2, startCol);
                }
            }

            // diagonals - top
            if (startRow > finishRow && startCol < finishCol) {
                // top right
                boolean bounds = finishRow == startRow - 2 && finishCol == startCol + 2;
                if (bounds && board[startRow - 1][startCol + 1] == HoppersConfig.GREEN) {
                    return new Coordinates(startRow - 1, startCol + 1);
                }
            }
            if (startRow > finishRow && startCol > finishCol) {
                // top left
                boolean bounds = finishRow == startRow - 2 && finishCol == startCol - 2;
                if (bounds && board[startRow - 1][startCol - 1] == HoppersConfig.GREEN) {
                    return new Coordinates(startRow - 1, startCol - 1);
                }
            }

            // diagonals - bottom
            if (startRow < finishRow && startCol < finishCol) {
                // bottom right
                boolean bounds = finishRow == startRow + 2 && finishCol == startCol + 2;
                if (bounds && board[startRow + 1][startCol + 1] == HoppersConfig.GREEN) {
                    return new Coordinates(startRow + 1, startCol + 1);
                }
            }
            if (startRow < finishRow && startCol > finishCol) {
                // bottom left
                boolean boundCheck = finishRow == startRow + 2 && finishCol == startCol - 2;
                if (boundCheck && board[startRow + 1][startCol - 1] == HoppersConfig.GREEN) {
                    return new Coordinates(startRow + 1, startCol - 1);
                }
            }
        }
        return null;
    }

    /**
     * Method that makes the move.
     *
     * @param row1 the first row
     * @param col1 the first column
     * @param row2 the second row
     * @param col2 the second column
     * @param row3 the third row
     * @param col3 the third column
     */
    public void makeHopMove(int row1, int col1, int row2, int col2, int row3, int col3) {
        board[row3][col3] = board[row1][col1];
        board[row2][col2] = HoppersConfig.LILYPAD;
        board[row1][col1] = HoppersConfig.LILYPAD;
    }

    /**
     * Method that returns string representation of the configuration.
     *
     * @return the string representation of the configuration
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                result.append(board[row][col]).append(col == cols - 1 ? "" : " ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Method that checks if two HoppersConfigs are the same by comparing the
     * two boards.
     *
     * @param o the other board
     * @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        boolean result = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.board[i][j] != ((HoppersConfig) o).board[i][j]) {
                    return false;
                }
            }
        }
        return result;
    }

    /**
     * Method that returns the hash code of the board plus the hash code of the
     * rows and the columns.
     *
     * @return the hash code of the configuration
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board) + HoppersConfig.rows + HoppersConfig.cols;
    }

    /**
     * Method that returns a character at a row and column.
     *
     * @return the board
     */
    public char[][] getBoard() {
        return this.board;
    }

    /**
     * Method that gets the board with the row and column.
     *
     * @param row the row
     * @param col the column
     * @return the board
     */
    public char[][] getTheBoard(int row, int col) {
        return this.board;
    }

    /**
     * Method that returns the number of rows.
     *
     * @return the rows
     */
    public int getRows() {
        return HoppersConfig.rows;
    }

    /**
     * Method that returns the number of columns.
     *
     * @return the columns
     */
    public int getCols() {
        return HoppersConfig.cols;
    }

    /**
     * Method that returns the cursor row.
     *
     * @return the cursor row
     */
    public int getCursorRow() {
        return this.cursorRow;
    }

    /**
     * Method that returns the cursor column.
     *
     * @return the cursor column
     */
    public int getCursorCol() {
        return this.cursorCol;
    }
}