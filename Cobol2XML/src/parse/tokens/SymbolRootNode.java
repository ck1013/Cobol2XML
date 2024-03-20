package com.example.parse;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * A root node for a symbol tree.
 *
 * This class represents the root node of a symbol tree, which is used to parse
 * character sequences into symbols.
 *
 * @author Steven J. Metsker
 */
public class SymbolRootNode extends SymbolNode {

    /**
     * The children of the root node.
     */
    protected SymbolNode[] children;

    /**
     * Create and initialize a root node.
     */
    public SymbolRootNode() {
        this(new SymbolNode[256]);
    }

    /**
     * Create and initialize a root node with the given children.
     *
     * @param children the children of the root node
     */
    public SymbolRootNode(SymbolNode[] children) {
        super(null, (char) 0);
        this.children = children;
        init();
    }

    /**
     * Add the given string as a symbol.
     *
     * @param s the character sequence to add
     */
    public void add(String s) {
        char c = s.charAt(0);
        SymbolNode n = ensureChildWithChar(c);
        n.addDescendantLine(s.substring(1));
        findDescendant(s).setValid(true);
    }

    /**
     * A root node has no parent and no character of its own, so its ancestry is "".
     *
     * @return an empty string
     */
    @Override
    public String ancestry() {
        return "";
    }

    /**
     * Find a child node with the given character.
     *
     * @param c the character to find
     * @return the child node with the given character
     */
    protected SymbolNode findChildWithChar(char c) {
        return children[c];
    }

    /**
     * Initialize the root node by creating and initializing its children.
     */
    protected void init() {
        int len = children.length;
        for (int i = 0; i < len; i++) {
            children[i] = new SymbolNode(this, (char) i);
            children[i].setValid(true);
        }
    }

    /**
     * Check if the root node has any children.
     *
     * @return true if the root node has any children, false otherwise
     */
    public boolean hasChildren() {
        for (SymbolNode child : children) {
            if (child.isValid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print the children of the root node.
     */
    public void printChildren() {
        for (SymbolNode child : children) {
            System.out.println(child);
        }
    }

    /**
     * Return a symbol string from a reader.
     *
     * @param r a reader to read from
     * @param first the first character of this symbol, already read from the reader
     * @return a symbol string from a reader
     * @throws IOException if an I/O error occurs
     */
    public String nextSymbol(PushbackReader r, int first) throws IOException {
        SymbolNode n1 = findChildWithChar((char) first);
        SymbolNode n2 = n1.deepestRead(r);
        SymbolNode n3 = n2.unreadToValid(r);
        return n3.ancestry();
    }
}
