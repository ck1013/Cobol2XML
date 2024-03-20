/*
 * Empty.java - A parser that matches an empty input sequence.
 *
 * Copyright (c) 1999 Steven J. Metsker. This software is free
 * software and is distributed under the terms of the GNU Lesser
 * General Public License.
 *
 * This parser is part of a library that is useful for matching
 * and manipulating input sequences. It is distributed without any
 * warranty.
 *
 * For more information, see the GNU Lesser General Public License.
 */

package parse;

import java.util.*;

/**
 * An empty parser that matches an empty input sequence. This parser
 * has no expansion and always succeeds in matching an empty input.
 */
public class Empty extends Parser {

    /**
     * Accepts a visitor and a collection of previously visited
     * parsers.
     *
     * @param   ParserVisitor   the visitor to accept
     * @param   ArrayList<Assembly>   a collection of previously visited parsers
     */
    public void accept(ParserVisitor pv, ArrayList<Assembly> visited) {
        pv.visitEmpty(this, visited);
    }

    /**
     * Given a set of assemblies, this method returns the set as
     * a successful match.
     *
     * @return   the input set of states
     *
     * @param   Vector<Assembly>   a vector of assemblies to match against
     */
    public ArrayList<Assembly> match(ArrayList<Assembly> in) {
        return elementClone(in);
    }

    /**
     * Returns an empty vector since there is no way to expand an
     * empty parser.
     *
     * @param   int   maxDepth, the maximum depth of the expansion
     * @param   int   depth, the current depth of the expansion
     *
     * @return   an empty ArrayList<Assembly>
     */
    protected ArrayList<Assembly> randomExpansion(int maxDepth, int depth) {
        return new ArrayList<Assembly>();
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param   ArrayList<Parser>   visited, a list of visited parsers
     *
     * @return   a string representation of this parser
     */
    protected String unvisitedString(ArrayList<Parser> visited) {
        return " empty ";
    }
}
