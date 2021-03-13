package io.github.sleepingraven.util.datastructure.common.utils.visualization;

import lombok.NonNull;
import io.github.sleepingraven.util.datastructure.common.Formatter;
import io.github.sleepingraven.util.datastructure.linkedlist.LinkedList;

import java.util.function.Function;

/**
 * 格式化 {@link LinkedList}
 *
 * @author Carry
 * @since 2020/6/21
 */
public class LinkedListFormatter<T extends LinkedList> implements Formatter<T> {
    private final Function<? super T, String> node2String;
    private final Function<? super T, ? extends T> nextGetter;
    
    public LinkedListFormatter(@NonNull Function<? super T, ?> toDisplay,
            @NonNull Function<? super T, ? extends T> nextGetter) {
        this.node2String = toDisplay.andThen(String::valueOf);
        this.nextGetter = nextGetter;
    }
    
    @Override
    public String format(T head) {
        if (head == null) {
            return "Empty list.";
        }
        return node2String.apply(head) + "->" + nextGetter.apply(head);
    }
    
}
