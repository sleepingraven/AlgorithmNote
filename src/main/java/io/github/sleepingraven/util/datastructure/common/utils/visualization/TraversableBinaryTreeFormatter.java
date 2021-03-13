package io.github.sleepingraven.util.datastructure.common.utils.visualization;

import io.github.sleepingraven.util.datastructure.binarytree.TraversableBinaryTree;

import java.util.function.Function;

/**
 * 格式化 {@link TraversableBinaryTree}
 *
 * @author Carry
 * @since 2020/6/1
 */
public class TraversableBinaryTreeFormatter<T extends TraversableBinaryTree<T>> extends BinaryTreeFormatter<T> {
    
    public TraversableBinaryTreeFormatter(Function<? super T, ?> toDisplay) {
        super(toDisplay, TraversableBinaryTree::getLeft, TraversableBinaryTree::getRight);
    }
    
}
