package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/16
 */
public class No1111_Cylinder {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double w, l;
        while ((w = scanner.nextInt()) != 0 && (l = scanner.nextInt()) != 0) {
            double r = w / (2 * Math.PI);
            double result1 = Math.PI * r * r * (l - 2 * r);
            
            r = Math.min(l / (2 * Math.PI + 2), w / 2);
            double result2 = Math.PI * r * r * w;
            
            System.out.printf("%.3f\n", Math.max(result1, result2));
        }
    }
    
}
