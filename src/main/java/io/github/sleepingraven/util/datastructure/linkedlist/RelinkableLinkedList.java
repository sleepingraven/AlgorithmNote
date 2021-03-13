package io.github.sleepingraven.util.datastructure.linkedlist;

/**
 * 表示可设置 next 节点的链表
 *
 * @author Carry
 * @since 2021/2/21
 */
public interface RelinkableLinkedList<T extends RelinkableLinkedList<T>> extends LinkedList {
    
    /**
     * 设置 next 节点
     *
     * @param next
     *         next 节点
     */
    void setNext(T next);
    
}
