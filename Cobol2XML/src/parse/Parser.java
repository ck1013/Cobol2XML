/*
 * @(#)Parser.java	 1.0.0
 *
 * Copyright (c) 1999 Steven J. Metsker
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

public abstract class Parser {
    /*
     * a name to identify this parser
     */
    private String name;

    /*
     * an object that will work on an assembly whenever this
     * parser successfully matches against the assembly
     */
    protected Assembler assembler;

    /**
     * Constructs a nameless parser.
     */
    protected Parser() {
        this.name = "";
    }

    /**
     * Constructs a parser with the given name.
     *
     * @param   String   A name to be known by. For parsers
     *                   that are deep composites, a simple name
     *                   identifying its purpose is useful.
     */
    protected Parser(String name) {
        this.name = name;
    }

    /**
     * Accepts a "visitor" which will perform some operation on
     * a parser structure. The book, "Design Patterns", explains
     * the visitor pattern.
     *
     * @param   ParserVisitor   the visitor to accept
     */
    public void accept(ParserVisitor pv) {
        accept(pv, new ArrayList<Assembly>());
    }

    /**
     * Accepts a "visitor" along with a collection of previously
     * visited parsers.
     *
     * @param   ParserVisitor   the visitor to accept
     *
     * @param   Vector   a collection of previously visited
     *                   parsers.
     */
    public abstract void accept(ParserVisitor pv, ArrayList<Assembly> visited);

    /**
     * Adds the elements of one vector to another.
     *
     * @param   al1   the ArrayList to add to
     *
     * @param   al2   the ArrayList with elements to add
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
     * @return   the most-matched assembly in a collection.
     *
     * @param   ArrayList   the collection to look through
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
     * @return   an assembly with the greatest possible number of
     *           elements consumed by this parser
     *
     * @param   Assembly   an assembly to match against
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
     * @return   either null, or a completely matched version of the
     *           supplied assembly
     *
     * @param   Assembly   an assembly to match against
     */
    public Assembly completeMatch(Assembly a) {
        Assembly best = bestMatch(a);
        if (best != null && !best.hasMoreElements()) {
            return best;
        }
       
