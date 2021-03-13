package io.github.sleepingraven.util.datastructure.common;

import java.util.Objects;

/**
 * 接收 int、T，返回 boolean 的函数式接口
 *
 * @author Carry
 * @since 2021/2/21
 */
@FunctionalInterface
public interface IntObjPredicate<T> {
    
    /**
     * 测试 int、T
     *
     * @param i
     *         int 参数
     * @param t
     *         T 参数
     *
     * @return 测试结果
     */
    boolean test(int i, T t);
    
    /**
     * 如果 this 返回 true，返回 other 的测试结果
     *
     * @param other
     *         测试结果作为 boolean 运算的第二个参数
     *
     * @return IntObjPredicate 实例。仅当 this 和 other 都为 true 时，返回 true
     */
    default IntObjPredicate<T> and(IntObjPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return (i, t) -> test(i, t) && other.test(i, t);
    }
    
    /**
     * 对 this 的返回值取反
     *
     * @return IntObjPredicate 实例。测试结果等于 this 的结果取反
     */
    default IntObjPredicate<T> negate() {
        return (i, t) -> !test(i, t);
    }
    
    /**
     * 如果 this 返回 false，返回 other 的测试结果
     *
     * @param other
     *         测试结果作为 boolean 运算的第二个参数
     *
     * @return IntObjPredicate 实例。当 this 或 other 为 true 时，返回 true
     */
    default IntObjPredicate<T> or(IntObjPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return (i, t) -> test(i, t) || other.test(i, t);
    }
    
}
