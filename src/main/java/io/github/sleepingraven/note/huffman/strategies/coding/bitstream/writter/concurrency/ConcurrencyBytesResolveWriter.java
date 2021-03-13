package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.writter.concurrency;

import io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer.BitStreamParser;
import io.github.sleepingraven.note.huffman.utils.io.BytesWriter;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;

import java.io.IOException;

/**
 * 使用多线程分离 bit 流，并解析为原符号并写入
 *
 * @author Carry
 * @since 2021/2/18
 */
public class ConcurrencyBytesResolveWriter extends BaseConcurrencyBytesWriter<BitStreamParser, Byte> {
    
    public ConcurrencyBytesResolveWriter(BytesWriter bytesWriter, HuffmanMapping huffmanMapping) {
        super(cap -> new BitStreamParser(huffmanMapping, cap), bytesWriter);
    }
    
    public void waitFinishing(int margin) throws IOException {
        // 关闭时将全部的内容刷新
        processor.prune(margin);
        flush();
    }
    
    /* 这里判断解压后的文件与源文件是否相同 */
    
    // String path = "D:/算法笔记-胡凡.pdf";
    // InputStream is;
    // byte[] bs1 = new byte[Configuration.IO_BYTES];
    // long total = 0;
    //
    // {
    //     try {
    //         is = new FileInputStream("path");
    //     } catch (FileNotFoundException e) {
    //         e.printStackTrace();
    //     }
    // }
    //
    // @Override
    // protected void bufferFlush() throws IOException {
    //     is.read(bs1, 0, getBufferPos());
    //     for (int i = 0; i < getBufferPos(); i++) {
    //         if (getBuffer()[i] != bs1[i]) {
    //             System.out.println();
    //         }
    //     }
    //     total += getBufferPos();
    //     super.bufferFlush();
    // }
}
