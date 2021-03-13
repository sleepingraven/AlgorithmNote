package io.github.sleepingraven.note.demo.questions.q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class SumOfLog {
    static final int MOD = (int) 1e9 + 7;
    static int[][][][] dp = new int[40][2][2][2];
    static int[] a = new int[35];
    static int[] b = new int[35];
    static int ans;
    
    /**
     * https://ac.nowcoder.com/acm/contest/9925/C
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        // int _ = 1;
        int _ = Integer.parseInt(in.readLine());
        while (_-- > 0) {
            String[] s = in.readLine().split(" ");
            int x = Integer.parseInt(s[0]);
            int y = Integer.parseInt(s[1]);
            
            for (int i = 1; i <= 30; i++) {
                a[i] = (x >> (i - 1)) & 1;
                b[i] = (y >> (i - 1)) & 1;
            }
            for (int[][][] is : dp) {
                for (int[][] js : is) {
                    for (int[] ks : js) {
                        Arrays.fill(ks, -1);
                    }
                }
            }
            ans = 0;
            memoization(30, true, true, true);
            out.println(ans);
        }
        
        out.close();
    }
    
    static int memoization(int pos, boolean lim1, boolean lim2, boolean lead) {
        if (pos == 0) {
            return 1;
        }
        if (dp[pos][lim1 ? 1 : 0][lim2 ? 1 : 0][lead ? 1 : 0] != -1) {
            return dp[pos][lim1 ? 1 : 0][lim2 ? 1 : 0][lead ? 1 : 0];
        }
        
        int currTotal = 0;
        int up1 = lim1 ? a[pos] : 1;
        int up2 = lim2 ? b[pos] : 1;
        for (int i = 0; i <= up1; i++) {
            for (int j = 0; j <= up2; j++) {
                if (i + j == 2) {
                    break;
                }
                int curr = memoization(pos - 1, lim1 && (i == up1), lim2 && (j == up2), lead && (i + j == 0));
                if (i + j == 1 && lead) {
                    ans = (int) ((ans + (long) curr * pos) % MOD);
                }
                currTotal = (currTotal + curr) % MOD;
            }
        }
        
        return dp[pos][lim1 ? 1 : 0][lim2 ? 1 : 0][lead ? 1 : 0] = currTotal;
    }
    
}
