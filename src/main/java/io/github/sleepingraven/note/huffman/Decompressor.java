package io.github.sleepingraven.note.huffman;

import io.github.sleepingraven.note.huffman.common.Constants;
import io.github.sleepingraven.note.huffman.common.HuffmanException;
import io.github.sleepingraven.note.huffman.entity.FileHeader;
import io.github.sleepingraven.note.huffman.strategies.coding.CodingMethod;
import io.github.sleepingraven.note.huffman.strategies.store.StoreWay;
import io.github.sleepingraven.note.huffman.utils.LogUtil;
import io.github.sleepingraven.note.huffman.utils.PathHelper;
import io.github.sleepingraven.note.huffman.wrapper.FileWrapper;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.InputStreamSupplier;
import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.IntConsumer;

/**
 * 解压器
 *
 * @author Carry
 * @since 2020/1/21
 */
public class Decompressor {
    /**
     * 当前迭代次数
     */
    private int round = 1;
    /**
     * 剩余迭代次数
     */
    private int roundsRemaining = -1;
    
    public String decompress(String destPath, CodingMethod codingMethod) {
        LogUtil.begin("开始解压", true, 1, false);
        
        Queue<File> decompressedFiles = new ArrayDeque<>();
        for (File destFile = new File(destPath); roundsRemaining != 0; round++) {
            destFile = decompressOnce(destFile, codingMethod);
            decompressedFiles.offer(destFile);
        }
        
        while (decompressedFiles.size() > 1) {
            File tf = decompressedFiles.poll();
            tf.deleteOnExit();
        }
        File source = decompressedFiles.peek();
        
        LogUtil.end("解压完成", true, 1);
        return source.getPath();
    }
    
    /**
     * 进行一轮解压
     *
     * @return 解压后的文件
     */
    private File decompressOnce(File destFile, CodingMethod codingMethod) {
        File srcFile = null;
        try {
            LogUtil.begin(String.format("开始第 %d 轮解压", round), false, 2, true);
            srcFile = doDecompress(destFile, codingMethod);
            LogUtil.end(String.format("解压完毕，剩余 %d 轮", roundsRemaining), true, 2);
        } catch (HuffmanException | IOException e) {
            e.printStackTrace();
        }
        
        return srcFile;
    }
    
    /* 解压文件 */
    
    /**
     * 开始解压
     *
     * @return 解压后的文件
     */
    private File doDecompress(File destFile, CodingMethod codingMethod) throws HuffmanException, IOException {
        try (InputStream is = new FileInputStream(destFile)) {
            // 读取文件头
            FileHeader fileHeader = readHeader(is);
            
            roundsRemaining = fileHeader.getIterations() - 1;
            
            // 读取源文件名称
            byte[] bs = new byte[fileHeader.getSrcFullNameLength()];
            is.read(bs);
            String srcFullName = new String(bs, Constants.CHARSET);
            
            // 根据名称创建源文件
            PathHelper pathHelper = new PathHelper(destFile, srcFullName);
            File srcFile = pathHelper.generateFile();
            srcFile.createNewFile();
            
            // 读取 Huffman 编码（树）
            bs = new byte[fileHeader.getEndOfHuffmanCode() - Constants.PRE_OFFSET - fileHeader.getSrcFullNameLength()];
            is.read(bs);
            HuffmanMapping huffmanMapping = fileHeader.getStoreWay().parseHuffmanMapping(bs);
            
            long codingLength = destFile.length() - fileHeader.getEndOfHuffmanCode();
            IntConsumer logger = new IntConsumer() {
                long total = 0;
                
                @Override
                public void accept(int len) {
                    LogUtil.status("正在解压", total += len, codingLength);
                }
            };
            // 读取字节编码
            InputStreamSupplier inFactory = () -> is;
            OutputStreamSupplier outFactory = new FileWrapper(srcFile, false);
            codingMethod.parseByteCoding(inFactory, is, outFactory, huffmanMapping, fileHeader.getLastBitNo(),
                                         codingLength, logger);
            
            return srcFile;
        }
    }
    
    /**
     * 读取压缩文件的「{@link FileHeader 文件头}」
     *
     * @see Constants#PRE_OFFSET
     */
    private FileHeader readHeader(InputStream is) throws IOException, HuffmanException {
        byte[] bs = new byte[Constants.PRE_OFFSET];
        // 获取前 PRE_OFFSET 个字节
        is.read(bs);
        
        int i = 0;
        // 迭代压缩次数
        int iterations = bs[i++];
        // Huffman 编码（树）的存放方式
        int swCode = bs[i++] & 0xff;
        // 源文件名称长度
        int srcNameLength = bs[i++];
        // Huffman编码（树）截止位置
        int endOfHuffmanCode = 0;
        for (int j = 0; j < 4; j++) {
            endOfHuffmanCode <<= 8;
            endOfHuffmanCode |= bs[i++] & 0xff;
        }
        // 编码末字节的有效位数
        int lastBitNo = bs[i];
        
        return new FileHeader(iterations, StoreWay.forSwCode(swCode), srcNameLength, endOfHuffmanCode, lastBitNo);
    }
    
}
