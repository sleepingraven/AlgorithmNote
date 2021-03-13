package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/12/31
 */
public class No1213_幸运儿 {
    
    /**
     * 每循环一轮都从1重新开始，与出圈问题有少许不同
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                list.add(i);
            }
            for (int i = 1; list.size() > 2; i++) {
                if (i >= list.size()) {
                    i = 1;
                }
                System.out.print(list.get(i) + " ");
                list.remove(i);
            }
            System.out.println();
            TreeSet<Integer> set = new TreeSet<>(list);
            for (int i : set) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
    
}
