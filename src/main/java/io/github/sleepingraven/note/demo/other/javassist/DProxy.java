package io.github.sleepingraven.note.demo.other.javassist;

import javassist.*;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 根据接口创建代理类。来源：https://www.cnblogs.com/exolution/archive/2012/10/24/2736597.html
 *
 * @author Carry
 * @since 2021/2/26
 */
public class DProxy {
    private static final String PROXY_CLASS_NAME = ".Gproxy$";
    private static int proxyIndex = 1;
    
    /**
     * 创建动态代理的工厂方法
     *
     * @param targetClass
     *         被代理的类型
     * @param interceptor
     *         拦截器实例
     *
     * @return 动态代理实例，它实现了 targerClass 的所有接口
     */
    public static Object createProxy(Class<?> targetClass, Interceptor interceptor) {
        int index = 0;
        /*获得运行时类的上下文*/
        ClassPool pool = ClassPool.getDefault();
        /*动态创建代理类*/
        CtClass proxy = pool.makeClass(targetClass.getPackage().getName() + PROXY_CLASS_NAME + proxyIndex++);
        
        try {
            /*获得DProxy类作为代理类的父类*/
            // getCanonicalName 会 NotFoundException
            CtClass superclass = pool.get(ToExtend.class.getName());
            proxy.setSuperclass(superclass);
            /*获得被代理类的所有接口*/
            CtClass[] interfaces = pool.get(targetClass.getName()).getInterfaces();
            for (CtClass i : interfaces) {
                /*动态代理实现这些接口*/
                proxy.addInterface(i);
                /*获得结构中的所有方法*/
                CtMethod[] methods = i.getDeclaredMethods();
                for (int n = 0; n < methods.length; n++) {
                    CtMethod m = methods[n];
                    /*构造这些Method参数 以便传递给拦截器的interceptor方法*/
                    StringBuilder fields = new StringBuilder();
                    fields.append("private static java.lang.reflect.Method method" + index);
                    fields.append("=Class.forName(\"");
                    fields.append(i.getName());
                    fields.append("\").getDeclaredMethods()[");
                    fields.append(n);
                    fields.append("];");
                    /*动态编译之*/
                    CtField cf = CtField.make(fields.toString(), proxy);
                    proxy.addField(cf);
                    GenerateMethods(proxy, m, index);
                    index++;
                }
            }
            /*创建构造方法以便注入拦截器*/
            CtConstructor cc =
                    new CtConstructor(new CtClass[] { pool.get(Interceptor.class.getCanonicalName()) }, proxy);
            cc.setBody("{super($1);}");
            proxy.addConstructor(cc);
            //proxy.writeFile();
            return proxy.toClass().getConstructor(Interceptor.class).newInstance(interceptor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static void GenerateMethods(CtClass proxy, CtMethod method, int index) {
        try {
            CtMethod cm = new CtMethod(method.getReturnType(), method.getName(), method.getParameterTypes(), proxy);
            /*构造方法体*/
            String mbody = "{super.interceptor.intercept(this,method" + index + ",$args);}";
            cm.setBody(mbody);
            proxy.addMethod(cm);
        } catch (CannotCompileException | NotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @RequiredArgsConstructor
    public static class ToExtend {
        /**
         * 代理拦截器(利用继承减少动态构造的字节码)
         */
        protected final Interceptor interceptor;
        
    }
    
    public static void main(String[] args) {
        Origin c = new Origin();
        AnInterface i = (AnInterface) DProxy.createProxy(Origin.class, new MyInterceptor(c));
        i.action(123);
    }
    
}

class Origin implements AnInterface {
    
    @Override
    public void action(int i) {
        System.out.println("do Action: " + i);
    }
    
}

@RequiredArgsConstructor
class MyInterceptor implements Interceptor {
    private final Object origin;
    
    @Override
    public int intercept(Object instance, Method method, Object[] Args) {
        try {
            System.out.println("before action");
            method.invoke(origin, Args);
            System.out.println("after action");
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}

interface AnInterface {
    
    void action(int i);
    
}

interface Interceptor {
    
    int intercept(Object instance, Method method, Object[] Args);
    
}
