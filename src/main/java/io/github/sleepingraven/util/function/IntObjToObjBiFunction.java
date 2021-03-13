package io.github.sleepingraven.util.function;

/**
 * @author Carry
 * @since 2021/2/16
 */
@FunctionalInterface
public interface IntObjToObjBiFunction<T, R> {
    
    R apply(int i, T t);
    
}
