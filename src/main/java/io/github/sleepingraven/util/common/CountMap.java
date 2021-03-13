package io.github.sleepingraven.util.common;

import io.github.sleepingraven.util.primitive.PrimitiveUtil;

import java.util.HashMap;
import java.util.function.IntUnaryOperator;

/**
 * 计数器
 *
 * @author Carry
 * @since 2021/2/9
 */
public class CountMap<T> extends HashMap<T, Integer> {
    
    public CountMap(int totalCapacity) {
        super((int) (totalCapacity / .75));
    }
    
    public int getCount(T key) {
        return getOrDefault(key, 0);
    }
    
    public Integer computeCount(T key, IntUnaryOperator remapping) {
        return compute(key, (k, v) -> remapping.applyAsInt(PrimitiveUtil.intValueNullable(v)));
    }
    
    public void increaseCount(T t) {
        computeCount(t, PrimitiveUtil::increase);
    }
    
    public void decreaseCount(T t) {
        computeCount(t, PrimitiveUtil::decrease);
    }
    
}
