package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author 10132
 */
public class No1100_采药 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        int m = scanner.nextInt();
        int[] gs = new int[m];
        int[] ts = new int[m];
        for (int i = 0; i < m; i++) {
            ts[i] = scanner.nextInt();
            gs[i] = scanner.nextInt();
        }
        
        int[] res = new int[t + 1];
        for (int i = 1; i <= gs.length; i++) {
            for (int j = t; j >= 1; j--) {
                if (j >= ts[i - 1]) {
                    res[j] = Math.max(res[j], res[j - ts[i - 1]] + gs[i - 1]);
                }
            }
        }
        System.out.println(res[t]);
    }
    
}
