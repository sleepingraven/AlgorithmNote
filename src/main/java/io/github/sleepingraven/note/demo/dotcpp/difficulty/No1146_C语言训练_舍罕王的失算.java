package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.math.BigDecimal;

/**
 * @author Carry
 * @since 2019/11/17
 */
public class No1146_C语言训练_舍罕王的失算 {
    private static final BigDecimal BD_2 = BigDecimal.valueOf(2);
    
    public static void main(String[] args) {
        BigDecimal bigDecimal = BigDecimal.valueOf(1);
        BigDecimal sum = BigDecimal.valueOf(1);
        for (int i = 1; i < 64; i++) {
            bigDecimal = bigDecimal.multiply(BD_2);
            sum = sum.add(bigDecimal);
        }
        // System.out.println(sum);
        System.out.printf("%.0f", Math.pow(2, 64) - 1);
    }
    
}
