/*
 * Token.java is a part of the parse.tokens package and represents a token
 * in a stream of tokens.
 *
 * Copyright (c) 1999 Steven J. Metsker. This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package parse.tokens;

import java.util.Objects;

/**
 * Represents a token in a stream of tokens. A token can be of different
 * types, including number, word, symbol, and quoted string.
 */
public class Token {

    /**
     * The type of this token. TokenType is an enumeration that indicates
     * the type of a token.
     */
    protected TokenType ttype;

    /**
     * The string value of this token, or null. This field is used to store
     * the string representation of a token, such as "word" or "quoted".
     */
    protected String sval;

    /**
     * The numeric value of this token, or 0. This field is used to store
     * the numeric representation of a token, such as 3.14.
     */
    protected double nval;

    // Constants

    /**
     * A constant indicating that the end of the stream has been read.
     */
    public static final TokenType TT_EOF = new TokenType("eof");

    /**
     * A constant indicating that there are no more tokens.
     */
    public static final Token EOF = new Token(TT_EOF, "", 0);

    /**
     * A constant indicating that a token is a number, like 3.14.
     */
    public static final TokenType TT_NUMBER = new TokenType("number");

    /**
     * A constant indicating a token is a word, like "cat".
     */
    public static final TokenType TT_WORD = new TokenType("word");

    /**
     * A constant indicating that a token is a symbol, like "<=".
     */
    public static final TokenType TT_SYMBOL = new TokenType("symbol");

    /**
     * A constant indicating that a token is a quoted string, like "Launch Mi".
     */
    public static final TokenType TT_QUOTED = new TokenType("quoted");

    // Constructors

    /**
     * Constructs a token from the given char.
     *
     * @param c the char
     * @return a token constructed from the given char
     */
    public Token(char c) {
        this(TT_SYMBOL, String.valueOf(c), 0);
        if (sval.length() == 1) {
            sval = sval.toUpperCase();
        }
    }

    /**
     * Constructs a token from the given number.
     *
     * @param nval the number
     * @return a token constructed from the given number
     */
    public Token(double nval) {
        this(TT_NUMBER, "", nval);
    }

    /**
     * Constructs a token from the given string.
     *
     * @param sval the string
     * @return a token constructed from the given string
     */
    public Token(String sval) {
        this(TT_WORD, sval, 0);
    }

    /**
     * Constructs a token of the indicated type and associated string or
     * numeric values.
     *
     * @param ttype the type of the token, typically one of the constants
     *              this class defines
     * @param sval  the string value of the token, typically null except
     *              for WORD and QUOTED tokens
     * @param nval  the numeric value of the token, typically 0 except
     *              for NUMBER tokens
     * @return a token
     */
    public Token(TokenType ttype, String sval, double nval) {
        this.ttype = ttype;
        this.sval = sval;
        this.nval = nval;
    }

    // Methods

    /**
     * Returns true if the supplied object is an equivalent token.
     *
     * @param o the object to compare
     * @return true, if the supplied object is of the same type and
     * value
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }
        Token t = (Token) o;

        if (ttype != t.ttype) {
            return false;
        }
        if (ttype == TT_NUMBER) {
            return nval == t.nval;
        }
        if (sval == null || t.sval == null) {
            return false;
        }
        return sval.equals(t.sval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ttype, sval, nval);
    }

    /**
     * Returns true if the supplied object is an equivalent token, given
     * mellowness about case in strings and characters.
     *
     * @param o the object to compare
     * @return true, if the supplied object is of the same type and
     * value. This method disregards case when comparing the string
     * value of tokens.
     */
    public boolean equalsIgnoreCase(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }
        Token t = (Token) o;

        if (ttype != t.ttype) {
           
