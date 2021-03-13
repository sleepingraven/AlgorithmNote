package io.github.sleepingraven.note.helloworld.sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 快速排序
 * <p>
 * 也属于交换排序
 * </p>
 * <p>
 * 分治法——在每一轮挑选一个基准元素，把数列拆解成两个部分。每一轮都遍历一遍，需要O(n)；需要log n到n轮
 * </p>
 * <p>
 * 快速排序时很重要的算法，与傅里叶变换等算法并称为二十世纪十大算法
 * </p>
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class QuickSort implements Sortable {
    /**
     * 递归实现
     */
    private static final Character RECURSIVE = '0';
    /**
     * 非递归实现
     */
    private static final Character NON_RECURSIVE = '1';
    
    /**
     * 单指针
     */
    private static final Character SIMPLE = '0';
    /**
     * 双指针
     */
    private static final Character DOUBLE = '1';
    
    /**
     * 默认使用递归双向
     */
    @Override
    public int[] sort(int[] array) {
        sort(array, RECURSIVE, DOUBLE);
        return array;
    }
    
    /**
     * 指定排序方式
     *
     * @param implementationMode
     *         是否递归实现
     * @param partitionMode
     *         分治模式，单向还是双向
     */
    public void sort(int[] array, final char implementationMode, final char partitionMode) {
        if (RECURSIVE.equals(implementationMode)) {
            quickSort(array, 0, array.length - 1, partitionMode);
        } else {
            quickSort(array, partitionMode);
        }
    }
    
    /**
     * 排序实现，递归方式
     *
     * @param partitionMode
     *         分治模式，单向或双向
     */
    private void quickSort(int[] array, int fromIndex, int toIndex, final char partitionMode) {
        // 结束条件
        if (fromIndex >= toIndex) {
            return;
        }
        // 排序并得到基准元素位置
        int pivotIndex = partitionDispatch(array, fromIndex, toIndex, partitionMode);
        // 根据基准元素，分成两部分并递归排序
        quickSort(array, fromIndex, pivotIndex - 1, partitionMode);
        quickSort(array, pivotIndex + 1, toIndex, partitionMode);
    }
    
    /**
     * 排序实现，非递归方式
     *
     * @param partitionMode
     *         分治模式，单向或双向
     */
    private void quickSort(int[] array, final char partitionMode) {
        // 用集合栈代替递归函数栈
        Stack<Map<String, Integer>> quickSortStack = new Stack<>();
        final String fromIndex = "fromIndex";
        final String toIndex = "toIndex";
        // 整个数列的起止下标，以hash的形式入栈
        Map<String, Integer> rootParam = new HashMap<>();
        rootParam.put(fromIndex, 0);
        rootParam.put(toIndex, array.length - 1);
        quickSortStack.push(rootParam);
        
        while (!quickSortStack.isEmpty()) {
            // 站定元素出栈，得到起止下标
            Map<String, Integer> param = quickSortStack.pop();
            // 得到基准元素位置
            int pivotIndex = partitionDispatch(array, param.get(fromIndex), param.get(toIndex), partitionMode);
            // 根据基准元素分成两部分，每一部分的起止下标入栈
            if (param.get(fromIndex) < pivotIndex - 1) {
                Map<String, Integer> leftParam = new HashMap<>();
                leftParam.put(fromIndex, param.get(fromIndex));
                leftParam.put(toIndex, pivotIndex - 1);
                quickSortStack.push(leftParam);
            }
            if (pivotIndex + 1 < param.get(toIndex)) {
                Map<String, Integer> rightParam = new HashMap<>();
                rightParam.put(fromIndex, pivotIndex + 1);
                rightParam.put(toIndex, param.get(toIndex));
                quickSortStack.push(rightParam);
            }
        }
    }
    
    /**
     * 分治（双边循环法）
     *
     * @return 基准元素的位置
     */
    private int partitionDoubleMode(int[] array, int fromIndex, int toIndex) {
        // 取首个元素（逆序序列可以随机选择）为基准元素
        int pivot = array[fromIndex];
        int left = fromIndex;
        int right = toIndex;
        
        while (left != right) {
            // 控制right指针左移
            while (left < right && array[right] > pivot) {
                right--;
            }
            // 控制left指针右移
            while (left < right && array[left] <= pivot) {
                left++;
            }
            if (left < right) {
                swap(array, left, right);
            }
        }
        
        // 交换pivot和指针重合点
        array[fromIndex] = array[left];
        array[left] = pivot;
        
        return left;
    }
    
    /**
     * 分治（单边循环法）
     *
     * @return 基准元素的位置
     */
    private int partitionSimpleMode(int[] array, int fromIndex, int toIndex) {
        // 取首个元素（逆序序列可以随机选择）为基准元素
        int pivot = array[fromIndex];
        int mark = fromIndex;
        
        for (int i = fromIndex + 1; i <= toIndex; i++) {
            if (array[i] < pivot) {
                swap(array, ++mark, i);
            }
        }
        
        array[fromIndex] = array[mark];
        array[mark] = pivot;
        
        return mark;
    }
    
    public static void main(String[] args) {
        QuickSort sorter = new QuickSort();
        
        int[] array1 = new int[] { 4, 4, 6, 5, 3, 2, 8, 1 };
        sorter.sort(array1, RECURSIVE, DOUBLE);
        System.out.println("双边循环：");
        System.out.println(Arrays.toString(array1));
        
        int[] array2 = new int[] { 4, 4, 6, 5, 3, 2, 8, 1 };
        sorter.sort(array2, RECURSIVE, SIMPLE);
        System.out.println("单边循环：");
        System.out.println(Arrays.toString(array2));
        
        int[] array3 = new int[] { 4, 7, 6, 5, 3, 2, 8, 1 };
        sorter.sort(array3, NON_RECURSIVE, SIMPLE);
        System.out.println("非递归：");
        System.out.println(Arrays.toString(array3));
    }
    
    private int partitionDispatch(int[] array, int fromIndex, int toIndex, final char partitionMode) {
        return DOUBLE.equals(partitionMode) ?
               partitionDoubleMode(array, fromIndex, toIndex) :
               partitionSimpleMode(array, fromIndex, toIndex);
    }
    
}
