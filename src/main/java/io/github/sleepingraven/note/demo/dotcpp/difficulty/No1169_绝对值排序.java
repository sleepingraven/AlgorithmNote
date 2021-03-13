package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/11/19
 */
public class No1169_绝对值排序 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TreeSet<AbsNode> set;
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            set = new TreeSet<>();
            if (n == 0) {
                break;
            }
            for (int i = 0; i < n; i++) {
                set.add(new AbsNode(scanner.nextInt()));
            }
            
            for (AbsNode an : set) {
                System.out.print(an.data + " ");
            }
        }
    }
    
    private static class AbsNode implements Comparable {
        private int data;
        private Integer abs;
        
        @Override
        public int compareTo(Object o) {
            return ((AbsNode) o).abs.compareTo(abs);
        }
        
        AbsNode(Integer data) {
            this.data = data;
            this.abs = Math.abs(data);
        }
        
    }
    
}
