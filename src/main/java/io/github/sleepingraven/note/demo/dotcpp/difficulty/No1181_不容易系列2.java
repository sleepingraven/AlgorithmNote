package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Carry
 * @since 2019/11/22
 */
public class No1181_不容易系列2 {
    private static final Map<Integer, BigDecimal> MAP = new TreeMap<>();
    
    static {
        MAP.put(1, BigDecimal.valueOf(0));
        MAP.put(2, BigDecimal.valueOf(1));
        
        for (int i = 3; i <= 20; i++) {
            MAP.put(i, BigDecimal.valueOf(i - 1).multiply(MAP.get(i - 1).add(MAP.get(i - 2))));
        }
    }
    
    /**
     * 错排公式
     * <ul>
     *     <li>
     *         错排公式定义<p>
     *         一段序列中一共有n个元素，那么这些元素共有n!种排列方式。假如在进行排列时，所有元素都不在原来的位置，那么称这个排列为错排。<p>
     *             错排数是指在一个有n个元素的序列中，有多少种排列方式是错排。
     *     </li>
     *     <li>
     *         错排公式
     *             <ol>
     *                 <li>
     *                     （常考点）递归关系：<p>
     *                         D(n) = (n - 1) (D(n-1) + D(n-2))，<p>
     *                         特别地有D(1)=0，D(2)=1
     *                 </li>
     *                 <li>
     *                     错排公式：<p>
     *                         D(n) = n! [(-1)^2 / 2! + … + (-1)^(n - 1) / (n - 1)! + (-1)^n / n!]，<p>
     *                         特别地有0!=1，1!=1
     *                 </li>
     *             </ol>
     *     </li>
     * </ul>
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            System.out.println(MAP.get(n));
        }
    }
    
}
