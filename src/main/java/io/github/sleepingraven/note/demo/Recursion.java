package io.github.sleepingraven.note.demo;

import org.junit.Test;
import io.github.sleepingraven.util.ac.ArrayUtil;

import java.util.Arrays;

/**
 * 递归练习
 * <p>
 * <ol>
 *     <li>
 *         求数组元素累加和，使用二分法<p>
 *         输入 { 2, 5, 3, 9, 12, 7 }<p>
 *         输出 38
 *     </li>
 *     <li>
 *         求组合数<p>
 *         输入 10, 3<p>
 *         输出 120
 *     </li>
 *     <li>
 *         生成全排列<p>
 *         输入 { 'a', 'b', 'c' }<p>
 *         输出 （共6行）
 *     </li>
 *     <li>
 *         生成两数之间的数的全排列<p>
 *         输入 1, 3<p>
 *         输出 （共6行）
 *     </li>
 *     <li>
 *         求最大公共子序列的长度<p>
 *         输入 "abc", "xbacd"<p>
 *         输出 2
 *     </li>
 *     <li>
 *         找出整数的加法划分<p>
 *         输入 6<p>
 *         输出
 *         <blockquote>
 *             <pre>
 * 6
 * 5+1
 * 4+2,4+1+1
 * 3+3,3+2+1,3+1+1+1
 * 2+2+2,2+2+1+1,2+1+1+1+1
 * 1+1+1+1+1+1</pre>（共11条）
 *         </blockquote>
 *     </li>
 *     <li>
 *         某财务部门结账时发现总金额不对头。很可能是从明细上漏掉了某1笔或几笔。
 *         如果已知明细账目清单，能通过编程找到漏掉的是哪1笔或几笔吗？
 * 	       为了方便，不妨假设所有的金额都是整数；每笔金额不超过1000，金额的明细条数不超过100。<p>
 * 	       输入 有错的总金额是6；明细共有5笔，金额为：3、2、4、3、1<p>
 * 	       输出 （共4条）
 *     </li>
 * </ol>
 *
 * @author Carry
 * @since 2020/1/16
 */
public class Recursion {
    
    @Test
    public void arraySumTest() {
        int[] a = { 2, 5, 3, 9, 12, 7 };
        int sum = Solution.arraySum(a, 0, a.length);
        System.out.println(sum);
    }
    
    @Test
    public void combinatorialNumberTest() {
        int n = 10;
        int m = 3;
        int c = Solution.combinatorialNumber(n, m);
        System.out.println(c);
    }
    
    @Test
    public void fullPermutationOfCharsTest() {
        char[] s = new char[] { 'a', 'b', 'c' };
        Solution.fullPermutationOfChars(s, 0);
    }
    
    @Test
    public void fullPermutationOfNumsTest() {
        int b = 1;
        int e = 3;
        int[] c = new int[e - b + 1];
        boolean[] h = new boolean[e + 1];
        Solution.fullPermutationOfNums(b, e, 0, c, h);
    }
    
    @Test
    public void maxCommonSubsequenceLengthTest() {
        String s1 = "abc";
        String s2 = "xbacd";
        int m = Solution.maxCommonSubsequenceLength(s1, s2);
        System.out.println(m);
    }
    
    @Test
    public void additiveDivisionTest() {
        int n = 6;
        int[] c = new int[n];
        Solution.additiveDivision(n, c, 0);
    }
    
    @Test
    public void fun1Test() {
        int sum = 6;
        int[] a = new int[] { 3, 2, 4, 3, 1 };
        boolean[] b = new boolean[a.length];
        
        Solution.fun1(Solution.arraySum(a, 0, a.length) - sum, a, b, 0);
    }
    
    /**
     * 解答
     */
    private static class Solution {
        
        /**
         * 求数组元素累加和，使用二分法
         *
         * @param a
         *         数组
         * @param begin
         *         开始下标，begin >= 0
         * @param end
         *         结束下标（不包含），end > begin，end <= a.length
         *
         * @return 累加和
         */
        public static int arraySum(int[] a, int begin, int end) {
            if (begin == end - 1) {
                return a[begin];
            }
            
            int mid = (begin + end) / 2;
            return arraySum(a, begin, mid) + arraySum(a, mid, end);
        }
        
