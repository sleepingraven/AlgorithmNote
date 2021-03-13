package io.github.sleepingraven.util.datastructure.binarytree;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import io.github.sleepingraven.util.datastructure.binarytree.AnnotatingBinaryTreeUtilO.ConvenienceBinaryTree;
import io.github.sleepingraven.util.datastructure.common.Formatter;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;
import io.github.sleepingraven.util.datastructure.common.utils.generation.BinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.generation.EditableBinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.generation.RelinkableBinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.BinaryTreeFormatter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.TraversableBinaryTreeFormatter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.Visualization;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 主要对各个工具进行测试
 *
 * @author Carry
 * @since 2020/6/18
 */
@FixMethodOrder(MethodSorters.JVM)
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class BinaryTreeFrameworkTest {
    private final int nodes;
    
    private Integer[] vs;
    private BinaryTree bt;
    
    /**
     * 记录调用了哪些构造方法
     */
    private static final Map<Class<? extends BinaryTree>, Set<String>> CONSTRUCTORS = new LinkedHashMap<>();
    /**
     * 记录节点数量和格式化后的字符串
     */
    private static final Map<Integer, Map<String, List<Class<? extends BinaryTree>>>> STRINGS = new LinkedHashMap<>();
    
    @Parameters
    public static List<Object[]> parameters() {
        return IntStream.range(0, 7)
                        .map(i -> 1 << i)
                        .map(i -> i - 1)
                        .mapToObj(i -> new Object[] { i })
                        .collect(Collectors.toList());
    }
    
    @Before
    public void assign() {
        vs = IntStream.range(0, nodes).boxed().toArray(Integer[]::new);
        bt = null;
    }
    
    @Test
    public void testAnnotatingBinaryTreeUtilO() throws NotFoundException, CannotCompileException {
        bt = AnnotatingBinaryTreeUtilO.generate(vs, null, "left", "right", Tn1.class);
    }
    
    @Test
    public void testRelinkableAndTraversable() {
        BinaryTreeGenerator<Tn2, Integer> generator = new RelinkableBinaryTreeGenerator<>(Tn2::new);
        bt = generator.generate(vs);
    }
    
    @Test
    public void testEditableAndVisible() {
        BinaryTreeGenerator<Tn4, Integer> generator = new EditableBinaryTreeGenerator<>(Tn4::new);
        bt = generator.generate(vs);
    }
    
    @Test
    public void testAnnotatingBinaryTreeUtil() {
        bt = AnnotatingBinaryTreeUtil.generate(vs, Tn6.class);
    }
    
    /* support classes */
    
    static class Tn1 extends ConvenienceBinaryTree<Tn1, Integer> implements Visualization {
        @Meta(Represent.VALUE)
        int val;
        @Getter
        Tn1 left;
        Tn1 right;
        
        private Tn1() {
            CONSTRUCTORS.computeIfAbsent(getClass(), c -> new HashSet<>()).add("private、无参构造被调用");
        }
        
        public Tn1(int value) {
            CONSTRUCTORS.computeIfAbsent(getClass(), c -> new HashSet<>()).add("public、value 构造被调用");
        }
        
    }
    
    static class Tn2 implements TraversableBinaryTree<Tn2>, RelinkableBinaryTree<Tn2> {
        static final Formatter<Tn2> formatter = new TraversableBinaryTreeFormatter<>(t -> t.value);
        Integer value;
        @Getter
        @Setter
        Tn2 left;
        @Getter
        @Setter
        Tn2 right;
        
        public Tn2(Integer value) {
            this.value = value;
        }
        
        @Override
        public String toString() {
            return formatter.format(this);
        }
        
    }
    
    static class Tn4 extends StandardBinaryTree<Tn4, Integer> implements Visualization {
    }
    
    static class Tn6 implements BinaryTree {
        @Meta(Represent.VALUE)
        int val;
        @Meta(Represent.LEFT)
        Tn6 left;
        @Meta(Represent.RIGHT)
        Tn6 right;
        
        private Tn6() {
            CONSTRUCTORS.computeIfAbsent(getClass(), c -> new HashSet<>()).add("private、无参构造被调用");
        }
        
        @Meta(Represent.FACTORY)
        public Tn6(Integer value) {
            CONSTRUCTORS.computeIfAbsent(getClass(), c -> new HashSet<>()).add("public、value 构造被调用");
        }
        
        @Override
        public String toString() {
            return AnnotatingBinaryTreeUtil.treeToString(this);
        }
        
    }
    
    @After
    public void after() {
        List<Class<? extends BinaryTree>> classes = STRINGS.computeIfAbsent(nodes, n -> new LinkedHashMap<>())
                                                           .computeIfAbsent(String.valueOf(bt), s -> new ArrayList<>());
        if (bt != null) {
            classes.add(bt.getClass());
        }
    }
    
    @AfterClass
    public static void afterClass() {
        CONSTRUCTORS.forEach((c, ss) -> {
            System.out.println(c.getSimpleName() + "：");
            ss.forEach(System.out::println);
            System.out.println();
        });
        STRINGS.forEach((n, m) -> {
            System.out.println("节点数：" + n);
            if (m.size() == 1) {
                System.out.println(m.keySet().iterator().next());
            } else {
                System.out.println("输出存在不同：");
                m.forEach((s, cs) -> {
                    System.out.println(s);
                    String classes = cs.stream().map(Class::getSimpleName).collect(Collectors.joining(", "));
                    System.out.println("» classes：" + classes);
                });
            }
            System.out.println();
        });
    }
    
    @BeforeClass
    public static void beforeClass() {
        BinaryTreeFormatter.rowCollector = BinaryTreeFormatter.JOINING_ONLY;
    }
    
}
