package io.github.sleepingraven.util.datastructure.common.utils.visualization;

import io.github.sleepingraven.util.datastructure.linkedlist.TraversableLinkedList;

import java.util.function.Function;

/**
 * 格式化 {@link TraversableLinkedList}
 *
 * @author Carry
 * @since 2021/2/25
 */
public class TraversableLinkedListFormatter<T extends TraversableLinkedList<T>> extends LinkedListFormatter<T> {
    
    public TraversableLinkedListFormatter(Function<? super T, ?> toDisplay) {
        super(toDisplay, TraversableLinkedList::getNext);
    }
    
}
