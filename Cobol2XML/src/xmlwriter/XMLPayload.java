/*
 * @(#)XMLPayload.java	 0.1.0
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

package xmlwriter;

import cobol.*; // Fixed the missing import statement
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.logging.Logger;

/**
 * XMLPayload class is used to create a XML document by adding Cobol elements.
 */
public class XMLPayload {
    Document doc;
    Element rootElement;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Constructs an XMLPayload object with a root element named "cobol".
     */
    public XMLPayload() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // XML parsers should not be vulnerable to XXE attacks
            dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // XML parsers should not be vulnerable to XXE attacks
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            // root element
            rootElement = doc.createElement("cobol");
            doc.appendChild(rootElement);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds Cobol elements to the XML document based on the provided Cobol object.
     *
     * @param c Cobol object containing the data for the XML document
     */
    public void addElements(Cobol c) {
        addElement("sectionName", c.getSectionName());
        addElement("divisionName", c.getDivisionName());
        addElement("Program_ID", c.getProgram_ID());
        addDateWrittenElements(c.getDayDateWritten(), c.getMonthDateWritten(), c.getYearDateWritten());
    }

    /**
     * Adds a Cobol element to the XML document.
     *
     * @param elementName Name of the Cobol element
     * @param elementValue Value of the Cobol element
     */
    private void addElement(String elementName, String elementValue) {
        if (elementValue != null) {
            Element cobolname = doc.createElement(elementName);
            cobolname.appendChild(doc.createTextNode(elementValue));
            rootElement.appendChild(cobolname);
        } else {
            LOGGER.warning("Null value for element: " + elementName);
        }
    }

    /**
     * Adds DateWritten elements to the XML document.
     *
     * @param dayDateWritten Day of the date
     * @param monthDateWritten Month of the date
     * @param yearDateWritten Year of the date
     */
    private void addDateWrittenElements(int dayDateWritten, String monthDateWritten, int yearDateWritten) {
        if (dayDateWritten != 0) {
            addElement("day-date-written", String.valueOf(dayDateWritten));
        }
        if (monthDateWritten != null) {
            addElement("month-date-written", monthDateWritten);
        }
        if (yearDateWritten != 0) {
            addElement("year-date-written", String.valueOf(yearDateWritten));
        }
    }

    // The rest of the code remains the same
}
