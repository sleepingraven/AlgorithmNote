package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1167_矩阵 {
    private static LinkedList<Integer>[] lists;
    private static int res;
    
    /**
     * 因为
     * <blockquote>
     * <pre>
     * 1 0 2     0 1 2
     * 4 3 5     3 4 5
     * 7 6 8  ,  6 7 8
     * </pre>
     * </blockquote>
     * 是一样的，
     * 所以不用移动首行
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        
        while ((n = scanner.nextInt()) != -1) {
            lists = new LinkedList[n];
            res = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                lists[i] = new LinkedList<>();
                for (int j = 0; j < n; j++) {
                    lists[i].add(scanner.nextInt());
                }
            }
            
            moveAndCalc(0);
            System.out.println(res);
        }
    }
    
    private static void moveAndCalc(int line) {
        if (line == lists.length - 1) {
            int m = getMax();
            if (res > m) {
                res = m;
            }
        } else {
            for (int i = 0; i < lists.length; i++) {
                moveAndCalc(line + 1);
            }
        }
        move(line);
    }
    
    private static void move(int line) {
        lists[line].addFirst(lists[line].removeLast());
    }
    
    private static int getMax() {
        int m = Integer.MIN_VALUE;
        for (int i = 0; i < lists.length; i++) {
            int n = 0;
            for (LinkedList<Integer> list : lists) {
                n += list.get(i);
            }
            if (m < n) {
                m = n;
            }
        }
        return m;
    }
    
}
