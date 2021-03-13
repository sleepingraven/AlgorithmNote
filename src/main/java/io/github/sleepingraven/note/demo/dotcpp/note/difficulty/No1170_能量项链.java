package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/3
 */
public class No1170_能量项链 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] balls = new int[201];
        for (int i = 1; i <= n; i++) {
            balls[i] = scanner.nextInt();
            // 为避免处理循环，将珠子加倍
            // 模拟环，把环拆完链
            balls[i + n] = balls[i];
        }
        
        System.out.println(fun1(n, balls));
        System.out.println(fun2(n, balls));
    }
    
    private static int fun1(int n, int[] balls) {
        int[][] dp = new int[202][202];
        
        // 每len个球合并
        for (int len = 2; len <= n; len++) {
            // 从第i个球开始
            for (int i = 1; i + len - 1 < 2 * n; i++) {
                // 处理第i到j个球
                int j = i + len - 1;
                // 左右部分的分段点情况
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k + 1][j] + balls[i] * balls[k + 1] * balls[j + 1]);
                }
            }
        }
        
        int res = 0;
        // len=n的串中，寻找最大值
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dp[i][i + n - 1]);
        }
        return res;
    }
    
    private static int fun2(final int n, int[] balls) {
        int result = 0;
        int[][] maxE = new int[201][201];
        for (int i = 2; i < 2 * n; i++) {  //右端点
            for (int j = i - 1; j > 0 && i - j < n; j--) { //左端点
                for (int k = j; k < i; k++) {
                    // 合并
                    maxE[j][i] =
                            Math.max(maxE[j][i], maxE[j][k] + maxE[k + 1][i] + balls[j] * balls[k + 1] * balls[i + 1]);
                }
                if (result < maxE[j][i]) {
                    result = maxE[j][i];
                }
            }
        }
        return result;
    }
    
}
