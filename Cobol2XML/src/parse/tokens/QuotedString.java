package parse.tokens;

import java.util.ArrayList;
import java.util.Random;

import parse.Parser;
import parse.Token;

/**
 * A QuotedString is a terminal that matches a sequence of characters
 * between double quotes.
 */
public class QuotedString extends Terminal {

    /**
     * Returns true if the given object is a quoted string.
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
     * Creates a set with one random quoted string with a length between 2 and 6.
     *
     * @param maxDepth  the maximum depth of the expansion
     * @param depth    the current depth of the expansion
     * @return an ArrayList with one random quoted string
     */
    @Override
    public ArrayList<String> randomExpansion(int maxDepth, int depth) {
        Random random = new Random();
        int length = random.nextInt(5) + 2;

        char[] letters = new char[length + 2];
        letters[0] = '"';
        letters[length + 1] = '"';

        for (int i = 0; i < length; i++) {
            int c = random.nextInt(26) + 'a';
            letters[i + 1] = (char) c;
        }

        ArrayList<String> v = new ArrayList<>();
        v.add(new String(letters));
        return v;
    }

    /**
     * Returns a textual description of this parser.
     *
     * @param visited a list of parsers already printed in this description
     * @return string a textual description of this parser
     * @see Parser#toString()
     */
    @Override
    public String unvisitedString(ArrayList<Parser> visited) {
        return "QuotedString";
    }
}
