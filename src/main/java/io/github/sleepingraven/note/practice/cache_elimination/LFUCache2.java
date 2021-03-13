package io.github.sleepingraven.note.practice.cache_elimination;

import java.util.HashMap;
import java.util.Map;

/**
 * LinkedHashSet/双链表
 *
 * @author Carry
 * @since 2021/1/28
 */
public class LFUCache2 {
    private final Map<Integer, Node> keyTable;
    private final Map<Integer, QuickModificationLinkedList<Node>> freqTable;
    private final int capacity;
    private int minFreq;
    
    public LFUCache2(int capacity) {
        this.keyTable = new HashMap<>((int) (capacity / .75));
        this.freqTable = new HashMap<>((int) (capacity / .75));
        this.capacity = capacity;
        this.minFreq = 0;
    }
    
    public int get(int key) {
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
            // 缓存已满，需要进行删除操作
            if (keyTable.size() == capacity) {
                QuickModificationLinkedList<Node> list = freqTable.get(minFreq);
                Node last = list.pollLast();
                keyTable.remove(last.key);
                if (list.isEmpty()) {
                    freqTable.remove(last.freq);
                }
            }
            Node node = new Node(key, value);
            freqTable.computeIfAbsent(node.freq, k -> new QuickModificationLinkedList<>(() -> new Node(0, 0)))
                     .offerFirst(node);
            keyTable.put(key, node);
            minFreq = 1;
        } else {
            Node node = refresh(key);
            node.value = value;
        }
    }
    
    private Node refresh(int key) {
        Node node = keyTable.get(key);
        QuickModificationLinkedList<Node> list = freqTable.get(node.freq);
        list.remove(node);
        if (list.isEmpty()) {
            freqTable.remove(node.freq);
            if (minFreq == node.freq) {
                minFreq++;
            }
        }
        node.freq++;
        freqTable.computeIfAbsent(node.freq, k -> new QuickModificationLinkedList<>(() -> new Node(0, 0)))
                 .offerFirst(node);
        return node;
    }
    
    static class Node extends ListNode<Node> {
        final int key;
        int value;
        int freq;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
        
    }
    
}
