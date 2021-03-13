package io.github.sleepingraven.note.helloworld.sort;

import io.github.sleepingraven.note.helloworld.tree.BinaryHeap;

import java.util.Arrays;

/**
 * 堆排序
 * <p>
 * 堆排序和快速排序的平均时间复杂度都是O(n*log n)，都是不稳定排序<br />
 * 快速排序最坏时间复杂度为O(n2)，堆排序稳定在O(n*log n)
 * </p>
 * <p>
 * 快排平均空间复杂度为O(log n)，堆排序为O(1)
 * </p>
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class HeapSort implements Sortable {
    
    @Override
    public int[] sort(int[] array) {
        // 1、构建最大堆：O(n)
        for (int i = array.length >> 1; i >= 0; i--) {
            downAdjust(array, i, array.length - 1);
        }
        System.out.println("最大堆：");
        System.out.println(Arrays.toString(array));
        
        // 2、循环删除堆顶元素，移到集合尾部，调整堆产生新的堆顶：O(n*log n)
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, i, 0);
            downAdjust(array, 0, i);
        }
        return array;
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
    private static void downAdjust(int[] array, int index, int length) {
        // 保存父结点的值
        int nonLeafData = array[index];
        int childIndex = BinaryHeap.getLeftChildIndex(index);
        
        while (childIndex < length) {
            // 如果有右孩子，且右孩子大于左孩子的值，则定位到右孩子
            if (childIndex + 1 < length && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 如果父结点大于或等于每一个孩子的值，则直接跳出
            if (nonLeafData >= array[childIndex]) {
                break;
            }
            // 单向赋值
            array[index] = array[childIndex];
            index = childIndex;
            childIndex = BinaryHeap.getLeftChildIndex(childIndex);
        }
        array[index] = nonLeafData;
    }
    
    public static void main(String[] args) {
        Sortable sorter = new HeapSort();
        
        int[] array1 = new int[] { 4, 4, 6, 5, 3, 2, 8, 1 };
        sorter.sort(array1);
        System.out.println("堆排序：");
        System.out.println(Arrays.toString(array1));
    }
    
}
