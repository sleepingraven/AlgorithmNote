package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1168_简单计算 {
    private static int n;
    private static double a0;
    private static double an1;
    private static double[] c;
    
    /**
     * <ol>
     *     <li>
     *         假设n=5
     *         <blockquote>
     *             <pre>
     * 2 * a[1] = a[0] + a[2] - 2 * c[1]
     * 2 * a[2] = a[1] + a[3] - 2 * c[2]
     * 2 * a[3] = a[2] + a[4] - 2 * c[3]
     * 2 * a[4] = a[3] + a[5] - 2 * c[4]
     * 2 * a[5] = a[4] + a[6] - 2 * c[5]</pre>
     *         </blockquote>
     *     </li>
     *     <li>
     *         把以上式子加起来，可消元得到：
     *        <blockquote>
     *            <pre>
     * a[1] + a[5] = a[0] + a[6] - 2 * (sum(1-5))，
     * 这里sum(1-5) = c[1] + c[2] + ... + c[5]</pre>
     *        </blockquote>
     *        想办法把a[5]也消去
     *     </li>
     *     <li>
     *         令n=4，把式子加起来，可消元得到：
     *        <blockquote>
     *            <pre>
     * a[1] + a[4] = a[0] + a[5] - 2 * (sum(1-4))</pre>
     *        </blockquote>
     *     </li>
     *     <li>
     *         再令n=3，把式子加起来，可消元得到：
     *        <blockquote>
     *            <pre>
     * a[1] + a[3] = a[0] + a[4] - 2 * (sum(1-3))</pre>
     *        </blockquote>
     *     </li>
     *     <li>
     *         直到n=1：
     *        <blockquote>
     *            <pre>
     * a[1] + a[1] = a[0] + a[2] - 2 * (sum(1-1))</pre>
     *        </blockquote>
     *     </li>
     *     <li>
     *         把以上得到的所有式子罗列出来：
     *        <blockquote>
     *            <pre>
     * a[1] + a[5] = a[0] + a[6]- 2*(sum(1-5))
     * a[1] + a[4] = a[0] + a[5]- 2*(sum(1-4))
     * a[1] + a[3] = a[0] + a[4]- 2*(sum(1-3))
     * a[1] + a[2]=  a[0] + a[3]- 2*(sum(1-2))
     * a[1] + a[1] = a[0] + a[2]- 2*(sum(1-1))</pre>
     *        </blockquote>
     *     </li>
     *     <li>
     *         再把这些式子加起来，消元得到：
     *         <blockquote>
     *             <pre>
     * 6 * a[1] = 5 * a[0] + a[6] - 2 * (sc(1-5))，
     * 其中sc(1-5) = sum(1-1) + sum(1-2) + ... + sum(1-5)，即sc(1-5) = c[1] + (c[1] + c[2])+ ... + (c[1] + c[2] + ... + c[5])</pre>
     *         </blockquote>
     *     </li>
     *     <li>
     *         最终：
     *         <blockquote>
     *             <pre>
     * a[1] = (5 * a[0] + a[6] - 2 * (sc(1-5))) / 6</pre>
     *         </blockquote>
     *     </li>
     *     <li>
     *         据归纳法当n=n时；
     *         <blockquote>
     *             <pre>
     * a[1] = (n * a[0] + a[n + 1] - 2 * (sc(1-n))) / (n + 1)</pre>
     *         </blockquote>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        a0 = scanner.nextDouble();
        an1 = scanner.nextDouble();
        c = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            c[i] = scanner.nextDouble();
        }
        
        double res = calc();
        System.out.println(String.format("%.2f", res));
    }
    
    private static double calc() {
        double sc = 0, sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += c[i];
            sc += sum;
        }
        
        return (n * a0 + an1 - 2 * sc) / (n + 1);
    }
    
}
