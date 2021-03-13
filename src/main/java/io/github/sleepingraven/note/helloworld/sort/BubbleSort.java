package io.github.sleepingraven.note.helloworld.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class BubbleSort implements Sortable {
    
    @Override
    public int[] sort(int[] array) {
        // 有序标记，已有序就提前结束
        boolean isSorted;
        for (int i = 0; i < array.length - 1; i++) {
            isSorted = true;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
        return array;
    }
    
    @Override
    public int[] optimizedSort(int[] array) {
        boolean isSorted;
        // 无序数列的边界，每次比较只需到此为止
        int sortBorder = array.length - 1;
        for (int i = 0; i < array.length - 1; i++) {
            isSorted = true;
            int j;
            for (j = 0; j < sortBorder; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    isSorted = false;
                }
            }
            sortBorder = j;
            if (isSorted) {
                break;
            }
        }
        return array;
    }
    
    /**
     * 鸡尾酒排序
     * <p>
     * 双向轮流交换
     * </p>
     * <p>
     * 适合大部分元素已经有序时
     * </p>
     * <p>
     * 未做有序区优化
     * </p>
     */
    @Override
    public int[] extraSort(int[] array) {
        boolean isSorted;
        for (int i = 0; i < array.length >> 1; i++) {
            // 奇数轮
            isSorted = true;
            for (int j = i; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            // 偶数轮
            isSorted = true;
            for (int j = array.length - 1 - i; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
        
        return array;
    }
    
    public static void main(String[] args) {
        Sortable sorter = new BubbleSort();
        
        int[] array1 = new int[] { 5, 8, 6, 3, 9, 2, 1, 7 };
        sorter.sort(array1);
        System.out.println("冒泡排序：");
        System.out.println(Arrays.toString(array1));
        
        int[] array2 = new int[] { 3, 4, 2, 1, 5, 6, 7, 8 };
        sorter.optimizedSort(array2);
        System.out.println("冒泡排序优化：");
        System.out.println(Arrays.toString(array2));
        
        int[] array3 = new int[] { 2, 3, 4, 5, 6, 7, 8, 1 };
        sorter.extraSort(array3);
        System.out.println("鸡尾酒排序：");
        System.out.println(Arrays.toString(array3));
    }
    
}
