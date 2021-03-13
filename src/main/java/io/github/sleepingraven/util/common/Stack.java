package io.github.sleepingraven.util.common;

import java.util.EmptyStackException;

/**
 * Stack 接口
 *
 * @author Carry
 * @since 2021/2/13
 * @see java.util.Stack
 */
public interface Stack<E> {
    
    /**
     * Pushes an item onto the top of this stack. This has exactly
     *
     * @param item
     *         the item to be pushed onto this stack.
     *
     * @return the <code>item</code> argument.
     */
    E push(E item);
    
    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return The object at the top of this stack (the last item
     * of the <tt>Vector</tt> object).
     *
     * @throws EmptyStackException
     *         if this stack is empty.
     */
    E pop();
    
    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return the object at the top of this stack (the last item
     * of the <tt>Vector</tt> object).
     *
     * @throws EmptyStackException
     *         if this stack is empty.
     */
    E peek();
    
    /**
     * Tests if this stack is empty.
     *
     * @return <code>true</code> if and only if this stack contains
     * no items; <code>false</code> otherwise.
     */
    boolean empty();
    
    /**
     * Tests if this stack is empty.
     *
     * @return <code>true</code> if and only if this stack contains
     * no items; <code>false</code> otherwise.
     */
    boolean isEmpty();
    
}
