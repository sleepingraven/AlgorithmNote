package io.github.sleepingraven.util.primitive;

import java.util.function.IntBinaryOperator;

/**
 * 基础类型操作的工具类
 *
 * @author Carry
 * @since 2021/2/10
 */
public class PrimitiveUtil {
    
    public static int intValueNullable(Integer i) {
        return i == null ? 0 : i;
    }
    
    public static long longValueNullable(Long i) {
        return i == null ? 0 : i;
    }
    
    public static short shortValueNullable(Short i) {
        return i == null ? 0 : i;
    }
    
    public static byte byteValueNullable(Byte i) {
        return i == null ? 0 : i;
    }
    
    public static char charValueNullable(Character i) {
        return i == null ? 0 : i;
    }
    
    public static float floatValueNullable(Float i) {
        return i == null ? 0 : i;
    }
    
    public static double doubleValueNullable(Double i) {
        return i == null ? 0 : i;
    }
    
    public static boolean booleanValueNullable(Boolean i) {
        return i != null && i;
    }
    
    public static int increase(int i) {
        return i + 1;
    }
    
    public static int decrease(int i) {
        return i - 1;
    }
    
    /**
     * @see IntBinaryOperator
     */
    public static int sum(int a, int b) {
        return Integer.sum(a, b);
    }
    
    /**
     * @see IntBinaryOperator
     */
    public static int diff(int a, int b) {
        return a - b;
    }
    
}
