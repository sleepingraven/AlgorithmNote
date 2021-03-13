package io.github.sleepingraven.util.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 重复使用 T
 *
 * @author Carry
 * @since 2021/2/4
 */
@AllArgsConstructor
public class RollingPair<T> {
    @Getter
    T primary;
    @Getter
    T secondary;
    final Consumer<? super T> cleaner;
    
    public RollingPair(Function<Boolean, ? extends T> supplier, Consumer<? super T> cleaner) {
        this(supplier.apply(true), supplier.apply(false), cleaner);
    }
    
    public RollingPair(Function<Boolean, ? extends T> supplier) {
        this(supplier.apply(true), supplier.apply(false), t -> {});
    }
    
    /**
     * 轮换
     */
    public void rotate() {
        clean(primary);
        T temp = primary;
        primary = secondary;
        secondary = temp;
    }
    
    public void clean(T t) {
        cleaner.accept(t);
    }
    
}
