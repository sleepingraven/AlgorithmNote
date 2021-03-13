package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/1
 */
public class No1228_最多约数问题 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        
        System.out.println(fun1(a, b));
        System.out.println(fun2(a, b));
    }
    
    /**
     * 类似素数筛的思路<p>
     * 如果n是一个数的约数，那么存在x使n乘x等于这个数，就可以求出以n为因数的其他数<p>
     * 时间复杂度：O(n*log n)
     */
    private static int fun1(int a, int b) {
        if (b == 1) {
            return 1;
        }
        
        // 不包含除数1和本身i
        int max = 0;
        int[] records = new int[5000000 + 1];
        for (int i = 2; i <= b; i++) {
            if (i >= a) {
                max = Math.max(records[i], max);
            }
            for (int j = i + i; j <= b; j += i) {
                records[j]++;
            }
        }
        
        return max + 2;
    }
    
    /**
     * 首先，考虑如何求解一个数有多少个约数。1的约数只有1个；其他的数字，对其进行质因数分解，
     * 可以得到 a = b1^c1 * b2^c2 * b3^c3 * ...如下结果，根据排列组合原理可得其约数个数为(c1+1)*(c2+1)*(c3+1)*...
     * <p>
     * 而对于一个数质因数分解的效率，最暴力的方法为sqrt(n)，就算进行优化，也只能接近log n，
     * 对于本题的数据范围而言，n*log n（n=500w）时间来说效率还是太低。
     * 所以我们需要O(n)的算法。
     * <p>
     * 考虑的质因数分解实质上是质数的提取过程，我们可以利用线性素数筛法来O(n)的计算n里面的素数个数；
     * 同时，线性筛的原理或者说过程的操作，其实恰好符合了约数个数的求解过程。
     * <p>
     * 对于
     * m = b1^c1 * b2^c2 * b3^c3
     * n = b1^(c1+1) * b2^c2 * b3^c3
     * 上述两个数的约数关系就是 div(n) = div(m) * (c1+2) / (c1+1)
     * 所以可以直接在线性筛的过程中求解出每个数的约数个数，然后就是循环求最大值了。
     * <p>
     * 注意事项：特判 1, 1 这组测试数据
     */
    private static int fun2(int a, int b) {
        int[] f = new int[5000000 + 1];
        int[] p = new int[5000000 + 1];
        int[] d = new int[5000000 + 1];
        int max = 1, t = 0;
        for (int i = 2; i <= b; i++) {
            if (f[i] == 0) {
                p[t++] = i;
                f[i] = d[i] = 2;
            }
            for (int j = 0; j < t && i * p[j] <= b; j++) {
                f[i * p[j]] = 2;
                d[i * p[j]] = d[i] * 2;
                if (i % p[j] == 0) {
                    f[i * p[j]] = f[i] + 1;
                    d[i * p[j]] = d[i] / f[i] * f[i * p[j]];
                    break;
                }
            }
            if (i >= a) {
                max = Math.max(max, d[i]);
            }
        }
        
        return max;
    }
    
}
