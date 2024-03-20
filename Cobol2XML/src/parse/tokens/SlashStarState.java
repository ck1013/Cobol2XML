/*
 * SlashStarState.java
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

public class SlashStarState extends TokenizerState {

    /**
     * Ignore everything up to a closing star and slash, and then return the
     * tokenizer's next token.
     *
     * @param r
     *            the PushbackReader to read from
     * @param theStar
     *            the position of the opening slash-star
     * @param t
     *            the Tokenizer to get the next token from
     * @return the tokenizer's next token
     * @throws IOException
     *             if there is an error reading from the PushbackReader
     */
    public Token nextToken(PushbackReader r, int theStar, Tokenizer t)
            throws IOException {
        int c = 0;
        int lastc = 0;
        while (c >= 0) {
            if ((lastc == '*') && (c == '/')) {
                break;
            }
           
