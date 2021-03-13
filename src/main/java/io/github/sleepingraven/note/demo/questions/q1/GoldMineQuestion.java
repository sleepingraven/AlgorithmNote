package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 一位国王有5座金矿，每座储量不同，挖掘人数也不同。
 * <blockquote>
 * <pre>
 * # 共10名工人  $ 200kg/3人  $ 300kg/4人
 * $ 350kg/3人  $ 400kg/5人  $ 500kg/5人
 * </pre>
 * </blockquote>
 * 如果参与的工人总数是10，每座金矿要么全挖，要么不挖，如不能派出一半的人挖取一半金矿。求出要想得到尽可能多的黄金，应该挖取哪几座金矿？
 *
 * @author 10132
 */
public class GoldMineQuestion {

    /**
     * 解题思路
     * <ol>
     *     <li>贪心算法。局部最优解，但整体上未必最优</li>
     *     <li>
     *         动态规划。和“背包问题”类似
     *         <p>把复杂的问题简化成规模较小的子问题，再从简单的子问题自底向上逐步递推，最终得到复杂问题的最优解</p>
     *         <ul>
     *             <li>
     *                 每一座金矿都存在“挖”和“不挖”两种选择：<br />
     *                 如果最后一个金矿注定不被挖掘，就简化成10个工人挖掘前4个的最优解；如果最后一个一定会挖，就简化成更少工人在前4个的最优解。
     *                 这两种简化情况，称为全局问题的两个最优子结构。
     *             </li>
     *             <li>
     *                 确定最优子结构，可以比较这两种情况收益的大小。
     *                 以此将问题一分为二、二分为四，一直简化成0个金矿或0个工人时的最优选择，这个收益结果显然是0，即问题的边界。
     *             </li>
     *             <li>
     *                 这就是动态规划的要点：确定全局最优解和最优子结构的关系，及问题的边界。即状态转移方程式：
     *                 <blockquote>
     *                     矿数：n；人数：w；含量：g[]；所需：p[]。<br />
     *                     F(n, w)为n个金矿、w个工人是的最优收益函数，则：<br /><br />
     *                     <p>
     *                         问题边界：
     *                         <pre>
     * F(n, w) = 0           (n == 0或w == 0)
     *                         </pre>
     *                     </p>
     *                     <p>
     *                         工人不够时，只有一种最优子结构：
     *                         <pre>
     * F(n, w) = F(n - 1, w) (n &gt;= 1, w &lt; p[n])
     *                         </pre>
     *                     </p>
     *                     <p>
     *                         常规情况：
     *                         <pre>
     * F(n, w) = max(F(n - 1, w), F(n - 1, w - p[n]) + g[n])
     *                       (n >= 1, w >= p[n])
     *                         </pre>
     *                     </p>
     *                 </blockquote>
     *             </li>
     *         </ul>
     *         <p>O(2n)</p>
     *     </li>
     *     <li>
     *         金矿数量越多，递归层次也越深，重复计算也越来越多。需要动态规划的另一核心：自底向上求解
     *         <p>O(nw) O(nw)</p>
     *     </li>
     *     <li>
     *         优化空间
     *         <p>O(nw) O(w)</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        int w = 10;
        int[] g = { 400, 500, 200, 300, 350 };
        int[] p = { 5, 5, 3, 4, 3 };

        System.out.println(getBestGoldMining(g.length, w, g, p));

        System.out.println();

        System.out.println(getBestGoldMiningOptimized(w, g, p));

        System.out.println();

        System.out.println(getBestGoldMiningOptimizedWithLessSpace(w, g, p));
    }

    /**
     * <blockquote>
     * <pre>
     * +---+---------------------------------+---+
     * | n |                w                | p |
     * +---+---------------------------------+---+
     * | 5 |                t                |   |
     * | 4 |        t               7        | 3 |
     * | 3 |    t       6       7       3    | 4 |
     * | 2 |  t   7   6   3   7   4   3   0  | 3 |
     * | 1 | t 5 7 2 6 1 3   7 2 4   3   0   | 5 |
     * +---+---------------------------------+---+
     * </pre>
     * </blockquote>
     *
     * @param n
     *         金矿总数
     * @param w
     *         工人总数
     * @param g
     *         金矿储量
     * @param p
     *         所需人数
     *
     * @return 最大收益
     */
    private static int getBestGoldMining(int n, int w, int[] g, int[] p) {
        if (w == 0 || n == 0) {
            return 0;
        }
        if (w < p[n - 1]) {
            return getBestGoldMining(n - 1, w, g, p);
        }
        return Math.max(getBestGoldMining(n - 1, w, g, p), getBestGoldMining(n - 1, w - p[n - 1], g, p) + g[n - 1]);
    }

    /**
     * <blockquote>
     * <pre>
     * +---------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
     * | g/p \ w |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |  t  |
     * +---------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
     * |  400/5  |   0 |   0 |   0 |   0 | 400 | 400 | 400 | 400 | 400 | 400 |
     * |  500/5  |   0 |   0 |   0 |   0 | 500 | 500 | 500 | 500 | 500 | 500 |
     * |  200/3  |   0 |   0 | 200 | 200 | 500 | 500 | 500 | 700 | 700 | 900 |
     * |  300/4  |   0 |   0 | 200 | 300 | 500 | 500 | 500 | 700 | 800 | 900 |
     * |  350/3  |   0 |   0 | 350 | 350 | 500 | 550 | 650 | 850 | 850 | 900 |
     * +---------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
     * </pre>
     * </blockquote>
     */
    private static int getBestGoldMiningOptimized(int w, int[] g, int[] p) {
        int n = g.length;
        // 保留了零号行和列
        int[][] resultTable = new int[n + 1][w + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                if (j < p[i - 1]) {
                    resultTable[i][j] = resultTable[i - 1][j];
                } else {
                    resultTable[i][j] = Math.max(resultTable[i - 1][j], resultTable[i - 1][j - p[i - 1]] + g[i - 1]);
                }
            }
        }

        return resultTable[n][w];
    }

    public static int getBestGoldMiningOptimizedWithLessSpace(int w, int[] g, int[] p) {
        int[] results = new int[w + 1];

        for (int i = 1; i <= g.length; i++) {
            for (int j = w; j >= 1; j--) {
                if (j >= p[i - 1]) {
                    results[j] = Math.max(results[j], results[j - p[i - 1]] + g[i - 1]);
                }
            }
        }

        return results[w];
    }

}
