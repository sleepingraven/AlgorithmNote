package io.github.sleepingraven.util.ac;

/**
 * @author Carry
 * @since 2020/11/25
 */
public class DataFilter {
    
    /**
     * @param pair
     *         结果数组，0：最大值，1：权值总和
     * @param mod
     *         权值求和的模数
     * @param nums
     *         0：当前数，1：权值
     */
    public static void totalOfMax(int[] pair, int mod, int[]... nums) {
        int max = Integer.MIN_VALUE;
        int cnt = 0;
        for (int[] num : nums) {
            if (num[0] > max) {
                max = num[0];
                cnt = num[1];
            } else if (num[0] == max) {
                cnt += num[1];
                cnt %= mod;
            }
        }
        pair[0] = max;
        pair[1] = cnt;
    }
    
    /**
     * @return 最大值，计数值
     */
    public static int[] countOfMax(int... nums) {
        int max = Integer.MIN_VALUE;
        int cnt = 0;
        for (int num : nums) {
            if (num > max) {
                max = num;
                cnt = 1;
            } else if (num == max) {
                cnt++;
            }
        }
        return new int[] { max, cnt };
    }
    
    public static boolean inBound(int i, int j, int n, int m) {
        return inBound(i, 0, n) && inBound(j, 0, m);
    }
    
    public static boolean inBound(int i, int l, int r) {
        return i >= l && i < r;
    }
    
    public static boolean oneAtBit(int num, int bitIndex) {
        return (num & (1 << bitIndex)) != 0;
    }
    
}
