package io.github.sleepingraven.util.datastructure.common.utils.visualization;

import io.github.sleepingraven.util.datastructure.common.Formatter;
import io.github.sleepingraven.util.datastructure.linkedlist.VisibleLinkedList;

/**
 * 格式化 {@link VisibleLinkedList}
 *
 * @author Carry
 * @since 2020/6/1
 */
public class VisibleLinkedListFormatter extends LinkedListFormatter<VisibleLinkedList<?>> {
    private static final Formatter<VisibleLinkedList<?>> FORMATTER = new VisibleLinkedListFormatter();
    
    private VisibleLinkedListFormatter() {
        super(VisibleLinkedList::getValue, VisibleLinkedList::getNext);
    }
    
    public static String visibleToString(VisibleLinkedList<?> head) {
        return FORMATTER.format(head);
    }
    
}
