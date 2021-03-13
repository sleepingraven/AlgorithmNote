package io.github.sleepingraven.util.datastructure.linkedlist;

/**
 * 表示可获取 next 节点的链表
 *
 * @author Carry
 * @since 2021/2/21
 */
public interface TraversableLinkedList<T extends TraversableLinkedList<T>> extends LinkedList {
    
    /**
     * 获取 next 节点
     *
     * @return next 节点
     */
    T getNext();
    
}
