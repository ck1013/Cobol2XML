package parse.tokens;

import java.io.IOException;
import java.util.Vector;

/**
 * A TokenStringSource is a source of TokenStrings. It reads TokenStrings
 * using the specified Tokenizer, delimited by the specified delimiter.
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
 * @author Steven J. Metsker
 */
public class TokenStringSource {
    protected Tokenizer tokenizer;
    protected String delimiter;
    protected TokenString cachedTokenString = null;

    /**
     * Constructs a TokenStringSource that will read TokenStrings
     * using the specified tokenizer, delimited by the specified delimiter.
     *
     * @param tokenizer   a tokenizer to read tokens from
     * @param delimiter   the character that fences off where one
     *                   TokenString ends and the next begins
     * @return   a TokenStringSource that will read TokenStrings
     *            from the specified tokenizer, delimited by the
     *            specified delimiter
     */
    public TokenStringSource(Tokenizer tokenizer, String delimiter) {
        this.tokenizer = tokenizer;
        this.delimiter = delimiter;
    }

    /**
     * Ensures that the cache is loaded.
     */
    protected void ensureCacheIsLoaded() {
        if (cachedTokenString == null) {
            loadCache();
        }
    }

    /**
     * Returns true if the source has more TokenStrings.
     *
     * @return   true, if the source has more TokenStrings that
     *           have not yet been popped with nextTokenString
     */
    public boolean hasMoreTokenStrings() {
        ensureCacheIsLoaded();
        return cachedTokenString != null;
    }

    /**
     * Loads the next TokenString into the cache, or sets the
     * cache to null if the source is out of tokens.
     */
    protected void loadCache() {
        Vector<Token> tokenVector = nextVector();
        if (tokenVector.isEmpty()) {
            cachedTokenString = null;
        } else {
            Token tokens[] = new Token[tokenVector.size()];
            tokenVector.toArray(tokens);
            cachedTokenString = new TokenString(tokens);
        }
    }

    /**
     * Shows the example in the class comment.
     *
     * @param args ignored
     */
    public static void main(String args[]) {
        String s = "I came; I saw; I left in peace;";

        if (s.isEmpty()) {
            System.out.println("No input string provided.");
            return;
        }

        TokenStringSource tss = new TokenStringSource(new Tokenizer(s), ";");

        while (tss.hasMoreTokenStrings()) {
            System.out.println(tss.nextTokenString());
        }
    }

    /**
     * Returns the next TokenString from the source.
     *
     * @return   the next TokenString from the source
     */
    public TokenString nextTokenString() {
        ensureCacheIsLoaded();
        TokenString returnTokenString = cachedTokenString;
        cachedTokenString = null;
        return returnTokenString;
    }

    /**
     * Returns a Vector of the tokens in the source up to either
     * the delimiter or the end of the source.
     *
     * @return   a Vector of the tokens in the source up to either
     *           the delimiter or the end of the source
     */
    protected Vector<Token> nextVector() {
        Vector<Token> v = new Vector<>();
        try {
            Token tok;
            while (true) {
                tok = tokenizer.nextToken();
                if (tok.ttype() == Token.TT_EOF ||
                        tok.sval().equals(delimiter)) {
                    break;
                }
                v.addElement(tok);
            }
        } catch (IOException e) {
            throw new InternalError(
                    "Problem tokenizing string: " + e);
        }
        return v;
    }
}
