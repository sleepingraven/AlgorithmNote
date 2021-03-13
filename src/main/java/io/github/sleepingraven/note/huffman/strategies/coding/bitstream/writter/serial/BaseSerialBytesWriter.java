package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.serial;

import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamProcessor;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.BaseBytesBufferedWriter;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;

import java.io.IOException;
import java.util.function.IntFunction;

/**
 * 将数据由 processor 处理后，放入 buffer，最后由 out 写入
 *
 * @author Carry
 * @since 2020/6/11
 */
public abstract class BaseSerialBytesWriter<P extends BitStreamProcessor<T>, T> extends BaseBytesBufferedWriter<P, T> {
    private static final int DEFAULT_BUFFER_CAPACITY = Configuration.IO_BYTES;
    private static final double DEFAULT_BUFFER_FACTOR = 0.9;
    
    /**
     * 存储处理完毕的字节
     */
    private final byte[] buffer;
    private int bufferPos;
    /**
     * 刷新队列前，如果达到阈值则刷新缓冲区
     */
    private final int safePos;
    
    private final OutputAdapter out;
    
    protected BaseSerialBytesWriter(IntFunction<P> processorFactory, OutputAdapter out) {
        this(DEFAULT_BUFFER_CAPACITY, DEFAULT_BUFFER_FACTOR, processorFactory, out);
    }
    
    private BaseSerialBytesWriter(int capacity, double bufferFactor, IntFunction<P> processorFactory,
            OutputAdapter out) {
        // 队列大小为 buffer.length - safePos
        super(processorFactory.apply((int) (capacity * (1 - bufferFactor))));
        
        capacity = (int) (processor.getCapacity() / (1 - bufferFactor));
        this.buffer = new byte[capacity];
        this.safePos = capacity - processor.getCapacity();
        
        this.out = out;
    }
    
    @Override
    protected void flush() throws IOException {
        queueFlush();
        if (!processor.isEmpty()) {
            throw new IllegalStateException("出现错误，循环队列仍有剩余数据");
        }
        bufferFlush();
    }
    
    @Override
    protected void queueFlush() throws IOException {
        // 确保缓存够用
        if (bufferPos >= safePos) {
            bufferFlush();
        }
        // 将就绪的字节读入缓存
        Byte b;
        while ((b = processor.dequeue()) != null) {
            buffer[bufferPos++] = b;
        }
    }
    
    @Override
    protected void bufferFlush() throws IOException {
        out.write(buffer, 0, bufferPos);
        bufferPos = 0;
    }
    
}
