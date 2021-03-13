package io.github.sleepingraven.util.datastructure.binarytree;

/**
 * 继承自 {@link AbstractPrintableBinaryTree} 和 {@link EditableBinaryTree}，子类需要提供 Getter/Setter
 *
 * @author Carry
 * @since 2021/2/21
 */
public abstract class BaseBinaryTree<T extends BaseBinaryTree<T, V>, V> extends AbstractPrintableBinaryTree<V>
        implements EditableBinaryTree<T, V> {
}
