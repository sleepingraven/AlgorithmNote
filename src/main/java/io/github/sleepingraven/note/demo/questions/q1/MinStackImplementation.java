package io.github.sleepingraven.note.demo.questions.q1;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 实现一个栈，有push()、pop()、getMin()三个方法，时间复杂度都是O(1)
 * <blockquote>
 * <pre>
 * 4, 9, 7, 3, 8, 5
 * </pre>
 * </blockquote>
 *
 * @author 10132
 */
public class MinStackImplementation {

    /**
     * 解题思路
     * <ol>
     *     <li>把最小值暂存起来。但存在问题</li>
     *     <li>
     *         使用备用栈
     *         <p>O(1) 最大O(n)</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(4);
        stack.push(9);
        stack.push(7);
        stack.push(3);
        stack.push(8);
        stack.push(5);
        System.out.println(stack.getMin());

        stack.pop();
        stack.pop();
        stack.pop();
        System.out.println(stack.getMin());
    }

    private static class MinStack {
        private Stack<Integer> mainStack = new Stack<>();
        private Stack<Integer> minStack = new Stack<>();

        private void push(int element) {
            this.mainStack.push(element);

            // 判断并压入辅助栈
            if (this.minStack.empty() || element <= this.minStack.peek()) {
                this.minStack.push(element);
            }
        }

        private Integer pop() {
            // 判断辅助栈并出栈
            if (this.mainStack.peek().equals(this.minStack.peek())) {
                this.minStack.pop();
            }

            return this.mainStack.pop();
        }

        private int getMin() {
            if (this.mainStack.empty()) {
                throw new EmptyStackException();
            }

            return this.minStack.peek();
        }

    }

}
