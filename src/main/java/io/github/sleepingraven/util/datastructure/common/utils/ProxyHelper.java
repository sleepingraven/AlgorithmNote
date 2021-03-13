package io.github.sleepingraven.util.datastructure.common.utils;

import javassist.*;
import javassist.CtField.Initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 为需要代理的类生成构造方法，并添加 getter/setter（在代理类中添加 Field 类型的静态变量来实现对原字段的 get/set）。
 *
 * @author Carry
 * @since 2020/6/20
 */
public class ProxyHelper<T> {
    private final Class<T> clazz;
    private final CtClass cc;
    
    public ProxyHelper(Class<T> clazz) throws NotFoundException, CannotCompileException {
        this.clazz = clazz;
        
        // this.cc = createCtClassO(clazz);
        this.cc = createCtClass(clazz);
    }
    
    /**
     * 1、直接复制 clazz 类，再移除属性和方法，添加传参构造；（早期的实现）
     * 但是如果 clazz 是内部类，调用生成类的 getSimpleName() 时，出现 IncompatibleClassChangeError，「disagree on InnerClasses attribute」。
     */
    private static CtClass createCtClassO(Class<?> clazz) throws NotFoundException, CannotCompileException {
        CtClass cc = getCtClass(clazz);
        cc.setName(getProxyClassName(clazz));
        cc.setSuperclass(getCtClass(clazz));
        
        // 移除冗余属性、方法
        for (CtField ctField : cc.getDeclaredFields()) {
            try {
                cc.removeField(ctField);
            } catch (NotFoundException ignored) {
            }
        }
        for (CtMethod ctMethod : cc.getDeclaredMethods()) {
            try {
                cc.removeMethod(ctMethod);
            } catch (NotFoundException ignored) {
            }
        }
        
        // 移除 private 构造，添加 call super
        for (CtConstructor ctConstructor : cc.getDeclaredConstructors()) {
            if (Modifier.isPrivate(ctConstructor.getModifiers())) {
                try {
                    cc.removeConstructor(ctConstructor);
                } catch (NotFoundException ignored) {
                }
            } else {
                ctConstructor.setBody("{ super($$); }");
            }
        }
        return cc;
    }
    
    /**
     * 2、新创建一个类
     */
    private static CtClass createCtClass(Class<?> clazz) throws NotFoundException, CannotCompileException {
        CtClass cc = ClassPool.getDefault().makeClass(getProxyClassName(clazz));
        cc.setSuperclass(getCtClass(clazz));
        return cc;
    }
    
    /**
     * 定义无参构造，并调用给定的父类构造
     */
    public void makeDefaultConstructor(Constructor<T> superConstructor) throws CannotCompileException {
        // 防止 private 构造
        try {
            CtConstructor defaultConstructor = cc.getDeclaredConstructor(null);
            cc.removeConstructor(defaultConstructor);
        } catch (NotFoundException ignored) {
        }
        
        String body = Arrays.stream(superConstructor.getParameterTypes())
                            .map(ProxyHelper::getDefaultDeclarationByClass)
                            .collect(Collectors.joining(", ", "{ super(", "); }"));
        
        CtConstructor defaultConstructor = CtNewConstructor.defaultConstructor(cc);
        defaultConstructor.setBody(body);
        cc.addConstructor(defaultConstructor);
    }
    
    /**
     * 代理字段。允许更换名称，如使用 value 代理 val
     *
     * @param field
     *         源字段
     * @param proxy
     *         新名称，getter/setter 将以该名称命名
     */
    public void addProxyField(Field field, String proxy) throws NotFoundException, CannotCompileException {
        String srcFieldName = field.getName();
        String proxyFieldName = getProxyFieldName(proxy, srcFieldName);
        
        // first
        CtField ctField = new CtField(getCtClass(Field.class), proxyFieldName, cc);
        ctField.setModifiers(Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);
        cc.addField(ctField,
                    Initializer.byCall(getCtClass(ProxyHelper.class), GET_DECLARED_FIELD_HELPER_NAME, new String[] {
                            field.getDeclaringClass().getName(), srcFieldName
                    }));
        
        // second
        // StringHelper sh = new StringHelper();
        // sh.add("private static final java.lang.reflect.Field %1$s = %2$s.%3$s(new String[] { \"%4$s\", \"%5$s\" });");
        // CtField ctField = CtFieldWithInit.make(
        //         sh.get(proxyFieldName, ProxyHelper.class.getCanonicalName(), GET_DECLARED_FIELD_HELPER_NAME,
        //                field.getDeclaringClass().getName(), srcFieldName), cc);
        // cc.addField(ctField);
    }
    
