package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/17
 */
public class No1127_C语言训练_尼科彻斯定理 {
    
    /**
     * <blockquote>
     * <pre>
     * 1^3 =  1                              1 = 1^2 + 0
     * 2^3 =  3 +  5                         5 = 2^2 + 1     3 =  1 +  2
     * 3^3 =  7 +  9 + 11                   11 = 3^2 + 2     7 =  5 +  2
     * 4^3 = 13 + 15 + 17 + 19              19 = 4^2 + 3    13 = 11 +  2
     * 5^3 = 21 + 23 + 25 + 27 + 29         29 = 5^2 + 4    21 = 19 +  2
     * 6^3 = 31 + 33 + 35 + 37 + 39 + 41    41 = 6^2 + 5    31 = 29 +  2
     * </pre>
     * </blockquote>
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.print(n + "*" + n + "*" + n + "=" + (int) Math.pow(n, 3) + "=");
        
        int odd = (int) Math.pow(n - 1, 2) + (n - 2) + 2;
        for (int i = 1; i < n; i++) {
            System.out.print(odd + "+");
            odd += 2;
        }
        System.out.println(odd);
    }
    
}
