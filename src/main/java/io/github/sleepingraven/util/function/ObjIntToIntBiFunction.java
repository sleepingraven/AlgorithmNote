package io.github.sleepingraven.util.function;

/**
 * @author Carry
 * @since 2021/2/12
 */
@FunctionalInterface
public interface ObjIntToIntBiFunction<T> {
    
    int applyAsInt(T t, int i);
    
}
