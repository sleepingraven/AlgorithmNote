package io.github.sleepingraven.util.datastructure.common.utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import io.github.sleepingraven.util.datastructure.common.utils.generation.BinaryTreeGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Carry
 * @since 2021/2/28
 */
@RunWith(Parameterized.class)
public class BinaryTreeFormatterTest extends BaseBinaryTreeGeneratorAndFormatterTest {
    static final double P = .3;
    
    public BinaryTreeFormatterTest(int nodes, int multiple) {
        super(nodes, multiple);
    }
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][] { { 7, 1 }, { 63, 1 }, { 63, 10 }, { 63, 100 }, { 63, 10_000 }, });
    }
    
    @Before
    public void assign() {
        switch (adjustment) {
            case SET_ALL:
                vs = new Integer[nodes];
                randomSetInt();
                break;
            case SET_NULL:
                vs = IntStream.range(0, nodes)
                              .map(i -> Math.multiplyExact(i, multiple))
                              .boxed()
                              .toArray(Integer[]::new);
                randomSetNull(P);
                break;
            case NONE:
                vs = IntStream.range(0, nodes)
                              .map(i -> Math.multiplyExact(i, multiple))
                              .boxed()
                              .toArray(Integer[]::new);
                break;
        }
    }
    
    @Test
    public void testBinaryTreeFormatter() {
        bt = generator.generate(vs);
    }
    
    final ValueAdjustment adjustment = ValueAdjustment.SET_NULL;
    
    enum ValueAdjustment {
        /**
         * 不变更
         */
        NONE,
        /**
         * 随机调整所有值
         */
        SET_ALL,
        /**
         * 随机将值设置为 null
         */
        SET_NULL,
    }
    
    @BeforeClass
    public static void beforeClass() {
        generator.setNullNode(BinaryTreeGenerator.NULL_VALUES_ALL_ALLOWED);
        
        // BinaryTreeFormatter.rowFormatter = BinaryTreeFormatter.ALIGNING_RIGHT;
    }
    
}
