package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/21
 */
public class No1177_三角形 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while (t-- != 0) {
            int n = scanner.nextInt();
            ArrayList<Integer>[] lists = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                lists[i] = new ArrayList<>(i);
                for (int j = 0; j <= i; j++) {
                    lists[i].add(scanner.nextInt());
                }
            }
            
            ArrayList<Integer> res = lists[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                for (int j = 0; j <= i; j++) {
                    res.set(j, lists[i].get(j) + Math.max(res.get(j), res.get(j + 1)));
                }
            }
            
            System.out.println(res.get(0));
        }
    }
    
}
