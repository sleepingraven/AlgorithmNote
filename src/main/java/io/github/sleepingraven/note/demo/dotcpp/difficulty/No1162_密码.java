package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1162_密码 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String string = scanner.nextLine();
            int validation = 0;
            if (string.length() >= 8 && string.length() <= 16) {
                if (string.matches(".*[A-Z].*")) {
                    validation++;
                }
                if (string.matches(".*[a-z].*")) {
                    validation++;
                }
                if (string.matches(".*\\d.*")) {
                    validation++;
                }
                if (string.matches(".*[~!@#$%^].*")) {
                    validation++;
                }
            }
            
            System.out.println(validation >= 3 ? "YES" : "NO");
        }
    }
    
}
