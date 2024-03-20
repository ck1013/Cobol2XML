package parse;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class representing an assembler that can parse a sequence of tokens
 * and perform some action on them.
 *
 * @param <T> the type of elements in the assembler's stack and the input sequence
 */
public abstract class Assembler<T> {

    /**
     * Returns a list of the elements on the assembler's stack that appear before a
     * specified fence.
     *
     * @param <F> the type of the fence object
     * @param a   the assembler whose stack should contain some number of items
     *            above a fence marker
     * @param fence the fence, a marker of where to stop popping the stack
     * @return a list of the elements above the specified fence
     */
    public static <F> List<T> elementsAbove(Assembler<T> a, F fence) {
        List<T> items = new ArrayList<>();
        while (!a.stackIsEmpty()) {
            T top = a.pop();
            if (top instanceof F && top == fence) {
                break;
            }
            items.add(top);
        }
        return items;
    }

    /**
