package io.github.sleepingraven.note.huffman.utils;

import lombok.NonNull;
import io.github.sleepingraven.note.huffman.entity.ByteDataNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Huffman 编码、树的相互转换
 *
 * @author Carry
 * @since 2021/2/16
 */
public class HuffmanMappingConverter {
    
    public static Map<Byte, byte[]> tree2Code(@NonNull ByteDataNode root) {
        Map<Byte, byte[]> huffmanCodeMap = new HashMap<>((int) (256 / 0.75));
        putHuffmanCodeOfTree(huffmanCodeMap, root, new StringBuilder());
        return huffmanCodeMap;
    }
    
    /**
     * 遍历 Huffman 树，获得叶子节点的 Huffman 编码。<p>
     * Huffman 编码存储于 byte 数组中，左分支记为 0，右分支记为 1
     *
     * @param huffmanCodeMap
     *         递归结果，存储字节的 Huffman 编码
     * @param root
     *         根节点
     * @param code
     *         Huffman 编码的缓存
     */
    private static void putHuffmanCodeOfTree(@NonNull Map<Byte, byte[]> huffmanCodeMap, @NonNull ByteDataNode root,
            @NonNull StringBuilder code) {
        if (root.getLeft() == null) {
            byte[] bs = new byte[code.length()];
            for (int i = 0; i < code.length(); i++) {
                bs[i] = (byte) code.charAt(i);
            }
            huffmanCodeMap.put(root.getData(), bs);
            return;
        }
        
        putHuffmanCodeOfTree(huffmanCodeMap, root.getLeft(), code.append((char) 0));
        code.deleteCharAt(code.length() - 1);
        
        putHuffmanCodeOfTree(huffmanCodeMap, root.getRight(), code.append((char) 1));
        code.deleteCharAt(code.length() - 1);
    }
    
    /**
     * 根据 Huffman 编码还原 Huffman 树
     *
     * @param huffmanCodeMap
     *         Huffman 编码
     *
     * @return Huffman 树
     */
    @NonNull
    public static ByteDataNode code2Tree(@NonNull Map<Byte, byte[]> huffmanCodeMap) {
        ByteDataNode root = new ByteDataNode();
        huffmanCodeMap.forEach((b, code) -> {
            ByteDataNode p = root;
            for (int co : code) {
                if (co == 0) {
                    if (p.getLeft() == null) {
                        p.setLeft(new ByteDataNode());
                    }
                    p = p.getLeft();
                } else {
                    if (p.getRight() == null) {
                        p.setRight(new ByteDataNode());
                    }
                    p = p.getRight();
                }
            }
            // 还原的 Huffman 树没有权值
            p.setData(b);
        });
        return root;
    }
    
}
