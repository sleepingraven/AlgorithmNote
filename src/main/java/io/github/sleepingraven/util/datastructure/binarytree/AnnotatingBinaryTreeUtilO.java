package io.github.sleepingraven.util.datastructure.binarytree;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;
import io.github.sleepingraven.util.datastructure.common.utils.MemberUtil;
import io.github.sleepingraven.util.datastructure.common.utils.MemberUtil.AnnotationTracker;
import io.github.sleepingraven.util.datastructure.common.utils.ProxyHelper;
import io.github.sleepingraven.util.datastructure.common.utils.generation.BinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.generation.EditableBinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.Visualization;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 生成一棵树，如果子类出现 {@link Visualization}，则 toString() 支持格式化。<p>
 * 这棵树需要是 {@link ConvenienceBinaryTree} 的子类，而不用实现 {@link VisibleBinaryTree} 和 {@link RelinkableBinaryTree}
 * 的方法。会生成它的代理类，并添加 getter/setter 方法。<p>
 * {@link AnnotatingBinaryTreeUtilO#generate(Object[], String, String, String, Class)} 方法需要传入 value、left、right
 * 属性的名称。<p>
 * 或者用 {@link Meta} 修饰这些属性，并传入 null，此时 {@link AnnotatingBinaryTreeUtilO#generate(Object[], Class)} 方法是一个简洁的选择。<p>
 * 注意，如果没有无参构造，会寻找参数为 V 的构造，如果仍没有，随机选择一个，传入对应类型的默认值。（需要是 public 或 protected）
 *
 * @author Carry
 * @since 2020/6/19
 * @deprecated 存在一个 bug：生成树之后，如果再添加节点，打印时就调用了 {@link ConvenienceBinaryTree} 的 getter。<p>
 * 而且使用动态代理较为复杂，容易有 bug。可以使用更为简单的 {@link AnnotatingBinaryTreeUtil} 类。
 */
@Deprecated
public class AnnotatingBinaryTreeUtilO {
    /**
     * 缓存已生成的 generator，因为 ProxyHelper 中没有调整代理类名，不能多次创建：「frozen class (cannot edit)」
     */
    private static final Map<Class<?>, BinaryTreeGenerator<?, ?>> CACHED_GENERATOR = new HashMap<>();
    
    public static <T extends ConvenienceBinaryTree<T, V>, V> T generate(V[] vs, Class<T> clazz)
            throws NotFoundException, CannotCompileException {
        return generate(vs, "", "", "", clazz);
    }
    
    public static <T extends ConvenienceBinaryTree<T, V>, V> T generate(V[] vs, String valueFieldName,
            String leftFieldName, String rightFieldName, Class<T> clazz)
            throws NotFoundException, CannotCompileException {
        if (!CACHED_GENERATOR.containsKey(clazz)) {
            CACHED_GENERATOR.put(clazz, createGenerator(
                    new FieldExplorer(valueFieldName, leftFieldName, rightFieldName, clazz), clazz));
        }
        BinaryTreeGenerator<T, V> generator = (BinaryTreeGenerator<T, V>) CACHED_GENERATOR.get(clazz);
        return generator.generate(vs);
    }
    
    private static class FieldExplorer {
        /**
         * 代理名称。比如代理「value」就能生成 getValue() 和 setValue(V)
         */
        private static final String PROXY_VALUE;
        private static final String PROXY_LEFT;
        private static final String PROXY_RIGHT;
        
        static {
            try {
                Method getValue = VisibleBinaryTree.class.getDeclaredMethod("getValue");
                PROXY_VALUE = ProxyHelper.parseGetterOrSetter(getValue);
                Method getLeft = VisibleBinaryTree.class.getDeclaredMethod("getLeft");
                PROXY_LEFT = ProxyHelper.parseGetterOrSetter(getLeft);
                Method getRight = VisibleBinaryTree.class.getDeclaredMethod("getRight");
                PROXY_RIGHT = ProxyHelper.parseGetterOrSetter(getRight);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        
        private final Field valueField;
        private final Field leftField;
        private final Field rightField;
        
        private FieldExplorer(String valueFieldName, String leftFieldName, String rightFieldName,
                Class<? extends ConvenienceBinaryTree<?, ?>> clazz) {
            valueField = StringUtils.isNotBlank(valueFieldName) ?
                         MemberUtil.getSourceField(clazz, valueFieldName) :
                         MemberUtil.getAnnotatedField(clazz, ConvenienceBinaryTree.class, trackerOf(Represent.VALUE));
            leftField = StringUtils.isNotBlank(leftFieldName) ?
                        MemberUtil.getSourceField(clazz, leftFieldName) :
                        MemberUtil.getAnnotatedField(clazz, ConvenienceBinaryTree.class, trackerOf(Represent.LEFT));
            rightField = StringUtils.isNotBlank(rightFieldName) ?
                         MemberUtil.getSourceField(clazz, rightFieldName) :
                         MemberUtil.getAnnotatedField(clazz, ConvenienceBinaryTree.class, trackerOf(Represent.RIGHT));
        }
        
        private static AnnotationTracker<Meta> trackerOf(Represent val) {
            return new AnnotationTracker<>(Meta.class, Meta::value, val);
        }
        
    }
    
    /**
     * create a proxy and return a generator
     */
    public static <T extends ConvenienceBinaryTree<T, V>, V> BinaryTreeGenerator<T, V> createGenerator(FieldExplorer fe,
            Class<T> clazz) throws NotFoundException, CannotCompileException {
        // 生成代理类
        final Type[] vTypes = MemberUtil.getActualTypeArguments(clazz, ConvenienceBinaryTree.class);
        Class<? extends T> clz = createProxyClass(fe, clazz, (Class<T>) vTypes[0], (Class<V>) vTypes[1]);
        // 创建Generator
        Supplier<T> factory = () -> {
            try {
                T t = clz.newInstance();
                return t;
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };
        return new EditableBinaryTreeGenerator<>(factory);
    }
    
    private static final Predicate<Member> SUB_ACCESSIBLE = member -> {
        int modifiers = member.getModifiers();
        return Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers);
    };
    private static final Predicate<Member> NULL_OR_SUB_INACCESSIBLE =
            Predicate.<Member>isEqual(null).or(SUB_ACCESSIBLE.negate());
    
    /**
     * get the proxy class
     */
    private static <T extends ConvenienceBinaryTree<T, V>, V> Class<? extends T> createProxyClass(FieldExplorer fe,
            final Class<T> clazz, Class<T> tType, Class<V> vType) throws NotFoundException, CannotCompileException {
        ProxyHelper<T> ph = new ProxyHelper<>(clazz);
        
        // 优先调用无参或者参数为 V 的构造方法
        Constructor<T> superConstructor = null;
        try {
            superConstructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException ignored) {
        } finally {
            if (NULL_OR_SUB_INACCESSIBLE.test(superConstructor)) {
                try {
                    superConstructor = clazz.getDeclaredConstructor(vType);
                } catch (NoSuchMethodException ignored) {
                } finally {
                    if (NULL_OR_SUB_INACCESSIBLE.test(superConstructor)) {
                        superConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                                                 .map(c -> (Constructor<T>) c)
                                                 .filter(SUB_ACCESSIBLE)
                                                 .findFirst()
                                                 .orElseThrow(() -> new RuntimeException("没有可见的构造方法！"));
                    }
                }
                ph.makeDefaultConstructor(superConstructor);
            }
        }
        
        // value
        ph.addProxyField(fe.valueField, FieldExplorer.PROXY_VALUE);
        // 类型是 Object 才可以
        ph.getter(FieldExplorer.PROXY_VALUE, Object.class, ConvenienceBinaryTree.class);
        ph.setter(FieldExplorer.PROXY_VALUE, Object.class, ConvenienceBinaryTree.class);
        
        // left
        ph.addProxyField(fe.leftField, FieldExplorer.PROXY_LEFT);
        // Object.class、clazz、tType 都不行
        ph.getter(FieldExplorer.PROXY_LEFT, ConvenienceBinaryTree.class, ConvenienceBinaryTree.class);
        ph.setter(FieldExplorer.PROXY_LEFT, ConvenienceBinaryTree.class, ConvenienceBinaryTree.class);
        
        // right
        ph.addProxyField(fe.rightField, FieldExplorer.PROXY_RIGHT);
        ph.getter(FieldExplorer.PROXY_RIGHT, ConvenienceBinaryTree.class, ConvenienceBinaryTree.class);
        ph.setter(FieldExplorer.PROXY_RIGHT, ConvenienceBinaryTree.class, ConvenienceBinaryTree.class);
        
        return ph.froze();
    }
    
    /**
     * base target class of the carry.util
     */
    public static class ConvenienceBinaryTree<T extends ConvenienceBinaryTree<T, V>, V> extends BaseBinaryTree<T, V> {
        
        @Override
        public V getValue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public T getLeft() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public T getRight() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setValue(V value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setLeft(T left) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setRight(T right) {
            throw new UnsupportedOperationException();
        }
        
    }
    
}
