package io.github.sleepingraven.note.demo.questions.q1;

import java.util.Stack;

/**
 * 用栈模拟一个队列，实现入队和出队
 *
 * @author 10132
 */
public class QueueOfStack {

    /**
     * 解题思路
     * <ol>
     *     <li>使用两个栈，实现先入先出</li>
     * </ol>
     */
    public static void main(String[] args) {
        StackQueue stackQueue = new StackQueue();

        stackQueue.enQueue(1);
        stackQueue.enQueue(2);
        stackQueue.enQueue(3);
        System.out.println(stackQueue.deQueue());
        System.out.println(stackQueue.deQueue());

        stackQueue.enQueue(4);
        System.out.println(stackQueue.deQueue());
        System.out.println(stackQueue.deQueue());
    }

    private static class StackQueue {
        private Stack<Integer> stack1 = new Stack<>();
        private Stack<Integer> stack2 = new Stack<>();

        /**
         * O(1)
         */
        private void enQueue(int element) {
            this.stack1.push(element);
        }

        /**
         * O(1)或O(n)，均摊时间复杂度为O(1)
         */
        private Integer deQueue() {
            if (this.stack2.isEmpty()) {
                if (this.stack1.isEmpty()) {
                    return null;
                }
                transfer();
            }

            return this.stack2.pop();
        }

        private void transfer() {
            while (!this.stack1.isEmpty()) {
                this.stack2.push(this.stack1.pop());
            }
        }

    }

}
