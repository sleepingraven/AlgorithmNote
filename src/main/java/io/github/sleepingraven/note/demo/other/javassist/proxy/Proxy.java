package io.github.sleepingraven.note.demo.other.javassist.proxy;

import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.reflect.InvocationHandler;

/**
 * @author Carry
 * @since 2020/6/16
 */
public class Proxy {
    // 动态生成代理类的前缀
    private static final String PROXY_CLASSNAME_PREFIX = "$Proxy_";
    // 缓存容器，防止生成同一个Class文件在同一个ClassLoader加载崩溃的问题
    private static final Map<String, Class<?>> PROXY_CLASS_CACHE = new HashMap<>();
    
    /* proxy caches */
    
    /**
     * 缓存已经生成的代理类的Class，key值根据 classLoader 和 targetClass 共同决定
     */
    private static <T> void saveProxyClassCache(ClassLoader classLoader, Class<T> targetClass,
            Class<? extends T> proxyClass) {
        PROXY_CLASS_CACHE.put(getCacheKey(classLoader, targetClass), proxyClass);
    }
    
    /**
     * 从缓存中取得代理类的Class，如果没有则返回 null
     */
    private static <T> Class<T> getProxyClassCache(ClassLoader classLoader, Class<T> targetClass) {
        return (Class<T>) PROXY_CLASS_CACHE.get(getCacheKey(classLoader, targetClass));
    }
    
    private static String getCacheKey(ClassLoader classLoader, Class<?> targetClass) {
        return classLoader.toString() + "::" + targetClass.getName();
    }
    
    /* proxy instantiation */
    
