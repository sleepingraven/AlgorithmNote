package io.github.sleepingraven.note.practice;

import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
@RequiredArgsConstructor
public class CircularVectorTest {
    private final int n;
    
    private int[][] expected, actual;
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                { 3 }, { 4 },
                });
    }
    
    @Before
    public void assign() {
        expected = generateMatrix1(n);
        actual = generateMatrix0(n);
    }
    
    @Test
    public void test() {
        Assert.assertArrayEquals(expected, actual);
    }
    
    private static int[][] generateMatrix1(int n) {
        int[][] matrix = new int[n][n];
        CircularVector cv = new CircularVector(0, n - 1, 0, n - 1);
        for (int i = 1; i <= n * n; i++) {
            matrix[cv.x()][cv.y()] = i;
            cv.next();
        }
        return matrix;
    }
    
    private static int[][] generateMatrix0(int n) {
        int[][] matrix = new int[n][n];
        int m = 1;
        int i = 0, j = n - 1;
        while (i <= j) {
            for (int t = i; t <= j; t++) {
                matrix[i][t] = m++;
            }
            for (int t = i + 1; t <= j; t++) {
                matrix[t][j] = m++;
            }
            for (int t = j - 1; t >= i; t--) {
                matrix[j][t] = m++;
            }
            for (int t = j - 1; t > i; t--) {
                matrix[t][i] = m++;
            }
            i++;
            j--;
        }
        return matrix;
    }
    
    @After
    public void print() {
        System.out.println(n);
        for (int[] row : expected) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
    
}
