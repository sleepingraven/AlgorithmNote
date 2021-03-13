package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/30
 */
public class No1207_字符排列问题 {
    // JC[i]存放i!的值
    private static final ArrayList<Long> JC = new ArrayList<>();
    
    static {
        JC.add(1L);
        JC.add(1L);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        HashMap<Character, Integer> map = new HashMap<>();
        String s = scanner.next();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        
        long res = jc(n);
        for (int i : map.values()) {
            res /= jc(i);
        }
        System.out.println(res);
    }
    
    private static long jc(int n) {
        if (n < 0) {
            throw new UnsupportedOperationException();
        }
        
        if (n < JC.size()) {
            return JC.get(n);
        }
        
        long res = JC.get(JC.size() - 1);
        for (int i = JC.size(); i <= n; i++) {
            res *= i;
            JC.add(res);
        }
        return res;
    }
    
}
