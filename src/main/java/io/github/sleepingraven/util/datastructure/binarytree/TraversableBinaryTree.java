package io.github.sleepingraven.util.datastructure.binarytree;

/**
 * 表示可获取孩子节点的二叉树
 *
 * @author Carry
 * @since 2021/2/21
 */
public interface TraversableBinaryTree<T extends TraversableBinaryTree<T>> extends BinaryTree {
    
    /**
     * 获取左孩子
     *
     * @return 左孩子
     */
    T getLeft();
    
    /**
     * 获取右孩子
     *
     * @return 右孩子
     */
    T getRight();
    
}
