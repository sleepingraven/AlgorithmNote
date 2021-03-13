package io.github.sleepingraven.note.demo.questions.q2;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/10/14
 */
public class Sequence {
    
    /**
     * <p>【问题描述】<br>小明想知道，满足以下条件的正整数序列的数量：<br>1. 第一项为 n；<br>2. 第二项不超过 n；<br>3. 从第三项开始，每一项小于前两项的差的绝对值。<br>请计算，对于给定的
     * n，有多少种满足条件的序列。<br>【输入格式】<br>输入一行包含一个整数 n。<br>【输出格式】<br>输出一个整数，表示答案。答案可能很大，请输出答案除以10000的余数。<br>【样例输入】<br>4<br>【样例输出】<br>7<br>【样例说明】<br>以下是满足条件的序列：<br>4
     * 1<br>4 1 1<br>4 1 2<br>4 2<br>4 2 1<br>4 3<br>4 4<br>【评测用例规模与约定】<br>对于 20% 的评测用例，1 &lt;= n &lt;= 5；<br>对于 50%
     * 的评测用例，1 &lt;= n &lt;= 10；<br>对于 80% 的评测用例，1 &lt;= n &lt;= 100；<br>对于所有评测用例，1 &lt;= n &lt;= 1000。<br><br><br></p>
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution();
    }
    
    private static class Solution {
        private static final int MOD = 10000;
        int[][] dp = new int[1001][1001];
        
        public void solution() {
            Scanner scanner = new Scanner(System.in);
            final int n = scanner.nextInt();
            
            int count = 0;
            for (int i = 1; i <= n; i++) {
                count = count + search(n, i) % MOD;
            }
            System.out.println(count % MOD);
        }
        
        private int search(int n, int m) {
            final int abs = Math.abs(n - m);
            if (abs <= 1) {
                return 1;
            }
            if (dp[n][m] != 0) {
                return dp[n][m];
            }
            
            int count = 0;
            for (int i = 0; i < abs; i++) {
                //若 i==0，意味着当前已结束。如 4 1 0 等同于4 1 ''
                if (i == 0) {
                    count++;
                    continue;
                }
                count += search(m, i);
            }
            dp[n][m] = count;
            return count % MOD;
        }
        
    }
    
}
