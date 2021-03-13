package io.github.sleepingraven.note.helloworld.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 * 来源：漫画算法：小灰的算法之旅
 *
 * @author 10132
 */
public class BinaryTree {
    
    /**
     * 构建二叉树，先序方式
     *
     * @param inputList
     *         输入序列
     *
     * @return 根节点
     */
    public static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (null == inputList || inputList.isEmpty()) {
            return null;
        }
        
        Integer data = inputList.removeFirst();
        if (null != data) {
            node = new TreeNode(data);
            node.leftChild = createBinaryTree(inputList);
            node.rightChild = createBinaryTree(inputList);
        }
        
        return node;
    }
    
    /* 1 遍历 */
    /* 1.1 深度优先遍历 */
    
    /**
     * 先序遍历
     *
     * @param node
     *         节点
     */
    public static void preOrderTraveral(TreeNode node) {
        if (null == node) {
            return;
        }
        
        System.out.print(node.data + ", ");
        preOrderTraveral(node.leftChild);
        preOrderTraveral(node.rightChild);
    }
    
    /**
     * 中序遍历
     *
     * @param node
     *         节点
     */
    public static void inOrderTraveral(TreeNode node) {
        if (null == node) {
            return;
        }
        
        inOrderTraveral(node.leftChild);
        System.out.print(node.data + ", ");
        inOrderTraveral(node.rightChild);
    }
    
    /**
     * 后序遍历
     *
     * @param node
     *         节点
     */
    public static void postOrderTraveral(TreeNode node) {
        if (null == node) {
            return;
        }
        
        postOrderTraveral(node.leftChild);
        postOrderTraveral(node.rightChild);
        System.out.print(node.data + ", ");
    }
    
    /**
     * 先序遍历，非递归
     *
     * @param node
     *         根节点
     */
    public static void preOrderTraveralWithStack(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        
        while (null != node || !stack.isEmpty()) {
            // 迭代访问节点的左孩子，并入栈
            while (null != node) {
                System.out.print(node.data + ", ");
                stack.push(node);
                node = node.leftChild;
            }
            
            // 如果没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }
    
    /**
     * 中序遍历，非递归
     *
     * @param node
     *         根节点
     */
    public static void inOrderTraveralWithStack(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        
        while (null != node || !stack.isEmpty()) {
            // 迭代访问节点的左孩子，并入栈
            while (null != node) {
                //
                stack.push(node);
                node = node.leftChild;
            }
            
            // 如果没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                node = stack.pop();
                System.out.print(node.data + ", ");
                node = node.rightChild;
            }
        }
    }
    
    /* 1.2 广度优先遍历 */
    
    /**
     * 层次遍历，非递归
     *
     * @param node
     *         根节点
     */
    public static void levelOrderTraversal(TreeNode node) {
        Queue<TreeNode> queue = new LinkedList<>();
        
        queue.offer(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.print(node.data + ", ");
            if (null != node.leftChild) {
                queue.offer(node.leftChild);
            }
            if (null != node.rightChild) {
                queue.offer(node.rightChild);
            }
        }
    }
    
    /**
     * <p>
     * <blockquote>
     * <pre>
     *        3
     *    2       8
     *  9   t   n   4
     * n n n n
     * </pre>
     * </blockquote>
     * </p>
     */
    public static void main(String[] args) {
        LinkedList<Integer> inputList =
                new LinkedList<>(Arrays.asList(3, 2, 9, null, null, 10, null, null, 8, null, 4));
        TreeNode root = createBinaryTree(inputList);
        
        System.out.println("先序遍历：");
        preOrderTraveral(root);
        System.out.println();
        preOrderTraveralWithStack(root);
        System.out.println();
        
        System.out.println("中序遍历：");
        inOrderTraveral(root);
        System.out.println();
        inOrderTraveralWithStack(root);
        System.out.println();
        
        System.out.println("后序遍历：");
        postOrderTraveral(root);
        System.out.println();
        
        System.out.println("层次遍历：");
        levelOrderTraversal(root);
        System.out.println();
    }
    
    /**
     * 二叉树节点
     */
    private static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;
        
        TreeNode(int data) {
            this.data = data;
        }
        
    }
    
}
