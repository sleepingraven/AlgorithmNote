package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author 10132
 */
public class No1004_母牛的故事 {
    
    public static void main(String[] args) {
        final int n = 55;
        Scanner scanner = new Scanner(System.in);
        List<Integer> inputs = new ArrayList<>(n);
        int temp;
        while ((temp = scanner.nextInt()) != 0) {
            inputs.add(temp);
        }
        
        int[] motherCow = motherCow(n);
        for (int in : inputs) {
            System.out.println(motherCow[in]);
        }
    }
    
    private static int[] motherCow(final int n) {
        int[] motherCow = new int[n];
        
        for (int i = 1; i < n; i++) {
            if (i < 4) {
                motherCow[i] = i;
            } else {
                motherCow[i] = motherCow[i - 1] + motherCow[i - 3];
            }
        }
        
        return motherCow;
    }
    
}
