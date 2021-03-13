package io.github.sleepingraven.util.datastructure.common.utils;

import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.BeforeClass;
import io.github.sleepingraven.util.datastructure.binarytree.BinaryTree;
import io.github.sleepingraven.util.datastructure.binarytree.StandardBinaryTree;
import io.github.sleepingraven.util.datastructure.common.utils.generation.BinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.generation.EditableBinaryTreeGenerator;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.BinaryTreeFormatter;
import io.github.sleepingraven.util.datastructure.common.utils.visualization.Visualization;

import java.util.Arrays;
import java.util.Random;

/**
 * 主要测试顶层工具类
 *
 * @author Carry
 * @since 2021/2/26
 */
@RequiredArgsConstructor
public abstract class BaseBinaryTreeGeneratorAndFormatterTest {
    static final BinaryTreeGenerator<BTree, Integer> generator = new EditableBinaryTreeGenerator<>(BTree::new);
    
    final int nodes;
    final int multiple;
    
    Integer[] vs;
    BinaryTree bt;
    
    static class BTree extends StandardBinaryTree<BTree, Integer> implements Visualization {
    }
    
    void randomSetInt() {
        Random random = new Random();
        for (int i = 0; i < vs.length; i++) {
            vs[i] = random.nextInt();
        }
    }
    
    void randomSetNull(double p) {
        Random random = new Random();
        for (int i = 0; i < vs.length; i++) {
            if (random.nextDouble() < p) {
                vs[i] = null;
            }
        }
    }
    
    @After
    public void after() {
        System.out.printf("节点数：%d，倍数：%d%n", nodes, multiple);
        System.out.println(Arrays.toString(vs));
        System.out.println(bt);
        System.out.println();
    }
    
    @BeforeClass
    public static void beforeClass() {
        BinaryTreeFormatter.rowCollector = BinaryTreeFormatter.JOINING_ONLY;
    }
    
}
