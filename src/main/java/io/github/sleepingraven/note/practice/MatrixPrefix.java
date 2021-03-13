package io.github.sleepingraven.note.practice;

import lombok.ToString;

/**
 * @author Carry
 * @since 2020/7/20
 */
@ToString
public class MatrixPrefix {
    private final int[][] p;
    
    /**
     * 求 matrix 的前缀和
     */
    public MatrixPrefix(int[][] matrix) {
        if (matrix.length == 0) {
            p = new int[0][];
            return;
        }
        int n = matrix.length, m = matrix[0].length;
        p = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p[i + 1][j + 1] = p[i + 1][j] + p[i][j + 1] - p[i][j] + matrix[i][j];
            }
        }
    }
    
    /**
     * matrix[0][0] 到 matrix[x - 1][y - 1] 的区域和
     */
    public int getPrefix(int x, int y) {
        return p[x][y];
    }
    
    /**
     * matrix[x1][y1] 到 matrix[x2 - 1][y2 - 1] 的区域和。<p>
     * 其中：
     * 0 <= x1 < n, x1 <= x2 <= n
     * 0 <= y1 < m, y1 <= y2 <= m
     */
    public int getRectSum(int x1, int y1, int x2, int y2) {
        return p[x2][y2] - p[x1][y2] - p[x2][y1] + p[x1][y1];
    }
    
}
