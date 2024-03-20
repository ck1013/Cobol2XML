/*
 * PubliclyCloneable.java
 * ---------------------
 *
 * Copyright (c) 2023 Example Corporation
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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * A PubliclyCloneable object is one to which any object can send <code>clone()</code>.
 *
 * @param <T> the type of the object being cloned
 * @author Example Corporation
 */
public interface PubliclyCloneable<T> extends Serializable {

    /**
     * A copy of the receiving object.
     *
     * @return a copy of the receiving object
     * @throws CloneNotSupportedException if the object does not implement the Cloneable interface
     */
    default T clone() throws CloneNotSupportedException {
        Objects.requireNonNull(this);

        Class<?> clazz = this.getClass();
        if (!Clonable.class.isAssignableFrom(clazz)) {
            throw new CloneNotSupportedException(
                String.format("%s does not implement Cloneable", clazz.getName()));
        }

        try {
            return clazz.getDeclaredMethod("clone").invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Cl
