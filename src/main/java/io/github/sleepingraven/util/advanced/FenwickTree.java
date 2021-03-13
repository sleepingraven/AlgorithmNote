package io.github.sleepingraven.util.advanced;

/**
 * 树状数组
 *
 * @author Carry
 * @since 2020/7/11
 */
public class FenwickTree {
    private final int[] c;
    
    /**
     * 创建长度为 n + 1 的树状数组，范围是：[1, n]
     */
    public FenwickTree(int n) {
        c = new int[n + 1];
    }
    
    /**
     * 查询 [l, r] 的区间和
     */
    public int query(int l, int r) {
        return query(r) - query(l - 1);
    }
    
    /**
     * 查询 [1, x] 的区间和
     */
    public int query(int x) {
        int s = 0;
        while (x > 0) {
            s += c[x];
            x -= Integer.lowestOneBit(x);
        }
        return s;
    }
    
    /**
     * 更新所有覆盖 x 的元素
     */
    public void update(int x, int dt) {
        while (x < c.length) {
            c[x] += dt;
            x += Integer.lowestOneBit(x);
        }
    }
    
}
