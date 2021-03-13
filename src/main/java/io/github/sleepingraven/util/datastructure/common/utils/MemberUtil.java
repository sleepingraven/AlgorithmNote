package io.github.sleepingraven.util.datastructure.common.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 在继承树中查询泛型、成员或已注解的成员
 *
 * @author Carry
 * @since 2020/6/19
 */
public class MemberUtil {
    
    /**
     * 获取 SUP 类被继承时的实际类型参数。需要传入一个子类 SUB
     */
    public static <SUB extends SUP, SUP> Type[] getActualTypeArguments(@NonNull Class<SUB> subclass,
            @NonNull Class<SUP> parameterizedClass) {
        Class<? super SUB> directSubclass = subclass;
        while (!parameterizedClass.equals(directSubclass.getSuperclass())) {
            directSubclass = directSubclass.getSuperclass();
        }
        ParameterizedType genericSuperclass = (ParameterizedType) directSubclass.getGenericSuperclass();
        return genericSuperclass.getActualTypeArguments();
    }
    
    public static <A extends Annotation> Field getAnnotatedField(@NonNull Class<?> subclass,
            @NonNull AnnotationTracker<A> tracker) {
        return getAnnotatedField(subclass, null, tracker);
    }
    
    /**
     * 在继承树中查找含有指定注解的字段
     */
    public static <SUB extends SUP, SUP, A extends Annotation> Field getAnnotatedField(@NonNull Class<SUB> subclass,
            Class<SUP> baseclass, @NonNull AnnotationTracker<A> tracker) {
        for (Class<? super SUB> cls = subclass; !cls.equals(baseclass); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(tracker.aClass)) {
                    A annotation = field.getDeclaredAnnotation(tracker.aClass);
                    if (tracker.meet.test(annotation)) {
                        return field;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * 在继承树中查找给定名称的字段
     */
    public static Field getSourceField(Class<?> clazz, String fieldName) {
        for (Class<?> superclass = clazz; superclass != null; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
        }
        return null;
    }
    
    /**
     * 在继承树中查询一个与给定方法名称相同，且参数类型相同的方法（未比较参数的实际类型参数）
     */
    public static <SUB extends SUP, SUP> Method getOverrode(@NonNull Class<SUB> subclass, Class<SUP> baseclass,
            @NonNull Method overrideMethod) {
        String methodName = overrideMethod.getName();
        Type[] parameterTypes = overrideMethod.getParameterTypes();
        for (Class<? super SUB> cls = subclass; !cls.equals(baseclass); cls = cls.getSuperclass()) {
            for (Method method : cls.getDeclaredMethods()) {
                if (methodName.equals(method.getName())) {
                    if (Arrays.equals(parameterTypes, method.getParameterTypes())) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * 在继承树中查找含有指定注解的构造方法
     */
    public static <T, A extends Annotation> Constructor<T> getAnnotatedConstructor(@NonNull Class<T> clazz,
            @NonNull AnnotationTracker<A> tracker) {
        for (Constructor<?> c : clazz.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(tracker.aClass)) {
                A annotation = c.getDeclaredAnnotation(tracker.aClass);
                if (tracker.meet.test(annotation)) {
                    return (Constructor<T>) c;
                }
            }
        }
        return null;
    }
    
    /**
     * locator of annotation
     */
    @RequiredArgsConstructor
    public static class AnnotationTracker<A extends Annotation> {
        @NonNull
        private final Class<A> aClass;
        @NonNull
        private final Predicate<A> meet;
        
        /**
         * 测试 A 时，用 mapper 转换为 V，如果与给定的 expected 相等，则测试成功
         */
        public <V> AnnotationTracker(@NonNull Class<A> aClass, @NonNull Function<A, V> mapper, V expected) {
            this(aClass, a -> Predicate.isEqual(expected).test(mapper.apply(a)));
        }
        
    }
    
}
