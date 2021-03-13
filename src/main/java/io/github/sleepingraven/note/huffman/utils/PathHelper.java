package io.github.sleepingraven.note.huffman.utils;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.function.IntFunction;

/**
 * 文件路径工具类。解析文件绝对路径，并支持生成目录下未存在的文件
 *
 * @author Carry
 * @since 2020/6/9
 */
@Getter
public class PathHelper {
    /**
     * 文件目录，包含「/」
     */
    private String directory;
    /**
     * 文件名称，不包含扩展名
     */
    private String name;
    /**
     * 文件后缀，包含前导「.」。默认则为 ""
     */
    private String extension;
    
    public PathHelper(File file) throws IOException {
        this(file.getCanonicalPath());
    }
    
    public PathHelper(String canonicalPath) {
        // 先解析路径，以防解析到路径中的「.」
        int end = parsePath(canonicalPath);
        parseFullName(canonicalPath.substring(end));
    }
    
    public PathHelper(File somethingInDirectory, String fullName) throws IOException {
        parsePath(somethingInDirectory.getCanonicalPath());
        parseFullName(fullName);
    }
    
    /* parser */
    
    /**
     * 从绝对路径，获得 directory 值
     *
     * @return directory 的截止位置
     */
    private int parsePath(String canonicalPath) {
        int lastIndex = Math.max(canonicalPath.lastIndexOf("/"), canonicalPath.lastIndexOf("\\"));
        int end = lastIndex + 1;
        directory = canonicalPath.substring(0, end);
        return end;
    }
    
    /**
     * 从 fullName 获得 name 和 extension 值
     */
    private void parseFullName(String fullName) {
        int lastIndex = fullName.lastIndexOf(".");
        if (lastIndex != -1) {
            name = fullName.substring(0, lastIndex);
            extension = fullName.substring(lastIndex);
        } else {
            name = fullName;
            extension = "";
        }
    }
    
    /* generator */
    
    public File generateFile() {
        return generateFile(extension);
    }
    
    public File generateFile(String extension) {
        return generateFile(directory, name, i -> name + "(" + i + ")", extension);
    }
    
    public static File generateFile(String directory, IntFunction<String> nameFunction, String extension) {
        return generateFile(directory, nameFunction.apply(0), nameFunction, extension);
    }
    
    /**
     * 以给定 directory、name 和 extension 生成一个文件。当该文件已存在时，调整 name 的值。该方法不会创建文件。
     */
    public static File generateFile(String directory, String name, IntFunction<String> adjustment, String extension) {
        File file = new File(directory + name + extension);
        for (int i = 1; file.exists(); i++) {
            file = new File(directory + adjustment.apply(i) + extension);
        }
        return file;
    }
    
}
