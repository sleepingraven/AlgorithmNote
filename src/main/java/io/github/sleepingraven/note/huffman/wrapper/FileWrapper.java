package io.github.sleepingraven.note.huffman.wrapper;

import java.io.*;

/**
 * 封装文件，提供对文件 IO 流的获取
 *
 * @author Carry
 * @since 2021/2/16
 */
public class FileWrapper implements InputStreamSupplier, OutputStreamSupplier {
    private final File file;
    private final boolean append;
    
    /**
     * 创建一个 InputStreamSupplier
     */
    public FileWrapper(File file) {
        this.file = file;
        this.append = false;
    }
    
    /**
     * 创建一个 OutputStreamSupplier
     */
    public FileWrapper(File file, boolean append) {
        this.file = file;
        this.append = append;
    }
    
    @Override
    public InputStream newInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }
    
    @Override
    public OutputStream newOutputStream() throws FileNotFoundException {
        return new FileOutputStream(file, append);
    }
    
}
