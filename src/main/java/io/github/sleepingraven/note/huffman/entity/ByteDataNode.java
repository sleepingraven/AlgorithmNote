package io.github.sleepingraven.note.huffman.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 二叉树节点，数据类型为字节。Huffman 树、字典树
 *
 * @author Carry
 * @since 2020/1/20
 */
@NoArgsConstructor
@Data
public class ByteDataNode implements Serializable {
    private static final long serialVersionUID = -3572016864028410120L;
    
    private byte data;
    private transient long weight;
    
    private ByteDataNode left;
    private ByteDataNode right;
    
    public ByteDataNode(byte data, long weight) {
        this.data = data;
        this.weight = weight;
    }
    
    public ByteDataNode(long weight, ByteDataNode left, ByteDataNode right) {
        this.weight = weight;
        this.left = left;
        this.right = right;
    }
    
}
