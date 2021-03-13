package io.github.sleepingraven.note.huffman.wrapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 表示一个输入流的生产者
 *
 * @author Carry
 * @since 2021/2/17
 */
@FunctionalInterface
public interface InputStreamSupplier {
    
    /**
     * 获取一个输入流
     *
     * @return 输入流
     *
     * @throws IOException
     *         获取过程中的 IO 错误
     */
    InputStream newInputStream() throws IOException;
    
}
