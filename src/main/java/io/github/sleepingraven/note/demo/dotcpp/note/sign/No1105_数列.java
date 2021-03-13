package io.github.sleepingraven.note.demo.dotcpp.note.sign;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author 10132
 */
public class No1105_数列 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigDecimal k = new BigDecimal(scanner.nextInt());
        int n = scanner.nextInt();
        
        BigDecimal result = new BigDecimal(0);
        BigDecimal temp = new BigDecimal(1);
        for (int i = 1; i <= n; i <<= 1) {
            if ((n & i) > 0) {
                result = result.add(temp);
            }
            temp = temp.multiply(k);
        }
        
        System.out.println(result);
    }
    
}
