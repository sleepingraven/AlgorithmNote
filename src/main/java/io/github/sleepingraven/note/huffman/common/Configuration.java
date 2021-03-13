package io.github.sleepingraven.note.huffman.common;

/**
 * @author Carry
 * @since 2020/6/8
 */
public interface Configuration {
    String TEMP_FILE_EXTENSION = ".h_tmp";
    String TARGET_FILE_EXTENSION = ".h_tar";
    
    /**
     * 每次读写字节数
     */
    int IO_BYTES = 1024 * 1024;
    /**
     * 阻塞队列大小
     */
    int BLOCKING_SIZE = 30;
    
}
