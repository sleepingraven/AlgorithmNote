package io.github.sleepingraven.util.advanced;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树
 *
 * @author Carry
 * @since 2021/2/4
 */
public class Trie {
    final Node root;
    
    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node p = root;
        for (char ch : word.toCharArray()) {
            p = p.children.computeIfAbsent(ch, c -> new Node());
        }
        p.end = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node p = find(word);
        return p != null && p.end;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node p = find(prefix);
        return p != null;
    }
    
    private Node find(String word) {
        Node p = root;
        for (char ch : word.toCharArray()) {
            if (!p.children.containsKey(ch)) {
                return null;
            }
            p = p.children.get(ch);
        }
        return p;
    }
    
    /**
     * 树节点
     */
    static class Node {
        final Map<Character, Node> children;
        boolean end;
        
        public Node() {
            this.children = new HashMap<>(256);
        }
        
    }
    
}
