package io.github.sleepingraven.note.helloworld.string;

/**
 * 字符串匹配，KMP算法
 * 来源：网络
 *
 * @author Carry
 * @since 2020/5/29
 */
public class StringMatching {
    
    public static void main(String[] args) {
        String t = "abccacababaabb";
        String p = "ababaab";
        System.out.println(KMP(t, p));
    }
    
    /**
     * 暴力破解法
     *
     * @param ts
     *         主串
     * @param ps
     *         模式串
     *
     * @return 如果找到，返回在主串中第一个字符出现的下标，否则为-1
     */
    public static int bf(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        // 主串的位置
        int i = 0;
        // 模式串的位置
        int j = 0;
        
        while (i < t.length && j < p.length) {
            if (t[i] == p[j]) {
                // 当两个字符相同，就比较下一个
                i++;
                j++;
            } else {
                // 一旦不匹配，i后退
                i = i - j + 1;
                // j归0
                j = 0;
            }
        }
        
        return j == p.length ? i - j : -1;
    }
    
    /**
     * 当 ps 很大时，如果倒数 ps.length 的元素不匹配，不能提前结束
     */
    public static int KMP(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        // 主串的位置
        int i = 0;
        // 模式串的位置
        int j = 0;
        
        int[] next = getNext(p);
        // int[] next = getNextOptimized(p);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) {
                // 当j为-1时，要移动的是i，当然j也要归0
                i++;
                j++;
            } else {
                // i不需要回溯了
                // i = i - j + 1;
                // j回到指定位置
                j = next[j];
            }
        }
        
        return j == p.length ? i - j : -1;
    }
    
    /**
     * 求模式串的next数组
     *
     * @param p
     *         模式串
     *
     * @return next数组
     */
    public static int[] getNext(char[] p) {
        int[] next = new int[p.length];
        next[0] = -1;
        
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        
        return next;
    }
    
    public static int[] getNextOptimized(char[] p) {
        int[] next = new int[p.length];
        next[0] = -1;
        
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                // 当两个字符相等时要跳过
                if (p[++j] == p[++k]) {
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        
        return next;
    }
    
}
