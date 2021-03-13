package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/30
 */
public class No1205_字符串的修改_rc {
    public static int min = Integer.MAX_VALUE;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        String b = scanner.nextLine();
        
        // 这是a > b 的情况
        fun(a, b, 0, 0);
        System.out.println(min);
    }
    
    /**
     * 正常递归搜索解法
     */
    private static void fun(String a, String b, int begin, int change) {
        // 本次递归的操作数都大于全局最小，此方法一定不是最优解
        if (change >= min) {
            return;
        }
        
        if (begin == a.length()) {
            // 如果修改后的字符串b长度大于a 之后需要移除操作
            if (b.length() > a.length()) {
                change += b.length() - a.length();
            }
            
            if (change < min) {
                min = change;
            }
            return;
        }
        
        if (begin == b.length()) {
            // 只能插入 因为错误的字符串长度不够
            // // 插入
            b = AddCharIndexOf(b, begin, a.charAt(begin));
            fun(a, b, begin + 1, change + 1);
            //System.out.println("[长度不够]插入之后：" + b);
        } else {
            if (b.charAt(begin) != a.charAt(begin)) {
                String tmp = b;// 下面也需要递归 所以用变量先储存下来
                // 删除
                b = RemoveCharIndexOf(b, begin);
                fun(a, b, begin, change + 1);
                //System.out.println("删除之后：" + b);
                b = tmp;// 回溯
                
                // // 插入
                b = AddCharIndexOf(b, begin, a.charAt(begin));
                fun(a, b, begin + 1, change + 1);
                //System.out.println("插入之后：" + b);
                b = tmp;// 回溯
                
                // 替换
                b = ReplaceCharIndexOf(b, begin, a.charAt(begin));
                fun(a, b, begin + 1, change + 1);
                // System.out.println("替换之后：" + b);
                b = tmp;// 回溯
            } else {
                // 如果对应的字符正确，跳过即可
                fun(a, b, begin + 1, change);
            }
        }
    }
    
    private static String ReplaceCharIndexOf(String b, int begin, char charAt) {
        if (begin != b.length() - 1) {
            // 不是结尾的字符串
            b = b.substring(0, begin) + charAt + b.substring(begin + 1);
        } else {
            b = b.substring(0, begin) + charAt;
        }
        return b;
    }
    
    private static String AddCharIndexOf(String b, int begin, char charAt) {
        if (begin == b.length()) {
            // 长度不够 对尾加上即可
            return b + charAt;
        } else {
            return b.substring(0, begin) + charAt + b.substring(begin);
        }
    }
    
    private static String RemoveCharIndexOf(String b, int begin) {
        if (begin != b.length() - 1) {
            // 不是结尾的字符串
            b = b.substring(0, begin) + b.substring(begin + 1);
        } else {
            b = b.substring(0, begin);
        }
        return b;
    }
    
}
