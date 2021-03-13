package io.github.sleepingraven.util.datastructure.common.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Carry
 * @since 2020/6/19
 */
public class StringHelper {
    private final List<String> list = new LinkedList<>();
    
    /**
     * 添加一行数据
     */
    public StringHelper add(String line) {
        list.add(line);
        return this;
    }
    
    public StringHelper add(String line, int index) {
        list.add(index, line);
        return this;
    }
    
    /**
     * 对 toString() 的结果 ，使用 {@link String#format(String, Object...)} 格式化
     */
    public String get(Object... args) {
        return String.format(toString(), args);
    }
    
    @Override
    public String toString() {
        return String.join("\n", list);
    }
    
    public int size() {
        return list.size();
    }
    
    public void clear() {
        list.clear();
    }
    
}
