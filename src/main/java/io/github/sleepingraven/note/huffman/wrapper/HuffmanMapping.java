package io.github.sleepingraven.note.huffman.wrapper;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import io.github.sleepingraven.note.huffman.entity.ByteDataNode;
import io.github.sleepingraven.note.huffman.utils.HuffmanMappingConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Huffman 映射。Huffman 编码/树
 *
 * @author Carry
 * @since 2021/2/16
 */
@AllArgsConstructor
public class HuffmanMapping {
    Map<Byte, byte[]> code;
    ByteDataNode tree;
    
    public HuffmanMapping() {
    }
    
    public HuffmanMapping(Map<Byte, byte[]> code) {
        this.code = nonNullCodeOf(code);
    }
    
    public HuffmanMapping(ByteDataNode tree) {
        this.tree = tree;
    }
    
    @NonNull
    public Map<Byte, byte[]> getCode() {
        if (code == null) {
            code = tree != null ? HuffmanMappingConverter.tree2Code(tree) : nonNullCodeOf(null);
        }
        return code;
    }
    
    public ByteDataNode getTree() {
        if (tree == null) {
            tree = HuffmanMappingConverter.code2Tree(code);
        }
        return tree;
    }
    
    private static Map<Byte, byte[]> nonNullCodeOf(Map<Byte, byte[]> huffmanCodeMap) {
        if (huffmanCodeMap == null) {
            huffmanCodeMap = new HashMap<>(0);
        }
        return huffmanCodeMap;
    }
    
}
