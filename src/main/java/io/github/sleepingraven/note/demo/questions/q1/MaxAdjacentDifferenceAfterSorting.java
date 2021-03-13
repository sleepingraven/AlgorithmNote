package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 有一无序整型数组，求排序后的任意两相邻元素的最大差值
 * <blockquote>
 * <pre>
 * before: 2, 6, 3, 4, 5, 10, 9
 * after:  2, 3, 4, 5, 6, 9, 10
 *                     &uarr;__&uarr;
 * </pre>
 * </blockquote>
 *
 * @author 10132
 */
public class MaxAdjacentDifferenceAfterSorting {

    /**
     * 解题思路
     * <ol>
     *     <li>直接排序</li>
     *     <li>
     *         利用数组下标
     *         <p>如有1、2、1 000 000，浪费空间</p>
     *     </li>
     *     <li>
     *         桶排序优化
     *         <p>创建n = length个桶，其中第一个桶的区间跨度为(max - min) / (n - 1)</p>
     *         <p>O(n)</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        int[] array = new int[] { 2, 6, 3, 4, 5, 10, 9 };
        System.out.println(getMaxSortedDistance(array));
    }

    private static int getMaxSortedDistance(int[] array) {
        // 1、得到max、min和range
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
        // 如果max == min，直接返回
        if (range == 0) {
            return 0;
        }

        // 2、初始化桶
        int bucketNum = array.length;
        Assistant[] buckets = new Assistant[bucketNum];
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = new Assistant();
        }

        // 3、遍历原始数组，确定每个桶的最大最小值
        for (int value : array) {
            // 确定数组元素所归属的桶下标
            int index = ((value - min) * (bucketNum - 1) / range);
            if (null == buckets[index].min || buckets[index].min > value) {
                buckets[index].min = value;
            }
            if (null == buckets[index].max || buckets[index].max < value) {
                buckets[index].max = value;
            }
        }

        // 4、遍历桶，找到最大差值
        int maxDistance = 0;
        int leftMax = buckets[0].max;
        for (int i = 1; i < buckets.length; i++) {
            if (null == buckets[i].min) {
                continue;
            }
            if (buckets[i].min - leftMax > maxDistance) {
                maxDistance = buckets[i].min - leftMax;
            }
            leftMax = buckets[i].max;
        }

        return maxDistance;
    }

    private static class Assistant {
        Integer max;
        Integer min;

    }

}
