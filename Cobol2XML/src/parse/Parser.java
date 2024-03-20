/*
 * Parser.java
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation.
 * 
 * This parser is an abstract class that serves as a base for other
 * parser implementations. It contains a name for identification and
 * an Assembler object that will process an assembly when this
 * parser successfully matches against it.
 */

package parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

public abstract class Parser {
    /*
     * The name of this parser
     */
    private String name;

    /*
     * An Assembler object that will process an assembly when this
     * parser successfully matches against it
     */
    protected Assembler assembler;

    /**
     * Constructs a Parser object with an empty name.
     */
    protected Parser() {
        this.name = "";
    }

    /**
     * Constructs a Parser object with the given name.
     *
     * @param name A name to be known by.
     */
    protected Parser(String name) {
        this.name = name;
    }

    /**
     * Accepts a ParserVisitor object that will perform some
     * operation on a parser structure.
     *
     * @param pv The ParserVisitor object to accept
     */
    public void accept(ParserVisitor pv) {
        accept(pv, new ArrayList<Assembly>());
    }

    /**
     * Accepts a ParserVisitor object along with a collection of
     * previously visited parsers.
     *
     * @param pv The ParserVisitor object to accept
     * @param visited A collection of previously visited parsers
     */
    public abstract void accept(ParserVisitor pv, ArrayList<Assembly> visited);

    /**
     * Adds the elements of one ArrayList to another.
     *
     * @param al1 The ArrayList to add to
     * @param al2 The ArrayList with elements to add
     */
    public static void add(ArrayList<Assembly> al1, ArrayList<Assembly> al2) {
        Enumeration<Assembly> e = Collections.enumeration(al2);

        while (e.hasMoreElements()) {
            al1.add(e.nextElement());
        }
    }

    /**
     * Returns the most-matched assembly in a collection.
     *
     * @param v The collection to look through
     * @return The most-matched assembly in a collection
     */
    protected Assembly best(ArrayList<Assembly> v) {
        Assembly best = null;
        Enumeration<Assembly> e = Collections.enumeration(v);

        while (e.hasMoreElements()) {
            Assembly a = e.nextElement();
            if (!a.hasMoreElements()) {
                return a;
            }
            if (best == null) {
                best = a;
            } else if (a.elementsConsumed() > best.elementsConsumed()) {
                best = a;
            }
        }
        return best;
    }

    /**
     * Returns an assembly with the greatest possible number of
     * elements consumed by matches of this parser.
     *
     * @param a An assembly to match against
     * @return An assembly with the greatest possible number of
     * elements consumed by this parser
     */
    public Assembly bestMatch(Assembly a) {
        ArrayList<Assembly> in = new ArrayList<Assembly>();
        in.add(a);
        ArrayList<Assembly> out = matchAndAssemble(in);
        return best(out);
    }

    /**
     * Returns either null, or a completely matched version of
     * the supplied assembly.
     *
     * @param a An assembly to match against
     * @return Either null, or a completely matched version of the
     * supplied assembly
     */
    public Assembly completeMatch(Assembly a) {
        Assembly best = bestMatch(a);
        if (best != null && !best.hasMoreElements()) {
            return best;
        }
        // ...
    }
    // ...
}
