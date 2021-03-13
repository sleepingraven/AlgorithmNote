package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/10
 */
public class No1109_Hanoi双塔问题 {
    private final static BigDecimal BD_1 = BigDecimal.valueOf(1);
    private final static BigDecimal BD_2 = BigDecimal.valueOf(2);
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        System.out.println(hanoi(BigDecimal.valueOf(n)).multiply(BD_2));
    }
    
    private static BigDecimal hanoi(BigDecimal n) {
        if (n.equals(BigDecimal.valueOf(1))) {
            return n;
        }
        return hanoi(n.subtract(BD_1)).multiply(BD_2).add(BD_1);
    }
    
}
