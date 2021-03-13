package io.github.sleepingraven.note.helloworld.sort;

import java.util.*;

/**
 * 计数排序
 * <p>
 * 不基于元素比较，利用数组下标确定位置
 * </p>
 * <p>
 * 如果原始数列规模是n，差值范围是m，时间复杂度是O(n+m)，不考虑结果数组空间复杂度是O(m)
 * </p>
 * <p>
 * 有局限性：<br />
 * 差值过大时，不仅严重浪费空间，时间复杂度也随之升高；
 * 数列元素不是整数时，也无法创建统计数组
 * </p>
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class CountingSort implements Sortable {
    
    /**
     * 适用于一定范围内的整数排序
     */
    @Override
    public int[] sort(int[] array) {
        // 1、得到数列最大值max
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        
        // 2、根据max确定统计数组的长度
        int[] countArray = new int[max + 1];
        
        // 3、遍历数列，填充统计数组
        for (int value : array) {
            countArray[value]++;
        }
        
        //4、遍历统计数组，得到结果
        int index = 0;
        int[] sortedArray = new int[array.length];
        for (int i = 0; i < countArray.length; i++) {
            for (int j = 0; j < countArray[i]; j++) {
                sortedArray[index++] = i;
            }
        }
        
        return sortedArray;
    }
    
    /**
     * 只以max来决定统计数组的长度，并不严谨。以max - min + 1作为长度，min作为偏移量
     * <p>
     * 为了区分相同的值，将统计数组变形：从第2个元素开始，每一个元素都加上前面元素之和：
     * <blockquote>
     * <pre>
     * i: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
     * v: 1, 0, 0, 0, 1, 2, 0, 0, 0, 1 -->
     * v: 1, 1, 1, 1, 2, 4, 4, 4, 4, 5
     * </pre>使统计数组的元素值，等于相应整数的最终排序位置的序号。如下标i的值为v，代表原始数列的整数i，最终排序在第v位
     * </blockquote>
     * </p>
     * <p>
     * 获得结果时，需要从后向前遍历输入数列
     * </p>
     * <p>
     * 优化后的排序属于稳定排序
     * </p>
     */
    @Override
    public int[] optimizedSort(int[] array) {
        // 1、得到max和min，计算差值range
        int max = array[0];
        int min = max;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            } else if (array[i] < min) {
                min = array[i];
            }
        }
        int range = max - min;
        
        // 2、创建统计数组并统计对应元素的个数
        int[] countArray = new int[range + 1];
        for (int i = 0; i < countArray.length; i++) {
            countArray[array[i] - min]++;
        }
        
        // 3、统计数组变形
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        
        // 4、倒序遍历原始数列，从统计数组找到正确位置，获得结果数组
        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            sortedArray[countArray[array[i] - min]-- - 1] = array[i];
        }
        
        return sortedArray;
    }
    
    /**
     * 桶排序
     * <p>
     * 另一种线性时间复杂度的排序算法，对计数排序做出了弥补
     * </p>
     * <p>
     * 类似于统计数组，创建若干个桶来协助排序，
     * 每个桶代表一个区间范围，可以承载多个元素
     * </p>
     * <p>
     * 这里创建的桶数量等于原始数列的元素数，除最后一个桶只包含数列最大值外，前面各个桶的区间按比例确定：<br />
     * 桶的数量：bucketNum = length;<br />
     * 区间跨度：(max - min) / (bucketNum - 1);<br />
     * </p>
     * <p>
     * 时间复杂度为O(n)。如果元素极不均衡，时间复杂度为O(n*log n)，还创建了许多空桶
     * </p>
     */
    @Override
    public double[] extraSort(double[] array) {
        // 1、得到max、min和range
        double max = array[0];
        double min = max;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            } else if (array[i] < min) {
                min = array[i];
            }
        }
        double range = max - min;
        
        // 2、初始化桶
        // 桶的数量
        int bucketNum = array.length;
        List<LinkedList<Double>> bucketList = new ArrayList<>();
        for (int i = 0; i < bucketNum; i++) {
            bucketList.add(new LinkedList<>());
        }
        
        // 3、遍历原始数组，将每个元素放入桶中
        for (double element : array) {
            int bucketNo = (int) ((element - min) * (bucketNum - 1) / range);
            bucketList.get(bucketNo).add(element);
        }
        
        // 4、对每个桶内部排序
        for (LinkedList<Double> doubles : bucketList) {
            // JDK底层采用了归并排序或归并的优化版本Timsort，时间复杂度近似O(n*log n)
            Collections.sort(doubles);
        }
        
        // 5、获得结果
        double[] sortedArray = new double[array.length];
        int index = 0;
        for (LinkedList<Double> list : bucketList) {
            for (double element : list) {
                sortedArray[index++] = element;
            }
        }
        
        return sortedArray;
    }
    
    public static void main(String[] args) {
        Sortable sorter = new CountingSort();
        int[] sortedArray1;
        
        int[] array1 = new int[] { 4, 4, 6, 5, 3, 2, 8, 1, 7, 5, 6, 0, 10 };
        sortedArray1 = sorter.sort(array1);
        System.out.println("计数排序：");
        System.out.println(Arrays.toString(sortedArray1));
        
        int[] array2 = new int[] { 95, 94, 91, 98, 99, 90, 99, 93, 91, 92 };
        sortedArray1 = sorter.optimizedSort(array2);
        System.out.println("优化计数排序：");
        System.out.println(Arrays.toString(sortedArray1));
        
        double[] array3 = new double[] { 4.12, 6.421, 0.0023, 3.0, 2.123, 8.122, 4.12, 10.09 };
        double[] sortedArray2 = sorter.extraSort(array3);
        System.out.println("桶排序：");
        System.out.println(Arrays.toString(sortedArray2));
    }
    
}
