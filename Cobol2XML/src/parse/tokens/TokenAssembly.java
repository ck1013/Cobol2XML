package parse.tokens;

import parse.Assembly;

public class TokenAssembly extends Assembly {

    private TokenString tokenString;

    /**
     * Constructs a TokenAssembly on a TokenString constructed from the given String.
     *
     * @param s the string to consume
     * @return a TokenAssembly that will consume a tokenized version of the supplied String
     * @throws NullPointerException if the tokenString is null
     */
    public TokenAssembly(String s) {
        this(new TokenString(s));
    }

    /**
     * Constructs a TokenAssembly on a TokenString constructed from the given Tokenizer.
     *
     * @param t the tokenizer to consume tokens from
     * @return a TokenAssembly that will consume a tokenized version of the supplied Tokenizer
     */
    public TokenAssembly(Tokenizer t) {
        this(new TokenString(t));
    }

    /**
     * Constructs a TokenAssembly from the given TokenString.
     *
     * @param tokenString the tokenString to consume
     * @throws NullPointerException if the tokenString is null
     */
    public TokenAssembly(TokenString tokenString) {
        if (tokenString == null) {
            throw new NullPointerException("TokenString cannot be null");
        }
        this.tokenString = tokenString;
    }

    /**
     * Returns a textual representation of the amount of this tokenAssembly that has been consumed.
     *
     * @param delimiter the mark to show between consumed elements
     * @return a textual description of the amount of this assembly that has been consumed
     */
    public String consumed(String delimiter) {
        if (delimiter == null) {
            delimiter = defaultDelimiter();
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < elementsConsumed(); i++) {
            if (i > 0) {
                buf.append(delimiter);
            }
            if (i < tokenString.length()) {
                buf.append(tokenString.tokenAt(i));
            }
        }
        return buf.toString();
    }

    /**
     * Returns the default string to show between elements consumed or remaining.
     *
     * @return the default string to show between elements consumed or remaining
     */
    public String defaultDelimiter() {
        return "/";
    }

    /**
     * Returns the number of elements in this assembly.
     *
     * @return the number of elements in this assembly
     */
    public int length() {
        return tokenString.length();
    }

    /**
     * Returns the next token.
     *
     * @return the next token from the associated token string.
     * @throws ArrayIndexOutOfBoundsException if there are no more tokens in this tokenizer's string.
     */
    public Object nextElement() {
        if (index < tokenString.length()) {
            return tokenString.tokenAt(index++);
        } else {
            throw new ArrayIndexOutOfBoundsException("No more tokens in this tokenizer's string.");
        }
    }

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     */
    public Object peek() {
        if (index < tokenString.length()) {
            return tokenString.tokenAt(index);
        } else {
            return null;
        }
    }

    /**
     * Returns a textual representation of the amount of this tokenAssembly that remains to be consumed.
     *
     * @param delimiter the mark to show between consumed elements
     * @return a textual description of the amount of this assembly that remains to be consumed
     */
    public String remainder(String delimiter) {
        if (delimiter == null) {
            delimiter = defaultDelimiter();
        }
        StringBuffer buf = new StringBuffer();
        for (int i = elementsConsumed(); i < tokenString.length(); i++) {
            if (i > elementsConsumed()) {
                buf.append(delimiter);
            }
            buf.append(tokenString.tokenAt(i));
        }
        return buf.toString();
    }
}
