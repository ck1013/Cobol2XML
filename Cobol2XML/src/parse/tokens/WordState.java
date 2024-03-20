/*
 * @(#)WordState.java	1.0.0
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
 * A state for recognizing a word.
 *
 * @author Steven J. Metsker
 * @version 1.0.0
 * @since 1.0.0
 */
public class WordState<T> extends TokenizerState<T> {
	
	protected char charbuf[] = new char[16];
	protected boolean wordChar[] = new boolean[256];
	protected boolean wordStartChar[] = new boolean[256];
	
	/**
	 * Constructs a word state with a default idea of what
	 * characters are admissible inside a word (as described in
	 * the class comment).
	 *
	 * @return a state for recognizing a word
	 */
	public WordState() {
		setWordChars('a', 'z', true);
		setWordChars('A', 'Z', true);
		setWordChars('0', '9', true);
		setWordChars('-', '-', true);
		setWordChars('_', '_', true);
		setWordChars('\'', '\'', true);
		setWordChars(0xc0, 0xff, true);
		setWordStartChars(wordChar);
	}
	
	/**
	 * Fatten up charbuf as necessary.
	 */
	protected void checkBufLength(int i) {
		if (i >= charbuf.length) {
			char nb[] = new char[charbuf.length * 2];
			System.arraycopy(charbuf, 0, nb, 0, charbuf.length);
			charbuf = nb;
		}
	}
	
	/**
	 * Reset the character buffer.
	 */
	protected void resetBuf() {
		for (int i = 0; i < charbuf.length; i++) {
			charbuf[i] = 0;
		}
	}
	
	/**
	 * Get the current character buffer.
	 *
	 * @return the current character buffer
	 */
	protected char[] getCharbuf() {
		return charbuf;
	}
	
	/**
	 * Get the current character buffer length.
	 *
	 * @return the current character buffer length
	 */
	protected int getCharbufLength() {
		return charbuf.length;
	}
	
	/**
	 * Return a word token from a reader.
	 *
	 * @return a word token from a reader
	 */
	public Token<T> nextToken(PushbackReader r, int c, Tokenizer<T> t)
		throws IOException {
		
		int i =
