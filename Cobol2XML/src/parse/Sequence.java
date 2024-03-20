/*
 * @(#)Sequence.java	 1.0.0
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
import java.util.List;
import java.util.logging.Logger;

public class Sequence extends CollectionParser<Assembly> {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Sequence() {
    }

    public Sequence(String name) {
        super(name);
    }

    public Sequence(Parser p) {
        super(p);
    }

    public Sequence(Parser p1, Parser p2) {
        super(p1, p2);
    }

    public Sequence(Parser p1, Parser p2, Parser p3) {
        super(p1, p2, p3);
    }

    public Sequence(Parser p1, Parser p2, Parser p3, Parser p4) {
        super(p1, p2, p3, p4);
    }

    public void accept(ParserVisitor<? super Sequence> pv, List<Assembly> visited) {
        pv.visitSequence(this, visited);
    }

    public List<Assembly> match(List<Assembly> in) {
        List<Assembly> out = new ArrayList<>(in);
        Enumeration<Parser> e = Collections.enumeration(subparsers);

        while (e.hasMoreElements()) {
            Parser p = e.nextElement();
            out = p.matchAndAssemble(out);
            if (out.isEmpty()) {
                return out;
            }
        }
        return out;
    }

    @Override
    protected List<Assembly> randomExpansion(int maxDepth, int depth) {
        LOGGER.info("random expansion");
        List<Assembly> v = new ArrayList<>();
        Enumeration<Parser> e = Collections.enumeration(subparsers);

        while (e.hasMoreElements()) {
            Parser p = e.nextElement();
            List<Parser> w = new ArrayList<>(p.randomExpansion(maxDepth, depth + 1));
            Enumeration<Parser> f = Collections.enumeration(w);

            while (f.hasMoreElements()) {
                Assembly ass = f.nextElement();
                v.add(ass);
            }
        }
        return v;
    }

    @Override
    protected String toStringSeparator() {
        return " ";
    }
}
