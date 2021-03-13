package io.github.sleepingraven.util.datastructure.binarytree;

/**
 * 表示可设置孩子节点的二叉树
 *
 * @author Carry
 * @since 2021/2/21
 */
public interface RelinkableBinaryTree<T extends RelinkableBinaryTree<T>> extends BinaryTree {
    
    /**
     * 设置左孩子
     *
     * @param left
     *         左孩子
     */
    void setLeft(T left);
    
    /**
     * 设置右孩子
     *
     * @param right
     *         右孩子
     */
    void setRight(T right);
    
}
