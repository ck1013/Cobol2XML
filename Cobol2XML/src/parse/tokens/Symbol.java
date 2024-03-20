/*
 * Symbol.java
 *
 * This class represents a Symbol parser that matches a specific token.
 * It is a part of the parse.tokens package.
 */

package parse.tokens;

import java.util.Vector;
import parse.Token;
import parse.Parser;

/**
 * A Symbol is a parser that matches a specific token.
 * It extends the Terminal class and implements the Parser interface.
 */
public class Symbol extends Terminal {

    // the literal to match
    protected Token symbol;

    /**

