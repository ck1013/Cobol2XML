/**
 * A state for recognizing symbols in a tokenizer. This class is responsible for 
 * identifying and processing symbols in the input stream. It maintains a list of 
 * multi-character symbols that it can recognize.
 *
 * This code is distributed under the GNU Lesser General Public License, which 
 * allows for free use, redistribution, and modification, but comes with no 
 * warranty.
 */
public class SymbolState extends TokenizerState {

    /**
     * The root node of a trie data structure that stores the multi-character 
     * symbols that this state can recognize.
     */
    SymbolRootNode symbols = new SymbolRootNode();

    /**
     * Constructs a symbol state with a default set of multi-character symbols to 
     * recognize. These symbols are: "!=", ":-", "<=", and ">=".
     */
    public SymbolState() {
        // Adds the default set of multi-character symbols to the trie
        add("!=");
        add(":-");
        add("<=");
        add(">=");
    }

    /**
     * Adds a multi-character symbol to the trie data structure.
     *
     * @param s the symbol to add, such as "=:="
     */
    public void add(String s) {
        symbols.add(s);
    }

    /**
     * Removes a multi-character symbol from the trie data structure.
     *
     * @param s the symbol to remove, such as "=:="
     */
    public void remove(String s) {
        symbols.remove(s);
    }

    /**
     * Reads the next token from the input stream, starting at the given character 
     * position. If the next token is a multi-character symbol that this state can 
     * recognize, it returns a new SymbolToken with the symbol's value. Otherwise, 
     * it returns null.
     *
     * @return a symbol token from the input stream, or null if no symbol is 
     *         recognized
     */
    public Token nextToken(PushbackReader r, int first, Tokenizer t) throws IOException {
        if (r == null) {
            throw new IllegalArgumentException("PushbackReader r cannot be null");
        }

        // Reads the next symbol from the input stream using the trie data structure
        String s = symbols.nextSymbol(r, first);
        if (s != null && !s.isEmpty()) { // If a symbol was recognized
            return new SymbolToken(s); // Returns a new SymbolToken with the symbol's value
        } else {
            return null; // Otherwise, returns null
        }
    }
}
