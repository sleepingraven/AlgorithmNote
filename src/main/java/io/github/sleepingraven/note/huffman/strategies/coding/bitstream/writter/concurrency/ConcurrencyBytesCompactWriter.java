package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.concurrency;

import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamGenerator;
import io.github.sleepingraven.note.huffman.utils.io.BytesWriter;

import java.io.IOException;

/**
 * 使用多线程压缩并写入字节数据
 *
 * @author 10132
 */
public class ConcurrencyBytesCompactWriter extends BaseConcurrencyBytesWriter<BitStreamGenerator, byte[]> {
    
    public ConcurrencyBytesCompactWriter(BytesWriter bytesWriter) {
        super(BitStreamGenerator::new, bytesWriter);
    }
    
    public int waitFinishing() throws IOException {
        // 关闭时将全部的内容刷新
        int margin = processor.ceil();
        flush();
        return margin;
    }
    
}
