package io.github.sleepingraven.note.huffman.strategies.store.serialization;

import io.github.sleepingraven.note.huffman.entity.ByteDataNode;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;

/**
 * @author Carry
 * @since 2020/6/10
 */
public class TreeSerialization extends AbstractSerializationStoreWay<ByteDataNode> {
    
    @Override
    protected ByteDataNode getSerializableByHuffmanMapping(HuffmanMapping huffmanMapping) {
        return huffmanMapping.getTree();
    }
    
    @Override
    protected HuffmanMapping parseHuffmanMapping(ByteDataNode s) {
        return new HuffmanMapping(s);
    }
    
}
