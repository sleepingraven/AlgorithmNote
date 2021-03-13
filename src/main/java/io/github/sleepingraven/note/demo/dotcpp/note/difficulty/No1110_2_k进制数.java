package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/16
 */
public class No1110_2_k进制数 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int w = scanner.nextInt();
        int digits = w / k + 1;
        int twoPowK = (int) Math.pow(2, k);
        long sum = 0;
        
        // 从第二位开始，到最高位前一位
        for (int i = 2; i < digits; i++) {
            sum += arrange(twoPowK, i);
        }
        
        // 若w能除尽k，最高位最大值为0
        int maxHead = (int) Math.min(Math.pow(2, w % k) - 1, twoPowK - digits);
        // high既小于二进制余出来的位数所能得到的最大数又小于X进制减位数
        // 计算最高位的排列数
        if (maxHead != 0) {
            sum += (arrange(twoPowK, digits) - arrange(twoPowK - maxHead, digits));
        }
        
        System.out.println(sum);
    }
    
    /**
     * A(n-1)(m)计算排列数
     *
     * @param n
     *         进制（最大数字+1）
     * @param m
     *         当前位置
     */
    private static long arrange(int n, int m) {
        long sum = 1;
        
        // (n - m) * ... * (n - 1)
        for (int i = 1; i <= m; i++) {
            sum *= (n - i);
        }
        // 2 * ... * m
        for (int i = 2; i <= m; i++) {
            sum /= i;
        }
        
        return sum;
    }
    
}
