package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/27
 */
public class No1188_做幻方 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final int n = scanner.nextInt();
            if (n == 0) {
                break;
            }
            
            int[][] res = new int[n][n];
            int val = 1;
            int i = n - 1;
            int j = n / 2;
            do {
                res[i][j] = val;
                
                if (res[(i + 1) % n][(j + 1) % n] == 0) {
                    i = (i + 1) % n;
                    j = (j + 1) % n;
                } else {
                    if (--i < 0) {
                        i += n;
                    }
                }
            } while (val++ != n * n);
            
            // 获得每一列最大值，以确定占几位
            int[] w = new int[n];
            val -= n / 2 + 1;
            for (int k = 0; k < n; k++) {
                // 获得每一列最大值
                w[k] = val;
                // 确定占几位
                int d = 0;
                do {
                    d++;
                } while ((w[k] /= 10) != 0);
                // 位数+1
                w[k] = d + 1;
                
                if (++val > n * n) {
                    val -= n;
                }
            }
            // 首位-1
            w[0] -= 1;
            
            StringBuilder stringBuilder = new StringBuilder();
            for (int[] l : res) {
                for (int k = 0; k < n; k++) {
                    int v = l[k];
                    stringBuilder.append(String.format("%" + w[k] + "d", v));
                }
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder);
        }
    }
    
}
