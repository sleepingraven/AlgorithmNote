package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1253_老王赛马 {
    private static final int[] RES = new int[26];
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            if (n == 0) {
                break;
            }
            
            int[] ms = new int[n];
            for (int i = 0; i < n; i++) {
                ms[i] = scanner.nextInt();
            }
            int[] as = new int[n];
            for (int i = 0; i < n; i++) {
                as[i] = scanner.nextInt();
            }
            Arrays.sort(ms);
            Arrays.sort(as);
            
            int count = 0;
            for (int i = 0, j = 0; i < n; i++) {
                if (ms[i] > as[j]) {
                    count++;
                    j++;
                }
            }
            System.out.println(count > n / 2 ? "YES" : "NO");
        }
    }
    
}
