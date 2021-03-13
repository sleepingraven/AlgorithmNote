package io.github.sleepingraven.note.demo.questions.q2;

import io.github.sleepingraven.note.demo.questions.q1.GreatestCommonDivisorAlgorithm;

import java.util.Arrays;

/**
 * @author 10132
 */
public class ArithmeticSequence {
    
    /**
     * 有一等差数列。给出其中n个整数（无序），求包含这n个整数的最短的等差数列有几项？
     * in:
     * 5
     * 2 6 4 10 20
     * out:
     * 10
     */
    public static void main(String[] args) {
        int[] nums = { 2, 6, 4, 10, 20, };
        int r = arithmeticProgression(nums);
        System.out.println(r);
    }
    
    /**
     * 解题思路
     * <p>先将给定的数字去重并排序，公差是排序后所有相邻数字差值的最大公约数</p>
     */
    private static int arithmeticProgression(int[] nums) {
        nums = Arrays.stream(nums).distinct().boxed().sorted().mapToInt(i -> i).toArray();
        // 公差
        int d = nums[1] - nums[0];
        for (int i = 2; i < nums.length; i++) {
            int diff = nums[i] - nums[i - 1];
            d = GreatestCommonDivisorAlgorithm.getGcdBit(d, diff);
        }
        
        return (nums[nums.length - 1] - nums[0]) / d + 1;
    }
    
}
