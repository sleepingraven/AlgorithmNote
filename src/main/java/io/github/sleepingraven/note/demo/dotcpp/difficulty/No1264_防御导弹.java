package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/4
 */
public class No1264_防御导弹 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            int h = scanner.nextInt();
            if (h == -1) {
                break;
            }
            list.add(h);
        }
        
        System.out.println(fun1(list));
    }
    
    /**
     * 动态规划:寻找最长递减序列
     * 300 250 275 252 200 138 245
     * 建立dp[]数组
     * 用dp[i]来存从第一个到第i个的最长递减数列长度
     * 第一个 300 所以dp[0] = 1
     * 第二个 250 可以加到300后面，变成300 250，所以dp[1] = 2
     * 第三个 275 不能加到300、250后面，但可以加到300后面，所以dp[2] = 2
     * ……
     * <p>
     * 显然，第i个数list[i]能加到一个递减数列的末尾list[j]的充分条件是：list[i] <= list[j]，
     * 为了保证求得的dp[i]是最长的递减数列，我们还要增加条件dp[i] < dp[j] + 1（dp[i]初值为1）。
     * 如果满足上面条件，就让dp[i] = d[j] + 1（以此来进行dp）
     * 最后dp数组中的最大值，就是题目所求
     */
    private static int fun1(List<Integer> list) {
        final int n = list.size();
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (list.get(j) >= list.get(i)) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                    }
                }
            }
        }
        
        Arrays.sort(dp);
        return dp[n - 1];
    }
    
}
