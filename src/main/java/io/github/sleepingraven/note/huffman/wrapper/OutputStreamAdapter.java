package io.github.sleepingraven.note.huffman.wrapper;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 封装 {@link OutputStream} 的输出适配器
 *
 * @author Carry
 * @since 2020/6/11
 */
@RequiredArgsConstructor
public class OutputStreamAdapter implements OutputAdapter {
    private final OutputStream os;
    
    @Override
    public void write(int b) throws IOException {
        os.write(b);
    }
    
    @Override
    public void write(byte[] bs, int off, int len) throws IOException {
        os.write(bs, off, len);
    }
    
}
