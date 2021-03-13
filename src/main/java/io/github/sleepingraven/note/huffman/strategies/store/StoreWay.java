package io.github.sleepingraven.note.huffman.strategies.store;

import io.github.sleepingraven.note.huffman.common.HuffmanException;
import io.github.sleepingraven.note.huffman.entity.ByteDataNode;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.IOException;

/**
 * Huffman 编码（树）的存放策略
 *
 * @author Carry
 * @since 2020/6/10
 * @see SwMapping
 */
public interface StoreWay {
    
    /**
     * 将 Huffman 编码（树）写入输出流或给定的输出适配器。如果 Huffman 编码个数为 2 个以上，由子类实现<p>
     * 如果直接序列化 Huffman 树，非叶子节点会占用额外空间，最多可达 511 个实例；如果序列化 Huffman 编码，解压时再转为 Huffman 树，则前缀会冗余存储。<p>
     * 测试结果（包含所有 256 种字节时）：
     * <p>
     * <table>
     *     <tr>
     *         <th>序列化方式</th>
     *         <th>编码（树）字节数</th>
     *     </tr>
     *     <tr>
     *         <td>写入 root</td>
     *         <td>4220B</td>
     *     </tr>
     *     <tr>
     *         <td>写入 byteCodeMap</td>
     *         <td>6596B</td>
     *     </tr>
     *     <tr>
     *         <td>格式化成 Map&lt;Byte, String&gt;</td>
     *         <td>5796B</td>
     *     </tr>
     *     <tr>
     *         <th>自定义方式</th>
     *         <th>编码（树）字节数</th>
     *     </tr>
     *     <tr>
     *         <td>写入编码</td>
     *         <td>1068B</td>
     *     </tr>
     * </table>
     *
     * @param outFactory
     *         输出流生产者，由实现类关闭
     * @param out
     *         给定的输出适配器，不应关闭
     * @param huffmanMapping
     *         Huffman 编码/树
     *
     * @throws IOException
     *         写入错误
     */
    default void writeHuffmanMapping(OutputStreamSupplier outFactory, OutputAdapter out, HuffmanMapping huffmanMapping)
            throws IOException {
        switch (huffmanMapping.getCode().size()) {
            case 0:
                return;
            case 1:
                out.write(huffmanMapping.getTree().getData());
                return;
            default:
                writeHuffmanMappingMultiple(outFactory, out, huffmanMapping);
        }
    }
    
    /**
     * 将 Huffman 编码（树）写入输出流或给定的输出适配器。Huffman 编码个数为 2 个以上<p>
     * 如果直接序列化 Huffman 树，非叶子节点会占用额外空间，最多可达 511 个实例；如果序列化 Huffman 编码，解压时再转为 Huffman 树，则前缀会冗余存储。<p>
     * 测试结果（包含所有 256 种字节时）：
     * <p>
     * <table>
     *     <tr>
     *         <th>序列化方式</th>
     *         <th>编码（树）字节数</th>
     *     </tr>
     *     <tr>
     *         <td>写入 root</td>
     *         <td>4220B</td>
     *     </tr>
     *     <tr>
     *         <td>写入 byteCodeMap</td>
     *         <td>6596B</td>
     *     </tr>
     *     <tr>
     *         <td>格式化成 Map&lt;Byte, String&gt;</td>
     *         <td>5796B</td>
     *     </tr>
     *     <tr>
     *         <th>自定义方式</th>
     *         <th>编码（树）字节数</th>
     *     </tr>
     *     <tr>
     *         <td>写入编码</td>
     *         <td>1068B</td>
     *     </tr>
     * </table>
     *
     * @param outFactory
     *         输出流生产者，由实现类关闭
     * @param out
     *         给定的输出适配器，不应关闭
     * @param huffmanMapping
     *         Huffman 编码/树
     *
     * @throws IOException
     *         写入错误
     */
    void writeHuffmanMappingMultiple(OutputStreamSupplier outFactory, OutputAdapter out, HuffmanMapping huffmanMapping)
            throws IOException;
    
    /**
     * 将字节数据解析为 Huffman 编码/树。如果 Huffman 编码个数为 2 个以上，由子类实现
     *
     * @param bs
     *         字节数据
     *
     * @return Huffman 编码/树
     *
     * @throws HuffmanException
     *         无法通过字节数组的数据还原
     */
    default HuffmanMapping parseHuffmanMapping(byte[] bs) throws HuffmanException {
        switch (bs.length) {
            case 0:
                return new HuffmanMapping();
            case 1:
                ByteDataNode root = new ByteDataNode();
                root.setData(bs[0]);
                return new HuffmanMapping(root);
            default:
                return parseHuffmanMappingMultiple(bs);
        }
    }
    
    /**
     * 将字节数据解析为 Huffman 编码/树。Huffman 编码个数为 2 个以上
     *
     * @param bs
     *         字节数据
     *
     * @return Huffman 编码/树
     *
     * @throws HuffmanException
     *         无法通过字节数组的数据还原
     */
    HuffmanMapping parseHuffmanMappingMultiple(byte[] bs) throws HuffmanException;
    
    /* non-abstract */
    
    /**
     * 获取当前策略对应的 SW 码
     *
     * @return 实例对应的 SW 码
     */
    default int getSwCode() {
        return SwMapping.from(this).getSwCode();
    }
    
    /**
     * 根据 SW 编码创建 StoreWay 对象
     *
     * @param swCode
     *         Sw 编码
     *
     * @return StoreWay 实例
     *
     * @throws HuffmanException
     *         SW 码错误
     */
    static StoreWay forSwCode(int swCode) throws HuffmanException {
        SwMapping swMapping = SwMapping.decode(swCode);
        if (swMapping == SwMapping.INVALID) {
            throw new HuffmanException("SW 码错误：" + Integer.toBinaryString(swCode));
        }
        return swMapping.getSupplier().get();
    }
    
}
