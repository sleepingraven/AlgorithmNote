package io.github.sleepingraven.note.huffman.utils.io;

/**
 * 循环复用的缓冲池。需要及时消费以免覆盖
 *
 * @author 10132
 */
public class LoopBuffer {
    private final byte[][] block;
    private int count;
    
    public LoopBuffer(int total, int capacity) {
        block = new byte[total][capacity];
    }
    
    /**
     * 取一个缓冲区。
     */
    public byte[] get() {
        count = (count + 1) % block.length;
        return block[count];
    }
    
}
