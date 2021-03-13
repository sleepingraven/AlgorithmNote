package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/28
 */
public class No1191_化学品问题 {
    private static final int[][] PRO = new int[33][99];
    
    /**
     * 分情况
     * <ol>
     *     <li>
     *         n < m：<p>
     *             随意放置
     *     </li>
     *     <li>
     *         n == m：<p>
     *             少一种情况
     *     </li>
     *     <li>
     *         n > m：<p>
     *             由n - 1变成n即在n - 1个试管后放1个试管，因为n号试管有放置和不放两种选择，所以是2 * (n - 1的方案数)。<p>
     *                 若试管装药品则可能爆炸，需要将错误的方案剔除，只能是n号试管的前m - 1个试管都放药品，前第m个不放药品。所以不合要求的为n - m - 1个试管合题意的方案数。
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int l = scanner.nextInt();
        while (l-- > 0) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            System.out.println(fun(n, m));
            // System.out.println(fun2(n, m));
        }
    }
    
    private static int fun(int n, final int m) {
        if (PRO[n][m] != 0) {
            return PRO[n][m];
        }
        if (n < m) {
            PRO[n][m] = (int) Math.pow(2, n);
            return PRO[n][m];
        }
        if (n == m) {
            PRO[n][m] = (int) (Math.pow(2, n) - 1);
            return PRO[n][m];
        }
        PRO[n][m] = fun(n - 1, m) + fun(n - 1, m) - fun(n - 1 - m, m);
        return PRO[n][m];
    }
    
    private static int fun2(int n, int m) {
        int[] pro = new int[n + 1];
        pro[0] = 1;
        for (int i = 1; i <= n; i++) {
            pro[i] = 2 * pro[i - 1];
            if (i == m) {
                pro[i] -= 1;
            } else if (i > m) {
                pro[i] -= pro[i - m - 1];
            }
        }
        
        return pro[n];
    }
    
}
