package io.github.sleepingraven.note.huffman.strategies.coding.serial;

import io.github.sleepingraven.note.huffman.strategies.coding.CodingMethod;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.InputStreamSupplier;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntConsumer;

/**
 * 单线程写入和解析字节编码
 *
 * @author Carry
 * @since 2021/2/17
 */
public abstract class BaseDefaultCodingMethod implements CodingMethod {
    
    /* writeByteCoding */
    
    @Override
    public int writeByteCodingMultiple(InputStreamSupplier inFactory, OutputStreamSupplier outFactory,
            OutputAdapter out, HuffmanMapping huffmanMapping, IntConsumer logger) throws IOException {
        return writeByteCodingMultiple(inFactory, out, huffmanMapping, logger);
    }
    
    /**
     * 将数据转换为字节编码，写入输出流或给定的输出适配器
     *
     * @param inFactory
     *         输入流生产者
     * @param out
     *         给定的输出适配器
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
    protected abstract int writeByteCodingMultiple(InputStreamSupplier inFactory, OutputAdapter out,
            HuffmanMapping huffmanMapping, IntConsumer logger) throws IOException;
    
    /* parseByteCoding */
    
    @Override
    public void parseByteCodingMultiple(InputStreamSupplier inFactory, InputStream is, OutputStreamSupplier outFactory,
            HuffmanMapping huffmanMapping, int lastBitNo, long codingLength, IntConsumer logger) throws IOException {
        parseByteCodingMultiple(is, outFactory, huffmanMapping, lastBitNo, codingLength, logger);
    }
    
    /**
     * 读取字节编码
     *
     * @param is
     *         输入流
     * @param outFactory
     *         输出流生产者
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
    protected abstract void parseByteCodingMultiple(InputStream is, OutputStreamSupplier outFactory,
            HuffmanMapping huffmanMapping, int lastBitNo, long codingLength, IntConsumer logger) throws IOException;
    
}
