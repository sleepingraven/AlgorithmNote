package io.github.sleepingraven.util.common;

/**
 * 滑动窗口、单调队列工具
 *
 * @author Carry
 * @since 2021/2/21
 */
public class IntDeque {
    final int[] deque;
    int front = 0;
    int rear = -1;
    
    public IntDeque(int capacity) {
        this.deque = new int[capacity];
    }
    
    public int peekFirst() {
        return deque[front];
    }
    
    public void pollFirst() {
        front++;
    }
    
    public void offerLast(int v) {
        deque[++rear] = v;
    }
    
    public int peekLast() {
        return deque[rear];
    }
    
    public void pollLast() {
        rear--;
    }
    
    public boolean isEmpty() {
        return front > rear;
    }
    
}
