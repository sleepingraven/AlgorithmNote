package io.github.sleepingraven.util.datastructure.common;

/**
 * 表示可以根据给定 V 数组生成 {@link Generable}
 *
 * @author Carry
 * @since 2020/6/19
 */
@FunctionalInterface
public interface Generative<T extends Generable, V> {
    
    /**
     * 根据 V 数组生成 T
     *
     * @param vs
     *         给定源数组
     *
     * @return 生成的 T
     */
    T generate(V[] vs);
    
}