        /**
         * 求组合数
         *
         * @param n
         *         共n个球
         * @param m
         *         取m个球，m >= 0
         *
         * @return 组合数
         */
        public static int combinatorialNumber(int n, int m) {
            if (n < m) {
                return 0;
            }
            if (n == m) {
                return 1;
            }
            if (m == 0) {
                return 1;
            }
            
            return combinatorialNumber(n - 1, m - 1) + combinatorialNumber(n - 1, m);
        }
        
        /**
         * 生成全排列。对k-(s.length - 1)的元素全排列，打印s
         *
         * @param s
         *         初始排列
         * @param k
         *         关注点，与其后的元素交换，k > 0
         */
        public static void fullPermutationOfChars(char[] s, int k) {
            if (k == s.length - 1) {
                System.out.println(Arrays.toString(s));
                return;
            }
            for (int i = k; i < s.length; i++) {
                // 试探
                ArrayUtil.swap(s, k, i);
                fullPermutationOfChars(s, k + 1);
                // 回溯
                ArrayUtil.swap(s, k, i);
            }
        }
        
        /**
         * 生成两数之间的数的全排列
         *
         * @param b
         *         开始数
         * @param e
         *         结束数
         * @param k
         *         当前位置
         * @param c
         *         缓存
         * @param h
         *         h[i]表示i是否已被使用
         */
        public static void fullPermutationOfNums(int b, int e, int k, int[] c, boolean[] h) {
            if (k == c.length) {
                System.out.println(Arrays.toString(c));
                return;
            }
            
            for (int i = b; i <= e; i++) {
                if (!h[i]) {
                    c[k] = i;
                    h[i] = true;
                    fullPermutationOfNums(b, e, k + 1, c, h);
                    h[i] = false;
                }
            }
        }
        
        /**
         * 求最大公共子序列的长度
         *
         * @param s1
         *         串1，s1 != null
         * @param s2
         *         串2，s2 != null
         *
         * @return 最大长度
         */
        public static int maxCommonSubsequenceLength(String s1, String s2) {
            if (s1.isEmpty() || s2.isEmpty()) {
                return 0;
            }
            
            if (s1.charAt(0) == s2.charAt(0)) {
                return maxCommonSubsequenceLength(s1.substring(1), s2.substring(1)) + 1;
            }
            return Math.max(maxCommonSubsequenceLength(s1, s2.substring(1)),
                            maxCommonSubsequenceLength(s1.substring(1), s2));
        }
        
        /**
         * 找出整数的加法划分
         *
         * @param n
         *         整数，n > 0
         * @param c
         *         缓存数组，c.length >= n
         * @param k
         *         当前位置，初值为0
         */
        public static void additiveDivision(int n, int[] c, int k) {
            if (n == 0) {
                // 打印加式
                System.out.print(c[0]);
                for (int i = 1; i < k; i++) {
                    System.out.printf("+%d", c[i]);
                }
                
                // 打印式间分隔符
                System.out.print(k == 1 || c[1] == 1 ? "\n" : ",");
            }
            
            for (int i = n; i > 0; i--) {
                if (k > 0) {
                    if (i > c[k - 1]) {
                        continue;
                    }
                }
                
                c[k] = i;
                additiveDivision(n - i, c, k + 1);
            }
        }
        
        public static void fun1(int sum, int[] a, boolean[] b, int k) {
            if (sum < 0) {
                return;
            }
            if (sum == 0) {
                for (int i = 0; i < a.length; i++) {
                    if (b[i]) {
                        System.out.printf("%d ", a[i]);
                    }
                }
                System.out.println();
                return;
            }
            if (k >= a.length) {
                return;
            }
            
            b[k] = true;
            fun1(sum - a[k], a, b, k + 1);
            
            b[k] = false;
            fun1(sum, a, b, k + 1);
        }
        
    }
    
}
