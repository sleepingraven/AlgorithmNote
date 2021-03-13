package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer;

/**
 * 将数据转换为字节
 *
 * @param <T>
 *         数据类型
 *
 * @author Carry
 * @since 2020/6/11
 */
public interface BitStreamProcessor<T> {
    
    /**
     * 数据入队
     *
     * @param t
     *         数据
     *
     * @return true 如果容量足够
     */
    boolean enqueue(T t);
    
    /**
     * 将就绪的字节出队
     *
     * @return 队首的就绪字节，没有则空
     */
    Byte dequeue();
    
    /**
     * 获得该处理器容量
     *
     * @return bit 流处理器的容量
     */
    int getCapacity();
    
    /**
     * 当前队列是否为空
     *
     * @return true if is empty
     */
    boolean isEmpty();
    
}
