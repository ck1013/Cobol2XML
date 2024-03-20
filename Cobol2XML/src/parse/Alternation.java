package com.example.parse;

import java.util.*;

/**
 * @(#)Alternation.java	 1.0.0
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

public class Alternation extends CollectionParser<Assembly> {

    public Alternation() {
    }

    public Alternation(String name) {
        super(name);
    }

    public Alternation(Parser p) {
        super(p);
    }

    public Alternation(Parser p1, Parser p2) throws NullPointerException {
        super(p1, p2);
    }

    public Alternation(Parser p1, Parser p2, Parser p3) throws NullPointerException {
        super(p1, p2, p3);
    }

    public Alternation(Parser p1, Parser p2, Parser p3, Parser p4) throws NullPointerException {
        super(p1, p2, p3, p4);
    }

    @Override
    public void accept(ParserVisitor pv, ArrayList<Assembly> visited) {
        pv.visitAlternation(this, visited);
    }

    @Override
    public ArrayList<Assembly> match(ArrayList<Assembly> in) {
        ArrayList<Assembly> out = new ArrayList<Assembly>();
        Enumeration<Parser> e = Collections.enumeration(subparsers);

        while (e.hasMoreElements()) {
            Parser p = e.nextElement();
            add(out, p.matchAndAssemble(in));
        }

        return out;
    }

    @Override
    protected ArrayList<?> randomExpansion(int maxDepth, int depth) {
        if (depth >= maxDepth) {
            return randomSettle(maxDepth, depth);
        }

        double n = (double) subparsers.size();
        int i = (int) (n * Math.random());
        Parser j = (Parser) subparsers.get(i);
        return j.randomExpansion(maxDepth, depth++);
    }

    @Override
    protected ArrayList<?> randomSettle(int maxDepth, int depth) {
        ArrayList<Parser> terms = new ArrayList<Parser>();
        Enumeration<Parser> e = Collections.enumeration(subparsers);

        while (e.hasMoreElements()) {
            Parser j = e.nextElement();
            if (j instanceof Terminal) {
                terms.add(j);
            }
        }

        ArrayList<Parser> which = terms;
        if (terms.isEmpty()) {
            which = subparsers;
        }

        double n = (double) which.size();
        int i = (int) (n * Math.random());
        Parser p = which.get(i);
        return p.randomExpansion(maxDepth, depth++);
    }

    @Override
    protected String toStringSeparator() {
        return "|";
    }
}
