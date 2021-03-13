package io.github.sleepingraven.util.datastructure.linkedlist;

/**
 * 继承自 {@link AbstractPrintableLinkedList} 和 {@link EditableLinkedList}，子类需要提供 Getter/Setter
 *
 * @author Carry
 * @since 2021/2/21
 */
public abstract class BaseLinkedList<T extends BaseLinkedList<T, V>, V> extends AbstractPrintableLinkedList<V>
        implements EditableLinkedList<T, V> {
}
