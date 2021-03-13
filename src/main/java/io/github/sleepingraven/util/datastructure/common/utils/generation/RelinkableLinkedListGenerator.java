package io.github.sleepingraven.util.datastructure.common.utils.generation;

import io.github.sleepingraven.util.datastructure.linkedlist.RelinkableLinkedList;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 根据 V 数组生成 {@link RelinkableLinkedList}
 *
 * @author Carry
 * @since 2020/6/18
 */
public class RelinkableLinkedListGenerator<T extends RelinkableLinkedList<T>, V> extends LinkedListGenerator<T, V> {
    
    public RelinkableLinkedListGenerator(Function<? super V, ? extends T> instantiator) {
        super(instantiator, RelinkableLinkedList::setNext);
    }
    
    public RelinkableLinkedListGenerator(Supplier<? extends T> factory, BiConsumer<? super T, ? super V> valueSetter) {
        super(factory, valueSetter, RelinkableLinkedList::setNext);
    }
    
}
