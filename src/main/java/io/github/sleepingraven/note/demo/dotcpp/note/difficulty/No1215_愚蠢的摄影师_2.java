package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/31
 */
public class No1215_愚蠢的摄影师_2 {
    static int sum = 0;
    static int[] res;
    
    /**
     * 超时<p>
     * 采用变形全排列思路，当a[i]和a[i+1]两个元素交换时，根据两个相邻元素差不超过2，所以a[i-1]到a[i+2]是不能移动的，除掉i==a.length-3这种情况
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        res = new int[n];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        ff(1);
        System.out.println(sum);
    }
    
    static void ff(int index) {
        if (index >= res.length - 1) {
            sum++;
            return;
        }
        
        for (int i = 0; i < 2; i++) {
            int temp = res[index];
            res[index] = res[index + i];
            res[index + i] = temp;
            
            if (i == 1 && index != res.length - 3) {
                ff(index + 3);
            } else {
                ff(index + 1);
            }
            
            temp = res[index];
            res[index] = res[index + i];
            res[index + i] = temp;
        }
    }
    
}
