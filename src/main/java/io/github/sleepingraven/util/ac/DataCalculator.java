package io.github.sleepingraven.util.ac;

import io.github.sleepingraven.util.advanced.InverseElement;

import static io.github.sleepingraven.util.ac.Constants.MOD;

/**
 * @author Carry
 * @since 2020/11/25
 */
public class DataCalculator {
    
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    
    public static long[][] combinationNumsInLong(int n) {
        long[][] c = new long[n][n];
        c[0][0] = 1;
        for (int i = 1; i < n; i++) {
            c[i][0] = 1;
            for (int j = 1; j < n; j++) {
                c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % MOD;
            }
        }
        return c;
    }
    
    /**
     * combination number c(n, m) modulo mod
     */
    public static int c(int n, int m, int mod) {
        long ans = 1;
        for (int i = 0; i < m; i++) {
            ans = ans * (n - i) % mod;
            ans = ans * InverseElement.inv(i + 1, mod) % mod;
        }
        return (int) ans;
    }
    
    /**
     * combination number c(n, m). (Pay attention to overflow)
     */
    public static long c(int n, int m) {
        long pro = 1;
        int j = n - m + 1;
        for (int i = 1; i <= m; i++) {
            pro *= j++;
            pro /= i;
        }
        return pro;
    }
    
    /**
     * 求 10 的整数次幂。
     * 取第 i 位数字：
     * int digital = num / decimalNum(i) % 10
     *
     * @return 共 digit 位的 10 的整数次幂的值
     */
    public static int decimalNum(int digit) {
        return (int) Math.pow(10, digit - 1);
    }
    
    /**
     * 除法向上取整。
     * a, b > 0
     * a+b <= MAX_VALUE
     */
    public static int divideCeiling(int a, int b) {
        return (a + (b - 1)) / b;
    }
    
    public static int qpow(long a, long b, int mod) {
        long ret = 1;
        a %= mod;
        while (b != 0) {
            if ((b & 1) == 1) {
                ret = ret * a % mod;
            }
            a = a * a % mod;
            b >>= 1;
        }
        return (int) ret;
    }
    
    public static double qpow(double a, long b) {
        double ans = 1;
        while (b != 0) {
            if ((b & 1) == 1) {
                ans *= a;
            }
            a *= a;
            b >>= 1;
        }
        return ans;
    }
    
    public static int qmul(long a, long b, int mod) {
        a %= mod;
        b %= mod;
        long res = 0;
        while (b != 0) {
            if ((b & 1) == 1) {
                res += a;
                if (res > mod) {
                    res -= mod;
                }
            }
            a += a;
            if (a > mod) {
                a -= mod;
            }
            b >>>= 1;
        }
        return (int) res;
    }
    
}
