package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1233_核电站问题 {
    private static final long[] PRO = new long[41];
    
    static {
        PRO[0] = 1;
        PRO[1] = 2;
        PRO[2] = 4;
        PRO[3] = 7;
        for (int i = 4; i < PRO.length; i++) {
            PRO[i] = 2 * PRO[i - 1] - PRO[i - 3 - 1];
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            System.out.println(PRO[n]);
        }
    }
    
}
