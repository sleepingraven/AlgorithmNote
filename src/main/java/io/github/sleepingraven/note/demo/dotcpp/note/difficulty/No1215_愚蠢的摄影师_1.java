package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/31
 */
public class No1215_愚蠢的摄影师_1 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(fun1(n));
    }
    
    /**
     * DFS
     */
    private static int fun0() {
        return 0;
    }
    
    /**
     * 这种题其实和1004这种差不多，都是每几个状态之后状态变化发生重复，现在的结果必然会对以后造成影响，所以现在的个数必然会对将来状态的个数造成影响，比如1004的母牛问题，每4年都是一个新的开始4年前的小羊长成大羊可以继续生产，所以把这个的前几个结果写出来
     * 1，1，2，4，6，9，14。。。。。。可以看出公式a[i] = a[i - 1] + a[i - 3] + 1;
     */
    private static int fun1(int n) {
        int[] res = new int[56];
        res[1] = 1;
        res[2] = 1;
        res[3] = 2;
        res[4] = 4;
        for (int i = 5; i < res.length; i++) {
            res[i] = res[i - 1] + res[i - 3] + 1;
        }
        
        return res[n];
    }
    
}
