/*
 * QuoteState.java
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

package parse.tokens;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * A state that returns a quoted string token from a reader. This class collects
 * characters until it sees a match to the character that the tokenizer used to
 * switch to this state.
 */
public class QuoteState extends TokenizerState {

    /** The character buffer used to store the characters of the quoted string. */
    protected char[] charbuf = new char[16];

    /**
     * Fatten up charbuf as necessary.
     *
     * @param i the index to check against the buffer length
     */
    protected void checkBufLength(int i) {
        if (i >= charbuf.length) {
            char nb[] = new char[charbuf.length * 2];
            System.arraycopy(charbuf, 0, nb, 0, charbuf.length);
            charbuf = nb;
        }
    }

    /**
     * Returns a quoted string token from a reader.
     *
     * @param r the reader to read from
     * @param cin the input character
     * @param t the tokenizer object
     * @return a quoted string token from a reader
     * @throws IOException if an I/O error occurs
     */
    public Token nextToken(PushbackReader r, int cin, Tokenizer t) throws IOException {
        int i = 0;
        charbuf[i++] = (char) cin;
        int c;
        do {
            c =
