package io.github.sleepingraven.util.advanced;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 并查集 todo 带权并查集
 *
 * @author Carry
 * @since 2020/7/16
 */
public class UnionFindSet {
    protected final int[] pre;
    private final int[] rank;
    private final int length;
    /**
     * 连通分量的个数
     */
    @Getter
    private int connectedNum;
    /**
     * 子树大小，仅对根结点有效（在 find 中就不必维护了）
     */
    private final int[] size;
    
    /**
     * @param length
     *         非负
     */
    public UnionFindSet(int length) {
        this(length, true);
    }
    
    /**
     * @param length
     *         非负
     * @param init
     *         初始化表示每个结点在不同的 set 里；<p>
     *         否则所有结点成为 pre[0] 的子结点
     */
    public UnionFindSet(int length, boolean init) {
        this.length = length;
        rank = new int[length];
        size = new int[length];
        if (init) {
            pre = IntStream.range(0, length).toArray();
            connectedNum = length;
            Arrays.fill(size, 1);
        } else {
            pre = new int[length];
            if (length > 0) {
                rank[0] = 1;
                connectedNum = 1;
                size[0] = length;
            }
        }
    }
    
    /**
     * @param set
     *         非空
     */
    public UnionFindSet(int[] set) {
        this(set, true);
    }
    
    /**
     * @param set
     *         非空
     * @param init
     *         初始化会将每个结点放在不同的 set 里；<p>
     */
    public UnionFindSet(int[] set, boolean init) {
        this.length = set.length;
        pre = set;
        rank = new int[length];
        size = new int[length];
        if (init) {
            for (int i = 0; i < length; i++) {
                pre[i] = i;
            }
            connectedNum = length;
            Arrays.fill(size, 1);
        } else {
            for (int i = 0; i < length; i++) {
                // do path compression to reset rank
                int root = find(i);
                if (root == i) {
                    connectedNum++;
                }
                size[root]++;
            }
        }
    }
    
    /**
     * 重置并查集
     */
    public void reset() {
        for (int i = 0; i < length; i++) {
            pre[i] = i;
        }
        Arrays.fill(rank, 0);
        connectedNum = length;
        Arrays.fill(size, 1);
    }
    
    /**
     * 查找 k 的根结点
     */
    public int find(int k) {
        if (pre[k] == k) {
            return k;
        }
        // 路径压缩
        return pre[k] = find(pre[k]);
    }
    
    /**
     * {@link UnionFindSet#find(int)} 的另一种写法
     */
    private int get(int x) {
        if (x != pre[x]) {
            // 路径压缩
            pre[x] = get(pre[x]);
        }
        return pre[x];
    }
    
    /**
     * @return 1 if merging with x as the root;<p>
     * -1 if merging with y as the root;<p>
     * 0 if they have been merged
     */
    public int union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) {
            return 0;
        }
        
        connectedNum--;
        //noinspection AlibabaSwitchStatement
        switch (Integer.compare(rank[x], rank[y])) {
            case 0:
                // 按秩合并
                rank[y]++;
            case -1:
                // 以 rank 较高的为父结点
                updateRoot(x, y);
                return -1;
            default:
                updateRoot(y, x);
                return 1;
        }
    }
    
    /**
     * 将 subt 和 root 合并，以 root 为父结点
     */
    public void hang(int subt, int root) {
        subt = find(subt);
        root = find(root);
        if (subt == root) {
            return;
        }
        
        connectedNum--;
        rank[root] = Math.max(rank[root], rank[subt] + 1);
        updateRoot(subt, root);
    }
    
    protected void updateRoot(int x, int r) {
        pre[x] = r;
        size[r] += size[x];
    }
    
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
    
    public boolean isRoot(int x) {
        return find(x) == x;
    }
    
    public int subSize(int x) {
        return size[find(x)];
    }
    
}
