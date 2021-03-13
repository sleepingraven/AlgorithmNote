package io.github.sleepingraven.note.demo.questions.q2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carry
 * @since 2020/1/18
 */
public class EvaluationExpression {

    /**
     * 读入一个只包含“+”、“-”、“*”、“/”的非负整数计算表达式，计算该表达式的值。
     * 输入："30/90-26+97-5-6-13/88*6+51/29+79*87+57*92"
     * 输出：12178.21
     */
    public static void main(String[] args) {
        String exp = "30/90-26+97-5-6-13/88*6+51/29+79*87+57*92";
        System.out.println(String.format("%.2f", Solution.calc(exp)));
    }

    private static class Solution {
        // 存储操作符优先级
        static final Map<Character, Integer> OP_PRIORITY_MAP = new HashMap<>();
        // 加入opStack[0]，不用判空
        static final char OP_STACK_BOTTOM = '@';

        static final Pattern PATTERN = Pattern.compile("\\d+");

        static {
            OP_PRIORITY_MAP.put('+', 1);
            OP_PRIORITY_MAP.put('-', 1);
            OP_PRIORITY_MAP.put('*', 2);
            OP_PRIORITY_MAP.put('/', 2);
            OP_PRIORITY_MAP.put(OP_STACK_BOTTOM, -1);
        }

        public static double calc(String exp) {
            Queue<?> rpn = parseReversePolishNotation(exp);
            Stack<Double> numStack = new Stack<>();
            while (!rpn.isEmpty()) {
                Object elem = rpn.poll();
                double d = 0;
                if (elem instanceof Integer) {
                    d = (double) (Integer) elem;
                } else {
                    double d1 = numStack.pop();
                    double d2 = numStack.pop();
                    switch ((char) elem) {
                        case '+':
                            d = d2 + d1;
                            break;
                        case '-':
                            d = d2 - d1;
                            break;
                        case '*':
                            d = d2 * d1;
                            break;
                        case '/':
                            d = d2 / d1;
                            break;
                        default:
                    }
                }
                numStack.push(d);
            }
            return numStack.peek();
        }

        /**
         * 解析为逆波兰式
         *
         * @param exp
         *         原串，中缀表达式
         *
         * @return 后缀表达式
         */
        public static Queue<?> parseReversePolishNotation(String exp) {
            // 操作符栈
            Stack<Character> opStack = new Stack<>();
            opStack.push(OP_STACK_BOTTOM);
            // 后缀表达式。数字：Integer；操作符：Character
            Queue<? super Object> reversePolishNotation = new LinkedList<>();

            Matcher matcher = PATTERN.matcher(exp);
            while (matcher.find()) {
                // 操作数
                int num = Integer.parseInt(matcher.group());
                reversePolishNotation.offer(num);

                if (matcher.end() >= exp.length()) {
                    while (opStack.size() > 1) {
                        reversePolishNotation.offer(opStack.pop());
                    }
                    break;
                }

                // 操作符
                char op = exp.charAt(matcher.end());
                while (OP_PRIORITY_MAP.get(op) <= OP_PRIORITY_MAP.get(opStack.peek())) {
                    reversePolishNotation.offer(opStack.pop());
                }
                opStack.push(op);
            }

            return reversePolishNotation;
        }

    }

}
