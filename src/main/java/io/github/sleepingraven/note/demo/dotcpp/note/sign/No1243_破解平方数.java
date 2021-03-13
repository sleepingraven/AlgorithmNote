package io.github.sleepingraven.note.demo.dotcpp.note.sign;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1243_破解平方数 {
    private static final int[] primeNums = new int[100];
    
    static {
        primeNums[0] = 2;
        int index = 1;
        boolean[] notPrimeFlags = new boolean[primeNums.length + 1];
        for (int i = 3; i < notPrimeFlags.length; i += 2) {
            if (!notPrimeFlags[i]) {
                primeNums[index++] = i;
            }
            for (int j = i * i; j < notPrimeFlags.length; j += i) {
                notPrimeFlags[j] = true;
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        int m = scanner.nextInt();
        
        // 质因式分解
        int[][] recs = new int[m][t];
        for (int i = 0; i < m; i++) {
            int num = scanner.nextInt();
            for (int j = 0; j < t; j++) {
            }
        }
    }
    
}
