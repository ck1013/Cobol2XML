/*
 * @(#)FileString.java	 1.0.0
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
 *
 */

package utensil;

import java.io.*;

/**
 * The FileString class provides a method for reading the contents of a file
 * and returning it as a single String object.
 */
public class FileString {

    /**
     * The BUFLEN constant defines the buffer length used for reading the
     * file.
     */
    private static final int BUFLEN = 1024;

    /**
     * The stringFromFileNamed method reads the contents of a file and
     * returns it as a single String object.
     *
     * @param fileName the name of the file to read
     * @return the contents of a file as a String
     * @throws IOException if the file is not found, or if there is any
     * problem reading the file
     */
    public static String stringFromFileNamed(String fileName) throws IOException {
        char buf[] = new char[BUFLEN]; // create character buffer

        try (FileReader in = new FileReader(fileName);
             StringWriter out = new StringWriter()) {

            int len;
            while ((len = in.read(buf, 0, BUFLEN)) != -1) {
                out.write(buf, 0, len); // write data to StringWriter
            }
            return out.toString(); // return resulting String
        } catch (IOException e) {
            throw e; // rethrow IOException
        } catch (Exception e) {
            e.printStackTrace(); // print stack trace for other exceptions
            throw new IOException(e); // wrap other exceptions in IOException
        }
    }
}
