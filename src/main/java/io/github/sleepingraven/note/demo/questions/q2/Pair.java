package io.github.sleepingraven.note.demo.questions.q2;

/**
 * @author Carry
 * @since 2020/10/14
 */
public class Pair {
    static int count;
    
    /**
     * 由1对括号，可以组成一种合法括号序列：()。<br>
     * 由2对括号，可以组成两种合法括号序列：()()、(())。<br>
     * 由4对括号，可以组成14种合法括号序列。<br>
     * 由n对括号组成的合法括号序列一共有多少种？
     */
    public static void main(String[] args) {
        final int n = 4;
        Solution.f(0, 0, n);
        System.out.println(count);
    }
    
    private static class Solution {
        
        private static void f(int l, int r, final int n) {
            if (l == n) {
                count++;
                return;
            }
            
            f(l + 1, r, n);
            if (l > r) {
                f(l, r + 1, n);
            }
        }
        
    }
    
}
