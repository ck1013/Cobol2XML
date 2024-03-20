package parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Logger;

public abstract class ParserTester {
    // Initialize a logger for the ParserTester class using its name
    private final static Logger LOGGER = Logger.getLogger(ParserTester.class.getName());
    
    // The parser to be tested
    protected Parser p;
    
    // Flag to log test strings
    protected boolean logTestStrings = true;

    // Constructor for ParserTester, initializes the parser
    protected ParserTester(Parser p) {
        this.p = p;
    }

    // Abstract method to assemble a string
    protected abstract Assembly assembly(String s);

    // Method to check if the parser can generate a problem at a given depth
    protected boolean canGenerateProblem(int depth) {
        String s = p.randomInput(depth, separator()); // Generate a random input string
        logTestString(s); // Log the test string if logTestStrings is true
        
        Assembly a = assembly(s); // Assemble the input string
        a.setTarget(freshTarget()); // Set the target for the assembly
        
        ArrayList<Assembly> in = new ArrayList<Assembly>(); // Initialize an ArrayList for input assemblies
        in.add(a); // Add the assembled input to the ArrayList
        
        ArrayList<?> out = completeMatches(p.match(in)); // Find matches for the input and store them in an ArrayList
        
        if (out.size() != 1) { // If there's not exactly one match, a problem is found
            logProblemFound(s, out.size()); // Log the problem found
            return true;
        }
        return false;
    }

    // Static method to find complete matches in a list of matches
    public static ArrayList<Assembly> completeMatches(ArrayList<?> in) {
        ArrayList<Assembly> out = new ArrayList<Assembly>(); // Initialize an ArrayList for complete matches
        Enumeration<?> e = Collections.enumeration(in); // Initialize an enumeration for the input list
        
        while (e.hasMoreElements()) { // Iterate through the input list
            Assembly a = (Assembly) e.nextElement(); // Get the next assembly
            if (!a.hasMoreElements()) { // If the assembly has no more elements, it's a complete match
                out.add(a); // Add it to the list of complete matches
            }
        }
        return out; // Return the list of complete matches
    }

    // Abstract method to create a fresh target Assembly
    protected Assembly freshTarget() {
        return null;
    }

    // Method to log a depth change
    protected void logDepthChange(int depth) {
        LOGGER.info("Testing depth " + depth + "...");
    }

    // Method to log that no problems were found
    protected void logPassed() {
        LOGGER.info("No problems found.");
    }

    // Method to log a problem found
    protected void logProblemFound(String s, int matchSize) {
        LOGGER.info("Problem found for string:");
        LOGGER.info(s);
        
        if (matchSize == 0) {
            LOGGER.info("Parser cannot match this apparently valid string.");
        } else {
            LOGGER.info("The parser found " + matchSize + " ways to parse this string.");
        }
    }

    // Method to log a test string
    protected void logTestString(String s) {
        if (logTestStrings) {
            LOGGER.info("    Testing string " + s);
        }
    }

    // Method to get the string separator
    protected String separator() {
        return " ";
    }

    // Method to set logTestStrings flag
    public void setLogTestStrings(boolean logTestStrings) {
        this.logTestStrings = logTestStrings;
    }

    // Method to test the parser at different depths
    public void test() {
        for (int depth = 2; depth < 8; depth++) {
            logDepthChange(depth); // Log the depth change
            for (int k = 0; k < 100; k++) {
                if (canGenerateProblem(depth)) {
                    return; // Return if a problem is found
                }
