package io.github.sleepingraven.note.demo.other.javassist.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

/**
 * @author Carry
 * @since 2020/6/16
 */
public class Main {
    static final Class<Target> CLAZZ = Target.class;
    
    public static void main(String[] args) {
        try {
            proxyByJavassist();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    private static void proxyByJavassist() throws NoSuchMethodException {
        Target target = new Target();
        // 指定被代理对象的构造器，内部会自动转换为代理对象的构造器
        Constructor<Target> constructor = CLAZZ.getConstructor();
        Object[] constructorParam = new Object[] {};
        
        // 指定方法回调的接口
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            System.out.println("before:" + method.getName());
            // 记住这儿是调用的被代理对象的方法，所以传参是 demo 而不是 proxy
            // method.setAccessible(true);
            Object result = method.invoke(target, args);
            System.out.println("after:" + method.getName());
            return result;
        };
        
        Target proxy =
                Proxy.newProxyInstance(CLAZZ.getClassLoader(), invocationHandler, CLAZZ, constructor, constructorParam);
        // 分别测试 public、protected、default的方法
        proxy.publicDemo();
        proxy.protectedDemo();
        proxy.defaultDemo();
        System.out.println();
        // 测试继承的public方法
        System.out.println(proxy.toString());
    }
    
}
