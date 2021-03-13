package io.github.sleepingraven.util.advanced;

import lombok.RequiredArgsConstructor;

/**
 * 矩阵运算
 *
 * @author Carry
 * @since 2020/9/14
 */
@RequiredArgsConstructor
public class MatrixUtil {
    private final int mod;
    
    public MatrixUtil() {
        this(1);
    }
    
    /**
     * 相乘（a[i].length == b.length）
     */
    public long[][] multiply(long[][] a, long[][] b) {
        // n：a的行数，m：b的列数
        final int n = a.length, m = b[0].length;
        long[][] pro = new long[n][m];
        
        // 两个矩阵相乘的算法
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < a[i].length; k++) {
                    pro[i][j] = (pro[i][j] + a[i][k] * b[k][j]) % mod;
                }
            }
        }
        
        return pro;
    }
    
    /**
     * 幂运算
     */
    public long[][] pow(long[][] a, int n) {
        // 初始为单位矩阵
        long[][] powered = identityMatrix(a.length);
        
        // 通过快速幂算法快速计算矩阵的 n 次方
        for (int i = n; i > 0; i >>= 1) {
            if ((i & 1) == 1) {
                powered = multiply(a, powered);
            }
            // 权值，i 每右移一次, a 就多一个平方
            a = multiply(a, a);
        }
        
        return powered;
    }
    
    /**
     * 单位矩阵
     */
    public static long[][] identityMatrix(int len) {
        long[][] im = new long[len][len];
        for (int i = 0; i < len; i++) {
            im[i][i] = 1;
        }
        return im;
    }
    
    /**
     * 转置
     */
    public int[][] transpose(int[][] matrix) {
        final int n = matrix.length, m = matrix[0].length;
        int[][] transposed = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
    
    /**
     * 交换二维 int 矩阵的两个元素
     */
    public static void swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        if (i1 == i2 && j1 == j2) {
            return;
        }
        matrix[i1][j1] ^= matrix[i2][j2];
        matrix[i2][j2] ^= matrix[i1][j1];
        matrix[i1][j1] ^= matrix[i2][j2];
    }
    
}
