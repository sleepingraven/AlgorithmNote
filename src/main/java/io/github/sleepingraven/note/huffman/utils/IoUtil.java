package io.github.sleepingraven.note.huffman.utils;

import io.github.sleepingraven.note.huffman.wrapper.OutputStreamSupplier;

import java.io.*;

/**
 * IO 工具类。序列化操作、关闭资源
 *
 * @author Carry
 * @since 2020/1/21
 */
public class IoUtil {
    
    /**
     * 序列化，空间占用较大
     */
    public static void writeSerializable(File file, Object obj, boolean append) throws IOException {
        try (OutputStream os = new FileOutputStream(file, append);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(obj);
        }
    }
    
    public static void writeSerializable(OutputStreamSupplier outFactory, Object obj) throws IOException {
        try (OutputStream os = outFactory.newOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(obj);
        }
    }
    
    public static Object readSerializable(File file) throws IOException, ClassNotFoundException {
        try (InputStream is = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        }
    }
    
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
