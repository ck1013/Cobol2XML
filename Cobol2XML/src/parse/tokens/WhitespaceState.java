/**
 * This class, WhitespaceState, is a part of the parse.tokens package and extends the TokenizerState class.
 * It is used to ignore whitespace characters during the tokenization process.
 *
 * @author Steven J. Metsker
 * @version 1.0.0
 * @copyright 1999 Steven J. Metsker
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 */
package parse.tokens;

import java.io.*;

public class WhitespaceState extends TokenizerState {

    /**
     * An array of booleans indicating whether each character is whitespace.
     */
    protected boolean whitespaceChar[] = new boolean[256];

    /**
     * Constructs a WhitespaceState object with a default idea of what characters are, in fact, whitespace.
     *
     * @return a state for ignoring whitespace
     */
    public WhitespaceState() {
        // Set the default whitespace characters
        setWhitespaceChars(0, ' ', true);
    }

    /**
     * Ignores whitespace (such as blanks and tabs), and returns the tokenizer's next token.
     *
     * @param r             a PushbackReader object
     * @param aWhitespaceChar an integer representing a whitespace character
     * @param t             a Tokenizer object
     * @return the tokenizer's next token
     * @throws IOException if an I/O error occurs
     */
    public Token nextToken(PushbackReader r, int aWhitespaceChar, Tokenizer t) throws IOException {
        int c;
        // Read and ignore whitespace characters until a non-whitespace character is encountered
        do {
            c = r.read();
        } while (c >= 0 && c < whitespaceChar.length && whitespaceChar[c]);

        // If a non-whitespace character was encountered, unread it and return the next token
        if (c >= 0) {
            r.unread(c);
        }
        return t.nextToken();
    }

    /**
     * Establishes the given characters as whitespace to ignore.
     *
     * @param from          the first character in the range
     * @param second        the second character in the range
     * @param shouldIgnore  a boolean indicating whether this state should ignore characters in the given range
     */
    public void setWhitespaceChars(int from, int to, boolean shouldIgnore) {
        // Iterate over the range of characters and set their whitespace status accordingly
        for (int i = from; i <= to; i++) {
            if (i >= 0 && i < whitespaceChar.length) {
                whitespaceChar[i] = shouldIgnore;
            }
        }
    }
}
