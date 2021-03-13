package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/17
 */
public class No1131_C语言训练_斐波纳契数列 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = 1, b = 1, c;
        StringBuilder stringBuilder = new StringBuilder(1 + " ");
        
        if (n > 1) {
            stringBuilder.append(1).append(" ");
        }
        for (int i = 3; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
            stringBuilder.append(c).append(" ");
        }
        
        System.out.println(stringBuilder);
    }
    
}
