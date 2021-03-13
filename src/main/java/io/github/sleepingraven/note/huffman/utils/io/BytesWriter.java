package io.github.sleepingraven.note.huffman.utils.io;

import lombok.RequiredArgsConstructor;
import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费者，向输出流写入字节数据
 *
 * @author 10132
 */
public class BytesWriter {
    public static final int BLOCKING_QUEUE_CAPACITY = Configuration.BLOCKING_SIZE;
    private static final byte[] TERMINATOR = new byte[0];
    
    private final BlockingQueue<byte[]> blockingQueue;
    private final Writer writer;
    /**
     * 对消费过程加锁，以便在 {@link BytesWriter#waitFinishing()} 等待写入完成
     */
    private final Lock stopped = new ReentrantLock();
    /**
     * 写线程对 {@link BytesWriter#stopped} 加锁过程的同步
     */
    private final AtomicBoolean sync = new AtomicBoolean(false);
    
    public BytesWriter(OutputStreamSupplier outFactory) {
        this.blockingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        this.writer = new Writer(outFactory);
        writer.start();
        while (!sync.get()) {
        }
    }
    
    @RequiredArgsConstructor
    private class Writer extends Thread {
        private final OutputStreamSupplier outFactory;
        private volatile IOException ioe;
        
        @Override
        public void run() {
            try {
                stopped.lockInterruptibly();
                try (OutputStream os = outFactory.newOutputStream()) {
                    sync.set(true);
                    byte[] buffer;
                    do {
                        buffer = blockingQueue.take();
                        os.write(buffer);
                    } while (buffer.length != 0);
                } catch (IOException e) {
                    ioe = e;
                    // 最后移除一个元素，防止生产者者一直阻塞
                    if (!blockingQueue.isEmpty()) {
                        blockingQueue.poll();
                    }
                } finally {
                    stopped.unlock();
                }
            } catch (InterruptedException ignored) {
            }
        }
        
    }
    
    /**
     * @param bs
     *         提供的数据
     *
     * @throws IOException
     *         写入错误
     */
    public void addBatch(byte[] bs) throws IOException {
        try {
            blockingQueue.put(bs);
        } catch (InterruptedException e) {
            writer.interrupt();
        }
        if (writer.ioe != null) {
            throw writer.ioe;
        }
    }
    
    /**
     * 等待写入完成
     *
     * @throws IOException
     *         写入错误
     */
    public void waitFinishing() throws IOException {
        addBatch(TERMINATOR);
        try {
            stopped.lockInterruptibly();
            try {
                if (writer.ioe != null) {
                    throw writer.ioe;
                }
            } finally {
                stopped.unlock();
            }
        } catch (InterruptedException ignored) {
            writer.interrupt();
        }
    }
    
}
