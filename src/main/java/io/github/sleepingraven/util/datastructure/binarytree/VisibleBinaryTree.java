package io.github.sleepingraven.util.datastructure.binarytree;

/**
 * 表示可获取孩子节点和值的二叉树
 *
 * @author Carry
 * @since 2021/2/25
 */
public interface VisibleBinaryTree<V> extends BinaryTree {
    
    /**
     * 获取节点的值
     *
     * @return 节点的值
     */
    V getValue();
    
    /**
     * 获取左孩子
     *
     * @return 左孩子
     */
    VisibleBinaryTree<V> getLeft();
    
    /**
     * 获取右孩子
     *
     * @return 右孩子
     */
    VisibleBinaryTree<V> getRight();
    
}
