package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author 10132
 */
public class No1103_开心的金明 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int price, value;
        
        int[] res = new int[n + 1];
        for (int i = 0; i < m; i++) {
            price = scanner.nextInt();
            value = scanner.nextInt();
            for (int j = n; j >= 1; j--) {
                if (j >= price) {
                    res[j] = Math.max(res[j], res[j - price] + value * price);
                }
            }
        }
        
        System.out.println(res[n]);
    }
    
}
