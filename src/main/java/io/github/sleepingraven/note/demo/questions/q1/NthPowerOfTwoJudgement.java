package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 给出一个正整数，判断是不是2的整数次幂
 *
 * @author 10132
 */
public class NthPowerOfTwoJudgement {

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         循环比较
     *         <p>O(log n)</p>
     *     </li>
     *     <li>
     *         位运算
     *         <p>O(1)</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        System.out.println(isPowerOf2(32));
        System.out.println(isPowerOf2(19));

        System.out.println(isPowerOf2Optimized(32));
        System.out.println(isPowerOf2Optimized(19));
    }

    private static boolean isPowerOf2(int num) {
        for (int i = 1; i <= num; i <<= 1) {
            if (i == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPowerOf2Optimized(int num) {
        return (num & num - 1) == 0;
    }

}
