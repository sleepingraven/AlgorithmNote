package io.github.sleepingraven.note.demo.other.javassist.demo;

/**
 * @author Carry
 * @since 2020/6/16
 */
public class AppClassLoader extends ClassLoader {
    
    private static class SingletonHolder {
        public final static AppClassLoader SINGLETON_INSTANCE = new AppClassLoader();
        
    }
    
    private AppClassLoader() {
    }
    
    public static AppClassLoader getInstance() {
        return SingletonHolder.SINGLETON_INSTANCE;
    }
    
    /**
     * 通过代理类的classBytes加载类
     */
    public Class<?> defineClass(String className, byte[] classBytes) {
        return defineClass(className, classBytes, 0, classBytes.length);
    }
    
}
