package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.serial;

import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamGenerator;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;

import java.io.IOException;

/**
 * 压缩并写入字节数据
 *
 * @author 10132
 */
public class BytesCompactWriter extends BaseSerialBytesWriter<BitStreamGenerator, byte[]> {
    
    public BytesCompactWriter(OutputAdapter out) {
        super(BitStreamGenerator::new, out);
    }
    
    public int finishAccepting() throws IOException {
        // 关闭时将全部的内容刷新
        int margin = processor.ceil();
        flush();
        return margin;
    }
    
}
