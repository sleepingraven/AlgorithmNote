package io.github.sleepingraven.note.practice;

import io.github.sleepingraven.util.ac.ArrayUtil;
import io.github.sleepingraven.util.common.IntArrayWrapper;

import java.util.Random;

/**
 * @author Carry
 * @since 2021/2/12
 */
public class ArrayKthFinder {
    
    /**
     * 求两个有序数组的第 k 小的元素，即归并排序后第 k - 1 的元素<p>
     * see also <a href="https://leetcode-cn.com/problems/median-of-two-sorted-arrays/">4. 寻找两个正序数组的中位数</a>
     */
    public static IntArrayWrapper findKth(IntArrayWrapper a, IntArrayWrapper b, int k) {
        int lena = a.getLength();
        int lenb = b.getLength();
        // 保证如果有数组为空，一定是 a
        if (lena > lenb) {
            return findKth(b, a, k);
        }
        if (lena == 0) {
            b.l += k - 1;
            return b;
        }
        if (k == 1) {
            return a.valueAtL() <= b.valueAtL() ? a : b;
        }
        
        int i = a.l + Math.min(lena, k / 2) - 1;
        int j = b.l + Math.min(lenb, k / 2) - 1;
        
        if (a.valueAt(i) > b.valueAt(j)) {
            k -= j - b.l + 1;
            b.l = j + 1;
        } else {
            k -= i - a.l + 1;
            a.l = i + 1;
        }
        return findKth(a, b, k);
    }
    
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    
    /**
     * 求排序后第 k - 1 的元素（返回结果的下标）
     */
    public static int findKth(int[] nums, int k) {
        // return quickSearch(nums, 0, nums.length - 1, nums.length - k);
        return quickSearch1(nums, 0, nums.length - 1, nums.length - k);
    }
    
    public static int quickSearch(int[] nums, int lo, int hi, int k) {
        // 每快排切分一次，如果 p 恰好等于 k 就返回
        int p = partition(nums, lo, hi);
        if (p == k) {
            return nums[p];
        }
        // 否则根据下标 p 与 k 的大小决定切分左段还是右段
        return p > k ? quickSearch(nums, lo, p - 1, k) : quickSearch(nums, p + 1, hi, k);
    }
    
    /**
     * 双指针切分，返回下标 p，使得比 nums[p] 小的数都在左边
     */
    public static int partition(int[] nums, int lo, int hi) {
        ArrayUtil.swap(nums, lo, RANDOM.nextInt(hi - lo + 1) + lo);
        int i = lo, j = hi + 1;
        while (i < j) {
            while (++i <= hi && nums[i] < nums[lo]) {
            }
            // 不能等于，否则可能越界
            while (nums[--j] > nums[lo]) {
            }
            if (i < j) {
                ArrayUtil.swapd(nums, i, j);
            }
        }
        ArrayUtil.swap(nums, lo, j);
        return j;
    }
    
    public static int quickSearch1(int[] nums, int l, int r, int k) {
        if (l == r) {
            return nums[l];
        }
        int p = partition1(nums, l, r);
        return p >= k ? quickSearch1(nums, l, p, k) : quickSearch1(nums, p + 1, r, k);
    }
    
    public static int partition1(int[] nums, int l, int r) {
        int pivot = nums[RANDOM.nextInt(r - l + 1) + l];
        int i = l - 1, j = r + 1;
        while (i < j) {
            while (nums[++i] < pivot) {
            }
            while (nums[--j] > pivot) {
            }
            if (i < j) {
                ArrayUtil.swapd(nums, i, j);
            }
        }
        return j;
    }
    
}
