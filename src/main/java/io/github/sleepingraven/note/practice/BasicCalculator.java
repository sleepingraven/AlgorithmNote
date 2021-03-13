package io.github.sleepingraven.note.practice;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.function.IntBinaryOperator;

/**
 * 来源：Leetcode 题解
 *
 * @author Carry
 * @since 2021/3/11
 */
public class BasicCalculator {
    
    private static final Operation[] OS = {
            new Operation('-', 1, (a, b) -> a - b),
            new Operation('+', 1, Integer::sum),
            new Operation('*', 2, (a, b) -> a * b),
            new Operation('/', 2, (a, b) -> a / b),
            new Operation('%', 2, (a, b) -> a % b),
            new Operation('^', 3, (a, b) -> (int) Math.pow(a, b))
    };
    private static final Mapper OP_MAPPER = new Mapper(OS);
    
    public int calculate(String s) {
        // 将所有的空格去掉，并将 (- 替换为 (0-
        s = s.replaceAll(" ", "");
        s = s.replaceAll("\\(-", "(0-");
        char[] cs = s.toCharArray();
        final int n = cs.length;
        // 存放所有的数字
        Deque<Integer> nums = new ArrayDeque<>(n);
        // 为了防止第一个数为负数，先加个 0
        nums.push(0);
        // 存放所有「非数字以外」的操作
        Deque<Character> ops = new ArrayDeque<>(n);
        for (int i = 0; i < n; i++) {
            char ch = cs[i];
            switch (ch) {
                case '(':
                    ops.push(ch);
                    break;
                case ')':
                    // 计算到最近一个左括号为止
                    while (!ops.isEmpty()) {
                        if (ops.peek() == '(') {
                            ops.pop();
                            break;
                        } else {
                            calc(nums, ops);
                        }
                    }
                    break;
                default:
                    if (Character.isDigit(ch)) {
                        int num = 0;
                        // 将从 i 位置开始后面的连续数字整体取出，加入 nums
                        for (; i < n && Character.isDigit(cs[i]); i++) {
                            num = 10 * num + cs[i] - '0';
                        }
                        nums.push(num);
                        i--;
                    } else {
                        // 有一个新操作要入栈时，先把栈内可以算的都算了
                        // 只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                        while (!ops.isEmpty() && ops.peek() != '(') {
                            if (OP_MAPPER.get(ops.peek()).priority < OP_MAPPER.get(ch).priority) {
                                break;
                            } else {
                                calc(nums, ops);
                            }
                        }
                        ops.push(ch);
                    }
            }
        }
        // 将剩余的计算完
        while (!ops.isEmpty() && ops.peek() != '(') {
            calc(nums, ops);
        }
        return nums.peek();
    }
    
    static void calc(Deque<Integer> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2) {
            return;
        }
        if (ops.isEmpty()) {
            return;
        }
        char op = ops.pop();
        int b = nums.pop(), a = nums.pop();
        int ans = OP_MAPPER.get(op).operating.applyAsInt(a, b);
        nums.push(ans);
    }
    
    private static class Mapper extends HashMap<Character, Operation> {
        
        public Mapper(Operation... os) {
            super((int) (os.length / .75));
            for (Operation o : os) {
                register(o);
            }
        }
        
        public void register(Operation o) {
            put(o.op, o);
        }
        
    }
    
    @RequiredArgsConstructor
    public static class Operation {
        final char op;
        final int priority;
        final IntBinaryOperator operating;
        
    }
    
}
