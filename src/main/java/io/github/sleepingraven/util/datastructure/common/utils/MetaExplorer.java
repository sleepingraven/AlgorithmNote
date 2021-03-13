package io.github.sleepingraven.util.datastructure.common.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;
import io.github.sleepingraven.util.datastructure.common.utils.MemberUtil.AnnotationTracker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 查找 {@link Meta} 修饰的构造方法和字段，提供构造和 Getter/Setter
 *
 * @author Carry
 * @since 2020/6/21
 */
public class MetaExplorer<T> {
    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final Object[] params;
    
    public MetaExplorer(@NonNull Class<T> clazz) {
        this.clazz = clazz;
        
        // constructor
        this.constructor = MemberUtil.getAnnotatedConstructor(clazz, new MetaTracker(Represent.FACTORY));
        if (constructor == null) {
            throw new RuntimeException("没有找到构造方法");
        }
        constructor.setAccessible(true);
        
        // params
        params = getDefaultParameterValues(constructor.getParameterTypes());
    }
    
    /* factory */
    
    public T newInstance() {
        try {
            return constructor.newInstance(params);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    /* field access */
    
    /**
     * 查找含有给定 Represent 的字段，不进行类型检查
     */
    public FieldAccessor<?> explore(@NonNull Represent val) {
        Field annotatedField = getAnnotatedField(val);
        return new FieldAccessor<>(annotatedField);
    }
    
    /**
     * 查找含有给定 Represent 的字段，同 type 检查类型
     */
    public <U> FieldAccessor<U> explore(@NonNull Represent val, @NonNull Class<U> type) {
        Field annotatedField = getAnnotatedField(val);
        Class<?> actualType = annotatedField.getType();
        if (type != null && !actualType.equals(type)) {
            throw new RuntimeException(String.format("传入的类型错误。预期：%s，传入：%s", actualType, type));
        }
        return new FieldAccessor<>(annotatedField);
    }
    
    /* find and access */
    
    /**
     * 包装字段，提供 Getter/Setter
     *
     * @param <U>
     *         字段的类型
     */
    @RequiredArgsConstructor
    public class FieldAccessor<U> {
        @NonNull
        final Field field;
        
        public Function<T, U> getter() {
            return this::get;
        }
        
        public BiConsumer<T, U> setter() {
            return this::set;
        }
        
        public U get(T obj) {
            try {
                return (U) field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
        public void set(T obj, U val) {
            try {
                field.set(obj, val);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    /**
     * 查找含有给定 Represent 的字段
     */
    private Field getAnnotatedField(@NonNull Represent val) {
        Field annotatedField = MemberUtil.getAnnotatedField(clazz, new MetaTracker(val));
        if (annotatedField == null) {
            throw new RuntimeException(String.format("找不到被修饰的字段：%s", val));
        }
        annotatedField.setAccessible(true);
        return annotatedField;
    }
    
    /**
     * locator of Meta
     */
    private static class MetaTracker extends AnnotationTracker<Meta> {
        
        public MetaTracker(@NonNull Represent val) {
            super(Meta.class, Meta::value, val);
        }
        
    }
    
    /* helper */
    
    private static final Map<Class<?>, ?> PRIMITIVE_AND_BOXED_DEFAULT_VALUES = new HashMap<Class<?>, Object>(32) {{
        put(short.class, (short) 0);
        put(int.class, 0);
        put(long.class, 0L);
        put(char.class, (char) 0);
        put(byte.class, (byte) 0);
        put(float.class, 0f);
        put(double.class, 0d);
        put(boolean.class, false);
        put(Short.class, (short) 0);
        put(Integer.class, 0);
        put(Long.class, 0L);
        put(Character.class, (char) 0);
        put(Byte.class, (byte) 0);
        put(Float.class, 0f);
        put(Double.class, 0d);
        put(Boolean.class, Boolean.FALSE);
    }};
    
    private static Object[] getDefaultParameterValues(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                     .map(cls -> PRIMITIVE_AND_BOXED_DEFAULT_VALUES.getOrDefault(cls, null))
                     .toArray();
    }
    
}
