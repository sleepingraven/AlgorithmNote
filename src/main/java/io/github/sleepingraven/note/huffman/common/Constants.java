package io.github.sleepingraven.note.huffman.common;

import io.github.sleepingraven.note.huffman.entity.FileHeader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Carry
 * @since 2021/2/13
 */
public interface Constants {
    Charset CHARSET = StandardCharsets.UTF_8;
    /**
     * 压缩文件开始预留若干字节的空间，包含以下几部分：
     * <table>
     *     <tr>
     *         <th>字节数</th>
     *         <th>说明</th>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>第几次迭代压缩</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>Huffman 编码（树）存放方式</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>源文件名称的字节数</td>
     *     </tr>
     *     <tr>
     *         <td>4</td>
     *         <td>Huffman 编码（树）的截止位置</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>字节编码中最后一个字节的有效位数。0-7，0 表示 8 位全部有效</td>
     *     </tr>
     * </table>
     *
     * @see FileHeader
     */
    int PRE_OFFSET = 8;
    
}
