package io.github.sleepingraven.note.practice.consistent_hash;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 一致性 Hash 操作
 *
 * @author Carry
 * @since 2021/1/31
 */
public class ConsistentHash<T> {
    /**
     * Hash 函数接口
     */
    private final IHashService iHashService;
    /**
     * 每个机器节点关联的虚拟节点数量
     */
    private final int numberOfReplicas;
    /**
     * 环形虚拟节点
     */
    private final TreeMap<Long, T> circle;
    
    public ConsistentHash(IHashService iHashService, int numberOfReplicas, Collection<T> nodes) {
        this.iHashService = iHashService;
        this.numberOfReplicas = numberOfReplicas;
        this.circle = new TreeMap<>();
        nodes.forEach(this::add);
    }
    
    /**
     * 增加真实机器节点
     *
     * @param node
     *         T
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(iHashService.hash(node.toString() + i), node);
        }
    }
    
    /**
     * 删除真实机器节点
     *
     * @param node
     *         T
     */
    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(iHashService.hash(node.toString() + i));
        }
    }
    
    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        
        long hash = iHashService.hash(key);
        
        // 沿环的顺时针找到一个虚拟节点
        Entry<Long, T> ceiling = circle.ceilingEntry(hash);
        if (ceiling == null) {
            ceiling = circle.firstEntry();
        }
        return ceiling.getValue();
    }
    
}
