package io.github.sleepingraven.note.huffman;

import lombok.RequiredArgsConstructor;
import io.github.sleepingraven.note.huffman.common.Configuration;
import io.github.sleepingraven.note.huffman.common.Constants;
import io.github.sleepingraven.note.huffman.entity.ByteDataNode;
import io.github.sleepingraven.note.huffman.entity.FileHeader;
import io.github.sleepingraven.note.huffman.strategies.coding.CodingMethod;
import io.github.sleepingraven.note.huffman.strategies.store.StoreWay;
import io.github.sleepingraven.note.huffman.utils.LogUtil;
import io.github.sleepingraven.note.huffman.utils.PathHelper;
import io.github.sleepingraven.note.huffman.utils.PriorityQueueInitializedWithIncomparable;
import io.github.sleepingraven.note.huffman.utils.io.BytesReader;
import io.github.sleepingraven.note.huffman.wrapper.*;

import java.io.*;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * 压缩器
 * future：
 * <ul>
 *     <li>目录和多文件压缩</li>
 *     <li>需要分解为更多类</li>
 * </ul>
 *
 * @author Carry
 * @see Compressor#doCompress(File, HuffmanMapping, StoreWay, CodingMethod)
 * @since 2020/1/20
 */
@RequiredArgsConstructor
public class Compressor {
    /**
     * 迭代压缩的次数
     */
    private final int compressTimes;
    /**
     * 当前迭代次数
     */
    private int round = 1;
    
    public String compress(String srcPath, StoreWay storeWay, CodingMethod codingMethod) {
        LogUtil.begin("开始压缩", true, 1, false);
        
        Queue<File> compressedFiles = new ArrayDeque<>(compressTimes);
        for (File srcFile = new File(srcPath); round <= compressTimes; round++) {
            srcFile = compressOnce(srcFile, storeWay, codingMethod);
            compressedFiles.offer(srcFile);
        }
        
        String destPath = compressedFiles.peek().getPath();
        while (compressedFiles.size() > 1) {
            File tf = compressedFiles.poll();
            tf.deleteOnExit();
        }
        File target = compressedFiles.peek();
        PathHelper pathHelper = new PathHelper(destPath);
        File real = pathHelper.generateFile(Configuration.TARGET_FILE_EXTENSION);
        if (target.renameTo(real)) {
            target = real;
        }
        
        LogUtil.end("压缩完成", true, 1);
        return target.getPath();
    }
    
