package io.github.sleepingraven.util.datastructure.common.utils.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import io.github.sleepingraven.util.datastructure.binarytree.BinaryTree;
import io.github.sleepingraven.util.datastructure.common.Generative;
import io.github.sleepingraven.util.datastructure.common.IntObjPredicate;
import io.github.sleepingraven.util.datastructure.common.utils.FunctionalUtil;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 根据 V 数组生成 {@link BinaryTree}
 *
 * @author Carry
 * @since 2020/6/16
 */
@RequiredArgsConstructor
public class BinaryTreeGenerator<T extends BinaryTree, V> implements Generative<T, V> {
    @NonNull
    private final Function<? super V, ? extends T> instantiator;
    @NonNull
    private final BiConsumer<? super T, ? super T> leftSetter;
    @NonNull
    private final BiConsumer<? super T, ? super T> rightSetter;
    
    public BinaryTreeGenerator(Supplier<? extends T> factory, BiConsumer<? super T, ? super V> valueSetter,
            BiConsumer<? super T, ? super T> leftSetter, BiConsumer<? super T, ? super T> rightSetter) {
        this(FunctionalUtil.wrapping(factory, valueSetter), leftSetter, rightSetter);
    }
    
    @Override
    public T generate(V[] vs) {
        if (vs == null) {
            return null;
        }
        return dfs(vs, 0);
    }
    
    /**
     * preorder 创建树。
     * 没有使用 BFS：ArrayDeque 不能存放 null；如果节点为 null，就会剪枝，不用访问它的孩子；
     * 空间和深度有关，当树是满二叉树时空间更优
     */
    private T dfs(V[] vs, int i) {
        if (nullNode.test(i, vs)) {
            return null;
        }
        T node = instantiator.apply(vs[i]);
        if (node == null) {
            return null;
        }
        leftSetter.accept(node, dfs(vs, 2 * i + 1));
        rightSetter.accept(node, dfs(vs, 2 * i + 2));
        return node;
    }
    
    /**
     * null 策略。如何处理 vs 中的 null 值
     */
    @Setter(onParam_ = { @NonNull })
    private IntObjPredicate<Object[]> nullNode = globalNullNode;
    
    /**
     * 每个实例的默认 null 策略
     */
    public static IntObjPredicate<Object[]> globalNullNode;
    
    /**
     * 对所有 null 值，创建包含 null 值的节点
     */
    public static final IntObjPredicate<Object[]> NULL_VALUES_ALL_ALLOWED = (i, vs) -> i >= vs.length;
    /**
     * 一旦遇到 null 值，则节点也为 null
     */
    public static final IntObjPredicate<Object[]> NULL_VALUES_NOT_ALLOWED =
            NULL_VALUES_ALL_ALLOWED.or((i, vs) -> Objects.isNull(vs[i]));
    
    /**
     * 一旦遇到 null 值，如果 blank 的元素为 true，则创建包含 null 值的节点，否则节点为 null
     */
    public static IntObjPredicate<Object[]> nullValuesIfBlank(boolean[] blank) {
        return NULL_VALUES_ALL_ALLOWED.or((i, vs) -> (Objects.isNull(vs[i]) && !blank[i]));
    }
    
    static {
        globalNullNode = NULL_VALUES_NOT_ALLOWED;
    }
    
    /**
     * 以 BFS 实现的 {@link BinaryTreeGenerator#NULL_VALUES_ALL_ALLOWED} 策略
     */
    @Deprecated
    public T generate0(V[] vs) {
        if (vs == null || vs.length == 0) {
            return null;
        }
        final int nodes = vs.length;
        
        Queue<T> queue = new ArrayDeque<>(nodes);
        T root = instantiator.apply(vs[0]);
        queue.offer(root);
        for (int i = 1; i < nodes; i += 2) {
            T node = queue.poll();
            // left
            T p = instantiator.apply(vs[i]);
            leftSetter.accept(node, p);
            queue.offer(p);
            // right
            if (i + 1 < nodes) {
                p = instantiator.apply(vs[i + 1]);
                rightSetter.accept(node, p);
                queue.offer(p);
            }
        }
        return root;
    }
    
}
