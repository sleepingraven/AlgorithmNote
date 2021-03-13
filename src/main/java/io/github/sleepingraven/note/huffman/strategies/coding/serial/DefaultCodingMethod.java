package io.github.sleepingraven.note.huffman.strategies.coding.serial;

import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.entity.ByteDataNode;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.InputStreamSupplier;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.IntConsumer;

/**
 * 默认的写入和解析字节编码方式
 *
 * @author Carry
 * @since 2020/6/11
 */
public class DefaultCodingMethod extends BaseDefaultCodingMethod {
    
    @Override
    public int writeByteCodingMultiple(InputStreamSupplier inFactory, OutputAdapter out, HuffmanMapping huffmanMapping,
            IntConsumer logger) throws IOException {
        try (InputStream is = inFactory.newInputStream()) {
            // 写入字节的缓存
            byte[] buffer = new byte[Configuration.IO_BYTES];
            // 写入字节的索引
            int index = 0;
            // 记录当前字节在第几位
            int bitNo = 0;
            
            byte[] bs = new byte[Configuration.IO_BYTES];
            int len;
            while ((len = is.read(bs)) > 0) {
                logger.accept(len);
                for (int i = 0; i < len; i++) {
                    byte[] code = huffmanMapping.getCode().get(bs[i]);
                    for (int co : code) {
                        buffer[index] <<= 1;
                        buffer[index] |= co;
                        
                        if (++bitNo == 8) {
                            bitNo = 0;
                            // bs 满时写入文件
                            if (++index == buffer.length) {
                                index = 0;
                                out.write(buffer);
                            }
                        }
                    }
                }
            }
            
            // 处理最后一个字节，使高位对齐
            if (bitNo != 0) {
                buffer[index] <<= 8 - bitNo;
                index++;
            } else {
                bitNo = 8;
            }
            if (index > 0) {
                out.write(buffer, 0, index);
            }
            return bitNo;
        }
    }
    
    @Override
    public void parseByteCodingMultiple(InputStream is, OutputStreamSupplier outFactory, HuffmanMapping huffmanMapping,
            int lastBitNo, long codingLength, IntConsumer logger) throws IOException {
        try (OutputStream os = outFactory.newOutputStream()) {
            // 写入数组的缓存
            byte[] buffer = new byte[Configuration.IO_BYTES];
            // 记录剩余字节数
            long bytesRemaining = codingLength;
            
            ByteCodingParserWriter parserWriter = new ByteCodingParserWriter(os, buffer, huffmanMapping.getTree());
            byte[] bs = new byte[Configuration.IO_BYTES];
            int len;
            do {
                len = is.read(bs);
                logger.accept(len);
                // 不处理最后一个字节
                if ((bytesRemaining -= len) <= 0) {
                    len--;
                }
                for (int i = 0; i < len; i++) {
                    parserWriter.parseByteCoding(bs[i]);
                }
            } while (bytesRemaining > 0);
            
            // 最后一个字节
            parserWriter.parseByteCoding(bs[len], lastBitNo);
            
            // 写入剩余数据
            if (parserWriter.index > 0) {
                os.write(buffer, 0, parserWriter.index);
            }
        }
    }
    
    /**
     * 解析字节编码，根据 Huffman 树还原数据，并由写入输出流。注意 root 不能为空，且不是叶子
     */
    private static class ByteCodingParserWriter {
        private final OutputStream os;
        private final byte[] buffer;
        private int index;
        private final ByteDataNode root;
        private ByteDataNode p;
        
        public ByteCodingParserWriter(OutputStream os, byte[] buffer, ByteDataNode root) {
            this.os = os;
            this.buffer = buffer;
            this.root = root;
            p = root;
        }
        
        private void parseByteCoding(byte b) throws IOException {
            parseByteCoding(b, 8);
        }
        
        private void parseByteCoding(byte b, int bitNo) throws IOException {
            int coding = b & 0xff;
            for (int i = 0; i < bitNo; i++) {
                int mask = 0x80 >> i;
                p = (coding & mask) == 0 ? p.getLeft() : p.getRight();
                if (p.getLeft() == null) {
                    buffer[index++] = p.getData();
                    // 满则写入
                    if (index == buffer.length) {
                        os.write(buffer);
                        index = 0;
                    }
                    p = root;
                }
            }
        }
        
    }
    
}
