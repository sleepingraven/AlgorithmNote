package io.github.sleepingraven.note.practice;

import junit.framework.TestCase;
import lombok.RequiredArgsConstructor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import io.github.sleepingraven.util.ac.DataGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * @author Carry
 * @since 2021/3/2
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class MatrixPrefixTest {
    private final String matrixStr;
    private static int[][] matrix;
    private static MatrixPrefix mp;
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                {
                        "[\n" + "  [3, 0, 1, 4, 2],\n" + "  [5, 6, 3, 2, 1],\n" + "  [1, 2, 0, 1, 5],\n" +
                        "  [4, 1, 0, 1, 7],\n" + "  [1, 0, 3, 0, 5]\n" + "]"
                },
                });
    }
    
    @BeforeClass
    public static void beforeClass() {
        matrix = DataGenerator.parseIntArray("[]", int[][].class);
        matrix = DataGenerator.parseIntArray("[[]]", int[][].class);
    }
    
    @Before
    public void assign() {
        matrix = DataGenerator.parseIntArray(matrixStr, int[][].class);
        mp = new MatrixPrefix(matrix);
    }
    
    @Test
    public void testGetPrefix() {
        if (matrix.length == 0) {
            return;
        }
        final int n = matrix.length, m = matrix[0].length;
        int[] colSum = new int[m];
        for (int i = 0; i <= n; i++) {
            if (i > 0) {
                for (int j = 0; j < m; j++) {
                    colSum[j] += matrix[i - 1][j];
                }
            }
            int sum = 0;
            for (int j = 0; j <= m; j++) {
                if (j > 0) {
                    sum += colSum[j - 1];
                }
                TestCase.assertEquals(String.format("getPrefix() 错误：i: %d, j: %d", i, j), sum, mp.getPrefix(i, j));
            }
        }
    }
    
    @Test
    public void testGetRectSum() {
        if (matrix.length == 0) {
            return;
        }
        final int n = matrix.length, m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = i; k <= n; k++) {
                    for (int l = j; l <= m; l++) {
                        int sum = 0;
                        for (int r = i; r < k; r++) {
                            for (int c = j; c < l; c++) {
                                sum += matrix[r][c];
                            }
                        }
                        TestCase.assertEquals(String.format("getPrefix() 错误：i: %d, j: %d, k: %d, l: %d", i, j, k, l),
                                              sum, mp.getRectSum(i, j, k, l));
                    }
                }
            }
        }
    }
    
    @After
    public void print() {
        System.out.println(Arrays.deepToString(matrix));
        System.out.println(mp);
        System.out.println();
    }
    
}
