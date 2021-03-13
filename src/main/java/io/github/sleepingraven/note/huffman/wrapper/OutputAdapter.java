package io.github.sleepingraven.note.huffman.wrapper;

import java.io.IOException;

/**
 * 提供对字节、字节数组的写入。由于 {@link java.io.RandomAccessFile} 实现了 {@link java.io.DataOutput}，而 {@link java.io.OutputStream}
 * 未实现这样的接口。
 *
 * @author Carry
 * @since 2020/6/11
 */
public interface OutputAdapter {
    
    /**
     * 向输出流写入 int 数据的最低一个字节
     *
     * @param b
     *         int 数据
     *
     * @throws IOException
     *         写入错误
     */
    void write(int b) throws IOException;
    
    /**
     * 向输出流写入 bs[] 数据
     *
     * @param bs
     *         待写数据
     *
     * @throws IOException
     *         写入错误
     */
    default void write(byte[] bs) throws IOException {
        write(bs, 0, bs.length);
    }
    
    /**
     * 向输出流写入 bs[] 数据
     *
     * @param bs
     *         待写数据
     * @param off
     *         开始位置
     * @param len
     *         长度
     *
     * @throws IOException
     *         写入错误
     */
    void write(byte[] bs, int off, int len) throws IOException;
    
}
