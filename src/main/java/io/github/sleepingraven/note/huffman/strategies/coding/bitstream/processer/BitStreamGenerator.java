package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer;

/**
 * 将字节数组压缩，每 8 个字节压缩为 1 个。数组元素为 0 或 1
 *
 * @author 10132
 */
public class BitStreamGenerator extends BaseBitStreamProcessor<byte[]> {
    
    public BitStreamGenerator(int capacity) {
        super(correctCapacityOf(capacity));
    }
    
    /**
     * 8 的倍数，以确保能 ceil
     */
    private static int correctCapacityOf(int capacity) {
        capacity = Math.max(capacity, 1);
        if (capacity % 8 != 0) {
            if (capacity > Integer.MAX_VALUE / 8 * 8) {
                capacity = Integer.MAX_VALUE / 8 * 8;
            } else {
                capacity = (capacity / 8 + 1) * 8;
            }
        }
        return capacity;
    }
    
    /**
     * 如果最后一个 bit 流不足 8 位，末尾补 0
     *
     * @return 补 0 数
     */
    public int ceil() {
        int lastBitNo = size % 8;
        if (lastBitNo == 0) {
            return 0;
        }
        
        int margin = 8 - lastBitNo;
        byte[] bs = new byte[margin];
        enqueue(bs);
        return margin;
    }
    
    /**
     * 字节依次入队，每个字节存放一位的值
     *
     * @param bs
     *         字节数组，元素值为 0 或 1
     */
    @Override
    public boolean enqueue(byte[] bs) {
        if (size + bs.length > capacity) {
            return false;
        }
        
        for (byte b : bs) {
            circularQueue[rear++] = b;
            rear %= circularQueue.length;
        }
        size += bs.length;
        return true;
    }
    
    /**
     * 取一个满 8 位的 bit 流并出队
     */
    @Override
    public Byte dequeue() {
        if (size < 8) {
            return null;
        }
        
        size -= 8;
        int b = 0;
        for (int i = 0; i < 8; i++) {
            b <<= 1;
            b |= circularQueue[front++];
            front %= circularQueue.length;
        }
        return (byte) b;
    }
    
}
