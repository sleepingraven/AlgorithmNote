package io.github.sleepingraven.note.helloworld.tree;

import java.util.Arrays;

/**
 * 二叉堆
 * <p>
 * 最小二叉堆：任何一个父结点的值，都小于或等于它左、右孩子结点的值
 * </p>
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class BinaryHeap {
    
    /**
     * “上浮”调整
     * <p>
     * 时间复杂度：O(log n)
     * </p>
     */
    public static void upAdjust(int[] array) {
        int index = array.length - 1;
        int parentIndex = getParentIndex(index);
        
        // 保存插入的叶子结点值，用于最后的赋值
        int lastNodeData = array[index];
        while (index > 0 && lastNodeData < array[parentIndex]) {
            // 单向赋值，无需交换
            array[index] = array[parentIndex];
            index = parentIndex;
            parentIndex = getParentIndex(parentIndex);
        }
        array[index] = lastNodeData;
    }
    
    /**
     * “下沉”调整
     * <p>
     * 时间复杂度：O(log n)
     * </p>
     *
     * @param index
     *         要“下沉”的结点
     * @param length
     *         堆的有效大小
     */
    public static void downAdjust(int[] array, int index, int length) {
        // 保存父结点的值
        int nonLeafData = array[index];
        int childIndex = getLeftChildIndex(index);
        
        while (childIndex < length) {
            // 如果有右孩子，且右孩子小于左孩子的值，则定位到右孩子
            if (childIndex + 1 < length && array[childIndex + 1] < array[childIndex]) {
                childIndex++;
            }
            // 如果父结点小于或等于每一个孩子的值，则直接跳出
            if (nonLeafData <= array[childIndex]) {
                break;
            }
            // 单向赋值
            array[index] = array[childIndex];
            index = childIndex;
            childIndex = getLeftChildIndex(childIndex);
        }
        array[index] = nonLeafData;
    }
    
    /**
     * 构建堆
     * <p>
     * 时间复杂度：O(n)，而不是O(n*log n)
     * </p>
     */
    public static void buildHeap(int[] array) {
        // 从最后一个非叶子结点开始，依次做“下沉”调整
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            downAdjust(array, i, array.length);
        }
    }
    
    /**
     * 二叉堆存储于数组
     * <p>
     * <blockquote>
     * 数组1
     * <pre>
     *        1
     *    3       2
     *  6   5   7   8
     * 9 t 0
     * </pre>
     * </blockquote>
     * </p>
     * <p>
     * <blockquote>
     * 数组2
     * <pre>
     *        7
     *    1       3
     *  t   5   2   8
     * 9 6
     * </pre>
     * </blockquote>
     * </p>
     */
    public static void main(String[] args) {
        int[] array = new int[] { 1, 3, 2, 6, 5, 7, 8, 9, 10, 0 };
        
        System.out.println("上浮：");
        upAdjust(array);
        System.out.println(Arrays.toString(array));
        
        System.out.println("构建二叉堆：");
        array = new int[] { 7, 1, 3, 10, 5, 2, 8, 9, 6 };
        buildHeap(array);
        System.out.println(Arrays.toString(array));
    }
    
    /**
     * 获得父结点的索引
     */
    static int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }
    
    /**
     * 获得左孩子的索引
     */
    public static int getLeftChildIndex(int parentIndex) {
        return parentIndex * 2 + 1;
    }
    
}
