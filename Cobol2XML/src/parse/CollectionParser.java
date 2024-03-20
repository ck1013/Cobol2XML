/*
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
import java.util.Collections;
import java.util.Iterator;
import java.util.Parser;

/**
 * An abstract class for parsers that are a collection of other parsers.
 */
public abstract class CollectionParser extends Parser {

    /**
     * The parsers that this parser is a collection of.
     */
    protected ArrayList<Parser> subparsers = new ArrayList<Parser>();

    /**
     * Supports subclass constructors with no arguments.
     */
    protected CollectionParser() {
    }

    /**
     * Supports subclass constructors with a name argument.
     *
     * @param name the name of this parser
     */
    protected CollectionParser(String name) {
        super(name);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parser.
     *
     * @param p the parser to add
     */
    protected CollectionParser(Parser p) {
        subparsers.add(p);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1 the first parser
     * @param p2 the second parser
     */
    protected CollectionParser(Parser p1, Parser p2) {
        subparsers.add(p1);
        subparsers.add(p2);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1 the first parser
     * @param p2 the second parser
     * @param p3 the third parser
     */
    protected CollectionParser(Parser p1, Parser p2, Parser p3) {
        subparsers.add(p1);
        subparsers.add(p2);
        subparsers.add(p3);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param p1 the first parser
     * @param p2 the second parser
     * @param p3 the third parser
     * @param p4 the fourth parser
     */
    protected CollectionParser(Parser p1, Parser p2, Parser p3, Parser p4) {
        subparsers.add(p1);
        subparsers.add(p2);
        subparsers.add(p3);
        subparsers.add(p4);
    }

    /**
     * A convenient way to construct a CollectionParser with the given parsers.
     *
     * @param parsers the parsers to add
     */
    protected CollectionParser(Parser... parsers) {
        for (Parser parser : parsers) {
            subparsers.add(parser);
        }
    }

    /**
     * Adds a parser to the collection.
     *
     * @param e the parser to add
     * @return this
     */
    public CollectionParser add(Parser e) {
        if (e != null) {
            subparsers.add(e);
        }
        return this;
    }

    /**
     * Returns this parser's subparsers.
     *
     * @return the subparsers
     */
    public ArrayList<Parser> getSubparsers() {
        return subparsers;
    }

    /**
     * Helps to textually describe this CollectionParser.
     *
     * @return the string to place between parsers in the collection
     */
    protected abstract String getSeparator();

    /**
     * Returns a textual description of this parser.
     *
     * @param visited the parsers that have already been visited
     * @return the string representation of this parser
     */
    protected String unvisitedString(ArrayList<Parser> visited) {
        StringBuffer buf = new StringBuffer("<");
        boolean needSeparator = false;
        Iterator<Parser> iterator = subparsers.iterator();
        while (iterator.hasNext())
