package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/5
 */
public class No1266_马拦过河卒 {
    
    /**
     * 6+++++++
     * 5++O+O++
     * 4+O+++O+
     * 3+++O+++
     * 2+O+++O+
     * 1++O+O++
     * 0+++++++
     * .0123456
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 目标点
        Point b = new Point(scanner.nextInt(), scanner.nextInt());
        // 马的位置
        Point h = new Point(scanner.nextInt(), scanner.nextInt());
        
        // 棋盘
        int[][] board = new int[b.y + 1][b.x + 1];
        // 非法标记
        boolean[][] mark = new boolean[b.y + 1][b.x + 1];
        markNotAllowed(mark, h, b);
        
        // 第一行
        for (int i = 0; i <= b.x; i++) {
            if (mark[0][i]) {
                break;
            }
            board[0][i] = 1;
        }
        // 第一列
        for (int i = 0; i <= b.y; i++) {
            if (mark[i][0]) {
                break;
            }
            board[i][0] = 1;
        }
        
        for (int i = 1; i <= b.y; i++) {
            for (int j = 1; j <= b.x; j++) {
                if (mark[i][j]) {
                    continue;
                }
                board[i][j] = board[i - 1][j] + board[i][j - 1];
            }
        }
        System.out.println(board[b.y][b.x]);
    }
    
    /**
     * 寻找非法的点，并标记
     */
    private static void markNotAllowed(boolean[][] mark, Point h, Point b) {
        mark(h, b, mark);
        for (int i = 0; i <= 1; i++) {
            // x加或减
            int xSign = i % 2 == 0 ? 1 : -1;
            for (int j = 0; j <= 1; j++) {
                // y加或减
                int ySign = j % 2 == 0 ? 1 : -1;
                Point point = new Point(h.x + xSign, h.y + ySign * 2);
                mark(point, b, mark);
                point = new Point(h.x + xSign * 2, h.y + ySign);
                mark(point, b, mark);
            }
        }
    }
    
    /**
     * 如果点在A-B矩阵里，标记为true
     */
    private static void mark(Point p, Point b, boolean[][] mark) {
        if (p.x >= 0 && p.x <= b.x && p.y >= 0 && p.y <= b.y) {
            mark[p.y][p.x] = true;
        }
    }
    
    /**
     * 求组合数
     */
    private static long co(int x, int y) {
        int d = x + y;
        return jc(d) / (jc(x) * jc(y));
    }
    
    /**
     * 求阶乘
     */
    private static long jc(int n) {
        long res = 1;
        for (int i = 2; i <= n; i++) {
            res *= i;
        }
        return res;
    }
    
    private static class Point {
        int x;
        int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
    }
    
}
