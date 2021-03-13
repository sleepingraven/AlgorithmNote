package io.github.sleepingraven.note.huffman.utils.io;

import lombok.RequiredArgsConstructor;
import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.wrapper.InputStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者，从输入流读取字节数据
 *
 * @author 10132
 */
public class BytesReader {
    public static final int BLOCKING_QUEUE_CAPACITY = Configuration.BLOCKING_SIZE;
    public static final int BUFFER_CAPACITY = Configuration.IO_BYTES;
    private static final byte[] TERMINATOR = new byte[0];
    
    private final BlockingQueue<byte[]> blockingQueue;
    private final Reader reader;
    private volatile boolean stopped;
    
    public BytesReader(InputStreamSupplier inFactory) {
        this.blockingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        reader = new Reader(inFactory);
        reader.start();
    }
    
    @RequiredArgsConstructor
    private class Reader extends Thread {
        private final InputStreamSupplier inFactory;
        private volatile IOException ioe;
        
        @Override
        public void run() {
            // 极端情况：BLOCKING_QUEUE_CAPACITY 个在 blockingQueue 中，1 个正在 read，1 个正在消费
            LoopBuffer loopBuffer = new LoopBuffer(BLOCKING_QUEUE_CAPACITY + 2, BUFFER_CAPACITY);
            byte[] buffer;
            try (InputStream is = inFactory.newInputStream()) {
                int len;
                while ((len = is.read(buffer = loopBuffer.get())) != -1) {
                    if (len < BUFFER_CAPACITY) {
                        blockingQueue.put(Arrays.copyOf(buffer, len));
                        break;
                    } else {
                        blockingQueue.put(buffer);
                    }
                }
            } catch (IOException e) {
                ioe = e;
                // 最后放入一个空数组，防止消费者一直阻塞
                if (blockingQueue.isEmpty()) {
                    blockingQueue.offer(TERMINATOR);
                }
            } catch (InterruptedException ignored) {
            } finally {
                stopped = true;
                // 最后放入一个空数组，防止消费者一直阻塞
                if (blockingQueue.isEmpty()) {
                    blockingQueue.offer(TERMINATOR);
                }
            }
        }
        
    }
    
    /**
     * @return 就绪的数据
     *
     * @throws IOException
     *         读取错误
     */
    public byte[] nextBatch() throws IOException {
        byte[] take = null;
        try {
            take = blockingQueue.take();
        } catch (InterruptedException e) {
            reader.interrupt();
        }
        if (reader.ioe != null) {
            throw reader.ioe;
        }
        return take;
    }
    
    /**
     * 队列中还有数据，或还未读完
     *
     * @return 是否还有未处理的数据
     */
    public boolean hasNext() {
        return !(blockingQueue.isEmpty() && stopped);
    }
    
}
