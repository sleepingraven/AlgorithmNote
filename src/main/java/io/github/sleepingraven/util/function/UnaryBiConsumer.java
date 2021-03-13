package io.github.sleepingraven.util.function;

import java.util.function.BiConsumer;

/**
 * @author Carry
 * @since 2021/2/21
 */
@FunctionalInterface
public interface UnaryBiConsumer<T> extends BiConsumer<T, T> {
}
