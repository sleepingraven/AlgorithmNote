package io.github.sleepingraven.util.ac;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * @author Carry
 * @since 2020/11/25
 */
public class ArrayUtil {
    
    /* common array operations */
    
    public static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        swapd(a, i, j);
    }
    
    public static void swap(char[] a, int i, int j) {
        if (i == j) {
            return;
        }
        swapd(a, i, j);
    }
    
    public static void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    /**
     * swap in different indexs
     */
    public static void swapd(int[] a, int i, int j) {
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }
    
    public static void swapd(char[] a, int i, int j) {
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }
    
    public static void reverse(int[] a) {
        reverse(a, 0, a.length);
    }
    
    public static void reverse(char[] a) {
        reverse(a, 0, a.length);
    }
    
    public static void reverse(int[] a, int from, int to) {
        int l = from, r = to - 1;
        while (l < r) {
            swapd(a, l++, r--);
        }
    }
    
    public static void reverse(char[] a, int from, int to) {
        int l = from, r = to - 1;
        while (l < r) {
            swapd(a, l++, r--);
        }
    }
    
    public static int indexOf(int[] a, int from, int k) {
        return indexOf(a, from, a.length, k);
    }
    
    public static int indexOf(int[] a, int from, int to, int k) {
        for (int i = from; i < to; i++) {
            if (a[i] == k) {
                return i;
            }
        }
        return to;
    }
    
    public static int binarySearch(int[] a, int k) {
        int l = 0, r = a.length;
        while (l < r) {
            int mid = l + r >>> 1;
            if (a[mid] < k) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }
    
    public static int lengthOfLIS(int[] nums) {
        final int n = nums.length;
        
        int[] tail = new int[n];
        tail[0] = nums[0];
        // 表示有序数组 tail 的最后一个已经赋值元素的索引
        int end = 0;
        for (int i = 1; i < n; i++) {
            int num = nums[i];
            // 查找插入位置
            if (num > tail[end]) {
                tail[++end] = num;
            } else {
                int idx = Arrays.binarySearch(tail, 0, end, num);
                if (idx < 0) {
                    idx = -idx - 1;
                }
                tail[idx] = num;
            }
        }
        
        return end + 1;
    }
    
    /* iteration operations */
    
    public static void forEachInRange(int from, int to, IntConsumer action) {
        int step = Integer.compare(to, from);
        for (int i = from; i != to; i += step) {
            action.accept(i);
        }
    }
    
    public static void forEachDigit(IntConsumer action) {
        for (int i = 0; i < 10; ++i) {
            action.accept(i);
        }
    }
    
    public static void forEachDigit(int[] a, IntFunction<IterationControl> action) {
        Predicate<Integer> equal = Predicate.isEqual(IterationControl.BREAK);
        for (int i = 0; i < 10; ++i) {
            if (equal.test(i)) {
                return;
            }
        }
    }
    
    public enum IterationControl {
        CONTINUE,
        BREAK,
    }
    
    /* sort operations */
    
    public static void quickSyncSortByFirstElements(int l, int r, int[]... as) {
        if (l >= r) {
            return;
        }
        
        int[] fst = as[0];
        int pivot = fst[l + r >> 1];
        int i = l - 1;
        int j = r + 1;
        while (i < j) {
            while (fst[++i] < pivot) {
            }
            while (fst[--j] > pivot) {
            }
            if (i < j) {
                for (int[] a : as) {
                    swapd(a, i, j);
                }
            }
        }
        
        quickSyncSortByFirstElements(l, j, as);
        quickSyncSortByFirstElements(j + 1, r, as);
    }
    
    public static void radixSort(int[] nums) {
        final int n = nums.length;
        if (n <= 1) {
            return;
        }
        
        int[] buf = new int[n];
        final int max = Arrays.stream(nums).max().getAsInt();
        for (long exp = 1; exp <= max; exp *= 10) {
            int[] cnt = new int[10];
            for (int num : nums) {
                int d = (int) (num / exp) % 10;
                cnt[d]++;
            }
            for (int i = 1; i < 10; i++) {
                cnt[i] += cnt[i - 1];
            }
            for (int i = n - 1; i >= 0; i--) {
                int d = (int) (nums[i] / exp) % 10;
                buf[--cnt[d]] = nums[i];
            }
            System.arraycopy(buf, 0, nums, 0, n);
        }
    }
    
}
