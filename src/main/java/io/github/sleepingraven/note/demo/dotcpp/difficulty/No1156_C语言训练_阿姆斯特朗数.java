package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/11/17
 */
public class No1156_C语言训练_阿姆斯特朗数 {
    private static final Map<Integer, Integer> MAP = new HashMap<>();
    
    static {
        for (int i = 0; i < 10; i++) {
            MAP.put(i, (int) Math.pow(i, 3));
        }
    }
    
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            int m = 100 * i;
            for (int j = 0; j < 10; j++) {
                int n = m + 10 * j;
                for (int k = 0; k < 10; k++) {
                    if (n + k == MAP.get(i) + MAP.get(j) + MAP.get(k)) {
                        set.add(n + k);
                    }
                }
            }
        }
        
        for (int i : set.tailSet(1, false)) {
            System.out.print(i + "  ");
        }
    }
    
}