    /**
     * 检查代理字段的 getter 方法，如果没有则生成 getter 方法。
     * 如果设置了过滤类型，仅检查该类型以下的 getter
     */
    public void getter(String proxy, Class<?> rtn, Class<? super T> filter)
            throws NotFoundException, CannotCompileException {
        String getter = "get" + Character.toUpperCase(proxy.charAt(0)) + proxy.substring(1);
        
        // first
        StringHelper sh = new StringHelper();
        sh.add("return ($r) %1$s.get(this);");
        // String body = newMethodBody(sh.get(getProxyFieldName(proxy)), CATCH_MAP);
        String body = sh.get(getProxyFieldName(proxy));
        CtMethod cm = CtNewMethod.make(Modifier.PUBLIC, getCtClass(rtn), getter, null, null, body, cc);
        cm.addCatch(THROW_RUNTIME_EXCEPTION_BEHAVIOR, getCtClass(IllegalAccessException.class), "e");
        
        // second
        // StringHelper sh = new StringHelper();
        // // 加泛型会报错：CannotCompileException
        // sh.add("public %1$s" + " %2$s() {");
        // sh.add("    try {");
        // sh.add("        return (%1$s) %3$s.get(this);");
        // sh.add("    } catch (IllegalAccessException e) {");
        // sh.add("        e.printStackTrace();");
        // sh.add("        throw new RuntimeException(e);");
        // sh.add("    }");
        // sh.add("}");
        // CtMethod cm = CtNewMethod.make(sh.get(rtn.getCanonicalName(), getter, getProxyFieldName(proxy)), cc);
        
        if (!isAnOverride(getter, cm.getSignature(), cc, filter)) {
            cc.addMethod(cm);
        } else {
            System.out.println("忽略 getter：" + getter);
        }
    }
    
    /**
     * 检查代理字段的 setter 方法，如果没有则生成 setter 方法。
     * 如果设置了过滤类型，仅检查该类型以下的 setter
     */
    public void setter(String proxy, Class<?> type, Class<? super T> filter)
            throws NotFoundException, CannotCompileException {
        String setter = "set" + Character.toUpperCase(proxy.charAt(0)) + proxy.substring(1);
        
        // first
        StringHelper sh = new StringHelper();
        sh.add("%1$s.set($0, $1);");
        String body = sh.get(getProxyFieldName(proxy));
        CtMethod cm =
                CtNewMethod.make(Modifier.PUBLIC, getCtClass(void.class), setter, new CtClass[] { getCtClass(type) },
                                 null, body, cc);
        cm.addCatch(THROW_RUNTIME_EXCEPTION_BEHAVIOR, getCtClass(IllegalAccessException.class), "e");
        
        if (!isAnOverride(setter, cm.getSignature(), cc, filter)) {
            cc.addMethod(cm);
        } else {
            System.out.println("忽略 setter：" + setter);
        }
    }
    
    /**
     * 生成代理类的字节码
     */
    public Class<? extends T> froze() throws CannotCompileException {
        // 生成同名类可以用ClassLoader。但是不能转型
        // AppClassLoader appClassLoader = AppClassLoader.getInstance();
        // Class<? extends T> clz = null;
        // try {
        //     clz = (Class<? extends T>) appClassLoader.defineClass(clazz.getName(), cc.toBytecode());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // 生成子类用toClass
        return (Class<? extends T>) cc.toClass();
    }
    
    /* helper */
    
    /**
     * 防止类似包装类转为原子类时的空指针；<p>
     * 如果构造参数是包装类型，出现类似：cannot find constructor carry.draft.Main$Tn(int)
     */
    private static final Map<Class<?>, String> PRIMITIVE_AND_BOXED_DEFAULT_DECLARATIONS =
            new HashMap<Class<?>, String>(32) {{
                put(short.class, "(short) 0");
                put(int.class, "0");
                put(long.class, "0L");
                put(char.class, "(char) 0");
                put(byte.class, "(byte) 0");
                put(float.class, "0f");
                put(double.class, "0d");
                put(boolean.class, "false");
                put(Short.class, "new Short((short) 0)");
                put(Integer.class, "new Integer(0)");
                put(Long.class, "new Long(0)");
                put(Character.class, "new Character((char) 0)");
                put(Byte.class, "new Byte((byte) 0)");
                put(Float.class, "new Float(0)");
                put(Double.class, "new Double(0)");
                put(Boolean.class, "Boolean.FALSE");
            }};
    