    /**
     * 进行一轮压缩
     *
     * @return 压缩文件
     */
    private File compressOnce(File srcFile, StoreWay storeWay, CodingMethod codingMethod) {
        File destFile = null;
        try {
            LogUtil.begin(String.format("开始第 %d 轮压缩", round), false, 2, false);
            
            HuffmanTreeParser huffmanTreeParser = new HuffmanTreeParser();
            // 读取文件，统计字节出现次数
            LogUtil.begin("开始解析", false, 3, true);
            Map<Byte, Long> frequencyMap = huffmanTreeParser.getFrequencyMap(srcFile);
            LogUtil.end("解析完毕", false, 3);
            // 建立 Huffman 树
            ByteDataNode root = huffmanTreeParser.buildHuffmanTree(frequencyMap);
            // 获得 Huffman 编码（树）
            HuffmanMapping huffmanMapping = new HuffmanMapping(root);
            
            // 写入压缩文件
            LogUtil.begin("开始压缩", false, 3, true);
            destFile = doCompress(srcFile, huffmanMapping, storeWay, codingMethod);
            LogUtil.end("压缩完毕", false, 3);
            
            LogUtil.end(String.format("压缩完成，剩余 %d 轮", compressTimes - round), true, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return destFile;
    }
    
    private static class HuffmanTreeParser {
        
        /* 文件解析 */
        
        /**
         * 读取文件，统计各字节出现的频数
         *
         * @return 字节与频数的映射
         */
        private Map<Byte, Long> getFrequencyMap0(File srcFile) throws IOException {
            // todo 使用 CountMap
            Map<Byte, Long> frequencyMap = new HashMap<>((int) (256 / 0.75));
            
            try (InputStream is = new FileInputStream(srcFile)) {
                byte[] bs = new byte[Configuration.IO_BYTES];
                long total = 0;
                int len;
                while ((len = is.read(bs)) > 0) {
                    LogUtil.status("正在解析", total += len, srcFile.length());
                    for (int i = 0; i < len; i++) {
                        frequencyMap.put(bs[i], frequencyMap.getOrDefault(bs[i], 0L) + 1);
                    }
                }
            }
            
            return frequencyMap;
        }
        
        /**
         * 生产者消费者模型来统计字节频率
         */
        private Map<Byte, Long> getFrequencyMap(File srcFile) throws IOException {
            // 开启读线程（生产者）
            BytesReader bytesReader = new BytesReader(new FileWrapper(srcFile));
            Map<Byte, Long> frequencyMap = new HashMap<>((int) (256 / 0.75));
            
            // 消费者，统计 byte 频率
            byte[] bs;
            long total = 0;
            while (bytesReader.hasNext()) {
                bs = bytesReader.nextBatch();
                LogUtil.status("正在解析", total += bs.length, srcFile.length());
                for (byte b : bs) {
                    frequencyMap.put(b, frequencyMap.getOrDefault(b, 0L) + 1);
                }
            }
            
            return frequencyMap;
        }
        
        /* Huffman 树相关操作。节点数据类型是 byte，若要提高通用性，可创建 HuffmanTreeNode<T> 类 */
        
        /**
         * 根据字节映射，构建 Huffman 树
         */
        private ByteDataNode buildHuffmanTree(Map<Byte, Long> frequencyMap) {
            // 依次创建节点
            List<ByteDataNode> nodes = frequencyMap.entrySet()
                                                   .stream()
                                                   .map(e -> new ByteDataNode(e.getKey(), e.getValue()))
                                                   .collect(Collectors.toList());
            
            PriorityQueue<ByteDataNode> pq = new PriorityQueueInitializedWithIncomparable<>(nodes,
                                                                                            Comparator.comparingLong(
                                                                                                    ByteDataNode::getWeight));
            
            // 构建 Huffman 树
            while (pq.size() > 1) {
                ByteDataNode bn1 = pq.poll();
                ByteDataNode bn2 = pq.poll();
                pq.offer(new ByteDataNode(bn1.getWeight() + bn2.getWeight(), bn1, bn2));
            }
            
            return pq.peek();
        }
        
    }
    
    /* 压缩文件 */
    
    /**
     * 开始压缩<p>
     * 需要将 Huffman 编码（树）和压缩后的字节编码存放到压缩文件<p>
     * 压缩文件包括元数据和内容数据。<p>
     * <ol>
     *     <li>
     *         元数据，即压缩文件的「{@link FileHeader 文件头}」
     *     </li>
     *     <li>
     *         内容数据：
     *         <ul>
     *             <li>源文件名称（UTF-8）；</li>
     *             <li>Huffman 编码（树）；</li>
     *             <li>字节编码。</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * @return 压缩文件
     *
     * @see Constants#PRE_OFFSET
     */
    private File doCompress(File srcFile, HuffmanMapping huffmanMapping, StoreWay storeWay, CodingMethod codingMethod)
            throws IOException {
        // 创建目标文件
        PathHelper pathHelper = new PathHelper(srcFile);
        File destFile = pathHelper.generateFile(Configuration.TEMP_FILE_EXTENSION);
        destFile.createNewFile();
        
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(destFile, "rw")) {
            // 文件头预留空间
            randomAccessFile.seek(0);
            for (int i = 0; i < Constants.PRE_OFFSET; i++) {
                randomAccessFile.writeByte(0);
            }
            FileHeader fileHeader = new FileHeader();
            fileHeader.setStoreWay(storeWay);
            
            // 写入源文件名称
            byte[] srcFullName = (pathHelper.getName() + pathHelper.getExtension()).getBytes(Constants.CHARSET);
            randomAccessFile.write(srcFullName);
            fileHeader.setSrcFullNameLength(srcFullName.length);
            
            OutputStreamSupplier outFactory = new FileWrapper(destFile, true);
            OutputAdapter out = new RandomAccessFileAdapter(randomAccessFile);
            // 写入Huffman编码/树
            storeWay.writeHuffmanMapping(outFactory, out, huffmanMapping);
            fileHeader.setEndOfHuffmanCode((int) destFile.length());
            randomAccessFile.seek(destFile.length());
            
            InputStreamSupplier inFactory = new FileWrapper(srcFile);
            IntConsumer logger = new IntConsumer() {
                long total = 0;
                
                @Override
                public void accept(int len) {
                    LogUtil.status("正在压缩", total += len, srcFile.length());
                }
            };
            // 写入字节编码
            int lastBitNo =
                    codingMethod.writeByteCoding(inFactory, outFactory, out, huffmanMapping, srcFile.length(), logger);
            fileHeader.setLastBitNo(lastBitNo);
            
            // 写入文件头
            writeHeader(randomAccessFile, fileHeader);
        }
        
        return destFile;
    }
    
    private void writeHeader(RandomAccessFile randomAccessFile, FileHeader fileHeader) throws IOException {
        randomAccessFile.seek(0);
        randomAccessFile.writeByte(round);
        randomAccessFile.writeByte(fileHeader.getStoreWay().getSwCode());
        randomAccessFile.writeByte(fileHeader.getSrcFullNameLength());
        randomAccessFile.writeInt(fileHeader.getEndOfHuffmanCode());
        randomAccessFile.writeByte(fileHeader.getLastBitNo());
    }
    
}
