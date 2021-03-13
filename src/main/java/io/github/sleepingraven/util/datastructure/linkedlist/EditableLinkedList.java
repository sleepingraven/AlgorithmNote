package io.github.sleepingraven.util.datastructure.linkedlist;

/**
 * 表示可设置 next 节点和值的链表
 *
 * @author Carry
 * @since 2021/2/25
 */
public interface EditableLinkedList<T extends EditableLinkedList<T, V>, V> extends RelinkableLinkedList<T> {
    
    /**
     * 设置节点值
     *
     * @param value
     *         节点值
     */
    void setValue(V value);
    
}
