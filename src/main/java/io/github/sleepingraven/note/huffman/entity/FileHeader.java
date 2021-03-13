package io.github.sleepingraven.note.huffman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.sleepingraven.note.huffman.common.Constants;
import io.github.sleepingraven.note.huffman.strategies.store.StoreWay;

/**
 * 压缩文件的文件头
 *
 * @author Carry
 * @since 2020/6/9
 * @see Constants#PRE_OFFSET
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileHeader {
    /**
     * 迭代压缩次数
     */
    private int iterations;
    private StoreWay storeWay;
    private int srcFullNameLength;
    private int endOfHuffmanCode;
    private int lastBitNo;
    
}
