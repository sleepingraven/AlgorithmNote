package io.github.sleepingraven.note.huffman.wrapper;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 封装 {@link RandomAccessFile} 的输出适配器
 *
 * @author Carry
 * @since 2020/6/11
 */
@RequiredArgsConstructor
public class RandomAccessFileAdapter implements OutputAdapter {
    private final RandomAccessFile raf;
    
    @Override
    public void write(int b) throws IOException {
        raf.write(b);
    }
    
    @Override
    public void write(byte[] bs, int off, int len) throws IOException {
        raf.write(bs, off, len);
    }
    
}
