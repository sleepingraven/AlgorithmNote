package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/1
 */
public class No1225_文科生的悲哀 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n <= 2) {
            System.out.println(1);
            return;
        }
        int[] res = new int[n + 1];
        // 政治
        res[1] = 1;
        // 历史
        res[2] = 1;
        // 政治、地理
        // res[3] = 2;
        // 历史 + 历史、综合
        // res[4] = 1 + 2;
        // 2 + 2 + 地理
        // res[5] = 2 + 2 + 1;
        // res[6] = 3 + 3 + 2;
        // res[7] = 5 + 5 + 3;
        for (int i = 3; i <= n; i++) {
            res[i] = (res[i - 2] + res[i - 1]) % 7654321;
        }
        System.out.println(res[n]);
    }
    
}
