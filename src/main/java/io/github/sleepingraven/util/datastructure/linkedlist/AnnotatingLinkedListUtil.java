package io.github.sleepingraven.util.datastructure.linkedlist;

import io.github.sleepingraven.util.datastructure.common.Formatter;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;
import io.github.sleepingraven.util.datastructure.common.utils.MetaExplorer;
import io.github.sleepingraven.util.datastructure.common.utils.generation.LinkedListGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.LinkedListFormatter;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 生成和格式化 {@link LinkedList}。<p>
 * 需要用 {@link Meta @Meta} 修饰节点值、next 和构造方法。<p>
 * 在实例化时，构造方法的参数为对应的默认值（基础类型和包装类是 0/false，其他类是 null）
 *
 * @author Carry
 * @since 2020/6/21
 */
public class AnnotatingLinkedListUtil {
    
    public static <T extends LinkedList, V> T generate(V[] vs, Class<T> clazz) {
        LinkedListGenerator<T, V> generator = newGenerator(clazz);
        return generator.generate(vs);
    }
    
    public static <T extends LinkedList, V> LinkedListGenerator<T, V> newGenerator(Class<T> clazz) {
        MetaExplorer<T> me = new MetaExplorer<>(clazz);
        Supplier<T> factory = me::newInstance;
        BiConsumer<T, V> valueSetter = (BiConsumer<T, V>) me.explore(Represent.VALUE).setter();
        BiConsumer<T, T> nextSetter = me.explore(Represent.NEXT, clazz).setter();
        return new LinkedListGenerator<>(factory, valueSetter, nextSetter);
    }
    
    public static <T extends LinkedList> String listToString(T head) {
        Formatter<T> formatter = newFormatter(head);
        return formatter.format(head);
    }
    
    public static <T extends LinkedList> Formatter<T> newFormatter(T head) {
        Class<T> clazz = (Class<T>) head.getClass();
        MetaExplorer<T> me = new MetaExplorer<>(clazz);
        Function<T, ?> valueGetter = me.explore(Represent.VALUE).getter();
        Function<T, T> nextGetter = me.explore(Represent.NEXT, clazz).getter();
        return new LinkedListFormatter<>(valueGetter, nextGetter);
    }
    
}
