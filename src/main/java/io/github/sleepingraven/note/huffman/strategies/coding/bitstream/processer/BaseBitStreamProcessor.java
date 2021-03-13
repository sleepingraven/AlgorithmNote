package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer;

import lombok.Getter;

/**
 * @author Carry
 * @since 2021/2/15
 */
public abstract class BaseBitStreamProcessor<T> implements BitStreamProcessor<T> {
    @Getter
    protected final int capacity;
    protected final byte[] circularQueue;
    protected int front;
    protected int rear;
    protected int size;
    
    public BaseBitStreamProcessor(int capacity) {
        this.capacity = capacity;
        this.circularQueue = new byte[capacity];
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
}
