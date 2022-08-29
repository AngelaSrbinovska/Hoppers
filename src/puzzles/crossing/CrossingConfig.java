package puzzles.crossing;

import puzzles.common.solver.Configuration;

import java.util.*;

/**
 * The purpose of CrossingConfig is that it will generate successors and tells
 * whether a configuration is a solution or not.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class CrossingConfig implements Configuration {
    /**
     * number of pups
     */
    private static int numOfPups;
    /**
     * number of wolves
     */
    private static int numOfWolves;
    /**
     * the left side with some amount of pups and wolves
     */
    private int[] leftSide = new int[2];
    /**
     * the right side with some amount of pups and wolves
     */
    private int[] rightSide = new int[2];
    /**
     * which side the boat is on
     */
    private Side side;

    /**
     * the boat can either be on the left or the right
     */
    public enum Side {
        LEFT,
        RIGHT
    }

    /**
     * The boat always starts on the left side of the river. The constructor also
     * sets the number of pups and number of wolves.
     *
     * @param numOfPups   the number of pups
     * @param numOfWolves the number of wolves
     */
    public CrossingConfig(int numOfPups, int numOfWolves) {
        this.side = Side.LEFT;
        this.leftSide = new int[]{numOfPups, numOfWolves};
        CrossingConfig.numOfPups = numOfPups;
        CrossingConfig.numOfWolves = numOfWolves;
    }

    /**
     * The copy constructor for setting the other configuration.
     *
     * @param side      the side on which the boat is on
     * @param leftSide  the left side
     * @param rightSide the right side
     */
    public CrossingConfig(Side side, int[] leftSide, int[] rightSide) {
        this.side = side;
        System.arraycopy(leftSide, 0, this.leftSide, 0, 2);
        System.arraycopy(rightSide, 0, this.rightSide, 0, 2);

    }

    /**
     * Method that returns a boolean value by checking if the number of pups on
     * the left and the number of wolves on the right is 0. It also checks if the
     * number of pups on the right and the number of wolves on the right side is
     * the same as the initial value of the pups and wolves. The boat should also
     * be on the right side of the river. That is when we know that we have a
     * solution.
     *
     * @return true if there is a solution; false otherwise
     */
    @Override
    public boolean isSolution() {
        return (this.leftSide[0] == 0 && this.leftSide[1] == 0 &&
                this.rightSide[0] == numOfPups && this.rightSide[1] == numOfWolves
                && this.side == Side.RIGHT);
    }

    /**
     * Method that generates all the possible configurations.
     *
     * @return hash set of all neighbors
     */
    @Override
    public Set<Configuration> getNeighbors() {
        Set<Configuration> neighbors = new HashSet<>();
        // if the boat is on the left
        if (this.side == Side.LEFT) {
            // we can transfer up to 2 pups
            if (this.leftSide[0] >= 2) {
                int[] left = new int[]{leftSide[0] - 2, leftSide[1]};
                int[] right = new int[]{rightSide[0] + 2, rightSide[1]};
                Configuration configuration = new CrossingConfig(Side.RIGHT, left, right);
                neighbors.add(configuration);
            }
            // we can also transfer 1 pup
            if (this.leftSide[0] >= 1) {
                int[] left = new int[]{leftSide[0] - 1, leftSide[1]};
                int[] right = new int[]{rightSide[0] + 1, rightSide[1]};
                Configuration configuration = new CrossingConfig(Side.RIGHT, left, right);
                neighbors.add(configuration);
            }
            // we can transfer only 1 wolf
            if (this.leftSide[1] >= 1) {
                int[] left = new int[]{leftSide[0], leftSide[1] - 1};
                int[] right = new int[]{rightSide[0], rightSide[1] + 1};
                Configuration configuration = new CrossingConfig(Side.RIGHT, left, right);
                neighbors.add(configuration);
            }
        }

        // if the boat is on the right
        if (this.side == Side.RIGHT) {
            // we can transfer up to 2 pups
            if (this.rightSide[0] >= 2) {
                int[] left = new int[]{leftSide[0] + 2, leftSide[1]};
                int[] right = new int[]{rightSide[0] - 2, rightSide[1]};
                Configuration configuration = new CrossingConfig(Side.LEFT, left, right);
                neighbors.add(configuration);
            }
            // we can also transfer 1 pup
            if (this.rightSide[0] >= 1) {
                int[] left = new int[]{leftSide[0] + 1, leftSide[1]};
                int[] right = new int[]{rightSide[0] - 1, rightSide[1]};
                Configuration configuration = new CrossingConfig(Side.LEFT, left, right);
                neighbors.add(configuration);
            }
            // we can transfer only 1 wolf
            if (this.rightSide[1] >= 1) {
                int[] left = new int[]{leftSide[0], leftSide[1] + 1};
                int[] right = new int[]{rightSide[0], rightSide[1] - 1};
                Configuration configuration = new CrossingConfig(Side.LEFT, left, right);
                neighbors.add(configuration);
            }
        }
        return neighbors;
    }

    /**
     * Method that returns string representation of the current configuration.
     *
     * @return the string representation of the current configuration
     */
    @Override
    public String toString() {
        if (this.side == Side.LEFT) {
            return "(BOAT) " + "left=" + Arrays.toString(this.leftSide) + ", right=" +
                    Arrays.toString(this.rightSide);
        }
        return "       left=" + Arrays.toString(this.leftSide) + ", right=" +
                Arrays.toString(this.rightSide) + " (BOAT)";
    }

    /**
     * Method that checks if two CrossingConfig are the same by comparing the
     * amount of pups on the left and right, and the amount of wolves on the
     * left and right.
     *
     * @param other the other object
     * @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof CrossingConfig otherConfig) {
            result = this.leftSide[0] == otherConfig.leftSide[0] &&
                    this.leftSide[1] == otherConfig.leftSide[1] &&
                    this.rightSide[0] == otherConfig.rightSide[0] &&
                    this.rightSide[1] == otherConfig.rightSide[1];
        }
        return result;
    }

    /**
     * Method that returns the sum of hash codes of the side on which the boat
     * is on, the left side and the right side with the certain amount of pups
     * and wolves.
     *
     * @return the hash code of a current configuration
     */
    @Override
    public int hashCode() {
        return this.side.hashCode() + Arrays.hashCode(this.leftSide) + Arrays.hashCode(this.rightSide);
    }
}
