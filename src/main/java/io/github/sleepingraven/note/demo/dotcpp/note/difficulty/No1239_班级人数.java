package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1239_班级人数 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            double m = scanner.nextDouble();
            double n = scanner.nextDouble();
            m /= 100;
            n /= 100;
            
            System.out.println(fun1(m, n));
            System.out.println(fun2(m, n));
            System.out.println(fun3(m, n));
        }
    }
    
    /**
     * m%x一定小于y，n%x大于y。
     * 而y是一个整数，所以要求出最小的x，则必须m%x与n%x之间恰好存在一个整数y，
     * 就可以表示为：(int) x * m% - (int) x * n% == 1。
     */
    private static int fun1(double m, double n) {
        for (int i = 1; true; i++) {
            if ((int) (i * n) - (int) (i * m) == 1) {
                return i;
            }
        }
    }
    
    private static int fun2(double m, double n) {
        for (int i = 1; ; i++) {
            if ((int) (i * n) > (int) (i * m)) {
                return i;
            }
        }
    }
    
    private static int fun3(double m, double n) {
        int i = 1, j = 1;
        while (true) {
            double s = (double) i / j;
            if (s > n) {
                j++;
            } else if (s < m) {
                i++;
            } else {
                return j;
            }
        }
    }
    
}
