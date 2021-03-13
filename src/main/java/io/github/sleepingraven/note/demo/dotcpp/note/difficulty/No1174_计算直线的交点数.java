package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/11/20
 */
public class No1174_计算直线的交点数 {
    private static TreeSet<Integer>[] sign = new TreeSet[21];
    
    static {
        // i条直线时
        for (int i = 1; i < sign.length; i++) {
            sign[i] = new TreeSet<>();
            sign[i].add(0);
            // 规划的方法是：i - j条直线平行，j条直线不平行于i - j条直线
            for (int j = 1; j < i; j++) {
                // j条直线内部交点数
                for (int k = 0; k <= j * (j - 1) / 2; k++) {
                    if (sign[j].contains(k)) {
                        // 总的交点数
                        sign[i].add(k + (i - j) * j);
                    }
                }
            }
        }
    }
    
    /**
     * <ol>
     *     <li>
     *         将n条直线排成一个序列，直线2和直线1最多只有1个交点，直线3和直线1和直线2最多有2个交点......直线n和其他n - 1条最多有n - 1个交点，即n条互不平行且无三线共点的直线的最多交点数:
     *         <blockquote>
     *             <pre>max = 1 + 2 + ... + (n - 1) = n * (n - 1) / 2</pre>
     *         </blockquote>
     *     </li>
     *     <li>
     *         假设有n = a + b条直线（即分成2组），则：
     *         <blockquote>
     *             <pre>交点数 = a内交点数 + b内交点数 + a、b间的交点数</pre>
     *         </blockquote>
     *     </li>
     *     <li>
     *         m条直线的交点数
     *       = (m - r)条平行线与r条直线交叉的交点数 + r条直线本身的交点数
     *       = (m - r) * r + r条之间本身的交点数（0<=r&lt;m）
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            
            for (int i = 0; i <= n * (n - 1) / 2; i++) {
                if (sign[n].contains(i)) {
                    System.out.print(i + " ");
                }
            }
            
            System.out.println();
        }
    }
    
}
