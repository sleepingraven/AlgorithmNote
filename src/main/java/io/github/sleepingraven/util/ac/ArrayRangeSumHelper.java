package io.github.sleepingraven.util.ac;

/**
 * @author Carry
 * @since 2021/2/12
 */
public class ArrayRangeSumHelper {
    final int[] prefixSum;
    final boolean shift;
    
    public ArrayRangeSumHelper(int[] nums) {
        this(nums, false);
    }
    
    public ArrayRangeSumHelper(int[] nums, boolean shift) {
        this.shift = shift;
        prefixSum = !shift ? prefixSum(nums) : prefixSumShift(nums);
    }
    
    /**
     * range sum inclusive
     *
     * @return sum of arr[l:r]
     */
    public int rangeSum(int l, int r) {
        return !shift ? rangeSum(l, r, prefixSum) : rangeSumShift(l, r, prefixSum);
    }
    
    /**
     * prefixSum[i] = nums[0:i]
     */
    static int[] prefixSum(int[] nums) {
        int[] prefixSum = new int[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        return prefixSum;
    }
    
    static int[] postfixSum(int[] nums) {
        int[] postfixSum = new int[nums.length];
        postfixSum[nums.length - 1] = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            postfixSum[i] = postfixSum[i + 1] + nums[i];
        }
        return postfixSum;
    }
    
    /**
     * range sum inclusive
     *
     * @return sum of arr[l:r]
     */
    static int rangeSum(int l, int r, int[] prefixSum) {
        return l == 0 ? prefixSum[r] : prefixSum[r] - prefixSum[l - 1];
    }
    
    /**
     * prefixSum[i] = nums[0:i)
     */
    static int[] prefixSumShift(int[] nums) {
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; ++i) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        return prefixSum;
    }
    
    /**
     * range sum inclusive
     *
     * @return sum of arr[l:r]
     */
    static int rangeSumShift(int l, int r, int[] prefixSum) {
        return prefixSum[r + 1] - prefixSum[l];
    }
    
}
