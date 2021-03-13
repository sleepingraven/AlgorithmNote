package io.github.sleepingraven.note.demo.questions.q2;

import java.math.BigInteger;

/**
 * @author Carry
 * @since 2020/1/17
 */
public class NarcissisticNumber {
    private static final BigInteger[] POW_21 = new BigInteger[10];

    static {
        POW_21[0] = BigInteger.ZERO;
        POW_21[1] = BigInteger.ONE;
        for (int i = 2; i < 10; i++) {
            BigInteger bi = BigInteger.valueOf(i);
            BigInteger bigInteger = BigInteger.ONE;
            for (int j = 0; j < 21; j++) {
                bigInteger = bigInteger.multiply(bi);
            }
            POW_21[i] = bigInteger;
        }
    }

    /**
     * 一个N位的十进制正整数，如果它的每个位上的数字的N次方的和等于这个数本身，则称其为花朵数。
     * 例如：
     * 当N=3时，153就满足条件，因为 1^3 + 5^3 + 3^3 = 153，这样的数字也被称为水仙花数（其中，“^”表示乘方，5^3表示5的3次方，也就是立方）。
     * 当N=4时，1634满足条件，因为 1^4 + 6^4 + 3^4 + 4^4 = 1634。
     * 当N=5时，92727满足条件。
     * 实际上，对N的每个取值，可能有多个数字满足条件。
     * 449177399146038697307
     * 128468643043731391252
     * 程序的任务是：求N=21时，所有满足条件的花朵数。
     * 注意：这个整数有21位，它的各个位数字的21次方之和正好等于这个数本身。
     * 如果满足条件的数字不只有一个，请从小到大输出所有符合条件的数字，每个数字占一行。因为这个数字很大，请注意解法时间上的可行性。要求程序在3分钟内运行完毕。
     */
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        int[] c = new int[10];
        fun(c, 0, 21);
        System.out.println(System.currentTimeMillis() - l);
    }

    /**
     * 递归方法
     *
     * @param c
     *         缓存，每个数字出现的次数
     * @param k
     *         当前位置，0-9
     * @param remain
     *         剩余次数，0-21
     */
    private static void fun(int[] c, int k, int remain) {
        if (remain == 0) {
            judgeAndPrint(c);
            return;
        }
        if (k == 9) {
            c[9] = remain;
            judgeAndPrint(c);
            c[9] = 0;
            return;
        }

        for (int i = 0; i <= remain; i++) {
            c[k] = i;
            fun(c, k + 1, remain - i);
        }
        c[k] = 0;
    }

    private static void judgeAndPrint(int[] c) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < 10; i++) {
            if (c[i] > 0) {
                sum = sum.add(POW_21[i].multiply(BigInteger.valueOf(c[i])));
            }
        }

        String sumString = sum.toString();
        if (sumString.length() != 21) {
            return;
        }
        int[] c2 = new int[10];
        for (char ch : sumString.toCharArray()) {
            c2[ch - '0']++;
        }
        for (int i = 0; i < 10; i++) {
            if (c[i] != c2[i]) {
                return;
            }
        }

        System.out.println(sum);
    }

}
