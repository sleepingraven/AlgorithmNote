package io.github.sleepingraven.note.huffman.strategies.coding.concurrency;

import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.concurrency.ConcurrencyBytesCompactWriter;
import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.concurrency.ConcurrencyBytesResolveWriter;
import io.github.sleepingraven.note.huffman.utils.io.BytesReader;
import io.github.sleepingraven.note.huffman.utils.io.BytesWriter;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.InputStreamSupplier;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;
import java.util.function.IntConsumer;

/**
 * 使用 bit 流处理器，并发写入和解析字节编码方式
 *
 * @author Carry
 * @since 2020/6/12
 */
public class ConcurrencyBitStreamCodingMethod extends BaseConcurrencyCodingMethod {
    
    @Override
    public int writeByteCodingMultiple(InputStreamSupplier inFactory, OutputStreamSupplier outFactory,
            HuffmanMapping huffmanMapping, IntConsumer logger) throws IOException {
        int lastBitNo = 8;
        BytesReader bytesReader = new BytesReader(inFactory);
        BytesWriter bytesWriter = new BytesWriter(outFactory);
        ConcurrencyBytesCompactWriter writer = new ConcurrencyBytesCompactWriter(bytesWriter);
        try {
            while (bytesReader.hasNext()) {
                byte[] bs = bytesReader.nextBatch();
                logger.accept(bs.length);
                for (byte b : bs) {
                    writer.accept(huffmanMapping.getCode().get(b));
                }
            }
        } finally {
            lastBitNo -= writer.waitFinishing();
        }
        return lastBitNo;
    }
    
    @Override
    public void parseByteCodingMultiple(InputStreamSupplier inFactory, OutputStreamSupplier outFactory,
            HuffmanMapping huffmanMapping, int lastBitNo, long codingLength, IntConsumer logger) throws IOException {
        BytesReader bytesReader = new BytesReader(inFactory);
        BytesWriter bytesWriter = new BytesWriter(outFactory);
        ConcurrencyBytesResolveWriter writer = new ConcurrencyBytesResolveWriter(bytesWriter, huffmanMapping);
        try {
            while (bytesReader.hasNext()) {
                byte[] bs = bytesReader.nextBatch();
                logger.accept(bs.length);
                for (byte b : bs) {
                    writer.accept(b);
                }
            }
        } finally {
            writer.waitFinishing(8 - lastBitNo);
        }
    }
    
}
