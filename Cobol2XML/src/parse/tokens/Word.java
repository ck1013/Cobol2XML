package parse.tokens;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import parse.Parser;

/**
 * A Word is a terminal that matches a sequence of characters.
 *
 * @author Steven J. Metsker
 * @version 1.0.0
 * @copyright (c) 1999 Steven J. Metsker
 * @license This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 */
public class Word extends Terminal {

    /**
     * Returns true if an assembly's next element is a word.
     *
     * @param o an element from an assembly
     * @return true, if an assembly's next element is a word
     */
    @Override
    protected boolean qualifies(Object o) {
        Token t = (Token) o;
        return t.isWord();
    }

    /**
     * Create a set with one random word (with 3 to 7 characters).
     *
     * @param maxDepth maximum depth of the parse tree
     * @param depth    current depth of the parse tree
     * @return an ArrayList with one random word
     */
    public ArrayList<String> randomExpansion(int maxDepth, int depth) {
        if (depth > maxDepth) {
            return new ArrayList<>();
        }

        int n = new Random().nextInt(5) + 3;
        char[] letters = new char[n];
        for (int i = 0; i < n; i++) {
            int c = new Random().nextInt(26) + 'a';
            letters[i] = (char) c;
        }

        ArrayList<String> v = new ArrayList<>();
        v.add(new String(letters));
        return v;
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param visited a list of parsers already printed in this description
     * @return string a textual description of this parser
     * @see Parser#toString()
     */
    @Override
    public String unvisitedString(ArrayList<Parser> visited) {
        if (visited.contains(this)) {
            return "";
        }
        visited.add(this);
        return "Word";
    }
}
