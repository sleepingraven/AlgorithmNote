package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/22
 */
public class No1178_三进制小数 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] ns = scanner.nextLine().split("/");
            double n = Double.parseDouble(ns[0]) / Double.parseDouble(ns[1]);
            
            int[] res = new int[11];
            for (int i = 0; i < res.length; i++) {
                n *= 3;
                int m = (int) n;
                res[i] = m;
                n -= m;
            }
            
            if (res[res.length - 1] >= 2) {
                res[res.length - 2]++;
            }
            for (int i = res.length - 2; i > 0; i--) {
                if (res[i] == 3) {
                    res[i] = 0;
                    res[i - 1] += 1;
                } else {
                    break;
                }
            }
            
            StringBuilder stringBuilder = new StringBuilder(".");
            for (int i : res) {
                stringBuilder.append(i);
            }
            System.out.println(stringBuilder.substring(0, 11));
        }
    }
    
}
