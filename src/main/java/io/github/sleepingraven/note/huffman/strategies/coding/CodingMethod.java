package io.github.sleepingraven.note.huffman.strategies.coding;

import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.InputStreamSupplier;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * 字节编码的读写策略
 *
 * @author Carry
 * @since 2020/6/11
 */
public interface CodingMethod {
    
    /**
     * 将数据转换为字节编码，写入输出流或给定的输出适配器。如果 Huffman 编码个数为 2 个以上，由子类实现
     *
     * @param inFactory
     *         输入流生产者，由实现类关闭
     * @param outFactory
     *         输出流生产者，由实现类关闭
     * @param out
     *         给定的输出适配器，不应关闭
     * @param huffmanMapping
     *         Huffman 编码/树
     * @param srcLength
     *         源文件长度
     * @param logger
     *         读取数据后，需要打印长度
     *
     * @return 最后一个字节的有效位数，1-8。如果 Huffman 编码个数不足 2 个，则返回值是无效的
     *
     * @throws IOException
     *         读写错误
     */
    default int writeByteCoding(InputStreamSupplier inFactory, OutputStreamSupplier outFactory, OutputAdapter out,
            HuffmanMapping huffmanMapping, long srcLength, IntConsumer logger) throws IOException {
        switch (huffmanMapping.getCode().size()) {
            case 0:
                return 0;
            case 1:
                byte[] bs = new byte[8];
                for (int i = 7; i >= 0; i--) {
                    bs[i] = (byte) srcLength;
                    srcLength >>>= 8;
                }
                out.write(bs);
                return 8;
            default:
                return writeByteCodingMultiple(inFactory, outFactory, out, huffmanMapping, logger);
        }
    }
    
    /**
     * 将数据转换为字节编码，写入输出流或给定的输出适配器。Huffman 编码个数为 2 个以上
     *
     * @param inFactory
     *         输入流生产者，由实现类关闭
     * @param outFactory
     *         输出流生产者，由实现类关闭
     * @param out
     *         给定的输出适配器，不应关闭
     * @param huffmanMapping
     *         Huffman 编码/树
     * @param logger
     *         读取数据后，需要打印长度
     *
     * @return 最后一个字节的有效位数，1-8
     *
     * @throws IOException
     *         读写错误
     */
    int writeByteCodingMultiple(InputStreamSupplier inFactory, OutputStreamSupplier outFactory, OutputAdapter out,
            HuffmanMapping huffmanMapping, IntConsumer logger) throws IOException;
    
    /**
     * 读取字节编码。如果 Huffman 编码个数为 2 个以上，由子类实现
     *
     * @param inFactory
     *         输入流生产者，由实现类关闭
     * @param is
     *         输入流
     * @param outFactory
     *         输出流生产者，由实现类关闭
     * @param huffmanMapping
     *         Huffman 编码/树
     * @param lastBitNo
     *         最后一个字节的有效位数，1-8
     * @param codingLength
     *         字节编码的长度
     * @param logger
     *         读取数据后，需要打印长度
     *
     * @throws IOException
     *         读写错误
     */
    default void parseByteCoding(InputStreamSupplier inFactory, InputStream is, OutputStreamSupplier outFactory,
            HuffmanMapping huffmanMapping, int lastBitNo, long codingLength, IntConsumer logger) throws IOException {
        switch (huffmanMapping.getCode().size()) {
            case 0:
                return;
            case 1:
                byte[] bs = new byte[8];
                is.read(bs);
                long srcLength = 0;
                for (int i = 0; i < 8; i++) {
                    srcLength <<= 8;
                    srcLength |= bs[i] & 0xffL;
                }
                
                bs = new byte[(int) Math.min(srcLength, Configuration.IO_BYTES)];
                Arrays.fill(bs, huffmanMapping.getTree().getData());
                try (OutputStream os = outFactory.newOutputStream()) {
                    while (true) {
                        if (srcLength > bs.length) {
                            os.write(bs);
                        } else {
                            os.write(bs, 0, (int) srcLength);
                            break;
                        }
                        srcLength -= bs.length;
                    }
                }
                return;
            default:
                parseByteCodingMultiple(inFactory, is, outFactory, huffmanMapping, lastBitNo, codingLength, logger);
        }
    }
    
    /**
     * 读取字节编码。Huffman 编码个数为 2 个以上
     *
     * @param inFactory
     *         输入流生产者，由实现类关闭
     * @param is
     *         输入流
     * @param outFactory
     *         输出流生产者，由实现类关闭
     * @param huffmanMapping
     *         Huffman 编码/树
     * @param lastBitNo
     *         最后一个字节的有效位数，1-8
     * @param codingLength
     *         字节编码的长度
     * @param logger
     *         读取数据后，需要打印长度
     *
     * @throws IOException
     *         读写错误
     */
    void parseByteCodingMultiple(InputStreamSupplier inFactory, InputStream is, OutputStreamSupplier outFactory,
            HuffmanMapping huffmanMapping, int lastBitNo, long codingLength, IntConsumer logger) throws IOException;
    
}
