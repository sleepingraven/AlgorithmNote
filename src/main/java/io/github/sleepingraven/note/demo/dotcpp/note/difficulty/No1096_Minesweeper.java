package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author 10132
 */
public class No1096_Minesweeper {
    private static char[][] area = new char[100][100];
    private static int x, y;
    
    private static int xFrom;
    private static int xTo;
    private static int yFrom;
    private static int yTo;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mineNum;
        int safeNum;
        String str;
        int n = 0;
        while ((x = scanner.nextInt()) != 0 && (y = scanner.nextInt()) != 0) {
            mineNum = safeNum = 0;
            for (int i = 0; i < x; i++) {
                str = scanner.next();
                for (int j = 0; j < y; j++) {
                    if (str.charAt(j) == '.') {
                        area[i][j] = '0';
                        safeNum++;
                    } else {
                        area[i][j] = '*';
                        mineNum++;
                    }
                }
            }
            
            //判断用哪种方法
            if (mineNum <= safeNum) {
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (area[i][j] == '*') {
                            minesweeper(i, j);
                        }
                    }
                }
            } else {
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (area[i][j] != '*') {
                            sweepMine(i - 1, j - 1);
                        }
                    }
                }
            }
            
            System.out.println("Field #" + ++n + ":");
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    System.out.print(area[i][j]);
                }
                System.out.println();
            }
            
            System.out.println();
        }
    }
    
    /**
     * 轰炸法
     */
    private static void minesweeper(int xOfMine, int yOfMine) {
        xFrom = Math.max(xOfMine - 1, 0);
        xTo = Math.min(xOfMine + 1, x - 1);
        yFrom = Math.max(yOfMine - 1, 0);
        yTo = Math.min(yOfMine + 1, y - 1);
        
        for (int i = xFrom; i <= xTo; i++) {
            for (int j = yFrom; j <= yTo; j++) {
                if (area[i][j] != '*') {
                    area[i][j]++;
                }
            }
        }
    }
    
    /**
     * 排雷法
     */
    private static void sweepMine(int xNonMine, int yNonMine) {
        xFrom = Math.max(xNonMine - 1, 0);
        xTo = Math.min(xNonMine + 1, x - 1);
        yFrom = Math.max(yNonMine - 1, 0);
        yTo = Math.min(yNonMine + 1, y - 1);
        
        for (int i = xFrom; i <= xTo; i++) {
            for (int j = yFrom; j <= yTo; j++) {
                if (area[i][j] == '*') {
                    area[xNonMine][yNonMine]++;
                }
            }
        }
    }
    
}
