package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1235_检查金币 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            n -= 265716;
            for (int i = 0; i < 10; i++) {
                System.out.print(9 + n % 3);
                System.out.print(i < 9 ? " " : "\n");
                n /= 3;
            }
        }
    }
    
}
