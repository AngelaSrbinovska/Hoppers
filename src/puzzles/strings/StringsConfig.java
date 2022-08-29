package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.*;

/**
 * The purpose of StringsConfig is that it will generate successors and tells
 * whether a configuration is a solution or not.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class StringsConfig implements Configuration {
    /**
     * the start configuration
     */
    private final String start;
    /**
     * the end configuration
     */
    private final String end;
    /**
     * the current configuration
     */
    private final String current;

    /**
     * StringsConfig sets the start, end and current configuration.
     *
     * @param start   the start configuration
     * @param end     the end configuration
     * @param current the current configuration
     */
    public StringsConfig(String start, String end, String current) {
        this.start = start;
        this.end = end;
        this.current = current;
    }

    /**
     * Method that returns the name of the current configuration.
     *
     * @return the name of the current configuration
     */
    public String getName() {
        return this.current;
    }

    /**
     * Method that returns a boolean value by checking if the current and end
     * configurations are the same. That is when we know that we have a solution.
     *
     * @return true if there is a solution; false otherwise
     */
    @Override
    public boolean isSolution() {
        return Objects.equals(this.current, end);
    }

    /**
     * Method that generates all the possible configurations.
     *
     * @return hash set of all neighbors
     */
    @Override
    public Set<Configuration> getNeighbors() {
        Set<Configuration> setOfNeighbors = new HashSet<>();
        for (int i = 0; i < current.length(); i++) {
            // next
            StringBuilder newStr = new StringBuilder(current);
            char next = ' ';
            next = (char) (current.charAt(i) + 1);

            // previous
            StringBuilder newStr2 = new StringBuilder(current);
            char previous = ' ';
            previous = (char) (current.charAt(i) - 1);

            // special case for 'Z' and 'A'
            if (current.charAt(i) == 'Z') {
                next = 'A';
            } else if (current.charAt(i) == 'A') {
                previous = 'Z';
            }

            // generating children
            newStr.setCharAt(i, next);
            StringsConfig stringsConfig1 = new StringsConfig(start, end, newStr.toString());
            setOfNeighbors.add(stringsConfig1);

            newStr2.setCharAt(i, previous);
            StringsConfig stringsConfig2 = new StringsConfig(start, end, newStr2.toString());
            setOfNeighbors.add(stringsConfig2);
        }
        return setOfNeighbors;
    }

    /**
     * Method that returns string representation of the current configuration.
     *
     * @return the string representation of the current configuration
     */
    @Override
    public String toString() {
        return this.current;
    }

    /**
     * Method that checks if two StringsConfig are the same by comparing their
     * names.
     *
     * @param other the other object
     * @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof StringsConfig) {
            StringsConfig otherString = (StringsConfig) other;
            result = this.current.equals(otherString.getName());
        }
        return result;
    }

    /**
     * Method that returns the hash code of a current configuration.
     *
     * @return the hash code of a current configuration
     */
    @Override
    public int hashCode() {
        return this.current.hashCode();
    }
}