/*
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
import java.io.Reader;
import java.io.StringReader;

/**
 * A simple tokenizer that reads characters from a reader and produces
 * tokens.
 */
public class Tokenizer {

    /*
     * The reader to read characters from
     */
    protected PushbackReader reader;

    /*
     * The number of characters that might be in a symbol
     */
    protected static final int DEFAULT_SYMBOL_MAX = 4;

    /*
     * The state lookup table
     */
    protected TokenizerState[] characterState = new TokenizerState[256];

    /*
     * The default states that actually consume text and
     * produce a token
     */
    protected NumberState numberState = new NumberState();
    protected QuoteState quoteState = new QuoteState();
    protected SlashState slashState = new SlashState();
    protected SymbolState symbolState = new SymbolState();
    protected WhitespaceState whitespaceState = new WhitespaceState();
    protected WordState wordState = new WordState();

    /**
     * Constructs a tokenizer with a default state table (as
     * described in the class comment).
     *
     * @return a tokenizer
     */
    public Tokenizer() {
        this(new InputStreamReader(System.in));
    }

    /**
     * Constructs a tokenizer to read from the supplied reader.
     *
     * @param reader the reader to read from
     */
    public Tokenizer(Reader reader) {
        setReader(reader);
        setDefaultCharacterStates(); // Initialize default character states
    }

    /**
     * Returns the reader this tokenizer will read from.
     *
     * @return the reader this tokenizer will read from
     */
    public PushbackReader getReader() {
        return reader;
    }

    /**
     * Returns the next token.
     *
     * @return the next token.
     *
     * @exception IOException if there is any problem reading
     */
    public Token nextToken() throws IOException {
        int c = reader.read(); // Read the next character

        if (c >= 0 && c < characterState.length) {
            return characterState[c].nextToken(reader, c, this); // Dispatch to the appropriate state based on the character
        }
        return Token.EOF; // Return the end-of-file token if the character is out of range
    }

    /**
     * Returns the state this tokenizer uses to build numbers.
     *
     * @return the state this tokenizer uses to build numbers
     */
    public NumberState numberState() {
        return numberState;
    }

    /**
     * Returns the state this tokenizer uses to build quoted
     * strings.
     *
     * @return the state this tokenizer uses to build quoted
     *          strings
     */
    public QuoteState quoteState() {
        return quoteState;
    }

    /**
     * Changes the state the tokenizer will enter upon reading
     * any character between "from" and "to".
     *
     * @param from the "from" character
     *
     * @param to the "to" character
     *
     * @param state the state to enter upon reading a
     *              character between "from" and "to"
     */
    public void setCharacterState(int from, int to, TokenizerState state) {
        for (int i = from; i <= to; i++) {
            if (i >= 0 && i < characterState.length) {
                characterState[i] = state; // Set the character state in the lookup table
            }
        }
    }

    /**
     * Sets the reader to read from.
     *
     * @param reader the reader to read from
