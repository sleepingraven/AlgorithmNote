package io.github.sleepingraven.note.huffman.strategies.store.serialization;

import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;

import java.util.Map;

/**
 * @author Carry
 * @since 2020/6/10
 */
public class CodeSerialization extends AbstractSerializationStoreWay<Map<Byte, byte[]>> {
    
    @Override
    protected Map<Byte, byte[]> getSerializableByHuffmanMapping(HuffmanMapping huffmanMapping) {
        return huffmanMapping.getCode();
    }
    
    @Override
    protected HuffmanMapping parseHuffmanMapping(Map<Byte, byte[]> s) {
        return new HuffmanMapping(s);
    }
    
}
