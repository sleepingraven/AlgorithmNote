package io.github.sleepingraven.note.huffman.wrapper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 表示一个输出流的生产者
 *
 * @author Carry
 * @since 2021/2/17
 */
@FunctionalInterface
public interface OutputStreamSupplier {
    
    /**
     * 获取一个输出流
     *
     * @return 输出流
     *
     * @throws IOException
     *         获取过程中的 IO 错误
     */
    OutputStream newOutputStream() throws IOException;
    
}
