package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/28
 */
public class No1205_字符串的修改_dp {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        String b = scanner.nextLine();
        
        judge(a, b);
    }
    
    /**
     * 感觉测试用例不全，用长度减去相同字符个数，没有考虑既要插入又要删除的情况，
     * 举个例子：
     * s1串：abcde
     * s2串：gabce
     * 串长度减去相同字符个数：5 - 4 = 1，但显然是要在s1串插入g删除b，即2次操作<p>
     * 正确的做法应该是用动态规划<p>
     * dp[i][j]表示a[0]-a[i]子串转换为b[0]-b[j]所需的字符操作次数。
     * 若a[i]==b[j]，则dp[i][j]==dp[i-1][j-1]，表示不需要操作;
     * 若a[i]!=b[j]，则可能的操作有替换、插入、删除三种方法，分别由前一步得出，并取最小值，
     * dp[i-1][j] + 1表示往a插入，dp[i][j-1] + 1表示往b插入，dp[i-1][j-1]+1表示替换当前字符。
     */
    private static void judge(String a, String b) {
        // map即dp数组
        int[][] map = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < a.length(); i++) {
            map[i][0] = i;
        }
        for (int i = 0; i < b.length(); i++) {
            map[0][i] = i;
        }
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    map[i + 1][j + 1] = map[i][j];
                } else {
                    map[i + 1][j + 1] = Math.min(map[i][j], Math.min(map[i][j + 1], map[i + 1][j])) + 1;
                }
            }
        }
        System.out.println(map[a.length()][b.length()]);
    }
    
}