    public static String getDefaultDeclarationByClass(Class<?> clz) {
        return PRIMITIVE_AND_BOXED_DEFAULT_DECLARATIONS.getOrDefault(clz, "null");
    }
    
    private static final String GET_DECLARED_FIELD_HELPER_NAME;
    
    static {
        String name = null;
        try {
            name = ProxyHelper.class.getDeclaredMethod("getDeclaredFieldHelper", String[].class).getName();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        GET_DECLARED_FIELD_HELPER_NAME = name;
    }
    
    private static final String THROW_RUNTIME_EXCEPTION_BEHAVIOR =
            "e.printStackTrace(); throw new RuntimeException(e);";
    private static final Map<Class<? extends Exception>[], String> CATCH_MAP =
            new HashMap<Class<? extends Exception>[], String>(4) {{
                put(new Class[] { IllegalAccessException.class }, THROW_RUNTIME_EXCEPTION_BEHAVIOR);
            }};
    
    /**
     * create a method body with catch block
     */
    private static String newMethodBody(String statement,
            Map<Class<? extends Exception>[], String> exceptionsToBeCaught) {
        StringHelper sh = new StringHelper();
        sh.add("{");
        sh.add("    " + statement);
        sh.add("}");
        if (exceptionsToBeCaught == null || exceptionsToBeCaught.isEmpty()) {
            return sh.toString();
        }
        
        sh.add("try {", 1);
        exceptionsToBeCaught.forEach((es, behavior) -> {
            // | 会报错
            for (Class<? extends Exception> e : es) {
                sh.add("catch (" + e.getCanonicalName() + " e) {");
                sh.add("    " + behavior);
                sh.add("}");
            }
        });
        sh.add("}");
        
        return sh.toString();
    }
    
    /**
     * 是否重写了从 subclass 到 filter 的继承路径中的方法
     */
    private static boolean isAnOverride(String name, String desc, CtClass subclass, Class<?> filter) {
        try {
            // 能获取 private
            CtMethod cm = subclass.getMethod(name, desc);
            if (filter == null) {
                return true;
            }
            CtClass baseclass = getCtClass(filter);
            if (!cm.getDeclaringClass().equals(baseclass) && cm.getDeclaringClass().subclassOf(baseclass)) {
                return true;
            }
        } catch (NotFoundException ignored) {
        }
        return false;
    }
    
    /* utils */
    
    /**
     * get the actually declared field when the proxy class init
     */
    public static Field getDeclaredFieldHelper(String[] strs) {
        try {
            Field field = Class.forName(strs[0]).getDeclaredField(strs[1]);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * get the class signature. Arrays are not tested
     */
    private static String getMethodSignature(Class<?> rtn, String name, Class<?>... params) throws NotFoundException {
        CtClass cc = getCtClass(Object.class);
        CtClass returnType = rtn == null ? null : getCtClass(rtn);
        String[] classnames = Arrays.stream(params).map(Class::getName).toArray(String[]::new);
        CtClass[] parameters = ClassPool.getDefault().get(classnames);
        CtMethod cm = new CtMethod(returnType, name, parameters, cc);
        return cm.getSignature();
    }
    
    /**
     * get the target name of the getter/setter method
     */
    public static String parseGetterOrSetter(Method gos) {
        String name = gos.getName();
        if (!name.startsWith("get") && !name.startsWith("set")) {
            throw new RuntimeException("方法不是getter/setter");
        }
        if (name.length() == 3) {
            throw new RuntimeException("方法名仅为get/set");
        }
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }
    
    /**
     * get the CtClass
     */
    private static CtClass getCtClass(Class<?> clazz) throws NotFoundException {
        return ClassPool.getDefault().get(clazz.getName());
    }
    
    /* namer */
    
    /**
     * class name of proxy。增加了包名，如果 clazz 是内部类，不用是 public。
     * 否则出现 CannotCompileException、ClassFormatError，「cannot access its superclass」
     */
    private static String getProxyClassName(Class<?> clazz) {
        return clazz.getPackage().getName() + "._Proxy$" + clazz.getSimpleName();
    }
    
    /**
     * proxy fields names
     */
    private final Map<String, String> PROXY_FIELD_NAMES = new HashMap<>(8);
    
    private String getProxyFieldName(String proxy, String srcFieldName) {
        return PROXY_FIELD_NAMES.computeIfAbsent(proxy, s -> "$PROXY_" + proxy + "_OF_FIELD_" + srcFieldName);
    }
    
    private String getProxyFieldName(String proxy) {
        return PROXY_FIELD_NAMES.get(proxy);
    }
    
}
