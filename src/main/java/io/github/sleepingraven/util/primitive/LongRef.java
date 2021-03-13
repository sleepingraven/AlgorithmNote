package io.github.sleepingraven.util.primitive;

/**
 * 模拟 long 的引用
 *
 * @author Carry
 * @since 2021/2/12
 */
public class LongRef {
    long v;
    
    public LongRef(long v) {
        this.v = v;
    }
    
    public long get() {
        return v;
    }
    
    public void set(long v) {
        this.v = v;
    }
    
}
