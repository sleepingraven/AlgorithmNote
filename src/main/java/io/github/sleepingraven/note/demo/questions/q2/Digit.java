package io.github.sleepingraven.note.demo.questions.q2;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/10/14
 */
public class Digit {
    
    /**
     * <p>【问题描述】<br>一个正整数如果任何一个数位不大于右边相邻的数位，则称为一个数位递增的数，例如1135是一个数位递增的数，而1024不是一个数位递增的数。<br>给定正整数 n，请问在整数 1 至 n
     * 中有多少个数位递增的数？<br>【输入格式】<br>输入的第一行包含一个整数 n。<br>【输出格式】<br>输出一行包含一个整数，表示答案。<br>【样例输入】<br>30<br>【样例输出】<br>26<br>【评测用例规模与约定】<br>对于
     * 40% 的评测用例，1 &lt;= n &lt;= 1000。<br>对于 80% 的评测用例，1 &lt;= n &lt;= 100000。<br>对于所有评测用例，1 &lt;= n &lt;=
     * 1000000。<br><br><br></p>
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution();
    }
    
    private static class Solution {
        int n;
        int count;
        
        public void solution() {
            Scanner sc = new Scanner(System.in);
            n = sc.nextInt();
            sc.close();
            
            dfs(0, 1);
            System.out.println(count - 1);
        }
        
        private void dfs(int num, int start) {
            if (num > n) {
                return;
            }
            
            count++;
            for (int i = start; i < 10; i++) {
                dfs(num * 10 + i, i);
            }
        }
        
    }
    
}
