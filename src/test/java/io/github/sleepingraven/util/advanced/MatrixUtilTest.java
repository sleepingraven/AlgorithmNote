package io.github.sleepingraven.util.advanced;

import junit.framework.TestCase;
import lombok.AllArgsConstructor;
import org.junit.Test;
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
@AllArgsConstructor
public class MatrixUtilTest {
    private static final int MOD = 1_000_000_007;
    
    private final int paramN;
    private final int expected;
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                { 1, 5 }, { 2, 10 }, { 3, 19 }, { 10_000, 76428576 }, { 20_000, 759959057 },
                });
    }
    
    @Test
    public void testMatrix() {
        int actual = countVowelPermutation(paramN);
        TestCase.assertEquals(expected, actual);
    }
    
    /**
     * <a href="https://leetcode-cn.com/problems/count-vowels-permutation/">1220. 统计元音字母序列的数目</a>
     */
    public int countVowelPermutation(int n) {
        MatrixUtil mt = new MatrixUtil(MOD);
        
        // 矩阵的公式
        long[][] grid = {
                { 0, 1, 0, 0, 0 }, { 1, 0, 1, 0, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 1, 0, 1 }, { 1, 0, 0, 0, 0 },
                };
        grid = mt.pow(grid, n - 1);
        
        // 初始值
        long[][] res = {
                { 1 }, { 1 }, { 1 }, { 1 }, { 1 },
                };
        res = mt.multiply(grid, res);
        
        long total = 0;
        for (int i = 0; i < 5; i++) {
            total += res[i][0];
        }
        return (int) (total % MOD);
    }
    
}
