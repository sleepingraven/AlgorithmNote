package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carry
 * @since 2019/11/16
 */
public class No1125_C语言训练_委派任务 {
    private static final char FIRST = 'A';
    private static final char LAST = 'F';
    private static final Map<Character, Integer> MAP = new HashMap<>();
    private static int i;
    
    static {
        int i = 1;
        for (char c = LAST; c >= FIRST; c--) {
            MAP.put(c, i);
            i <<= 1;
        }
    }
    
    public static void main(String[] args) {
        // -------------ABCDEF
        final int m = 0b111111;
        for (i = 0b010000; i <= m; i++) {
            if (condition2() && condition3() && condition4() && condition5() && condition6()) {
                print();
            }
        }
    }
    
    private static boolean judge(char c) {
        return (i & MAP.get(c)) != 0;
    }
    
    private static boolean judgeNot(char c) {
        return !judge(c);
    }
    
    private static boolean condition2() {
        return !(judge('A') && judge('D'));
    }
    
    private static boolean condition3() {
        return (judge('A') && judge('E') && judgeNot('F')) || (judge('A') && judgeNot('E') && judge('F')) ||
               (judgeNot('A') && judge('E') && judge('F'));
    }
    
    private static boolean condition4() {
        return judge('B') && judge('C') || (judgeNot('B') && judgeNot('C'));
    }
    
    private static boolean condition5() {
        return judge('C') && judgeNot('D') || (judgeNot('C') && judge('D'));
    }
    
    private static boolean condition6() {
        return judge('D') || (judgeNot('D') && judgeNot('E'));
    }
    
    private static void print() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c = FIRST; c <= LAST; c++) {
            if (judge(c)) {
                stringBuilder.append(c).append(',');
            }
        }
        System.out.println(stringBuilder);
    }
    
}
