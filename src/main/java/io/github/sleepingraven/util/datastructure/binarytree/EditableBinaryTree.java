package io.github.sleepingraven.util.datastructure.binarytree;

/**
 * 表示可设置孩子节点和值的二叉树
 *
 * @author Carry
 * @since 2021/2/25
 */
public interface EditableBinaryTree<T extends EditableBinaryTree<T, V>, V> extends RelinkableBinaryTree<T> {
    
    /**
     * 设置节点值
     *
     * @param value
     *         节点值
     */
    void setValue(V value);
    
}
