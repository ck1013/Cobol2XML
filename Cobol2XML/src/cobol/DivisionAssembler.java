package com.julianm.bass;

import java.util.Token;

/*
 * @(#)DataDivisionAssembler.java	 0.0.1
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
 *
 */

/**
 * Assembles a Data Division from a given Assembly.
 */
public class DataDivisionAssembler extends com.parse.Assembler {

    /**
     * Pop a string from the Assembly and set the target DataDivision to this string.
     *
     * @param   assembly   the assembly to work on
     */
    public void workOn(Assembly assembly) {
        Cobol c = new Cobol();
        Token t = (Token) assembly.pop();
        c.setDivisionName(t.sval().trim());
        assembly.setTarget(c);
    }
}
