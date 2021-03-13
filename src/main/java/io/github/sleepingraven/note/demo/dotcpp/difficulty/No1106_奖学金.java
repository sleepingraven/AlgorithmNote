package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author 10132
 */
public class No1106_奖学金 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        TreeSet<Stu> set = new TreeSet<>();
        
        for (int i = 1; i <= n; i++) {
            set.add(new Stu(i, scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
            if (set.size() > 5) {
                set.pollFirst();
            }
        }
        
        Iterator<Stu> it = set.descendingIterator();
        Stu s;
        while (it.hasNext()) {
            s = it.next();
            System.out.println(s.no + " " + (s.a + s.b + s.c));
        }
    }
    
    private static class Stu implements Comparable {
        private Integer no;
        private Integer a;
        private Integer b;
        private Integer c;
        
        @Override
        public int compareTo(Object o) {
            Stu s = (Stu) o;
            Integer total = a + b + c;
            Integer sTotal = s.a + s.b + s.c;
            if (!total.equals(sTotal)) {
                return total.compareTo(sTotal);
            }
            if (!a.equals(s.a)) {
                return a.compareTo(s.a);
            }
            return s.no.compareTo(no);
        }
        
        Stu(int no, Integer a, Integer b, Integer c) {
            this.no = no;
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
    }
    
}
