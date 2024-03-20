/*
 * Program_IDAssembler.java
 *
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

package com.julianbass.cobol;

import parse.Assembly;
import parse.Token;
import parse.tokens.StringToken;

public class Program_IDAssembler extends Assembler {

    /**
     * Pop a string, and set the target Program_ID to this string.
     *
     * @param   assembly   the assembly to work on
     */
    @Override
    public void workOn(Assembly assembly) {
        Cobol cobol = new Cobol();
        Token token = (Token) assembly.pop();
        if (token instanceof StringToken) {
            cobol.setProgram_ID(token.sval().trim());
            assembly.setTarget(cobol);
        } else {
            throw new IllegalArgumentException("Expected a string token, but got: " + token
