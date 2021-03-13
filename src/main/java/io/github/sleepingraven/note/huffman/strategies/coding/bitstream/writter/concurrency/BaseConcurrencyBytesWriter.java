package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.concurrency;

import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamProcessor;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.BaseBytesBufferedWriter;
import io.github.sleepingraven.note.huffman.utils.io.BytesWriter;
import io.github.sleepingraven.note.huffman.utils.io.LoopBuffer;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * 将数据由 processor 处理后，放入 buffer，最后由 writer 写入
 *
 * @author Carry
 * @since 2021/2/18
 */
public abstract class BaseConcurrencyBytesWriter<P extends BitStreamProcessor<T>, T>
        extends BaseBytesBufferedWriter<P, T> {
    private static final int DEFAULT_BUFFER_CAPACITY = Configuration.IO_BYTES;
    
    private byte[] buffer;
    private int bufferPos;
    private final LoopBuffer loopBuffer;
    
    private final BytesWriter bytesWriter;
    
    public BaseConcurrencyBytesWriter(IntFunction<P> processorFactory, BytesWriter bytesWriter) {
        this(DEFAULT_BUFFER_CAPACITY, processorFactory, bytesWriter);
    }
    
    public BaseConcurrencyBytesWriter(int capacity, IntFunction<P> processorFactory, BytesWriter bytesWriter) {
        super(processorFactory.apply(capacity));
        // 极端情况：BLOCKING_QUEUE_CAPACITY 个在 blockingQueue 中，1 个正在 write，1 个 作为 buffer 在处理中
        this.loopBuffer = new LoopBuffer(BytesWriter.BLOCKING_QUEUE_CAPACITY + 2, DEFAULT_BUFFER_CAPACITY);
        this.buffer = loopBuffer.get();
        
        this.bytesWriter = bytesWriter;
    }
    
    @Override
    protected void flush() throws IOException {
        try {
            queueFlush();
            if (!processor.isEmpty()) {
                throw new IllegalStateException("出现错误，循环队列仍有剩余数据");
            }
            buffer = Arrays.copyOf(buffer, bufferPos);
            bufferFlush();
        } finally {
            bytesWriter.waitFinishing();
        }
    }
    
    @Override
    protected void queueFlush() throws IOException {
        Byte b;
        while ((b = processor.dequeue()) != null) {
            if (bufferPos == DEFAULT_BUFFER_CAPACITY) {
                bufferFlush();
            }
            buffer[bufferPos++] = b;
        }
    }
    
    @Override
    protected void bufferFlush() throws IOException {
        bytesWriter.addBatch(buffer);
        bufferPos = 0;
        buffer = loopBuffer.get();
    }
    
    // public int getBufferPos() {
    //     return bufferPos;
    // }
    //
    // public byte[] getBuffer() {
    //     return buffer;
    // }
}
