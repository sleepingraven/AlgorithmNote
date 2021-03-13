package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author 10132
 */
public class No1099_校门外的树 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int l = scanner.nextInt() + 1;
        int m = scanner.nextInt();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        
        int la, ra;
        Entry<Integer, Integer> entry;
        for (int i = 0; i < m; i++) {
            la = scanner.nextInt();
            ra = scanner.nextInt();
            if ((entry = map.lowerEntry(la)) != null && entry.getValue() >= la - 1) {
                map.remove(la = entry.getKey());
                if (entry.getValue() > ra) {
                    ra = entry.getValue();
                }
            }
            for (entry = map.ceilingEntry(la); entry != null && entry.getKey() <= ra + 1;
                 entry = map.higherEntry(entry.getValue())) {
                map.remove(entry.getKey());
                if (entry.getValue() >= ra) {
                    ra = entry.getValue();
                    break;
                }
            }
            map.put(la, ra);
        }
        
        for (Entry<Integer, Integer> entry2 : map.entrySet()) {
            l -= entry2.getValue() - entry2.getKey() + 1;
        }
        System.out.println(l);
    }
    
}
