package io.github.sleepingraven.note.huffman.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 以给定集合初始化，并指定比较器
 *
 * @author Carry
 * @since 2020/6/8
 */
public class PriorityQueueInitializedWithIncomparable<E> extends PriorityQueue<E> {
    private static final Field SUPER_COMPARATOR;
    private static final Method INIT_FROM_COLLECTION;
    
    static {
        Field cf = null;
        Method cm = null;
        try {
            cf = PriorityQueue.class.getDeclaredField("comparator");
            cf.setAccessible(true);
            cm = PriorityQueue.class.getDeclaredMethod("initFromCollection", Collection.class);
            cm.setAccessible(true);
        } catch (NoSuchFieldException | NoSuchMethodException ignored) {
        }
        SUPER_COMPARATOR = cf;
        INIT_FROM_COLLECTION = cm;
    }
    
    /**
     * 以给定集合初始化，并指定比较器
     */
    public PriorityQueueInitializedWithIncomparable(Collection<E> c, Comparator<E> comparator) {
        try {
            SUPER_COMPARATOR.set(this, comparator);
            INIT_FROM_COLLECTION.invoke(this, c);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
}
