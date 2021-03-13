package io.github.sleepingraven.util.datastructure.binarytree;

import io.github.sleepingraven.util.datastructure.common.utils.visualization.VisibleBinaryTreeFormatter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.Visualization;

/**
 * 如果实现了 {@link Visualization} 接口，则会使用可视化类 {@link VisibleBinaryTreeFormatter} 打印。提供了 Getter 方法
 *
 * @author Carry
 * @since 2020/6/16
 */
public abstract class AbstractPrintableBinaryTree<V> implements VisibleBinaryTree<V> {
    
    @Override
    public String toString() {
        if (this instanceof Visualization) {
            return VisibleBinaryTreeFormatter.visibleToString(this);
        } else {
            return super.toString();
        }
    }
    
}
