package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1230_最小重量机器设计问题 {
    private static int n, m, c;
    private static int[][] ws, cs;
    
    static int wMin;
    static int[] minRecord;
    static int wCache;
    static int cCache;
    
    static int[] record;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 部件
        n = scanner.nextInt();
        // 供应商
        m = scanner.nextInt();
        // 总价格
        c = scanner.nextInt();
        // 重量
        ws = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ws[i][j] = scanner.nextInt();
            }
        }
        // 价格
        cs = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cs[i][j] = scanner.nextInt();
            }
        }
        
        wMin = Integer.MAX_VALUE;
        minRecord = new int[n];
        wCache = 0;
        cCache = 0;
        fun();
        // System.out.println(String.format("%d\n%d %d %d", wMin, minRecord[0] + 1, minRecord[1] + 1, minRecord[2] + 1));
        wMin = Integer.MAX_VALUE;
        minRecord = new int[n];
        wCache = 0;
        cCache = 0;
        record = new int[n];
        fun1(0);
        System.out.println(String.format("%d\n%d %d %d", wMin, minRecord[0] + 1, minRecord[1] + 1, minRecord[2] + 1));
    }
    
    /**
     * 3层循环，即n = 3时
     */
    private static void fun() {
        for (int i = 0; i < m; i++) {
            wCache += ws[0][i];
            cCache += cs[0][i];
            for (int j = 0; j < m; j++) {
                wCache += ws[1][j];
                cCache += cs[1][j];
                for (int k = 0; k < m; k++) {
                    wCache += ws[2][k];
                    cCache += cs[2][k];
                    if (cCache <= c && wCache < wMin) {
                        wMin = wCache;
                        minRecord[0] = i;
                        minRecord[1] = j;
                        minRecord[2] = k;
                    }
                    cCache -= cs[2][k];
                    wCache -= ws[2][k];
                }
                wCache -= ws[1][j];
                cCache -= cs[1][j];
            }
            wCache -= ws[0][i];
            cCache -= cs[0][i];
        }
    }
    
    /**
     * 递归方法
     */
    private static void fun1(int recursionNum) {
        for (int i = 0; i < m; i++) {
            if (recursionNum < n) {
                record[recursionNum] = i;
                
                wCache += ws[recursionNum][i];
                cCache += cs[recursionNum][i];
                fun1(recursionNum + 1);
                wCache -= ws[recursionNum][i];
                cCache -= cs[recursionNum][i];
            } else {
                if (cCache <= c && wCache < wMin) {
                    wMin = wCache;
                    System.arraycopy(record, 0, minRecord, 0, record.length);
                }
            }
        }
    }
    
    /**
     * 嵌套方法~s
     */
    private static void fun2(int nestingNum) {
        for (int i = 0; i < m; i++) {
            if (nestingNum < n) {
                record[nestingNum] = i;
                
                wCache += ws[nestingNum][i];
                cCache += cs[nestingNum][i];
                fun2(nestingNum + 1);
                wCache -= ws[nestingNum][i];
                cCache -= cs[nestingNum][i];
            } else {
                if (cCache <= c && wCache < wMin) {
                    wMin = wCache;
                    System.arraycopy(record, 0, minRecord, 0, record.length);
                }
            }
        }
    }
    
}
