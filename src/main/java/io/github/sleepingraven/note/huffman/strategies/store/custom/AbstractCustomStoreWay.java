package io.github.sleepingraven.note.huffman.strategies.store.custom;

import io.github.sleepingraven.note.huffman.strategies.store.StoreWay;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;

/**
 * 以自定义方式存放 Huffman 编码（树）
 *
 * @author Carry
 * @since 2020/6/11
 */
public abstract class AbstractCustomStoreWay implements StoreWay {
    
    /* writeHuffmanMapping */
    
    @Override
    public final void writeHuffmanMappingMultiple(OutputStreamSupplier outFactory, OutputAdapter out,
            HuffmanMapping huffmanMapping) throws IOException {
        writeHuffmanMappingMultiple(out, huffmanMapping);
    }
    
    /**
     * 将 Huffman 编码/树写入给定的输出适配器
     *
     * @param out
     *         给定的输出适配器
     * @param huffmanMapping
     *         Huffman 编码/树
     *
     * @throws IOException
     *         写入错误
     */
    protected abstract void writeHuffmanMappingMultiple(OutputAdapter out, HuffmanMapping huffmanMapping)
            throws IOException;
    
    /* parseHuffmanMapping */
}
