package io.github.sleepingraven.util.datastructure.common;

/**
 * 表示可将 T 格式化为 String
 *
 * @author Carry
 * @since 2020/2/26
 */
@FunctionalInterface
public interface Formatter<T> {
    
    /**
     * 将 T 格式化
     *
     * @param t
     *         给定 T
     *
     * @return 格式化后的串
     */
    String format(T t);
    
}
