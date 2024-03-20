/*
 * DateAssembler.java - Version 1.0
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

package cobol;

import parse.Assembly;
import parse.Parser;
import parse.tokens.Token;

public class DateAssembler extends Parser {

    // Set the target SectionName to the popped string
    public void workOn(Assembly a) {
        Cobol c = new Cobol();
        Token t = (Token) a.pop();
        String tokenString = t.sval().trim();

        // Deconstruct token string into month, day, and year components
        String monthString = tokenString.substring(0, 3);
        c.setMonthDateWritten(monthString);

        String dayString = tokenString.substring(3, 5);
        int day = Integer.parseInt(dayString);
        c.setDayDateWritten(day);

        String yearString = tokenString.substring(5, 9);
        int year = Integer.parseInt(yearString);
        c.setYearDateWritten(year);

        a.setTarget(c);
    }
}
