// Project02: Strings and Crossing Puzzles

package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

/**
 * The class for the strings puzzles for moving as quickly as possible from a
 * starting string to a finish string by rotating letters one position at a time.
 *
 * @author Anita Srbinovska (as2950@rit.edu)
 */
public class Strings {

    /**
     * The main method checks the length of the arguments, and displays the
     * total and unique configurations as well as the start and finish string.
     *
     * @param args a native array of Strings
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            String start = args[0];
            String finish = args[1];
            System.out.println("Start: " + start + ", End: " + finish);

            Solver solver = new Solver();
            Configuration configuration = new StringsConfig(start, finish, start);
            solver.solve(configuration);
            System.out.println("Total configs: " + solver.setTotal());
            System.out.println("Unique configs: " + Solver.unique);
            solver.steps();
        }
    }
}
