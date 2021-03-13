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

/**
 * @author Carry
 * @since 2021/2/28
 */
@RunWith(Parameterized.class)
public class BinaryTreeGeneratorTest extends BaseBinaryTreeGeneratorAndFormatterTest {
    
    public BinaryTreeGeneratorTest(Integer[] vs) {
        super(vs != null ? vs.length : 0, 1);
        this.vs = vs;
    }
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Integer[][][] {
                { { null, null, 2, 3, 4, 5, 6, } },
                { null },
                { {} },
                { { null, } },
                { { null, null, null, null, null, null, null, } },
                { { null, null, null, 3, 4, null, null, } },
                });
    }
    
    @Before
    public void assign() {
    }
    
    @Test
    public void testBinaryTreeGenerator() {
        bt = generator.generate(vs);
    }
    
    @BeforeClass
    public static void beforeClass() {
        // generator.setNullNode(BinaryTreeGenerator.NULL_VALUES_ALL_ALLOWED);
        boolean[] blank = { true, false, false, false, false, false, false, };
        generator.setNullNode(BinaryTreeGenerator.nullValuesIfBlank(blank));
        
        // BinaryTreeFormatter.rowFormatter = BinaryTreeFormatter.ALIGNING_RIGHT;
    }
    
}
