package io.github.sleepingraven.note.practice;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 缓冲区中的中位数
 * 来源：Leetcode 题解
 *
 * @author Carry
 * @since 2021/2/3
 */
public class MedianFinder {
    /**
     * 大根堆，维护较小的一半元素
     */
    private final PriorityQueue<Integer> small;
    /**
     * 小根堆，维护较大的一半元素
     */
    private final PriorityQueue<Integer> large;
    /**
     * 记录「延迟删除」的元素及其出现次数
     */
    private final Map<Integer, Integer> delayed;
    /**
     * small 和 large 包含的有效元素个数
     */
    private int smallSize, largeSize;
    
    public MedianFinder(int n) {
        this.small = new PriorityQueue<>(n, Comparator.reverseOrder());
        this.large = new PriorityQueue<>(n, Comparator.naturalOrder());
        this.delayed = new HashMap<>((int) (n / .75));
    }
    
    public double getMedian() {
        return (smallSize + largeSize) % 2 == 0 ? getMedianDoubly() : getMedianSingly();
    }
    
    public double getMedianSingly() {
        return small.peek();
    }
    
    public double getMedianDoubly() {
        return ((double) small.peek() + large.peek()) / 2;
    }
    
    public void add(int num) {
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
            ++smallSize;
        } else {
            large.offer(num);
            ++largeSize;
        }
        makeBalance();
    }
    
    public void remove(int num) {
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);
        if (num <= small.peek()) {
            --smallSize;
            if (num == small.peek()) {
                prune(small);
            }
        } else {
            --largeSize;
            if (num == large.peek()) {
                prune(large);
            }
        }
        makeBalance();
    }
    
    /**
     * 调整 small 和 large 中的元素个数
     */
    private void makeBalance() {
        if (smallSize > largeSize + 1) {
            // small 比 large 元素多 2 个
            large.offer(small.poll());
            --smallSize;
            ++largeSize;
            // small 堆顶元素被移除，需要进行 prune
            prune(small);
        } else if (smallSize < largeSize) {
            // large 比 small 元素多 1 个
            small.offer(large.poll());
            ++smallSize;
            --largeSize;
            // large 堆顶元素被移除，需要进行 prune
            prune(large);
        }
    }
    
    /**
     * 不断移除堆顶的过期元素
     */
    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int num = heap.peek();
            if (!delayed.containsKey(num)) {
                break;
            }
            if (delayed.compute(num, (k, v) -> v - 1) == 0) {
                delayed.remove(num);
            }
            heap.poll();
        }
    }
    
}
