package parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * A {@code Terminal} is a {@code Parser} that matches a single
 * {@code Assembly}.
 *
 * @author Steven J. Metsker
 * @copyright (c) 1999 Steven J. Metsker
 * @license GNU Lesser General Public License, version 2.1
 * @source Terminal.java
 * @see Parser
 */
public class Terminal extends Parser {

    /*
     * whether or not this terminal should push itself upon an
     * assembly's stack after a successful match
     */
    protected boolean discard = false;

    /**
     * Constructs an unnamed terminal.
     */
    public Terminal() {
    }

    /**
     * Constructs a terminal with the given name.
     *
     * @param name A name to be known by.
     */
    public Terminal(String name) {
        super(name);
    }

    /**
     * Accepts a "visitor" and a collection of previously visited
     * parsers.
     *
     * @param pv the visitor to accept
     * @param visited a collection of previously visited parsers
     */
    public void accept(ParserVisitor pv, ArrayList<Assembly> visited) {
        pv.visitTerminal(this, visited);
    }

    /**
     * A convenience method that sets discarding to be true.
     *
     * @return this
     */
    public Terminal discard() {
        return setDiscard(true);
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
        ArrayList<Assembly> out = new ArrayList<Assembly>();
        Iterator<Assembly> e = in.iterator();
        while (e.hasNext()) {
            Assembly a = e.next();
            Assembly b = matchOneAssembly(a);
            if (b != null) {
                out.add(b);
            }
        }
        return out;
    }

    /**
     * Returns an assembly equivalent to the supplied assembly, this
     * terminal will have been removed from the front of the
     * assembly. As with any parser, if the match succeeds, this
     * terminal's assembler will work on the assembly. If the
     * match fails, this method returns null.
     *
     * @param in the assembly to match against
     *
     * @return a copy of the incoming assembly, advanced by this
     *         terminal or null if the match fails
     */
    protected Assembly matchOneAssembly(Assembly in) {
        if (!in.hasNext()) {
            return null;
        }
        if (qualifies(in.next())) {
            Assembly out = new Assembly(in);
            if (!discard) {
                out.push(out.pop());
            }
            return out;
        }
        return null;
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
        return true;
    }

    /**
     * By default, creates a collection with this terminal's
     * string representation of itself. (Most subclasses
     * override this.)
     *
     * @param maxDepth the maximum depth of the expansion
     * @param depth the current depth of the expansion
     *
     * @return a collection of strings representing the
     *         expansion of this parser
     */
    public ArrayList<String> randomExpansion(int maxDepth, int depth) {
        ArrayList<String> v = new ArrayList<String>();
        v.add(this.toString());
        return v;
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
        this.discard = discard;
        return this;
    }

    /*
     * Returns a textual description of this parser.
     */
    protected String unvisitedString(ArrayList<Parser> visited) {
        return "any";
    }
}
