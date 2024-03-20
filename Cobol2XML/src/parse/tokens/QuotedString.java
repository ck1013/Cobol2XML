package parse.tokens;

import java.util.ArrayList;
import java.util.Random;

import parse.Parser;
import parse.Token;

/**
 * A QuotedString is a terminal that matches a sequence of characters
 * between double quotes. This class extends the Terminal class and
 * overrides its methods to provide specific behavior for quoted strings.
 */
public class QuotedString extends Terminal {

    /**
     * Returns true if the given object is a quoted string. This method
     * checks if the token is a quoted string by calling the isQuotedString()
     * method of the Token class.
     *
     * @param o an object to check
     * @return true if the object is a quoted string
     */
    @Override
    protected boolean qualifies(Object o) {
        Token t = (Token) o;
        return t.isQuotedString();
    }

    /**

