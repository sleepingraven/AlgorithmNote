package io.github.sleepingraven.note.demo.questions.q2;

import java.util.*;
import java.util.function.ToIntFunction;

/**
 * @author Carry
 * @since 2020/10/14
 */
public class Party {
    
    /**
     * 【问题描述】<br>小明要组织一台晚会，总共准备了 n 个节目。然后晚会的时间有限，他只能最终选择其中的 m 个节目。<br>这 n 个节目是按照小明设想的顺序给定的，顺序不能改变。<br>小明发现，观众对于晚上的喜欢程度与前几个节目的好看程度有非常大的关系，他希望选出的第一个节目尽可能好看，在此前提下希望第二个节目尽可能好看，依次类推。<br>小明给每个节目定义了一个好看值，请你帮助小明选择出
     * m 个节目，满足他的要求。<br>【输入格式】<br>输入的第一行包含两个整数 n, m ，表示节目的数量和要选择的数量。<br>第二行包含 n 个整数，依次为每个节目的好看值。<br>【输出格式】<br>输出一行包含 m
     * 个整数，为选出的节目的好看值。<br>【样例输入】<br>5 3<br>3 1 2 5 4<br>【样例输出】<br>3 5 4<br>【样例说明】<br>选择了第1, 4,
     * 5个节目。<br>【评测用例规模与约定】<br>对于 30% 的评测用例，1 &lt;= n &lt;= 20；<br>对于 60% 的评测用例，1 &lt;= n &lt;= 100；<br>对于所有评测用例，1
     * &lt;=
     * n &lt;= 100000，0 &lt;= 节目的好看值 &lt;= 100000。<br><br>
     */
    public static void main(String[] args) {
        // Solution solution = new Solution();
        Solution2 solution = new Solution2();
        solution.solution();
    }
    
    private static class Solution {
        
        /**
         * 贪心
         */
        public void solution() {
            Scanner sc = new Scanner(System.in);
            final int n = sc.nextInt(), m = sc.nextInt();
            int[] nums = new int[n];
            int[] order = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = order[i] = sc.nextInt();
            }
            sc.close();
            
            // 记录最大的 m 个数
            Arrays.sort(order);
            List<String> list = new ArrayList<>();
            for (int i = n - m; i < n; i++) {
                list.add(Integer.toString(order[i]));
            }
            
            StringBuilder buffer = new StringBuilder();
            for (int num : nums) {
                String s = Integer.toString(num);
                if (list.contains(s)) {
                    list.remove(s);
                    buffer.append(s).append(" ");
                }
            }
            System.out.println(buffer.toString());
        }
        
    }
    
    private static class Solution2 {
        static final int N = 100005;
        perform[] ps = new perform[N];
        
        /**
         * 排序
         */
        public void solution() {
            Scanner sc = new Scanner(System.in);
            final int n = sc.nextInt(), m = sc.nextInt();
            for (int i = 0; i < n; i++) {
                ps[i] = new perform(i, sc.nextInt());
            }
            sc.close();
            
            // 第一次针对节目的好看程度进行降序排序
            Arrays.sort(ps, 0, n, Comparator.comparingInt((ToIntFunction<perform>) value -> value.value).reversed());
            // 第二次针对前面 m 个节目的序号进行升序排序
            Arrays.sort(ps, 0, m, Comparator.comparingInt(value -> value.pos));
            
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < m; i++) {
                buffer.append(ps[i].value).append(" ");
            }
            System.out.println(buffer.toString());
        }
        
        private static class perform {
            final int pos, value;
            
            public perform(int pos, int value) {
                this.pos = pos;
                this.value = value;
            }
            
        }
        
    }
    
}
