package io.github.sleepingraven.util.datastructure.binarytree;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.Visualization;

/**
 * 具有必需属性的二叉树，包含有 value 属性、left、right 节点、无参构造和 Getter/Setter 方法。如果子类实现 {@link Visualization} 接口，toString() 方法会格式化树
 *
 * @author Carry
 * @since 2020/6/18
 */
@NoArgsConstructor
@Getter
@Setter
public class StandardBinaryTree<T extends StandardBinaryTree<T, V>, V> extends BaseBinaryTree<T, V>
        implements Visualization {
    private V value;
    private T left;
    private T right;
    
}
