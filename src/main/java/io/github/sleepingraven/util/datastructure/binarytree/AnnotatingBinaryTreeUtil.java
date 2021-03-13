package io.github.sleepingraven.util.datastructure.binarytree;

import io.github.sleepingraven.util.datastructure.common.Formatter;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;
import io.github.sleepingraven.util.datastructure.common.utils.MetaExplorer;
import io.github.sleepingraven.util.datastructure.common.utils.generation.BinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.BinaryTreeFormatter;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 生成和格式化 {@link BinaryTree}。<p>
 * 需要用 {@link Meta @Meta} 修饰节点值、left、right 和构造方法。<p>
 * 在实例化时，构造方法的参数为对应的默认值（基础类型和包装类是 0/false，其他类是 null）
 *
 * @author Carry
 * @since 2020/6/20
 */
public class AnnotatingBinaryTreeUtil {
    
    public static <T extends BinaryTree, V> T generate(V[] vs, Class<T> clazz) {
        BinaryTreeGenerator<T, V> generator = newGenerator(clazz);
        return generator.generate(vs);
    }
    
    public static <T extends BinaryTree, V> BinaryTreeGenerator<T, V> newGenerator(Class<T> clazz) {
        MetaExplorer<T> me = new MetaExplorer<>(clazz);
        Supplier<T> factory = me::newInstance;
        BiConsumer<T, V> valueSetter = (BiConsumer<T, V>) me.explore(Represent.VALUE).setter();
        BiConsumer<T, T> leftSetter = me.explore(Represent.LEFT, clazz).setter();
        BiConsumer<T, T> rightSetter = me.explore(Represent.RIGHT, clazz).setter();
        return new BinaryTreeGenerator<>(factory, valueSetter, leftSetter, rightSetter);
    }
    
    public static <T extends BinaryTree> String treeToString(T root) {
        Formatter<T> formatter = newFormatter(root);
        return formatter.format(root);
    }
    
    public static <T extends BinaryTree> Formatter<T> newFormatter(T root) {
        Class<T> clazz = (Class<T>) root.getClass();
        MetaExplorer<T> me = new MetaExplorer<>(clazz);
        Function<T, ?> valueGetter = me.explore(Represent.VALUE).getter();
        Function<T, T> leftGetter = me.explore(Represent.LEFT, clazz).getter();
        Function<T, T> rightGetter = me.explore(Represent.RIGHT, clazz).getter();
        return new BinaryTreeFormatter<>(valueGetter, leftGetter, rightGetter);
    }
    
}
