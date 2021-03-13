package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/31
 */
public class No1217_换位置 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n-- > 0) {
            int m = scanner.nextInt();
            System.out.println(fun1(m));
            System.out.println(fun3(m));
            System.out.println(fun2(m));
        }
    }
    
    private static long fun1(int m) {
        if ((m & 0x1) != 0) {
            return ((long) m - 1) * (m - 1) / 4;
        } else {
            return ((long) m / 2) * (m / 2 - 1);
        }
    }
    
    /**
     * 想要给一个环逆序排序，首先就需要把环分成两部分，两条直链，
     * 对于每条直链求逆序交换的次数很容易得出公式（冒泡排序的最差情况）n*(n-1)/2，
     * 这个时候就需要考虑这个环是奇数环还是偶数环了,然后对两条链求和相加即可。
     */
    private static long fun2(int m) {
        int half = m / 2;
        long sum = ((half - 1) * half) / 2;
        if ((m & 0x1) != 0) {
            sum += sum + half;
        } else {
            sum += sum;
        }
        return sum;
    }
    
    /**
     * 人数：1 2 3 4 5 6 7  8  9 10
     * 次数：0 0 1 2 4 6 9 12 16 20
     */
    private static long fun3(int m) {
        if (m <= 2) {
            return 0;
        } else {
            long res = 0;
            for (int i = ((m & 0x1) != 0) ? 1 : 2; i < m; i += 2) {
                res += i;
            }
            return res;
        }
    }
    
}
