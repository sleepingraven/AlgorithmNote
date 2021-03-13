package io.github.sleepingraven.note.practice.cache_elimination;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carry
 * @since 2021/1/28
 */
public class LRUCache2 {
    private final Map<Integer, Node> cache;
    private final int capacity;
    private final QuickModificationLinkedList<Node> list;
    
    public LRUCache2(int capacity) {
        this.cache = new HashMap<>((int) (capacity / .75));
        this.capacity = capacity;
        list = new QuickModificationLinkedList<>(() -> new Node(0, 0));
    }
    
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        refresh(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (!cache.containsKey(key)) {
            Node node = new Node(key, value);
            // 添加进哈希表及双向链表的头部
            cache.put(key, node);
            list.offerFirst(node);
            // 如果超出容量，删除双向链表的尾部节点及哈希表中对应的项
            if (cache.size() > capacity) {
                Node last = list.pollLast();
                cache.remove(last.key);
            }
        } else {
            Node node = cache.get(key);
            node.value = value;
            refresh(node);
        }
    }
    
    private void refresh(Node node) {
        list.remove(node);
        list.offerFirst(node);
    }
    
    static class Node extends ListNode<Node> {
        final int key;
        int value;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
        
    }
    
}
