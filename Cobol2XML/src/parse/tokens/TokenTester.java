/*
 * @(#)TokenTester.java	1.0.0
 *
 * Copyright (c) 1999-2023 Steven J. Metsker
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
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

package parse.tokens;

import parse.*;

/**
 * A tester class for the Token parser. This class extends the ParserTester class and overrides its assembly() method to create a new TokenAssembly instance.
 */
public class TokenTester extends ParserTester {

    /**
     * Constructs a new TokenTester instance with the given Parser.
     *
     * @param p the Parser to test
     *
     * This constructor initializes a new TokenTester instance with the given Parser object.
     */
    public TokenTester(Parser p) {
        super(p);
    }

    /**
     * Creates a new TokenAssembly instance with the given string.
     *
     * @param s the string to create the TokenAssembly from
     * @return a new TokenAssembly instance
     *
     * This method overrides the assembly() method of the ParserTester class to create a new TokenAssembly instance instead of a DefaultAssembly instance.
     */
    @Override
    protected Assembly assembly(String s) {
        return new TokenAssembly(s);
    }
}

