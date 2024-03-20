package parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Logger;

public abstract class ParserTester {
    // use the classname for the logger, this way you can refactor
    private final static Logger LOGGER = Logger.getLogger(ParserTester.class.getName());
    protected Parser p;
    protected boolean logTestStrings = true;

    protected ParserTester(Parser p) {
        this.p = p;
    }

    protected abstract Assembly assembly(String s);

    protected boolean canGenerateProblem(int depth) {
        String s = p.randomInput(depth, separator());
        logTestString(s);
        Assembly a = assembly(s);
        a.setTarget(freshTarget());
        ArrayList<Assembly> in = new ArrayList<Assembly>();
        in.add(a);
        ArrayList<?> out = completeMatches(p.match(in));
        if (out.size() != 1) {
            logProblemFound(s, out.size());
            return true;
        }
        return false;
    }

    public static ArrayList<Assembly> completeMatches(ArrayList<?> in) {
        ArrayList<Assembly> out = new ArrayList<Assembly>();
        Enumeration<?> e = Collections.enumeration(in);
        while (e.hasMoreElements()) {
            Assembly a = (Assembly) e.nextElement();
            if (!a.hasMoreElements()) {
                out.add(a);
            }
        }
        return out;
    }

    protected Assembly freshTarget() {
        return null;
    }

    protected void logDepthChange(int depth) {
        LOGGER.info("Testing depth " + depth + "...");
    }

    protected void logPassed() {
        LOGGER.info("No problems found.");
    }

    protected void logProblemFound(String s, int matchSize) {
        LOGGER.info("Problem found for string:");
        LOGGER.info(s);
        if (matchSize == 0) {
            LOGGER.info("Parser cannot match this apparently valid string.");
        } else {
            LOGGER.info("The parser found " + matchSize + " ways to parse this string.");
        }
    }

    protected void logTestString(String s) {
        if (logTestStrings) {
            LOGGER.info("    Testing string " + s);
        }
    }

    protected String separator() {
        return " ";
    }

    public void setLogTestStrings(boolean logTestStrings) {
        this.logTestStrings = logTestStrings;
    }

    public void test() {
        for (int depth = 2; depth < 8; depth++) {
            logDepthChange(depth);
            for (int k = 0; k < 100; k++) {
                if (canGenerateProblem(depth)) {
                    return;
                }
            }
        }
        logPassed();
    }
}
