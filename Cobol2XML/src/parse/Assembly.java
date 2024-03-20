package parse;

import java.util.*;

/**
 * A base class for assemblies that consume elements in a sequence.
 * <p>
 * Assemblies can be used to parse or generate sequences of elements,
 * such as lines of text, tokens in a language, or items in a list.
 * <p>
 * This class provides default implementations for cloning, counting, and accessing elements,
 * as well as a stack for intermediate results.
 * <p>
 * Subclasses must implement the consumed(String) and remainder(String) methods.
 */
public abstract class Assembly<T>
	implements Enumeration<T>, PubliclyCloneable {
	
	protected Stack<T> stack = new Stack<T>();
	protected PubliclyCloneable<T> target;
	protected int index = 0;
	
	/**
	 * Return a copy of this object.
	 * <p>
	 * This implementation creates a shallow copy of this object.
	 * Subclasses may need to override this method for deep copying.
	 * @return a copy of this object
	 */
	@Override
	public Object clone() {
		try(
			Assembly<T> a = (Assembly<T>) super.clone();
			a.stack = (Stack<T>) stack.clone();
			if (target != null) {
				a.target = (PubliclyCloneable<T>) target.clone();
			}
			return a;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(
		}
	}
	
	/**
	 * Returns the elements of the assembly that have been 
	 * consumed, separated by the specified delimiter.
	 * <p>
	 * This implementation concatenates the consumed elements with the delimiter.
	 * Subclasses should override this method for custom behavior.
	 * @param   delimiter   the mark to show between consumed
	 *                   elements
	 * @return   the elements of the assembly that have been 
	 *           consumed
	 */
	public abstract String consumed(String delimiter);
	
	/**
	 * Returns the default string to show between elements.
	 * <p>
	 * This implementation returns a single space character.
	 * Subclasses may override this method for custom behavior.
	 * @return   the default string to show between elements
	 */
	public abstract String defaultDelimiter();
	
	/**
	 * Returns the number of elements that have been consumed.
	 * @return   the number of elements that have been consumed
	 */
	public int elementsConsumed() {
		return index;
	}
	
	/**
	 * Returns the number of elements that have not been consumed.
	 * @return   the number of elements that have not been 
	 *           consumed
	 */
	public int elementsRemaining(){
		return length() - elementsConsumed();
	}
	
	/**
	 * Removes this assembly's stack.
	 * <p>
	 * This implementation returns the stack directly.
	 * Subclasses may override this method for custom behavior.
	 * @return   this assembly's stack
	 */
	public Stack<T> getStack(){
		return stack;
	}
	
	/**
	 * Returns the object identified as this assembly's "target". 
	 * <p>
	 * This implementation returns the target directly.
	 * Subclasses may override this method for custom behavior.
	 * @return   the target of this assembly
	 */
	public PubliclyCloneable<T> getTarget(){
		return target;
	}
	
	/**
	 * Returns true if this assembly has unconsumed elements.
	 * @return   true, if this assembly has unconsumed elements
	 */
	public boolean hasMoreElements()
