package io.github.sleepingraven.util.advanced;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 元素类型 为 E 的并查集
 *
 * @author Carry
 * @since 2021/1/15
 */
public class UnionFindGeneric<E> {
    private final Map<E, E> parent;
    private final int length;
    /**
     * 连通分量的个数
     */
    private int connectedNum;
    /**
     * 子树大小，仅对根节点有效
     */
    private final Map<E, Integer> sizeTable;
    
    public UnionFindGeneric(int length) {
        this.length = length;
        parent = new HashMap<>((int) (length / .75));
        sizeTable = new HashMap<>((int) (length / .75));
    }
    
    public UnionFindGeneric(E[] elems) {
        this(elems.length);
        Arrays.stream(elems).forEach(this::find);
    }
    
    public UnionFindGeneric(Collection<E> elems) {
        this(elems.size());
        elems.forEach(this::find);
    }
    
    public E find(E k) {
        E p;
        if (!parent.containsKey(k)) {
            p = k;
            connectedNum++;
            sizeTable.put(p, 1);
        } else {
            p = parent.get(k);
            if (!p.equals(k)) {
                p = find(parent.get(k));
            }
        }
        parent.put(k, p);
        return p;
    }
    
    public void union(E x, E y) {
        hang(x, y);
    }
    
    public void hang(E subt, E root) {
        subt = find(subt);
        root = find(root);
        if (subt.equals(root)) {
            return;
        }
        
        parent.put(subt, root);
        connectedNum--;
        int subSize = sizeTable.get(subt);
        sizeTable.compute(root, (k, v) -> v + subSize);
    }
    
    public boolean connected(E x, E y) {
        return find(x).equals(find(y));
    }
    
    public int getConnectedNum() {
        return connectedNum;
    }
    
    public int subSize(E x) {
        return sizeTable.get(find(x));
    }
    
}
