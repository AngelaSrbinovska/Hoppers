// Project02: Strings and Crossing Puzzles

package puzzles.common.solver;

import java.util.Collection;

/**
 * An interface with methods used in StringsConfig and CrossingsConfig.
 *
 * @author RIT CS
 */
public interface Configuration {

    /**
     * A method that checks if there is a solution.
     *
     * @return true/false
     */
    boolean isSolution();

    /**
     * A method that gets all the successors with special cases.
     *
     * @return all successors
     */
    Collection<Configuration> getNeighbors();

    /**
     * A method that checks if two objects are equal.
     *
     * @param other the other object
     * @return true/false
     */
    boolean equals(Object other);

    /**
     * A method for the hash code of the current configuration.
     *
     * @return the hash code of current configuration
     */
    int hashCode();

    /**
     * A method for the textual representation of the current configuration.
     *
     * @return a textual representation of the current configuration
     */
    String toString();
}