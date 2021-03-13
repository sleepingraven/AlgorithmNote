package io.github.sleepingraven.util.common;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * 包装了 ArrayDeque 的 Stack
 *
 * @author Carry
 * @since 2021/2/13
 */
public class ArrayStack<E> implements Stack<E> {
    private final ArrayDeque<E> deque;
    
    public ArrayStack() {
        this(new ArrayDeque<>());
    }
    
    public ArrayStack(int initialCapacity) {
        this(new ArrayDeque<>(initialCapacity));
    }
    
    public ArrayStack(Collection<? extends E> c) {
        this(new ArrayDeque<>(c));
    }
    
    public ArrayStack(ArrayDeque<E> deque) {
        this.deque = deque;
    }
    
    @Override
    public E push(E item) {
        deque.push(item);
        return item;
    }
    
    @Override
    public E pop() {
        ensureNotEmpty();
        return deque.pop();
    }
    
    @Override
    public E peek() {
        ensureNotEmpty();
        return deque.peek();
    }
    
    @Override
    public boolean empty() {
        return deque.isEmpty();
    }
    
    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }
    
    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }
    
}
