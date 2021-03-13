package io.github.sleepingraven.util.datastructure.common.utils.generation;

import io.github.sleepingraven.util.datastructure.binarytree.EditableBinaryTree;

import java.util.function.Supplier;

/**
 * 根据 V 数组生成 {@link EditableBinaryTree}
 *
 * @author Carry
 * @since 2021/2/25
 */
public class EditableBinaryTreeGenerator<T extends EditableBinaryTree<T, V>, V>
        extends RelinkableBinaryTreeGenerator<T, V> {
    
    public EditableBinaryTreeGenerator(Supplier<? extends T> factory) {
        super(factory, EditableBinaryTree::setValue);
    }
    
}
