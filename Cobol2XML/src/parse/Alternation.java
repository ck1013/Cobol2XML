package com.example.parse;

import java.util.*;

/**
 * Alternation is a CollectionParser that matches an input Assembly list if any of its sub-parsers can match it.
 * It extends CollectionParser<Assembly> and implements the Parser interface.
 *
 * @param <Assembly> The type of the elements in the Assembly list.
 */
public class Alternation<Assembly> extends CollectionParser<Assembly> {

    /**
     * Constructs an Alternation object with no name or sub-parsers.
     */
    public Alternation() {
    }

    /**
     * Constructs an Alternation object with the given name and no sub-parsers.
     *
     * @param name The name of the Alternation object.
     */
    public Alternation(String name) {
        super(name);
    }

    /**
     * Constructs an Alternation object with the given Parser as its only sub-parser.
     *
     * @param p The Parser object to be added as a sub-parser.
     */
    public Alternation(Parser p) {
        super(p);
    }

    /**
     * Constructs an Alternation object with the given Parsers as its sub-parsers.
     *
     * @param p1 The first Parser object to be added as a sub-parser.
     * @param p2 The second Parser object to be added as a sub-parser.
     * @throws NullPointerException If either p1 or p2 is null.
     */
    public Alternation(Parser p1, Parser p2) throws NullPointerException {
        super(p1, p2);
    }

    /**
     * Constructs an Alternation object with the given Parsers as its sub-parsers.
     *
     * @param p1 The first Parser object to be added as a sub-parser.
     * @param p2 The second Parser object to be added as a sub-parser.
     * @param p3 The third Parser object to be added as a sub-parser.
     * @throws NullPointerException If any of p1, p2, or p3 is null.
     */
    public Alternation(Parser p1, Parser p2, Parser p3) throws NullPointerException {
        super(p1, p2, p3);
    }

    /**
     * Constructs an Alternation object with the given Parsers as its sub-parsers.
     *
     * @param p1 The first Parser object to be added as a sub-parser.
     * @param p2 The second Parser object to be added as a sub-parser.
     * @param p3 The third Parser object to be added as a sub-parser.
     * @param p4 The fourth Parser object to be added as a sub-parser.
     * @throws NullPointerException If any of p1, p2, p3, or p4 is null.
     */
    public Alternation(Parser p1, Parser p2, Parser p3, Parser p4) throws NullPointerException {
        super(p1, p2, p3, p4);
    }

    /**
     * Accepts a ParserVisitor object and an ArrayList of Assembly objects.
     * Calls the visitAlternation method of the ParserVisitor object with this and visited as arguments.
     *
     * @param pv   The ParserVisitor object to be accepted.
     * @param visited An ArrayList of Assembly objects to be visited.
     */
    @Override
    public void accept(ParserVisitor pv, ArrayList<Assembly> visited) {
        pv.visitAlternation(this, visited);
    }

    /**
     * Matches an input Assembly list with this Alternation object's sub-parsers.
     * Returns an ArrayList of Assembly objects that match any of the sub-parsers.
     *
     * @param in An ArrayList of Assembly objects to be matched.
     * @return An ArrayList of Assembly objects that match any of the sub-parsers.
     */
    @Override
    public ArrayList<Assembly> match(ArrayList<Assembly> in) {
        ArrayList<Assembly> out = new ArrayList<Assembly>();
        Enumeration<Parser> e = Collections.enumeration(subparsers);

        while (e.hasMoreElements()) {
            Parser p = e.nextElement();
            add(out, p.matchAndAssemble(in));
        }

        return out;
    }

    /**
     * Generates a random expansion of this Alternation object.
     * If the depth is greater than or equal to the maximum depth, it returns a random settlement.
     * Otherwise, it selects a random sub-parser and generates a random expansion of it with an increased depth.
     *
     * @param maxDepth The maximum depth of the random expansion.
     * @param depth    The current depth of the random expansion.
     * @return An ArrayList of Assembly objects representing the random expansion.
     */
    @Override
    protected ArrayList<?> randomExpansion(int maxDepth, int depth) {
        if (depth >= maxDepth) {
            return randomSettle(maxDepth, depth);
        }

        double n = (double) subparsers.size();
        int i =
