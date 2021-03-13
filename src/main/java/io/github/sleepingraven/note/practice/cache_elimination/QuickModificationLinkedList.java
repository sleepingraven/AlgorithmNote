package io.github.sleepingraven.note.practice.cache_elimination;

import java.util.function.Supplier;

/**
 * O(1) 更新的链表。直接操作节点
 *
 * @author Carry
 * @since 2021/1/28
 */
public class QuickModificationLinkedList<E extends ListNode<E>> {
    private final E head;
    private final E tail;
    
    QuickModificationLinkedList(Supplier<E> nodeSupplier) {
        this.head = nodeSupplier.get();
        this.tail = nodeSupplier.get();
        head.next = tail;
        tail.prev = head;
    }
    
    void offerFirst(E node) {
        addAfter(node, head);
    }
    
    void offerLast(E node) {
        addBefore(node, tail);
    }
    
    E peekFirst() {
        return head.next;
    }
    
    E pollFirst() {
        E first = peekFirst();
        remove(first);
        return first;
    }
    
    E peekLast() {
        return tail.prev;
    }
    
    E pollLast() {
        E last = peekLast();
        remove(last);
        return last;
    }
    
    void addAfter(E node, E prev) {
        node.prev = prev;
        node.next = prev.next;
        prev.next.prev = node;
        prev.next = node;
    }
    
    void addBefore(E node, E next) {
        node.prev = next.prev;
        node.next = next;
        next.prev.next = node;
        next.prev = node;
    }
    
    void remove(E node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    boolean isEmpty() {
        return head.next == tail;
    }
    
}
