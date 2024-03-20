import java.util.Objects;

/*
 * @(#)LimitingLinearCalculator.java	 1.0.0
 *
 * Copyright (c) 1999 Steven J. Metsker
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

package utensil;

public class LimitingLinearCalculator extends LinearCalculator {

    public LimitingLinearCalculator(double xFrom, double xTo, double yFrom, double yTo) {
        super(xFrom, xTo, yFrom, yTo);
    }

    /**
     * Return the value on the first scale, corresponding to the given
     * value on the second scale. Limit the X value to be between xFrom
     * and xTo.
     *
     * @return the value on the first scale, corresponding to the given
     *         value on the second scale
     */
    public double calculateXforGivenY(double y) {
        Objects.requireNonNull(y);
        if (y >= yFrom && y <= yTo) {
            return xFrom + (xTo - xFrom) * (y - yFrom) / (yTo - yFrom);
        }
        return y < yFrom ? xFrom : xTo;
    }

    /**
     * Return the value on the second scale, corresponding to the given
     * value on the first scale. Limit the Y value to be between yFrom
     * and yTo.
     *
     * @return the value on the second scale, corresponding to the given
     *         value on the first scale
     */
    public double calculateYforGivenX(double x) {
        Objects.requireNonNull(x);
        if (x >= xFrom && x <= xTo) {
            return yFrom + (yTo - yFrom) * (x - xFrom) / (xTo - xFrom);
        }
        return x < xFrom ? yFrom : yTo;
    }
}
