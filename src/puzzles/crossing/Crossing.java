// Project02: Strings and Crossing Puzzles

package puzzles.crossing;

import puzzles.common.solver.Solver;

/**
 * The class for the crossing puzzles for moving as quickly as possible pups and
 * wolves from the left to the right side of the river.
 *
 * @author Anita Srbinovska (as2950@rit.edu)
 */
public class Crossing {

    /**
     * The main method checks the length of the arguments, and displays the
     * total and unique configurations as well as the number of pups and wolves.
     *
     * @param args a native array of Strings
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Crossing pups wolves"));
        } else {
            int pups = Integer.parseInt(args[0]);
            int wolves = Integer.parseInt(args[1]);
            System.out.println("Pups: " + pups + ", Wolves: " + wolves);

            CrossingConfig configuration = new CrossingConfig(pups, wolves);
            Solver solver = new Solver();
            solver.solve(configuration);
            System.out.println("Total configs: " + solver.setTotal());
            System.out.println("Unique configs: " + Solver.unique);
            solver.steps();
        }
    }
}
