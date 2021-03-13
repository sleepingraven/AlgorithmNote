package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/27
 */
public class No1186_倒杨辉三角形 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int[][] ns = new int[n][n];
            ns[0][0] = 1;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j <= i; j++) {
                    ns[i][j] = ns[i - 1][j];
                    if (j - 1 >= 0) {
                        ns[i][j] += ns[i - 1][j - 1];
                    }
                }
            }
            
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n - 1; i >= 0; i--) {
                for (int j = 1; j < n - i; j++) {
                    stringBuilder.append("   ");
                }
                for (int j = 0; j <= i; j++) {
                    stringBuilder.append(String.format("%3d", ns[i][j])).append("   ");
                }
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder);
        }
    }
    
}
