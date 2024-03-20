/**
 * A {@code Terminal} is a {@code Parser} that matches a single
 * {@code Assembly}. This class is responsible for parsing a single
 * unit of input and determining if it matches the expected pattern.
 *
 * @author Steven J. Metsker
 * @copyright (c) 1999 Steven J. Metsker
 * @license GNU Lesser General Public License, version 2.1
 * @source Terminal.java
 * @see Parser
 */

/**
 * Constructs an unnamed terminal. This constructor initializes a new
 * instance of the Terminal class without any specific name or
 * settings.
 */
public Terminal() {
}

/**
 * Constructs a terminal with the given name. This constructor
 * initializes a new instance of the Terminal class with the
 * specified name.
 *
 * @param name A name to be known by.
 */
public Terminal(String name) {
    super(name); // Call the superclass constructor with the given name
}

/**
 * Accepts a "visitor" and a collection of previously visited
 * parsers. This method is part of the visitor pattern implementation,
 * allowing external objects to traverse and interact with the
 * parser tree.
 *
 * @param pv the visitor to accept
 * @param visited a collection of previously visited parsers
 */
public void accept(ParserVisitor pv, ArrayList<Assembly> visited) {
    pv.visitTerminal(this, visited); // Perform the visitor's terminal-specific action
}

/**
 * A convenience method that sets discarding to be true. This method
 * changes the discard flag to true, meaning that the terminal will
 * not push itself onto the assembly stack after a successful match.
 *
 * @return this
 */
public Terminal discard() {
    return setDiscard(true); // Call the setDiscard method with the argument true
}

/**
 * Given a collection of assemblies, this method matches this
 * terminal against all of them, and returns a new collection
 * of the assemblies that result from the matches.
 *
 * @return a collection of assemblies that result from
 *         matching against a beginning set of assemblies
 *
 * @param in a collection of assemblies to match against
 */
public ArrayList<Assembly> match(ArrayList<Assembly> in) {
    // ...
}

/**
 * Returns an assembly equivalent to the supplied assembly, this
 * terminal will have been removed from the front of the
 * assembly. This method performs the actual matching process,
 * advancing the assembly if a match is successful.
 *
 * @param in the assembly to match against
 *
 * @return a copy of the incoming assembly, advanced by this
 *         terminal or null if the match fails
 */
protected Assembly matchOneAssembly(Assembly in) {
    // ...
}

/**
 * The mechanics of matching are the same for many terminals,
 * except for the check that the next element on the assembly
 * qualifies as the type of terminal this terminal looks for.
 * This method performs that check.
 *
 * @param o an element from a assembly
 *
 * @return true, if the object is the kind of terminal this
 *         parser seeks
 */
protected boolean qualifies(Object o) {
    // ...
}

/**
 * By default, creates a collection with this terminal's
 * string representation of itself. (Most subclasses
 * override this.) This method generates a simple random expansion
 * for the terminal, which is useful for testing and debugging purposes.
 *
 * @param maxDepth the maximum depth of the expansion
 * @param depth the current depth of the expansion
 *
 * @return a collection of strings representing the
 *         expansion of this parser
 */
public ArrayList<String> randomExpansion(int maxDepth, int depth) {
    // ...
}

/**
 * By default, terminals push themselves upon a assembly's
 * stack, after a successful match. This routine will turn
 * off (or turn back on) that behavior.
 *
 * @param discard true, if this terminal should push
 *                itself on a assembly's stack
 *
 * @return this
 */
public Terminal setDiscard(boolean discard) {
    this.discard = discard; // Set the discard flag
    return this;
}

/*
 * Returns a textual description of this parser.
 * This method generates a simple string representation of the
 * terminal, which is useful for debugging and logging purposes.
 */
protected String unvisitedString(ArrayList<Parser> visited) {
    // ...
}
