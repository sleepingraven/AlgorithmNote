package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/1
 */
public class No1226_方砖问题 {
    
    /**
     * +++--
     * +++--
     * +++*#
     * --*--
     * --#--
     * <p>
     * +++++--
     * +++++--
     * +++++__
     * +++++__
     * +++++*#
     * --__*--
     * --__#--
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while (t-- > 0) {
            int n = scanner.nextInt();
            if (n % 2 == 0) {
                System.out.println(4);
            } else {
                System.out.println((6 + (n - 3)));
            }
        }
    }
    
}
