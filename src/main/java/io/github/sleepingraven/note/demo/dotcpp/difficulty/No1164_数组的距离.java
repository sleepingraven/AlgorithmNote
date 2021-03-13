package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1164_数组的距离 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < m; i++) {
            set.add(scanner.nextInt());
        }
        
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int in = scanner.nextInt();
            
            Integer lower = set.lower(in);
            if (lower != null && in - lower < res) {
                res = in - lower;
            }
            
            set = (TreeSet<Integer>) set.tailSet(in);
            if (set.isEmpty()) {
                break;
            }
            Integer higher = set.first();
            if (higher == in) {
                res = 0;
                break;
            }
            if (higher - in < res) {
                res = higher - in;
            }
        }
        
        System.out.println(res);
    }
    
}
