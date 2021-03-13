package io.github.sleepingraven.util.datastructure.common.utils;

import lombok.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 提供对函数式实例的转换
 *
 * @author Carry
 * @since 2021/2/25
 */
public class FunctionalUtil {
    
    /**
     * create T and wrap V to it
     */
    public static <T, V> Function<V, T> wrapping(@NonNull Supplier<? extends T> factory,
            @NonNull BiConsumer<? super T, ? super V> assignment) {
        return v -> {
            T t = factory.get();
            assignment.accept(t, v);
            return t;
        };
    }
    
}
