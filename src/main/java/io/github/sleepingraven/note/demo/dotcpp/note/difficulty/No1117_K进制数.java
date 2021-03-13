package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/16
 */
public class No1117_K进制数 {
    private static int k;
    
    /**
     * 这个题应该是一个递推题。<p>
     * 可用s[i]表示第i位k进制数的总数，就有：s[i] = (s[i - 1] + s[i - 2]) * (k - 1)。
     * 解释一下：第i位k进制数的总数s[i]等于：第i - 1位为0与不为0的情况之和乘以第i位的情况数(1到k - 1)
     * (1)第i - 1位为0的情况应该等于i - 2位不为0的情况总数，即s[i - 2]
     * (2)第i - 1位不为0的情况应该等于s[i - 1]
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        k = scanner.nextInt();
        
        System.out.println(s(n));
    }
    
    private static int s(int n) {
        if (n == 1) {
            return k - 1;
        } else if (n == 2) {
            return k * (k - 1);
        } else {
            return s(n - 1) * (k - 1) + s(n - 2) * (k - 1);
        }
    }
    
}
