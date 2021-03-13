package io.github.sleepingraven.note.helloworld.tree;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 优先队列
 * <p>
 * 最大优先队列：最大的元素优先出队
 * </p>
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class PriorityQueue {
    /**
     * 二叉堆
     */
    private int[] array;
    /**
     * 现有长度
     */
    private int size;
    
    /**
     * 入队
     * <p>时间复杂度：O(log n)</p>
     *
     * @param value
     *         入队元素
     */
    public void enQueue(int value) {
        if (size >= array.length) {
            resize();
        }
        
        array[size++] = value;
        upAdjust();
    }
    
    /**
     * 出队
     * <p>
     * 时间复杂度：O(log n)
     * </p>
     *
     * @return 出队元素
     */
    public int deQueue() {
        if (size < 0) {
            throw new NoSuchElementException("The Queue is empty!");
        }
        
        // 获取堆顶元素
        int front = array[0];
        array[0] = array[--size];
        downAdjust();
        return front;
    }
    
    /**
     * “上浮”调整
     */
    private void upAdjust() {
        int index = size - 1;
        int parentIndex = BinaryHeap.getParentIndex(index);
        
        // 保存插入的叶子结点值，用于最后的赋值
        int lastNodeData = array[index];
        while (index > 0 && lastNodeData > array[parentIndex]) {
            // 单向赋值，无需交换
            array[index] = array[parentIndex];
            index = parentIndex;
            parentIndex = BinaryHeap.getParentIndex(parentIndex);
        }
        array[index] = lastNodeData;
    }
    
    /**
     * “下沉”调整
     */
    private void downAdjust() {
        // 保存父结点的值
        int parentIndex = 0;
        int nonLeafData = array[parentIndex];
        int childIndex = 1;
        
        while (childIndex < size) {
            // 如果有右孩子，且右孩子大于左孩子的值，则定位到右孩子
            if (childIndex + 1 < size && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 如果父结点大于或等于每一个孩子的值，则直接跳出
            if (nonLeafData >= array[childIndex]) {
                break;
            }
            // 单向赋值
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = BinaryHeap.getLeftChildIndex(childIndex);
        }
        array[parentIndex] = nonLeafData;
    }
    
    public static void main(String[] args) {
        try {
            PriorityQueue priorityQueue = new PriorityQueue();
            priorityQueue.enQueue(3);
            priorityQueue.enQueue(5);
            priorityQueue.enQueue(10);
            priorityQueue.enQueue(2);
            priorityQueue.enQueue(7);
            
            System.out.println("出队元素：" + priorityQueue.deQueue());
            System.out.println("出队元素：" + priorityQueue.deQueue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public PriorityQueue() {
        //初始容量为32
        array = new int[32];
    }
    
    /**
     * 队列扩容
     */
    private void resize() {
        // 容量翻倍
        int newSize = size * 2;
        array = Arrays.copyOf(array, newSize);
    }
    
}
