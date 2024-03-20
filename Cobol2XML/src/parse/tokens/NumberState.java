/*
 * NumberState.java
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

public class NumberState extends TokenizerState {
    protected int c;
    protected double value;
    protected boolean absorbedLeadingMinus;
    protected boolean absorbedDot;
    protected boolean gotAdigit;

    /**
     * Convert a stream of digits into a number, making this
     * number a fraction if the boolean parameter is true.
     */
    protected double absorbDigits(PushbackReader r, boolean fraction) throws IOException {
        int divideBy = 1;
        double v = 0;
        while ('0' <= c && c <= '9') {
            gotAdigit = true;
            v = v * 10 + (c - '0');
            c = r.read();
            if (fraction) {
                divideBy *= 10;
            }
        }
        if (fraction) {
            v = v / divideBy;
        }
        return v;
    }

    /**
     * Return a number token from a reader.
     *
     * @return a number token from a reader
     */
    public Token nextToken(PushbackReader r, int cin, Tokenizer t) throws IOException {
        reset(cin);
        parseLeft(r);
        parseRight(r);
        r.unread(c);
        return value(r, t);
    }

    /**
     * Parse up to a decimal point.
     */
    protected void parseLeft(PushbackReader r) throws IOException {
        if (c == '-') {
            c = r.read();
            absorbedLeadingMinus = true;
        }
        value = absorbDigits(r, false);
        if (c != '.' && c != '\n' && c != '\r' && c != -1) {
            r.unread(c);
        }
    }

    /**
     * Parse from a decimal point to the end of the number.
     */
    protected void parseRight(PushbackReader r) throws IOException {
        if (c == '.') {
            c = r.read();
            absorbedDot = true;
            if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9' && c != '\n' && c != '\r' && c != -1) {
                r.unread(c);
            }
            value += absorbDigits(r, true);
        }
        if (c != '\n' && c != '\r' && c != -1) {
            r.unread(c);
        }
    }

    /**
     * Prepare to assemble a new number.
     */
    protected void reset(int cin) {
        c = cin;
        value = 0;
        absorbedLeadingMinus = false;
        absorbedDot = false;
        gotAdigit = false;
    }

    /**
     * Put together the pieces of a number.
     */
    protected Token value(PushbackReader r, Tokenizer t) throws IOException {
        if (!gotAdigit) {
            if (absorbedLeadingMinus && absorbedDot) {
                r.unread('.');
                return t.symbolState().nextToken(r, '-', t);
            }
            if (absorbedLeadingMinus) {
                return t.symbolState().nextToken(r, '-', t);
            }
            if (absorbedDot) {
                return t.symbolState().nextToken(r, '
