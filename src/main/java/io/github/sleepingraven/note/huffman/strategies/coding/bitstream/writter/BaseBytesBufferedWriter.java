package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter;

import lombok.RequiredArgsConstructor;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamProcessor;

import java.io.IOException;

/**
 * 接收数据，由 processor 处理后，取出到缓存，最后写入数据
 *
 * @author Carry
 * @since 2021/2/18
 */
@RequiredArgsConstructor
public abstract class BaseBytesBufferedWriter<P extends BitStreamProcessor<T>, T> {
    /**
     * bit 流处理器
     */
    protected final P processor;
    
    public void accept(T t) throws IOException {
        if (!processor.enqueue(t)) {
            queueFlush();
            if (!processor.enqueue(t)) {
                // 抛异常以防万一
                throw new IllegalStateException("队列刷新失败，仍然无法添加数据");
            }
        }
    }
    
    /**
     * 刷新队列和缓存
     *
     * @throws IOException
     *         写入错误
     */
    protected abstract void flush() throws IOException;
    
    /**
     * 当队列空间不足和关闭时，需要取出队列的数据
     *
     * @throws IOException
     *         写入错误
     */
    protected abstract void queueFlush() throws IOException;
    
    /**
     * 当缓存空间不足和关闭时，需要刷新缓冲区
     *
     * @throws IOException
     *         写入错误
     */
    protected abstract void bufferFlush() throws IOException;
    
}
