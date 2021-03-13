package io.github.sleepingraven.util.common;

/**
 * 用滑动窗口处理数组 int[]，避免重新创建并复制元素
 *
 * @author Carry
 * @since 2021/2/12
 */
public class IntArrayWrapper {
    private final int[] a;
    public int l, r;
    
    public IntArrayWrapper(int[] a) {
        this.a = a;
        this.l = 0;
        this.r = a.length;
    }
    
    public int valueAt(int i) {
        return a[i];
    }
    
    public int valueAtL() {
        return valueAt(l);
    }
    
    public int getLength() {
        return r - l;
    }
    
}
