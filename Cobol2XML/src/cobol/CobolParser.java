/*
 * Copyright (c) 2019 Julian M. Bass
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

package cobol;

import parse.Alternation;
import parse.Assembler;
import parse.Empty;
import parse.Parser;
import parse.Sequence;
import parse.tokens.CaselessLiteral;
import parse.tokens.Num;
import parse.tokens.Symbol;
import parse.tokens.Tokenizer;
import parse.tokens.Word;

/**
 * CobolParser is a class that provides a parser for COBOL source code.
 * It creates a COBOL object as an assembly's target and recognizes
 * the selected COBOL source code constructs.
 */
public class CobolParser {
    
    /**
     * Default constructor for CobolParser.
     */
    public CobolParser() {
        // constructor
    }
    
    /**
     * Return a parser that will recognize the selected COBOL source code constructs:
     *
     * This parser creates a COBOL object as an assembly's target.
     *
     * @return a parser that will recognize and build a COBOL object from a source code file.
     */
    public Parser cobol() {
        Alternation a = new Alternation();
        
        Symbol fullstop = new Symbol('.');
        fullstop.discard(); // discards the full stop symbol
        
        a.add(ProgramID());
        a.add(DivisionName());
        a.add(SectionName());
        a.add(DateWritten());
        a.add(new Empty()); // matches an empty sequence
        
        return a;
    }
    
    /**
     * Return a parser that will recognize the grammar:
     *
     *    Program Identifier = Word
     *
     */
    protected Parser ProgramID() {
        Sequence s = new Sequence();
        s.add(new CaselessLiteral("program-id"));
        s.add(fullstop); // discards the full stop symbol
        s.add(new Word().setAssembler(new Program_idAssembler()));
        return s;
    }
    
    /**
     * Return a parser that will recognise the grammar:
     *
     *    <divisionName> division
     *
     */
    protected Parser DivisionName() {
        Sequence s = new Sequence();
        s.add(new Word().setAssembler(new DivisionAssembler()));
        s.add(new CaselessLiteral("division"));
        s.add(fullstop); // discards the full stop symbol
        return s;
    }
    
    /**
     * Return a parser that will recognize the grammar:
     *
     *    Section Name = Word
     *
     */
    protected Parser SectionName() {
        Sequence s = new Sequence();
        s.add(new Word().setAssembler(new SectionNameAssembler()));
        s.add(new CaselessLiteral("section"));
        s.add(fullstop); // discards the full stop symbol
        return s;
    }
    
    /**
     * Return a parser that will recognise the grammar:
     *
     *    working-storage section
     *
     */
    protected Parser DateWritten() {
        Sequence s = new Sequence();
        s.add(new CaselessLiteral("date-written"));
        s.add(fullstop); // discards the full stop symbol
        s.add(new Num()); // matches a number
        s.add(new Symbol('-').discard()); // discards the hyphen symbol
        s.add(new Word()); // matches a word
        s.add(new Symbol('-').discard()); // discards the hyphen symbol
        s.add(new Word().discard()); // discards the word
        s.add(fullstop); // discards the full stop symbol
        s.setAssembler(new DateAssembler());
        return s;
    }
    
    /**
     * Returns the primary parser for this class -- cobol().
     *
     * @return the primary parser for this class -- cobol
     */
    public static Parser start() {
        return new CobolParser().cobol();
    }
    
    /**
     * Returns a tokenizer that does not allow spaces to appear inside
     * the "words" that identify cobol's grammar.
     *
     * @return a tokenizer that does not allow spaces to appear inside
     * the "words" that identify cobol grammar.
     */
    public static Tokenizer tokenizer() {
        Tokenizer t = new Tokenizer();
        t.wordState().setWordChars(' ', ' ', false);
        return t;
    }
    
    /**
     * Parses the given COBOL source code using the COBOL parser.
     *
     * @param cobolSourceCode the COBOL source code to parse
     * @return the parsed COBOL object
     */
    public COBOL parse(String cobolSourceCode) {
        Tokenizer tokenizer = tokenizer();
        tokenizer.input(cobolSourceCode);
        Parser parser = cobol();
        COBOL cobol = (COBOL) parser.parse(
