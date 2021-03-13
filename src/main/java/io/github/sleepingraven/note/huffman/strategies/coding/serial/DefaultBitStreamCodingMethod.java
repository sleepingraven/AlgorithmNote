package io.github.sleepingraven.note.huffman.strategies.coding.serial;

import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.serial.BytesCompactWriter;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.serial.BytesResolveWriter;
import io.github.sleepingraven.note.huffman.wrapper.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.IntConsumer;

/**
 * 使用 bit 流处理器写入和解析字节编码方式
 *
 * @author Carry
 * @since 2020/6/11
 */
public class DefaultBitStreamCodingMethod extends BaseDefaultCodingMethod {
    
    @Override
    public int writeByteCodingMultiple(InputStreamSupplier inFactory, OutputAdapter out, HuffmanMapping huffmanMapping,
            IntConsumer logger) throws IOException {
        BytesCompactWriter writer = new BytesCompactWriter(out);
        try (InputStream is = inFactory.newInputStream()) {
            byte[] bs = new byte[Configuration.IO_BYTES];
            int len;
            while ((len = is.read(bs)) > 0) {
                logger.accept(len);
                for (int i = 0; i < len; i++) {
                    writer.accept(huffmanMapping.getCode().get(bs[i]));
                }
            }
            return 8 - writer.finishAccepting();
        }
    }
    
    @Override
    public void parseByteCodingMultiple(InputStream is, OutputStreamSupplier outFactory, HuffmanMapping huffmanMapping,
            int lastBitNo, long codingLength, IntConsumer logger) throws IOException {
        try (OutputStream os = outFactory.newOutputStream()) {
            OutputAdapter out = new OutputStreamAdapter(os);
            BytesResolveWriter writer = new BytesResolveWriter(out, huffmanMapping);
            
            byte[] bs = new byte[Configuration.IO_BYTES];
            int len;
            while ((len = is.read(bs)) > 0) {
                logger.accept(len);
                for (int i = 0; i < len; i++) {
                    writer.accept(bs[i]);
                }
            }
            writer.finishAccepting(8 - lastBitNo);
        }
    }
    
}
