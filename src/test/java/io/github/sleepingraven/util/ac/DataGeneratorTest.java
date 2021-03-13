package io.github.sleepingraven.util.ac;

import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

/**
 * @author Carry
 * @since 2021/2/12
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class DataGeneratorTest {
    private final String str;
    private final Object expected;
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                { "[1,2,3]", new int[] { 1, 2, 3, } },
                { "[[1,2,3],[4,5,6],[7,8,9]]", new int[][] { { 1, 2, 3, }, { 4, 5, 6, }, { 7, 8, 9, }, } },
                {
                        "[[[1,2,3],[4,5,6],[7,8,9]],[[10,11,12],[13,14,15],[16,17,18]],[[19,20,21],[22,23,24],[25,26,27]]]",
                        new int[][][] {
                                { { 1, 2, 3, }, { 4, 5, 6, }, { 7, 8, 9, }, },
                                { { 10, 11, 12, }, { 13, 14, 15, }, { 16, 17, 18, }, },
                                { { 19, 20, 21, }, { 22, 23, 24, }, { 25, 26, 27, }, },
                                }
                },
                });
    }
    
    @Test
    public void testParseIntArray() {
        Object actual = DataGenerator.parseIntArray(str);
        assertArrayEquals(expected, actual);
    }
    
    @BeforeClass
    public static void testParseIntArrayWithType() {
        int[] matrix1 = DataGenerator.parseIntArray("[]", int[].class);
        System.out.println(Arrays.toString(matrix1));
        assertArrayEquals(new int[0], matrix1);
        
        int[][] matrix = DataGenerator.parseIntArray("[[]]", int[][].class);
        System.out.println(Arrays.deepToString(matrix));
        assertArrayEquals(new int[][] { new int[0] }, matrix);
        
        String[] matrix3 = DataGenerator.parseStringArray("[]", String[].class);
        System.out.println(Arrays.toString(matrix3));
        assertArrayEquals(new String[0], matrix3);
        
        String[][] matrix4 = DataGenerator.parseStringArray("[[]]", String[][].class);
        System.out.println(Arrays.deepToString(matrix4));
        assertArrayEquals(new String[][] { new String[0] }, matrix4);
    }
    
    /**
     * @see Assert#assertArrayEquals(Object[], Object[])
     */
    private static void assertArrayEquals(Object expecteds, Object actuals) {
        new ExactComparisonCriteria().arrayEquals(null, expecteds, actuals);
    }
    
}
