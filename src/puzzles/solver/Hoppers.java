package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;

/**
 * The main class for the Hoppers puzzle.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class Hoppers {
    /**
     * It requires one command line arguments - the filename.
     *
     * @param args the filename
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        } else {
            System.out.println("File: " + args[0]);
            Configuration configuration = new HoppersConfig(args[0]);
            System.out.print(configuration);
            Solver solver = new Solver();
            solver.solve(configuration);
            solver.displayStatistics();
        }
    }
}
