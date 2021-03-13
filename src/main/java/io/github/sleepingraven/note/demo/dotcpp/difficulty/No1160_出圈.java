package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1160_出圈 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = (scanner.nextInt() - 1) % n;
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                list.add(i);
            }
            
            for (int i = m; list.size() > 1; i += m) {
                if (i >= list.size()) {
                    i %= list.size();
                }
                list.remove(i);
            }
            
            System.out.println(list.get(0) + 1);
        }
    }
    
}
