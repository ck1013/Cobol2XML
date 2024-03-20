/*
 * ParserVisitor.java
 *
 * This class is the base class for all parser visitors in a parser library.
 * It provides a template method design pattern for visiting different types
 * of parsers in a recursive descent parser.
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
 */

package parse;

import java.util.ArrayList;

public abstract class ParserVisitor {

    /**
     * Visits an alternation parser. This method should be implemented by
     * subclasses to provide specific behavior when visiting an alternation
     * parser.
     *
     * @param a     the alternation parser to visit
     * @param visited a collection of previously visited parsers
     */
    public abstract void visitAlternation(Alternation a, ArrayList<Assembly> visited);

    /**
     * Visits an empty parser. This method should be implemented by
     * subclasses to provide specific behavior when visiting an empty parser.
     *
     * @param e     the empty parser to visit
     * @param visited a collection of previously visited parsers
     */
    public abstract void visitEmpty(Empty e, ArrayList<Assembly> visited);

    /**
     * Visits a repetition parser. This method should be implemented by
     * subclasses to provide specific behavior when visiting a repetition parser.
     *
     * @param r     the repetition parser to visit
     * @param visited a collection of previously visited parsers
     */
    public abstract void visitRepetition(Repetition r, ArrayList<Assembly> visited);

    /**
     * Visits a sequence parser. This method should be implemented by
     * subclasses to provide specific behavior when visiting a sequence parser.
     *
     * @param s     the sequence parser to visit
     * @param visited a collection of previously visited parsers
     */
    public abstract void visitSequence(Sequence s, ArrayList<Assembly> visited);

    /**
     * Visits a terminal parser. This method should be implemented by
     * subclasses to provide specific behavior when visiting a terminal parser.
     *
     * @param t     the terminal parser to visit
     * @param visited a collection of previously visited parsers
     */
    public abstract void visitTerminal(Terminal t, ArrayList<Assembly> visited);
}
