package io.github.sleepingraven.util.datastructure.linkedlist;

import io.github.sleepingraven.util.datastructure.common.utils.visualization.TraversableBinaryTreeFormatter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.VisibleLinkedListFormatter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.Visualization;

/**
 * 如果实现了 {@link Visualization} 接口，则会使用可视化类 {@link TraversableBinaryTreeFormatter} 打印。提供了 Getter 方法
 *
 * @author Carry
 * @since 2020/6/16
 */
public abstract class AbstractPrintableLinkedList<V> implements VisibleLinkedList<V> {
    
    @Override
    public String toString() {
        if (this instanceof Visualization) {
            return VisibleLinkedListFormatter.visibleToString(this);
        } else {
            return super.toString();
        }
    }
    
}
