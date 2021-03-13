package io.github.sleepingraven.util.datastructure.common.utils.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import io.github.sleepingraven.util.datastructure.common.Generative;
import io.github.sleepingraven.util.datastructure.common.utils.FunctionalUtil;
import io.github.sleepingraven.util.datastructure.linkedlist.LinkedList;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 根据 V 数组生成 {@link LinkedList}
 *
 * @author Carry
 * @since 2020/6/21
 */
@RequiredArgsConstructor
public class LinkedListGenerator<T extends LinkedList, V> implements Generative<T, V> {
    @NonNull
    private final Function<? super V, ? extends T> instantiator;
    @NonNull
    private final BiConsumer<? super T, ? super T> nextSetter;
    
    public LinkedListGenerator(Supplier<? extends T> factory, BiConsumer<? super T, ? super V> valueSetter,
            BiConsumer<? super T, ? super T> nextSetter) {
        this(FunctionalUtil.wrapping(factory, valueSetter), nextSetter);
    }
    
    @Override
    public T generate(V[] vs) {
        if (vs == null || vs.length == 0) {
            return null;
        }
        
        T head = instantiator.apply(vs[0]);
        T prev = head;
        for (int i = 1; i < vs.length; i++) {
            T curr = instantiator.apply(vs[i]);
            nextSetter.accept(prev, curr);
            prev = curr;
        }
        
        return head;
    }
    
}
