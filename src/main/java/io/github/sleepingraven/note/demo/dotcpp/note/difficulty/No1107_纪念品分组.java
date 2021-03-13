package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Carry
 * @since 2019/11/10
 */
public class No1107_纪念品分组 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();
        int n = scanner.nextInt();
        ValueNum vn = new ValueNum();
        
        Integer temp, num = 0;
        for (int i = 0; i < n; i++) {
            temp = scanner.nextInt();
            if (vn.containsKey(limit - temp)) {
                vn.remove(limit - temp);
                num++;
            } else {
                vn.put(temp);
            }
        }
        
        Integer i;
        while (!vn.isEmpty()) {
            i = vn.firstKey();
            if ((temp = vn.floorKey(limit - i)) != null) {
                vn.remove(temp);
            }
            vn.remove(i);
            num++;
        }
        
        System.out.println(num);
    }
    
    private static class ValueNum extends TreeMap<Integer, Integer> {
        
        void remove(Integer key) {
            if (containsKey(key) && get(key) > 1) {
                replace(key, get(key) - 1);
            } else {
                super.remove(key);
            }
        }
        
        void put(Integer key) {
            if (containsKey(key)) {
                replace(key, get(key) + 1);
            } else {
                super.put(key, 1);
            }
        }
        
    }
    
}
