package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/3
 */
public class No1258_连续自然数和 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int m = scanner.nextInt();
            fun1(m);
            fun2(m);
            fun3(m);
            fun00(m);
            fun01(m);
        }
    }
    
    /**
     * 暴力破解
     */
    private static void fun1(int m) {
        for (int i = 1; i <= m / 2; i++) {
            int sum = i;
            int j = i;
            do {
                j++;
                sum += j;
            } while (sum < m);
            if (sum == m) {
                System.out.println(String.format("%d %d", i, j));
            }
        }
        System.out.println();
    }
    
    /**
     * 涉及到数论。使用等差数列公式。
     * <p>
     * 设第一项为left，最后一项为right，连续自然数和为m。
     * 使用等差数列求和可得：(left + right) * (right - left + 1) = 2 * M。
     * 再假设:k1 = right - left + l，k2 = left + right（k1 < k2）
     * 此时把问题转化成了求两个整数k1，k2其乘积是2 * m。
     * <p>
     * 解方程可得：left = (k2 - k1 + 1) / 2，right = (k1 + k2 - 1) / 2。
     * 值得注意的是因为left和right是自然数，这两个方程有解，当且仅当k1和k2一个奇数一个偶数，
     * 也就是说要满足：k1 % 2 != k2 % 2，对于k1从sqrt(2 * m)到1枚举即可。
     * 同时要注意left必须小于right。
     */
    private static void fun2(int m) {
        m = 2 * m;
        for (int k1 = (int) Math.sqrt(m); k1 >= 1; k1--) {
            if (m % k1 == 0) {
                int k2 = m / k1;
                if (k1 % 2 != k2 % 2) {
                    int left = (k2 - k1 + 1) / 2;
                    int right = (k1 + k2 - 1) / 2;
                    if (left < right) {
                        System.out.println(String.format("%d %d", left, right));
                    }
                }
            }
        }
        System.out.println();
    }
    
    /**
     * 涉及到数论。使用等差数列公式。
     * <p>
     * 设第一项为x，最后一项为x + n，连续自然数和为m。
     * 使用等差数列求和可得：
     * (2 * x + n) * (n + 1) / 2 = m
     * 推导出(2 * x + n) * (n + 1) = 2 * m，则n从sqrt(2 * m)开始到1枚举即可。那么x为多少呢？请看：
     * ==> 2 * x + n = 2 * m / (n + 1)
     * ==> 2 * x = 2 * m / (n + 1) - n
     * ==> x = (2 * m / (n + 1) - n ) / 2
     * 又因为x为自然数，所以2 * m一定能被n + 1整除，即2 * m % (n + 1)为零，
     * 另外，同理：(2 * m / (n + 1) - n) % 2也为0。
     */
    private static void fun3(int m) {
        m = 2 * m;
        for (int n = (int) Math.sqrt(m); n >= 1; n--) {
            if (m % (n + 1) == 0 && (m / (n + 1) - n) % 2 == 0) {
                int x = (m / (n + 1) - n) / 2;
                if (x > 0) {
                    System.out.println(String.format("%d %d", x, x + n));
                }
            }
        }
        System.out.println();
    }
    
    private static void fun00(int m) {
        // max = min + i - 1
        // m = (min + max) * i / 2 = (2 * min + n - 1) * n / 2
        int k = (int) Math.sqrt(2 * m);
        for (int i = k; i >= 2; i--) {
            if ((2 * m) % i == 0) {
                int min = 2 * m / i - (i - 1);
                if (min % 2 == 0) {
                    System.out.printf("%d %d\n", min / 2, min / 2 + i - 1);
                }
            }
        }
        System.out.println();
    }
    
    /**
     * 思路：m 分解成连续 n 个自然数相加，那么这 n 个数的平均数 m/n 应该是这些数中心的位置，或者
     * 中心附近。那么第一个数就是 m/n- n/2，这个值要大于 0 。
     * ==>>    2*m > n*n
     * ==>>    n < sqrt(2*m)
     * 知道了 n 的值，就能知道第一项的值
     * 用low 表示第一项
     * low = m/n-n/2;
     * 考虑到整数除法，有可能除不尽的情况，low 有可能等于 m/n-n/2+1
     * 最后一项的值 high = low +n-1
     */
    private static void fun01(int m) {
        long low, high;
        long n;
        //这里是为了处理强制转换损失的精度
        long nMax = (long) (1 + Math.sqrt(m * 2));
        
        for (n = nMax; n >= 2; n--) {
            //用循环来处理low可能出现的情况
            for (low = m / n - n / 2; low <= m / n - n / 2 + 1; low++) {
                //这里是等差数列求和的一个变化
                if (2 * m == low * 2 * n + n * (n - 1)) {
                    high = low + n - 1;
                    System.out.println(String.format("%d %d", low, high));
                    //一个n值，对应一个low，所以一旦求出，就跳出循环了
                    break;
                }
            }
        }
        
        System.out.println();
    }
    
}
