package io.github.sleepingraven.note.huffman.strategies.store.serialization;

import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.common.HuffmanException;
import io.github.sleepingraven.note.huffman.strategies.store.StoreWay;
import io.github.sleepingraven.note.huffman.utils.IoUtil;
import io.github.sleepingraven.note.huffman.utils.PathHelper;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 以序列化方式存放 Huffman 编码（树）
 *
 * @param <S>
 *         Serializable type
 *
 * @author Carry
 * @since 2020/6/10
 */
public abstract class AbstractSerializationStoreWay<S> implements StoreWay {
    
    /* writeHuffmanMapping */
    
    @Override
    public final void writeHuffmanMappingMultiple(OutputStreamSupplier outFactory, OutputAdapter out,
            HuffmanMapping huffmanMapping) throws IOException {
        writeHuffmanMappingMultiple(outFactory, huffmanMapping);
    }
    
    private void writeHuffmanMappingMultiple(OutputStreamSupplier outFactory, HuffmanMapping huffmanMapping)
            throws IOException {
        S serializable = getSerializableByHuffmanMapping(huffmanMapping);
        IoUtil.writeSerializable(outFactory, serializable);
    }
    
    /**
     * 把 Huffman 编码（树）转为需要写入的类型
     *
     * @param huffmanMapping
     *         Huffman 编码/树
     *
     * @return 待写数据
     */
    protected abstract S getSerializableByHuffmanMapping(HuffmanMapping huffmanMapping);
    
    /* parseHuffmanMapping */
    
    @Override
    public final HuffmanMapping parseHuffmanMappingMultiple(byte[] bs) throws HuffmanException {
        File tempFile =
                PathHelper.generateFile("", i -> UUID.randomUUID().toString(), Configuration.TEMP_FILE_EXTENSION);
        
        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(bs);
            S s = (S) IoUtil.readSerializable(tempFile);
            return parseHuffmanMapping(s);
        } catch (IOException | ClassNotFoundException e) {
            throw new HuffmanException("解析失败！", e);
        } finally {
            tempFile.deleteOnExit();
        }
    }
    
    /**
     * 将读取的对象解析为 Huffman 编码/树
     *
     * @param s
     *         读取并反序列化后的数据
     *
     * @return Huffman 编码/树
     */
    protected abstract HuffmanMapping parseHuffmanMapping(S s);
    
}
