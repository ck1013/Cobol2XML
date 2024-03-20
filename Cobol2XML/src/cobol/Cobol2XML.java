package cobol;

import java.io.*;
import java.util.logging.*;
import logger.MyLogger;
import parse.*;
import parse.tokens.*;
import xmlwriter.*;

public class Cobol2XML {

    private static final Logger LOGGER = Logger.getLogger(Cobol2XML.class.getName());

    public static void main(String[] args) {
        try {
            MyLogger.setup();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problems with creating the log files", e);
            throw new RuntimeException(e);
        }

        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Cobol2XML V0.1.0");

        if (args.length != 2) {
            LOGGER.severe("Usage: java Cobol2XML <input_file> <output_file>");
            return;
        }

        XMLPayload xmlp = new XMLPayload();

        try (InputStream is = new FileInputStream(args[0]);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            Tokenizer t = CobolParser.tokenizer();
            Parser p = CobolParser.start();

            String s;
            while ((s = r.readLine()) != null) {
                t.setString(s);
                Assembly in = new TokenAssembly(t);
                Assembly out = p.bestMatch(in);
                Cobol c = new Cobol();
                c = (Cobol) out.getTarget();

                if (c != null) {
                    xmlp.addElements(c);
                }
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading input file", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing cobol source", e);
        }

        try {
            xmlp.writeFile(args[1]);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error writing output file", e);
        }
    }
}
