/**
 * Returns a quoted string token from a reader.
 * This method reads characters from the given reader and collects them until it finds a matching character
 * for the quote that switched the tokenizer to this state.
 *
 * @param r          the reader to read from
 * @param cin        the input character
 * @param t          the tokenizer object
 * @return           a quoted string token from a reader
 * @throws IOException if an I/O error occurs
 */
public Token nextToken(PushbackReader r, int cin, Tokenizer t) throws IOException {
    int i = 0;
    charbuf[i++] = (char) cin; // Store the initial quote character
    int c;
    do {
        c = r.read(); // Read the next character from the reader
        checkBufLength(i); // Ensure the character buffer has enough space
        charbuf[i++] = (char) c; // Store the character in the buffer
    } while (c != cin); // Continue until the matching quote character is found

    // Create a new token with the collected characters (excluding the starting and ending quotes)
    String lexeme = new String(charbuf, 1, i - 2);
    Token token = new Token(TokenType.QUOTED_STRING, lexeme, t.getLine(), t.getColumn());

    // Move the reader position past the ending quote
    t.nextColumn(i - 1);
    t.nextLine(r);

    return token;
}