    /**
     * 返回一个动态创建的代理类，此类继承自 targetClass
     *
     * @param classLoader
     *         从哪一个ClassLoader加载Class
     * @param invocationHandler
     *         代理类中每一个方法调用时的回调接口
     * @param targetClass
     *         被代理对象
     * @param targetConstructor
     *         被代理对象的某一个构造器，用于决定代理对象实例化时采用哪一个构造器
     * @param targetParam
     *         被代理对象的某一个构造器的参数，用于实例化构造器
     */
    public static <T> T newProxyInstance(ClassLoader classLoader, InvocationHandler invocationHandler,
            Class<T> targetClass, Constructor<T> targetConstructor, Object... targetParam) {
        if (classLoader == null || targetClass == null || invocationHandler == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            // 查看是否有缓存
            Class<? extends T> proxyClass = getProxyClassCache(classLoader, targetClass);
            if (proxyClass == null) {
                ClassPool pool = new ClassPool(true);
                pool.importPackage(InvocationHandler.class.getName());
                pool.importPackage(Method.class.getName());
                // 被代理类
                CtClass targetCtClass = pool.get(targetClass.getName());
                // 检查被代理类是否是 final的
                if ((targetCtClass.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    throw new IllegalArgumentException("class is final");
                }
                // 新建代理类
                CtClass proxyCtClass = pool.makeClass(generateProxyClassName(targetClass));
                // 设置描述符
                proxyCtClass.setModifiers(Modifier.PUBLIC | Modifier.FINAL);
                // 设置继承关系
                proxyCtClass.setSuperclass(targetCtClass);
                // 添加构造器
                addConstructors(pool, proxyCtClass, targetCtClass);
                // 添加方法
                addMethods(proxyCtClass, targetCtClass, targetClass);
                // 从指定ClassLoader加载Class
                proxyClass = (Class<? extends T>) proxyCtClass.toClass(classLoader, null);
                // 缓存
                saveProxyClassCache(classLoader, targetClass, proxyClass);
                // 输出到文件保存，用于debug调试
                // File outputFile = new File("D:\\");
                // proxyCtClass.writeFile(outputFile.getAbsolutePath());
                proxyCtClass.defrost();
            }
            // 实例化代理对象
            return newInstance(proxyClass, invocationHandler, targetConstructor, targetParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 生成代理类的类名生成规则
     */
    private static String generateProxyClassName(Class<?> targetClass) {
        return targetClass.getPackage().getName() + "." + PROXY_CLASSNAME_PREFIX + targetClass.getSimpleName();
    }
    
    /**
     * 根据被代理类的构造器，构造代理类对象。代理类的所有构造器都是被代理类构造器前添加一个invocationHandler 参数
     *
     * @param proxyClass
     *         代理类
     * @param invocationHandler
     *         代理类所有构造器的第一个参数
     * @param targetConstructor
     *         被代理类的构造器
     * @param targetParam
     *         被代理类的构造器的参数
     */
    private static <S extends T, T> S newInstance(Class<S> proxyClass, InvocationHandler invocationHandler,
            Constructor<T> targetConstructor, Object... targetParam) throws Exception {
        Class<?>[] parameterTypes = merge(InvocationHandler.class, targetConstructor.getParameterTypes());
        Constructor<S> constructor = proxyClass.getConstructor(parameterTypes);
        Object[] params = merge(invocationHandler, targetParam);
        return constructor.newInstance(params);
    }
    
    /* constructs and methods */
    
    /**
     * 代理类添加构造器，基于被代理类的构造器，在所有参数开头添加一个 {@link InvocationHandler} 参数
     */
    private static void addConstructors(ClassPool pool, CtClass proxyClass, CtClass targetClass) throws Exception {
        // 添加 invocationHandler 字段
        CtField field = CtField.make("private InvocationHandler invocationHandler = null;", proxyClass);
        proxyClass.addField(field);
        for (CtConstructor constructor : targetClass.getConstructors()) {
            CtClass[] parameterTypes =
                    merge(pool.get(InvocationHandler.class.getName()), constructor.getParameterTypes());
            CtConstructor newConstructor = new CtConstructor(parameterTypes, proxyClass);
            // 因为第一个参数指定为 InvocationHandler，所以super()的参数是从2开始的
            StringBuilder buffer = new StringBuilder();
            for (int i = 1; i <= constructor.getParameterTypes().length; i++) {
                buffer.append("$").append(i + 1).append(",");
            }
            if (buffer.length() > 0) {
                buffer.setLength(buffer.length() - 1);
            }
            // 添加构造器方法
            String code = String.format("{ super(%s); this.invocationHandler = $1; }", buffer.toString());
            newConstructor.setBody(code);
            proxyClass.addConstructor(newConstructor);
        }
    }
    
    /**
     * 先添加 Public 的方法，然后添加 Project 和 default 的方法
     *
     * @param proxyCtClass
     *         代理类
     * @param targetCtClass
     *         被代理类
     * @param targetClass
     *         被代理类
     */
    private static void addMethods(CtClass proxyCtClass, CtClass targetCtClass, Class<?> targetClass) throws Exception {
        int methodNameIndex =
                addMethod(proxyCtClass, targetCtClass, targetClass.getMethods(), targetCtClass.getMethods(), true, 0);
        addMethod(proxyCtClass, targetCtClass, targetClass.getDeclaredMethods(), targetCtClass.getDeclaredMethods(),
                  false, methodNameIndex);
    }
    
    /**
     * 代理类添加方法，基于被代理类的共有方法。因为{@link CtClass#getMethods()} 和 {@link Class#getMethods()}返回的列表顺序不一样，所以需要做一次匹配
     *
     * @param proxyCtClass
     *         代理类
     * @param targetCtClass
     *         被代理类
     * @param methods
     *         被代理类的方法数组
     * @param ctMethods
     *         被代理类的方法数组
     * @param publicTar
     *         是否是共有方法，是：只包含public方法；否：包含projected和default方法
     * @param methodNameIndex
     *         新建方法的命名下标
     */
    private static int addMethod(CtClass proxyCtClass, CtClass targetCtClass, Method[] methods, CtMethod[] ctMethods,
            boolean publicTar, int methodNameIndex) throws Exception {
        for (CtMethod cm : ctMethods) {
            int modifiers = cm.getModifiers();
            // final和static修饰的方法不能被继承
            if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                continue;
            }
            if (Modifier.isNative(modifiers)) {
                continue;
            }
            
            if (publicTar) {
                // public 方法
                if (!Modifier.isPublic(modifiers)) {
                    continue;
                }
            } else {
                // protected 方法
                if (Modifier.isPublic(modifiers) || Modifier.isPrivate(modifiers)) {
                    continue;
                }
            }
            // 匹配对应的方法
            int methodIndex = findSameMethod(methods, cm);
            if (methodIndex == -1) {
                continue;
            }
            // 将这个方法作为字段保存，便于新增的方法能够访问原来的方法
            String code;
            if (publicTar) {
                code = String.format("private static Method method%d = Class.forName(\"%s\").getMethods()[%d];",
                                     methodNameIndex, targetCtClass.getName(), methodIndex);
            } else {
                code = String.format("private static Method method%d = Class.forName(\"%s\").getDeclaredMethods()[%d];",
                                     methodNameIndex, targetCtClass.getName(), methodIndex);
            }
            CtField field = CtField.make(code, proxyCtClass);
            proxyCtClass.addField(field);
            
            CtMethod newCtMethod = new CtMethod(cm.getReturnType(), cm.getName(), cm.getParameterTypes(), proxyCtClass);
            // 区分静态与非静态，主要就是对象是否传null。注意这里必须用($r)转换类型，否则会发生类型转换失败的问题
            if (Modifier.isStatic(modifiers)) {
                System.out.println(cm);
                code = String.format("return ($r) invocationHandler.invoke(null, method%d, $args);", methodNameIndex);
            } else {
                code = String.format("return ($r) invocationHandler.invoke(this, method%d, $args);", methodNameIndex);
            }
            newCtMethod.setBody(code);
            newCtMethod.setModifiers(modifiers);
            proxyCtClass.addMethod(newCtMethod);
            methodNameIndex++;
        }
        return methodNameIndex;
    }
    
    /**
     * 从 methods 找到等于 ctMethod 的下标索引并返回。找不到则返回 -1
     */
    private static int findSameMethod(Method[] methods, CtMethod ctMethod) {
        for (int i = 0; i < methods.length; i++) {
            if (equalsMethod(methods[i], ctMethod)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 判断{@link Method} 和 {@link CtMethod} 是否相等。主要从方法名、返回值类型、参数类型三个维度判断
     */
    private static boolean equalsMethod(Method method, CtMethod ctMethod) {
        if (method == null && ctMethod == null) {
            return true;
        }
        if (method == null || ctMethod == null) {
            return false;
        }
        try {
            if (method.getName().equals(ctMethod.getName()) &&
                method.getReturnType().getName().equals(ctMethod.getReturnType().getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                CtClass[] parameterTypesCt = ctMethod.getParameterTypes();
                if (parameterTypes.length != parameterTypesCt.length) {
                    return false;
                }
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!parameterTypes[i].getName().equals(parameterTypesCt[i].getName())) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 将 item 和 array 合并到 merge
     */
    private static <T> T[] merge(T item, T[] array) {
        List<T> list = new ArrayList<>();
        list.add(item);
        Collections.addAll(list, array);
        return list.toArray(array);
        // Object[] merge = new Object[array.length + 1];
        // merge[0] = item;
        // System.arraycopy(array, 0, merge, 1, array.length);
        // return (T[]) Arrays.copyOf(merge, merge.length, array.getClass());
    }
    
}
