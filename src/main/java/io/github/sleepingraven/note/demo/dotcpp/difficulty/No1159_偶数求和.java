package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1159_偶数求和 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        while (scanner.hasNext()) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            int i = 0;
            for (int b = m + 1; (i += m) <= n; b += 2 * m) {
                System.out.print(b + " ");
            }
            if ((i -= m) < n) {
                i++;
                System.out.print(i + n);
            }
            System.out.println();
        }
    }
    
}
