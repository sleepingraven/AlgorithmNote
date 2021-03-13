package io.github.sleepingraven.note.practice.cache_elimination;

import java.util.HashMap;
import java.util.Map;

/**
 * 多级链表
 *
 * @author Carry
 * @since 2021/1/28
 */
public class LFUCache3 {
    private final Map<Integer, EntryNode> cache;
    private final int capacity;
    private final QuickModificationLinkedList<FreqWrap> freqLists;
    
    public LFUCache3(int capacity) {
        cache = new HashMap<>(capacity);
        this.capacity = capacity;
        freqLists = new QuickModificationLinkedList<>(() -> new FreqWrap(0));
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        EntryNode node = freqInc(key);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (!cache.containsKey(key)) {
            if (cache.size() == capacity) {
                FreqWrap lastFreqWrap = freqLists.peekLast();
                EntryNode last = lastFreqWrap.pollLast();
                cache.remove(last.key);
                if (lastFreqWrap.isEmpty()) {
                    freqLists.pollLast();
                }
            }
            EntryNode node = new EntryNode(key, value);
            cache.put(key, node);
            FreqWrap fw;
            if (freqLists.isEmpty() || (fw = freqLists.peekLast()).freq != 1) {
                fw = new FreqWrap(1);
                freqLists.offerLast(fw);
            }
            fw.offerFirst(node);
        } else {
            EntryNode node = freqInc(key);
            node.value = value;
        }
    }
    
    private EntryNode freqInc(int key) {
        EntryNode node = cache.get(key);
        // 移除 node, 链表为空则删除
        FreqWrap freqWrap = node.freqList;
        FreqWrap prevFreqWrap = freqWrap.prev;
        freqWrap.remove(node);
        if (freqWrap.isEmpty()) {
            freqLists.remove(freqWrap);
        }
        // 将 node 加入新 freq 对应的链表
        final int freq = freqWrap.freq + 1;
        if (prevFreqWrap.freq != freq) {
            FreqWrap fw = new FreqWrap(freq);
            freqLists.addAfter(fw, prevFreqWrap);
            prevFreqWrap = fw;
        }
        prevFreqWrap.offerFirst(node);
        return node;
    }
    
    static class FreqWrap extends ListNode<FreqWrap> {
        final QuickModificationLinkedList<EntryNode> freqList;
        final int freq;
        
        public FreqWrap(int freq) {
            this.freqList = new QuickModificationLinkedList<>(() -> new EntryNode(0, 0));
            this.freq = freq;
        }
        
        void offerFirst(EntryNode node) {
            freqList.offerFirst(node);
            node.freqList = this;
        }
        
        EntryNode pollLast() {
            return freqList.pollLast();
        }
        
        void remove(EntryNode node) {
            freqList.remove(node);
        }
        
        boolean isEmpty() {
            return freqList.isEmpty();
        }
        
    }
    
    static class EntryNode extends ListNode<EntryNode> {
        final int key;
        int value;
        /**
         * Node 所在频次的双向链表
         */
        FreqWrap freqList;
        
        public EntryNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
        
    }
    
}
