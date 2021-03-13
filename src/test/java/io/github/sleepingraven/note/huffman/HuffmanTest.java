package io.github.sleepingraven.note.huffman;

import io.github.sleepingraven.note.huffman.strategies.coding.CodingMethod;
import io.github.sleepingraven.note.huffman.strategies.coding.concurrency.ConcurrencyBitStreamCodingMethod;
import io.github.sleepingraven.note.huffman.strategies.store.StoreWay;
import io.github.sleepingraven.note.huffman.strategies.store.custom.CodeCustom;

/**
 * @author Carry
 * @since 2021/2/19
 */
public class HuffmanTest {
    private static final String ROOT_PATH = HuffmanTest.class.getResource("/").getPath() + "huffman/";
    private static final int COMPRESS_TIMES = 1;
    
    /**
     * <h2>test1.txt</h2>
     * <h3>测试文本</h3>
     * <blockquote>
     * <pre>
     * abacb
     * bcac
     * de
     * f ab</pre>
     * </blockquote>
     * <h3>Huffman 树</h3>
     * <blockquote>
     * <pre>
     *       N
     *  N         N
     * a b    N        N
     *      10 13   c     N
     *                  N   N
     *                32 e f d</pre>
     * </blockquote>
     * <p>
     * <h2>test2.txt</h2>
     * <h3>测试文本</h3>
     * <blockquote>
     * <pre>
     * ab,bsc,fsdifo,sb,abaccab</pre>
     * </blockquote>
     * <h3>字节编码</h3>
     * <blockquote>
     * <pre>
     * 111 00 110 00 100 011 110 010 100 10111 1010 010 10110 110 100 00 110 111 00 111 011 011 111 00
     * [-26, 35, -54, 94, -107, -76, 55, 59, 124]
     * 11100110 00100011 11001010 01011110 10010101 10110100 00110111 00111011 01111100</pre>
     * </blockquote>
     * <p>
     * <h2>test-empty.txt</h2>
     * <p>
     * <h2>test-single-1.txt</h2>
     * <h3>测试文本</h3>
     * <blockquote>
     * <pre>
     * a</pre>
     * </blockquote>
     * <p>
     * <h2>test-single-3.txt</h2>
     * <h3>测试文本</h3>
     * <blockquote>
     * <pre>
     * aaa</pre>
     * </blockquote>
     */
    private static String srcPath() {
        // return ROOT_PATH + "test1.txt";
        // return ROOT_PATH + "test2.txt";
        // return ROOT_PATH + "test-empty.txt";
        // return ROOT_PATH + "test-single-1.txt";
        // return ROOT_PATH + "test-single-3.txt";
        // return "D:/w0909ppgt4s.mp4";
        // return "D:/Java并发编程的艺术.txt";
        return "D:/算法笔记-胡凡.pdf";
    }
    
    /**
     * TreeSerialization
     * CodeCustom
     */
    private static StoreWay storeWay() {
        return new CodeCustom();
    }
    
    /**
     * DefaultCodingMethod
     * DefaultBitStreamCodingMethod
     * ConcurrencyBitStreamCodingMethod
     */
    private static CodingMethod codingMethod1() {
        return new ConcurrencyBitStreamCodingMethod();
    }
    
    private static CodingMethod codingMethod2() {
        return new ConcurrencyBitStreamCodingMethod();
    }
    
    /**
     * &#64;Test 不能正常打印进度
     */
    public static void main(String[] args) {
        String destPath = doTestCompress(srcPath(), storeWay(), codingMethod1());
        String decompressPath = doTestDecompress(destPath, codingMethod2());
        System.out.println(destPath);
        System.out.println(decompressPath);
    }
    
    public static String doTestCompress(String srcPath, StoreWay storeWay, CodingMethod codingMethod) {
        Compressor compressor = new Compressor(COMPRESS_TIMES);
        return compressor.compress(srcPath, storeWay, codingMethod);
    }
    
    public static String doTestDecompress(String destPath, CodingMethod codingMethod) {
        Decompressor decompressor = new Decompressor();
        return decompressor.decompress(destPath, codingMethod);
    }
    
}
