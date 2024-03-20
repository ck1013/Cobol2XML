package parse.tokens;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import parse.Parser;

/**
 * A Word is a terminal that matches a sequence of characters.
 * This class represents a Word in the context of a parser.
 * It extends the Terminal class and overrides the 'qualifies' method
 * to check if a given token is a Word.
 *
 * @author Steven J. Metsker
 * @version 1.0.0
 * @copyright (c) 1999 Steven J. Metsker
 * @license This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 */
public class Word extends Terminal {

    /**
     * Returns true if an assembly's next element is a word.
     * This method checks if the given object 'o' is a Word by calling
     * the 'isWord' method on the Token 't'.
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
     * This method generates a random word with a length between 3 and 7
     * characters and returns it as an ArrayList.
     *
     * @param maxDepth maximum depth of the parse tree
     * @param depth    current depth of the parse tree
     * @return an ArrayList with one random word
     */
    public ArrayList<String> randomExpansion(int maxDepth, int depth) {
        if (depth > maxDepth) {
            return new ArrayList<>();
        }

        int n = new Random().nextInt(5) + 3; // generate random number between 3 and 7
        char[] letters = new char[n];
        for (int i = 0; i < n; i++) {
            int c = new Random().nextInt(26) + 'a'; // generate random lowercase letter
            letters[i] = (char) c;
        }

        ArrayList<String> v = new ArrayList<>();
        v.add(new String(letters));
        return v;
    }

    /**
     * Returns a textual description of this parser.
     * This method returns a string representation of the Word parser.
     * If the Word parser has already been printed, it returns an empty string.
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

