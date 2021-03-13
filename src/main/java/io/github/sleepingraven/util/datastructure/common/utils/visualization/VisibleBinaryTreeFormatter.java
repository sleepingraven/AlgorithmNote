package io.github.sleepingraven.util.datastructure.common.utils.visualization;

import io.github.sleepingraven.util.datastructure.binarytree.VisibleBinaryTree;
import io.github.sleepingraven.util.datastructure.common.Formatter;

/**
 * 格式化 {@link VisibleBinaryTree}
 *
 * @author Carry
 * @since 2021/2/25
 */
public class VisibleBinaryTreeFormatter extends BinaryTreeFormatter<VisibleBinaryTree<?>> {
    private static final Formatter<VisibleBinaryTree<?>> FORMATTER = new VisibleBinaryTreeFormatter();
    
    private VisibleBinaryTreeFormatter() {
        super(VisibleBinaryTree::getValue, VisibleBinaryTree::getLeft, VisibleBinaryTree::getRight);
    }
    
    public static String visibleToString(VisibleBinaryTree<?> root) {
        return FORMATTER.format(root);
    }
    
}
