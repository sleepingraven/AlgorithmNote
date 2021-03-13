package io.github.sleepingraven.util.datastructure.linkedlist;

/**
 * 表示可获取 next 节点和值的链表
 *
 * @author Carry
 * @since 2021/2/21
 */
public interface VisibleLinkedList<V> extends LinkedList {
    
    /**
     * 获取节点值
     *
     * @return 节点值
     */
    V getValue();
    
    /**
     * 获取 next 节点
     *
     * @return next 节点
     */
    VisibleLinkedList<V> getNext();
    
}
