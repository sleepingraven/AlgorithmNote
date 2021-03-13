package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1163_排队买票 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int m1 = scanner.nextInt();
        int m2 = scanner.nextInt();
        
        System.out.println(s0(m1, m2) * jc(m1) * jc(m2));
        System.out.println(s1(m, m1, m2));
    }
    
    /**
     * 求组合数
     */
    private static int s0(int m1, int m2) {
        // 两元钱的人数大于一元钱，就没可能
        if (m2 > m1) {
            return 0;
        }
        // 只有一元钱
        if (m2 == 0) {
            return 1;
        }
        return s0(m1 - 1, m2) + s0(m1, m2 - 1);
    }
    
    /**
     * 卡特兰数
     */
    private static int s1(int m, int m1, int m2) {
        if (m2 == 0) {
            return jc(m);
        }
        
        int[] sum = new int[11];
        int a = 1, b = 1;
        for (int i = 0; i <= m; i++) {
            // sum[0]=1 sum[1]=4 sum[2]=6 sum[3]=4 sum4=1
            sum[i] = a / b;
            // a0=4  a1=12 a2=24 a3=24
            a *= (m - i);
            // b0=1 b1=2 b2=6 b3=24
            b *= (i + 1);
        }
        
        int[] cell = new int[] { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800 };
        // sum[N]-sum[k-1]为所求个数，后面为全排列个数
        int ans = (sum[m1] - sum[m2 - 1]) * cell[m1] * cell[m2];
        return Math.max(ans, 0);
    }
    
    /**
     * 计算阶乘
     */
    private static int jc(int n) {
        int sum = 1;
        for (int i = 2; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }
    
}
