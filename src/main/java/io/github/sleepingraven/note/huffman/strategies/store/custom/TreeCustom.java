package io.github.sleepingraven.note.huffman.strategies.store.custom;

import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;

/**
 * @author Carry
 * @since 2020/6/10
 */
public class TreeCustom extends AbstractCustomStoreWay {
    
    @Override
    public void writeHuffmanMappingMultiple(OutputAdapter out, HuffmanMapping huffmanMapping) {
        throw new UnsupportedOperationException("暂未支持！");
    }
    
    @Override
    public HuffmanMapping parseHuffmanMappingMultiple(byte[] bs) {
        throw new UnsupportedOperationException("暂未支持！");
    }
    
}
