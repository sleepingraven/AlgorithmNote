package io.github.sleepingraven.util.advanced;

import io.github.sleepingraven.util.primitive.LongRef;

/**
 * 逆元
 * 同余方程 ax ≡ 1 (mod b)，x = inv(a)
 *
 * @author Carry
 * @since 2020/11/13
 */
public class InverseElement {
    
    public static long inv(long a, long p) {
        LongRef x = new LongRef(0);
        LongRef y = new LongRef(0);
        exGcd(a, p, x, y);
        return (x.get() % p + p) % p;
    }
    
    /**
     * 扩展欧几里得法
     */
    public static long exGcd(long a, long b, LongRef x, LongRef y) {
        if (b == 0) {
            x.set(1);
            y.set(0);
            return a;
        }
        
        long d = exGcd(b, a % b, y, x);
        y.set(y.get() - a / b * x.get());
        return d;
    }
    
}
