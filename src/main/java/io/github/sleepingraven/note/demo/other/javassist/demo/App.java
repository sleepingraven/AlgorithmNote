package io.github.sleepingraven.note.demo.other.javassist.demo;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Carry
 * @since 2020/6/16
 */
public class App {
    static final String CLASS_NAME = UserInfo.class.getName();
    
    public static void main(String[] args) {
        try {
            UserInfo userInfo = new UserInfo(1, "test");
            System.out.println("before:" + userInfo);
            
            Class<?> clazz = newClazz();
            Object obj = getProxyObject(clazz, userInfo);
            System.out.println("after:" + obj);
            //测试反射调用添加的方法
            System.out.println(obj.getClass().getDeclaredMethod("test").invoke(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static Class<?> newClazz() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(CLASS_NAME);
        CtMethod cm = CtNewMethod.make("public String test() { return \"test() is called: \"+ toString();  }", cc);
        cc.addMethod(cm);
        return AppClassLoader.getInstance().defineClass(CLASS_NAME, cc.toBytecode());
    }
    
    /**
     * 根据子类的Class对象，创建代理类，具有和父类相同的属性值
     */
    static <SUB extends SUP, SUP> SUB getProxyObject(Class<SUB> clazz, SUP src)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        // final 未考虑
        SUB newInstance = clazz.getDeclaredConstructor().newInstance();
        for (Field field : src.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);
            Field newInstanceField = newInstance.getClass().getDeclaredField(fieldName);
            newInstanceField.setAccessible(true);
            newInstanceField.set(newInstance, field.get(src));
        }
        return newInstance;
    }
    
}
