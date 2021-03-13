package io.github.sleepingraven.note.practice.cache_elimination;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * 平衡树。get 时间复杂度 O(logn)，put 时间复杂度 O(logn)。操作的时间复杂度瓶颈在于 BST 的插入删除均需要 O(logn) 的时间。
 *
 * @author Carry
 * @since 2021/1/28
 */
public class LFUCache1 {
    private final Map<Integer, Node> keyTable;
    private final int capacity;
    private final TreeSet<Node> s;
    private int time;
    
    public LFUCache1(int capacity) {
        this.capacity = capacity;
        this.keyTable = new HashMap<>((int) (capacity / .75));
        this.s = new TreeSet<>();
    }
    
    public int get(int key) {
        // 如果哈希表中没有键 key，返回 -1
        if (!keyTable.containsKey(key)) {
            return -1;
        }
        Node node = refresh(key);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (!keyTable.containsKey(key)) {
            // 如果到达缓存容量上限
            if (keyTable.size() == capacity) {
                // 从哈希表和 BST 中删除最近最少使用的缓存
                Node first = s.first();
                keyTable.remove(first.key);
                s.remove(first);
            }
            // 创建新的缓存节点
            Node node = new Node(key, value, ++time);
            // 将新缓存放入哈希表和 BST 中
            keyTable.put(key, node);
            s.add(node);
        } else {
            refresh(key, value);
        }
    }
    
    private Node refresh(int key) {
        return doRefresh(key, node -> {});
    }
    
    private void refresh(int key, int value) {
        doRefresh(key, node -> node.value = value);
    }
    
    private Node doRefresh(int key, Consumer<Node> valueUpdater) {
        // 得到旧的缓存
        Node node = keyTable.get(key);
        s.remove(node);
        // 缓存更新
        node.freq++;
        node.timestamp = ++time;
        valueUpdater.accept(node);
        // 将新缓存重新放入哈希表和 BST 中
        keyTable.put(key, node);
        s.add(node);
        return node;
    }
    
    static class Node implements Comparable<Node> {
        static final Comparator<Node> COMP =
                Comparator.comparingInt((Node node) -> node.freq).thenComparingInt(node -> node.timestamp);
        final int key;
        int value;
        int freq;
        int timestamp;
        
        Node(int key, int value, int timestamp) {
            this.key = key;
            this.value = value;
            this.freq = 1;
            this.timestamp = timestamp;
        }
        
        @Override
        public int compareTo(Node rhs) {
            return COMP.compare(this, rhs);
        }
        
        @Override
        public boolean equals(Object another) {
            if (this == another) {
                return true;
            }
            if (another instanceof Node) {
                Node rhs = (Node) another;
                return freq == rhs.freq && timestamp == rhs.timestamp;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return freq * 1000000007 + timestamp;
        }
        
    }
    
}
