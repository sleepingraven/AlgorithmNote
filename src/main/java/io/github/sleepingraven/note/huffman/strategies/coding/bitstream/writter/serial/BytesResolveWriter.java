package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.serial;

import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamParser;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;

import java.io.IOException;

/**
 * 分离 bit 流，并解析为原符号并写入
 *
 * @author 10132
 */
public class BytesResolveWriter extends BaseSerialBytesWriter<BitStreamParser, Byte> {
    
    public BytesResolveWriter(OutputAdapter out, HuffmanMapping huffmanMapping) {
        super(cap -> new BitStreamParser(huffmanMapping, cap), out);
    }
    
    public void finishAccepting(int margin) throws IOException {
        // 关闭时将全部的内容刷新
        processor.prune(margin);
        flush();
    }
    
}
