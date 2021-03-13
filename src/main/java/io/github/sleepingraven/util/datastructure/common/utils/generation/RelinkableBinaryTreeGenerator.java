package io.github.sleepingraven.util.datastructure.common.utils.generation;

import io.github.sleepingraven.util.datastructure.binarytree.RelinkableBinaryTree;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 根据 V 数组生成 {@link RelinkableBinaryTree}
 *
 * @author Carry
 * @since 2020/6/18
 */
public class RelinkableBinaryTreeGenerator<T extends RelinkableBinaryTree<T>, V> extends BinaryTreeGenerator<T, V> {
    
    public RelinkableBinaryTreeGenerator(Function<? super V, ? extends T> instantiator) {
        super(instantiator, RelinkableBinaryTree::setLeft, RelinkableBinaryTree::setRight);
    }
    
    public RelinkableBinaryTreeGenerator(Supplier<? extends T> factory, BiConsumer<? super T, ? super V> valueSetter) {
        super(factory, valueSetter, RelinkableBinaryTree::setLeft, RelinkableBinaryTree::setRight);
    }
    
}
