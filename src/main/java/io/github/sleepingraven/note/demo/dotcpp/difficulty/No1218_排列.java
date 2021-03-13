package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/31
 */
public class No1218_排列 {
    
    /**
     * 题目中说的按从小到大是误导，不需要给数字排序
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n-- > 0) {
            int[] ns = new int[4];
            for (int i = 0; i < ns.length; i++) {
                ns[i] = scanner.nextInt();
            }
            // Arrays.sort(ns);
            
            fun(ns);
        }
    }
    
    private static void fun(int[] ns) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 如果重复跳出本次循环
                if (j == i) {
                    continue;
                }
                
                for (int k = 0; k < 4; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    
                    // 计算最后一个数字的下标
                    int l = 6 - i - j - k;
                    System.out.print(String.format("%d%d%d%d ", ns[i], ns[j], ns[k], ns[l]));
                }
            }
            
            System.out.println();
        }
        
        System.out.println();
    }
    
}
