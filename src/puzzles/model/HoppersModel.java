package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.*;

/**
 * The HoppersModel that will represent the logic and rules of the game.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class HoppersModel {
    /**
     * array list that will hold the first coordinates selected
     */
    private final ArrayList<Integer> firstCoord;
    /**
     * the collection of observers of the model
     */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();
    /**
     * the current configuration
     */
    private HoppersConfig currentConfig;
    /**
     * the filename
     */
    private String filename;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }

    // ******** The Hoppers Model ********

    /**
     * Constructor for the HoppersModel that creates a new HoppersConfig with
     * the filename and sets it to the current configuration.
     */
    public HoppersModel(String filename) {
        try {
            currentConfig = new HoppersConfig(filename);
        } catch (Throwable e) {
            alertObservers("Failed to load: " + filename);
        }
        this.filename = filename;
        firstCoord = new ArrayList<>();
    }

    /**
     * Method that gets the current configuration.
     *
     * @return the current configuration
     */
    public HoppersConfig getCurrentConfig() {
        return this.currentConfig;
    }

    /**
     * Method that handles the select option. For the first selection, the user
     * selects a cell on the board with the intention of selecting the piece at
     * that location. If there is a piece there, selection for the second part is
     * made. Otherwise, if there is no piece there an error message is displayed.
     * For the second selection, the user selects another cell on the board with
     * the intention of moving the previously selected piece to this location.
     * If the move is valid, the board updates. If the move is invalid, an
     * error message is displayed.
     *
     * @param row the row
     * @param col the column
     */
    public void select(int row, int col) {
        if (currentConfig.checkCoordinates(row, col)) {
            if (firstCoord.size() == 0) {
                if (currentConfig.containsFrog(row, col)) {
                    firstCoord.add(row);
                    firstCoord.add(col);
                    alertObservers("Selected (" + row + ", " + col + ")");
                } else {
                    if (currentConfig.containsLilypad(row, col)) {
                        alertObservers("No frog at (" + row + ", " + col + ")");
                    } else {
                        alertObservers("Invalid selection at (" + row + ", " + col + ")");
                    }
                }
            } else {
                if (currentConfig.containsFrog(firstCoord.get(0), firstCoord.get(1))) {
                    if (currentConfig.isValidMove(firstCoord.get(0), firstCoord.get(1), row, col) != null) {
                        Coordinates coord = currentConfig.isValidMove(firstCoord.get(0), firstCoord.get(1), row, col);
                        currentConfig.makeHopMove(firstCoord.get(0), firstCoord.get(1), coord.row(), coord.col(), row, col);
                        alertObservers("Jumped from (" + firstCoord.get(0) + ", " + firstCoord.get(1) + ") to (" + row + ", " + col + ")");
                        firstCoord.clear();
                    } else {
                        alertObservers("Can't jump from (" + firstCoord.get(0) + ", " + firstCoord.get(1) + ")" + " to (" + row + ", " + col + ")");
                        firstCoord.clear();
                    }
                } else {
                    alertObservers("Can't jump from (" + firstCoord.get(0) + ", " + firstCoord.get(1) + ")" + " to (" + row + ", " + col + ")");
                    firstCoord.clear();
                }
            }
        } else {
            alertObservers("Invalid selection at (" + row + ", " + col + ")");
        }
    }

    /**
     * Method for the hint option. When the user enters hint, if the current
     * state of the puzzle is solvable, the puzzle should advance to the next
     * step in the solution with an indication that it was successful. Otherwise,
     * the puzzle should remain in the same state and indicate that there is no
     * solution.
     */
    public void hint() {
        if (currentConfig.isSolution()) {
            alertObservers("Already solved!");
        } else {
            Solver solver = new Solver();
            Optional<Collection<Configuration>> lst = solver.solve(this.currentConfig);
            if (lst.isEmpty()) {
                alertObservers("No solution");
            } else {
                alertObservers("Next step!");
                Collection<Configuration> config = lst.get();
                List<Configuration> newListConfig = new ArrayList<>(config);
                firstCoord.clear();
                this.currentConfig = (HoppersConfig) newListConfig.get(1);
            }
        }
    }

    /**
     * Method that checks if there is a solution or not.
     *
     * @param hoppersConfig the hoppersConfig
     * @return true if there is a solution; false otherwise
     */
    public boolean noSolution(HoppersConfig hoppersConfig) {
        Solver solver = new Solver();
        Optional<Collection<Configuration>> lst = solver.solve(hoppersConfig);
        return lst.isEmpty();
    }

    /**
     * Method that is responsible when the user enters reset. This method works
     * in a way that the previously loaded file is reloaded, causing the puzzle
     * to return to its initial state. Also, a message to the user is displayed.
     */
    public void reset() {
        try {
            this.currentConfig = new HoppersConfig(filename);
            alertObservers("Puzzle reset!");
        } catch (IOException e) {
            System.out.println();
        }
    }

    /**
     * When the user enters load, this method will provide the path and name of
     * a puzzle file for the game to load. If the file is readable it is
     * guaranteed to be a valid puzzle file and the new puzzle file is loaded and
     * displayed. If the file cannot be read, an error message is displayed and
     * the previous puzzle file remains loaded.
     *
     * @param filename the filename
     */
    public void load(String filename) {
        try {
            this.filename = filename;
            this.currentConfig = new HoppersConfig(filename);
            alertObservers("Loaded: " + filename);
        } catch (Throwable e) {
            alertObservers("Failed to load: " + filename);
        }
    }

    /**
     * Method that returns a string representation of the board by showing the
     * rows and columns.
     *
     * @return a string representation of the board with the rows and columns
     */
    public String rowsColsBoard() {
        StringBuilder result = new StringBuilder("   ");
        for (int i = 0; i < currentConfig.getCols(); i++) {
            result.append(i);
            if (i <= currentConfig.getCols()) {
                result.append(" ");
            }

        }
        result.append("\n  ");
        result.append("--".repeat(Math.max(0, currentConfig.getCols())));
        result.append("\n");
        for (int j = 0; j < currentConfig.getRows(); j++) {
            result.append(j).append("|");
            for (int c = 0; c < currentConfig.getRows(); c++) {
                result.append(" ").append(currentConfig.getBoard()[j][c]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Method that gets the filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return this.filename;
    }
}