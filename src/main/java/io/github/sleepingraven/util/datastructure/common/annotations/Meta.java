package io.github.sleepingraven.util.datastructure.common.annotations;

import java.lang.annotation.*;

/**
 * 标识需要查找的成员
 *
 * @author Carry
 * @since 2020/6/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR })
@Inherited
public @interface Meta {
    
    /**
     * 描述标识了哪一种成员
     *
     * @author Carry
     * @since 2020/6/19
     */
    Represent value();
    
    enum Represent {
        /**
         * 表示节点值成员
         */
        VALUE,
        /**
         * 表示二叉树左孩子成员
         */
        LEFT,
        /**
         * 表示二叉树右孩子成员
         */
        RIGHT,
        /**
         * 表示构造方法成员
         */
        FACTORY,
        /**
         * 表示链表后继节点成员
         */
        NEXT,
        /**
         * 表示链表前驱节点成员（保留，还未使用）
         */
        PREV,
        /**
         * 表示父节点成员（保留，还未使用）
         */
        PARENT,
        /**
         * 表示接收 value 的构造方法成员（保留，还未使用）
         */
        VALUED_FACTORY,
    }
    
}
