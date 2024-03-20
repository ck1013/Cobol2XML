/*
 * @(#)SlashState.java	 1.0.0
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

package parse.tokens;

import java.io.*;

/**
 * This class represents the SlashState in a tokenizer, which handles the '/' character.
 * It can delegate to a comment-handling state or return a token with just a slash in it.
 */
public class SlashState extends TokenizerState {

    /** The SlashStarState instance for handling '/*' comments. */
    protected SlashStarState slashStarState = new SlashStarState();

    /** The SlashSlashState instance for handling '//' comments. */
    protected SlashSlashState slashSlashState = new SlashSlashState();

    /**
     * Either delegate to a comment-handling state, or return a token with just a slash in it.
     *
     * @param  r             the PushbackReader to read characters from
     * @param  theSlash      the slash character ('/' in ASCII)
     * @param  t             the Tokenizer instance
     * @return              either just a slash token, or the results of delegating to a comment-handling state
     * @throws IOException  if an I/O error occurs
     */
    public Token nextToken(PushbackReader r, int theSlash, Tokenizer t) throws IOException {
        int c = r.read();
        if (c == '*') {
            return slashStarState.nextToken(r, '*', t);
        }
        if (c == '/') {
            return slashSlashState.nextToken(r, '/', t);
        }
        if (c >= 0) {
            r.unread(c);
        }
        return new Token(Token.TT_SYMBOL, "/", 0);
    }
}
