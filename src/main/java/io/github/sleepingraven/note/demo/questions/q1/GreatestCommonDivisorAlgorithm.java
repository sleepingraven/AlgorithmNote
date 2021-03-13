package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 求两个整数的最大公约数，尽量优化算法性能
 *
 * @author 10132
 */
public class GreatestCommonDivisorAlgorithm {

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         暴力枚举
     *         <p>如果传入10000和10001，要循环4999次</p>
     *         <p>O(b)</p>
     *     </li>
     *     <li>
     *         辗转相除法
     *         <p>又名欧几里得算法（Euclidean algorithm），是已知最古老的算法，可追溯至公元前300年前</p>
     *         <p>该算法基于一个定理：两个整数a和b（a>b），其最大公约数等于c = a % b和b的最大公约数</p>
     *         <p>当两数较大时，取模运算性能较差</p>
     *         <p>近似为O(log a)，但取模运算性能较差</p>
     *     </li>
     *     <li>
     *         更相减损术
     *         <p>出自《九章算术》</p>
     *         <P>原理很简单：两个整数a和b（a>b），其最大公约数等于c = a - b和b的最大公约数</P>
     *         <p>不够稳定，通过求差来递归，运算次数远大于辗转相除法。如计算10000和1的最大公约数，要递归9999次</p>
     *         <p>最大O(a)</p>
     *     </li>
     *     <li>
     *         结合优势，在更相减损术的基础上使用移位运算
     *         <ul>
     *             <li>a == b:      gcd(a, b) = a</li>
     *             <li>e(a), e(b):     gcd(a, b) = 2 * gcd(a / 2, b / 2)</li>
     *             <li>e(a), o(b):  gcd(a, b) = gcd(a / 2, b)</li>
     *             <li>o(a), e(b):  gcd(a, b) = gcd(a, b / 2)</li>
     *             <li>o(a), o(b):     gcd(a, b) = gcd(a - b, b)</li>
     *         </ul>
     *         <p>
     *             如求gcd(25, 10)：
     *             <ol>
     *                 <li>->gcd(25, 5)</li>
     *                 <li>->gcd(20, 5)</li>
     *                 <li>->gcd(10, 5)</li>
     *                 <li>->gcd(5, 5)</li>
     *             </ol>
     *         </p>
     *         <p>避免了取模运算，且性能稳定</p>
     *         <p>O(log a)</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        System.out.println("First implementation: ");
        System.out.println(getGcdEnum(25, 5));
        System.out.println(getGcdEnum(100, 80));
        System.out.println(getGcdEnum(27, 14));

        System.out.println("Second implementation: ");
        System.out.println(getGcdDiv(25, 5));
        System.out.println(getGcdDiv(100, 80));
        System.out.println(getGcdDiv(27, 14));

        System.out.println("Third implementation: ");
        System.out.println(getGcdSub(25, 5));
        System.out.println(getGcdSub(100, 80));
        System.out.println(getGcdSub(27, 14));

        System.out.println("Fourth implementation: ");
        System.out.println(getGcdBit(25, 5));
        System.out.println(getGcdBit(100, 80));
        System.out.println(getGcdBit(27, 14));
    }

    private static int getGcdEnum(int a, int b) {
        int big = Math.max(a, b);
        int small = Math.min(a, b);

        if (big % small == 0) {
            return small;
        }

        for (int i = small / 2; i > 1; i--) {
            if (small % i == 0 && big % i == 0) {
                return i;
            }
        }

        return 1;
    }

    private static int getGcdDiv(int a, int b) {
        int big = Math.max(a, b);
        int small = Math.min(a, b);

        if (big % small == 0) {
            return small;
        }

        return getGcdDiv(big % small, small);
    }

    private static int getGcdSub(int a, int b) {
        if (a == b) {
            return a;
        }

        int big = Math.max(a, b);
        int small = Math.min(a, b);

        return getGcdSub(big - small, small);
    }

    public static int getGcdBit(int a, int b) {
        if (a == b) {
            return a;
        }

        if ((a & 1) == 0 && (b & 1) == 0) {
            return getGcdBit(a >> 1, b >> 1) << 1;
        } else if ((a & 1) == 0 && (b & 1) != 0) {
            return getGcdBit(a >> 1, b);
        } else if ((a & 1) != 0 && (b & 1) == 0) {
            return getGcdBit(a, b >> 1);
        } else {
            int big = Math.max(a, b);
            int small = Math.min(a, b);
            return getGcdBit(big - small, small);
        }
    }

}
