package io.github.sleepingraven.note.practice.cache_elimination;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 使用 LinkedHashMap
 *
 * @author Carry
 * @since 2021/1/28
 */
public class LRUCache1 {
    private final LruMap cache;
    private final int capacity;
    
    public LRUCache1(int capacity) {
        this.capacity = capacity;
        this.cache = new LruMap();
    }
    
    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        cache.put(key, value);
    }
    
    class LruMap extends LinkedHashMap<Integer, Integer> {
        public LruMap() {
            super((int) (capacity / .75), .75f, true);
        }
        
        @Override
        protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
        
    }
    
}
