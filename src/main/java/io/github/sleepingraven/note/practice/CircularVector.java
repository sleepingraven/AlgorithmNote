package io.github.sleepingraven.note.practice;

/**
 * 从 (left, top) 开始，顺时针移动
 *
 * @author Carry
 * @since 2020/7/12
 */
public class CircularVector {
    /**
     * 行 / 列 / 移动标识（横向 1、纵向 0）
     */
    private static final int[][] DIR = new int[][] {
            { 0, 1, 1 }, { 1, 0, 0 }, { 0, -1, 1 }, { -1, 0, 0 }
    };
    private int di;
    
    private final int[] coordinate;
    /**
     * i：对应标识符。j：向[0-增大/1-减小]的方向移动
     */
    private final int[][] bound;
    
    public CircularVector(int left, int right, int top, int bottom) {
        coordinate = new int[] { left, top };
        bound = new int[][] { { bottom + 1, top }, { right + 1, left - 1 } };
    }
    
    public boolean next() {
        final int m = moveIdentifier();
        final int tj = DIR[di][m] >>> 31;
        if (coordinate[m] == bound[m][tj]) {
            return false;
        }
        
        if (coordinate[m] + DIR[di][m] == bound[m][tj]) {
            bound[m][tj] = coordinate[m];
            di = (di + 1) % DIR.length;
        }
        coordinate[0] += DIR[di][0];
        coordinate[1] += DIR[di][1];
        return true;
    }
    
    public int x() {
        return coordinate[0];
    }
    
    public int y() {
        return coordinate[1];
    }
    
    /**
     * 获得当前移动标识符
     */
    private int moveIdentifier() {
        return DIR[di][2];
    }
    
}
