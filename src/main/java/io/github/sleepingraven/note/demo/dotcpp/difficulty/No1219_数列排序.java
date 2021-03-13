package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/31
 */
public class No1219_数列排序 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n-- > 0) {
            ArrayList<Integer> ns = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                ns.add(scanner.nextInt());
            }
            int curr = 1;
            for (int i = 1; i < ns.size(); i++) {
                Integer num = ns.remove(curr);
                if (num > ns.get(0)) {
                    ns.add(num);
                } else {
                    ns.add(1, num);
                    curr++;
                }
            }
            if (curr >= 2) {
                curr--;
                ns.add(curr, ns.remove(0));
            }
            for (int i : ns) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
    
}
