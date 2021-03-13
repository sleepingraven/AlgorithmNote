package io.github.sleepingraven.util.ac;

import io.github.sleepingraven.util.datastructure.binarytree.AnnotatingBinaryTreeUtil;
import io.github.sleepingraven.util.datastructure.binarytree.BinaryTree;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;
import io.github.sleepingraven.util.datastructure.linkedlist.AnnotatingLinkedListUtil;
import io.github.sleepingraven.util.datastructure.linkedlist.LinkedList;

import java.util.List;

/**
 * Leetcode 中的数据模型
 *
 * @author Carry
 * @since 2020/11/25
 */
public class Leetcode {
    
    /**
     * @see AnnotatingLinkedListUtil
     */
    public static class ListNode implements LinkedList {
        @Meta(Represent.VALUE)
        public int val;
        @Meta(Represent.NEXT)
        public ListNode next;
        
        @Meta(Represent.FACTORY)
        public ListNode(int x) {
            val = x;
        }
        
        @Override
        public String toString() {
            return AnnotatingLinkedListUtil.listToString(this);
        }
        
    }
    
    /**
     * @see AnnotatingBinaryTreeUtil
     */
    public static class TreeNode implements BinaryTree {
        @Meta(Represent.VALUE)
        public int val;
        @Meta(Represent.LEFT)
        public TreeNode left;
        @Meta(Represent.RIGHT)
        public TreeNode right;
        
        @Meta(Represent.FACTORY)
        public TreeNode(Integer value) {
            val = value;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public String toString() {
            return AnnotatingBinaryTreeUtil.treeToString(this);
        }
        
    }
    
    public static class NodeT {
        int val;
        List<NodeT> children;
        
        public NodeT() {
        }
        
        public NodeT(int val) {
            this.val = val;
        }
        
        public NodeT(int val, List<NodeT> children) {
            this.val = val;
            this.children = children;
        }
        
    }
    
    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;
        
        public Node() {
        }
        
        public Node(int _val) {
            val = _val;
        }
        
        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
        
    }
    
    public static class Node1 {
        int val;
        Node1 next;
        Node1 random;
        
        public Node1(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
        
    }
    
    public static class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
        
    }
    
    public interface NestedInteger {
        
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        boolean isInteger();
        
        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        Integer getInteger();
        
        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        List<NestedInteger> getList();
        
    }
    
}
