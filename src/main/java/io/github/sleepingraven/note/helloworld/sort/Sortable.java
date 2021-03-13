package io.github.sleepingraven.note.helloworld.sort;

import io.github.sleepingraven.util.ac.ArrayUtil;

/**
 * <pre>
 * +----------------+------------------+------------------+------------------+-----+
 * |                |   平均时间复杂度   |   最坏时间复杂度    |    空间复杂度     | 稳定 |
 * +----------------+------------------+------------------+------------------+-----+
 * | bubble sort    |      O(n^2)      |        -         |       O(1)       |  √  | # 冒泡排序
 * | selection sort |      O(n^2)      |                  |                  |     | # 选择排序
 * | insertion sort |      O(n^2)      |                  |                  |     | # 插入排序
 * | shell sort     | O(n^2 - n*log n) |                  |                  |     | # 希尔排序
 * | quick sort     |    O(n*log n)    |      O(n^2)      |     O(log n)     |  ×  | # 快速排序
 * | merge sort     |    O(n*log n)    |                  |                  |  √  | # 归并排序
 * | heap sort      |    O(n*log n)    |        -         |       O(1)       |  ×  | # 堆排序
 * | counting sort  |      O(n+m)      |        -         |       O(m)       |  √  | # 计数排序
 * | bucket sort    |       O(n)       |    O(n*log n)    |       O(n)       |  √  | # 桶排序
 * | radix sort     |       O(n)       |                  |                  |     | # 基数排序
 * +----------------+------------------+------------------+------------------+-----+
 * </pre>
 *
 * @author 10132
 */
public interface Sortable {
    
    /**
     * 数组排序
     *
     * @param array
     *         待排数组
     *
     * @return 排序后的数组，可能是另一个数组
     */
    int[] sort(int[] array);
    
    /**
     * 优化数组排序方法
     *
     * @param array
     *         待排数组
     *
     * @return 排序后的数组，可能是另一个数组
     */
    default int[] optimizedSort(int[] array) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 额外数组排序方法
     *
     * @param array
     *         待排数组
     *
     * @return 排序后的数组，可能是另一个数组
     */
    default int[] extraSort(int[] array) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 额外数组排序方法
     *
     * @param array
     *         待排数组
     *
     * @return 排序后的数组，可能是另一个数组
     */
    default double[] extraSort(double[] array) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 交换元素
     *
     * @param array
     *         数组
     * @param firstIndex
     *         下标1
     * @param secondIndex
     *         下标2
     */
    default void swap(int[] array, int firstIndex, int secondIndex) {
        ArrayUtil.swap(array, firstIndex, secondIndex);
    }
    
}
