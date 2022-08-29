package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * The class for the HoppersPTUI puzzle that plays the game.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class HoppersPTUI implements Observer<HoppersModel, String> {
    /**
     * the model
     */
    private static HoppersModel model;
    /**
     * sign that will help the user to enter a command
     */
    private static final String PROMPT = "> ";

    /**
     * Constructor that will create a new HoppersModel with the filename.
     *
     * @param filename the filename
     * @throws IOException throw an IOException if the file is not found
     */
    public HoppersPTUI(String filename) {
        model = new HoppersModel(filename);
        model.load(filename);
        model.addObserver(this);
    }

    /**
     * The update method that prints the appropriate message to the user.
     *
     * @param model the model
     * @param msg   the message
     */
    @Override
    public void update(HoppersModel model, String msg) {
        System.out.println(msg);
    }

    /**
     * Method that lists all the commands the user can use.
     *
     * @param args the arguments
     */
    public void listOfCommands(String[] args) {
        System.out.println();
        System.out.println("""
                h(int)              -- hint next move
                l(oad) filename     -- load new puzzle file
                s(elect) r c        -- select cell at r, c
                q(uit)              -- quit the game
                r(eset)             -- reset the current game""");
    }

    /**
     * Method that returns the current configuration.
     *
     * @return the current configuration
     */
    public String getBoard() {
        return model.rowsColsBoard();
    }

    /**
     * The main method will ask for user input and call each command accordingly
     * from the model.
     *
     * @param args the arguments
     * @throws IOException throw an IOException if the file is not found
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            System.out.println("Loaded: " + args[0]);
            HoppersPTUI hoppersPTUI = new HoppersPTUI(args[0]);
            System.out.println(hoppersPTUI.getBoard());
            hoppersPTUI.listOfCommands(args);
            Scanner in = new Scanner(System.in);
            System.out.print(PROMPT);
            if (in.hasNextLine()) {
                do {
                    String[] line = in.nextLine().split("\\s+");
                    if (line[0].startsWith("q")) {
                        System.exit(0);
                    }
                    if (line[0].startsWith("s")) {
                        if (line.length < 3) {
                        } else {
                            int row = Integer.parseInt(line[1]);
                            int col = Integer.parseInt(line[2]);
                            model.select(row, col);
                            System.out.println(model.rowsColsBoard());
                            System.out.print(PROMPT);
                        }
                    } else if (line[0].startsWith("h")) {
                        if (line.length != 1) {
                        } else {
                            model.hint();
                            System.out.println(model.rowsColsBoard());
                            System.out.print(PROMPT);
                        }
                    } else if (line[0].startsWith("l")) {
                        if (line.length != 2) {
                        } else {
                            model.load(line[1]);
                            System.out.println(model.rowsColsBoard());
                            System.out.print(PROMPT);
                        }
                    } else if (line[0].startsWith("r")) {
                        if (line.length != 1) {
                        } else {
                            model.reset();
                            System.out.println("Loaded: " + model.getFilename());
                            System.out.println(model.rowsColsBoard());
                            System.out.println("Puzzle reset!");
                            System.out.println(model.rowsColsBoard());
                            System.out.print(PROMPT);
                        }
                    } else {
                        System.out.print("Invalid command");
                        hoppersPTUI.listOfCommands(args);
                        System.out.print(PROMPT);
                    }
                } while (in.hasNextLine());
            }
        }
    }
}
