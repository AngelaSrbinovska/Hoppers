// Project02: Strings and Crossing Puzzles

package puzzles.common.solver;

import java.util.*;

/**
 * This class is uses Breadth-First-Search and is common for both StringsConfig
 * and CrossingConfig classes.
 *
 * @author Anita Srbinovska (as2950@rit.edu)
 * @author Angela Srbinovska (as2179@rit.edu)
 * @author RIT CS
 */
public class Solver {
    /**
     * the total number of configurations starting at one
     */
    private static int numOfSteps;

    public static int total = 1;
    /**
     * the unique number of configurations starting at one
     */
    public static int unique = 1;
    /**
     * the path represented as a LinkedList
     */
    private final List<Configuration> path = new LinkedList<>();
    /**
     * the total number of configurations
     */
    public int totalNumOfConfigs = 1;
    /**
     * the unique number of configurations
     */
    public int uniqueConfigs = 1;

    /**
     * The solve method is the BFS to get the shortest path. In this method the
     * total and unique number of configurations are increased.
     *
     * @param configuration the configurations
     */
    public Optional<Collection<Configuration>> solve(Configuration configuration) {
        List<Configuration> queue = new LinkedList<>();
        queue.add(configuration);
        Map<Configuration, Configuration> predMap = new HashMap<>();
        predMap.put(configuration, configuration);
        while (!queue.isEmpty()) {
            Configuration current = queue.remove(0);
            if (current.isSolution()) {
                return Optional.of(constructPath(predMap, configuration, current));
            }
            for (Configuration neighbor : current.getNeighbors()) {
                this.totalNumOfConfigs++;
                if (!predMap.containsKey(neighbor)) {
                    this.uniqueConfigs++;
                    predMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Method that constructs the path.
     *
     * @param map       the predecessors map, that has the Configuration as the key
     *                  and value
     * @param startNode the start node
     * @param endNode   the end node
     * @return the path
     */
    private List<Configuration> constructPath(Map<Configuration, Configuration> map, Configuration startNode, Configuration endNode) {
        if (map.containsKey(endNode)) {
            Configuration currentNode = endNode;
            while (!currentNode.equals(startNode)) {
                this.path.add(0, currentNode);
                currentNode = map.get(currentNode);
            }
            this.path.add(0, startNode);
        }
        return this.path;
    }

    /**
     * This method is a setter for the total number of configurations, and it
     * increments by one.
     *
     * @return the total number of configurations
     */
    public void displayStatistics() {
        // total number of configs
        int totalNumOfConfigs = totalConfigs();
        System.out.println("Total configs: " + totalNumOfConfigs);

        // unique number of configs
        System.out.println("Unique configs: " + this.uniqueConfigs);

        // steps
        if (this.path.size() > 0) {
            for (Configuration step : this.path) {
                System.out.println("Step " + steps() + ":\n" + step);
            }
        } else {
            System.out.println("No solution");
        }
    }

    public int setTotal() {
        return Solver.total++;
    }

    /**
     * This method keeps track of the steps and checks if there is a solution,
     * and it gets the number of solutions with the path.
     */
    public void steps2() {
        if (path.size() > 0) {
            for (int i = 0; i < path.size(); ++i) {
                System.out.println("Step " + i + ":\n" + path.get(i));
            }
        } else {
            System.out.println("No solution");
        }
    }

    /**
     * Method that increments the number of steps, until the finish configuration
     * is found.
     *
     * @return the number of steps
     */
    public int steps() {
        return numOfSteps++;
    }

    /**
     * Method that increments the total number of configurations.
     *
     * @return the total number of configurations
     */
    public int totalConfigs() {
        return this.totalNumOfConfigs++;
    }
}
