package io.github.sleepingraven.note.demo.questions.q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Fireworks {
    
    /**
     * https://ac.nowcoder.com/acm/contest/10272/F
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        // int _ = 1;
        int _ = Integer.parseInt(in.readLine());
        while (_-- > 0) {
            String[] s = in.readLine().split(" ");
            int n = Integer.parseInt(s[0]);
            int m = Integer.parseInt(s[1]);
            double p = Double.parseDouble(s[2]);
            
            p /= 10000;
            int l = 1, r = (int) 1e9;
            while (l < r) {
                int ml = l + (r - l) / 3, mr = r - (r - l) / 3;
                if (cal(ml, n, m, p) > cal(mr, n, m, p)) {
                    l = ml + 1;
                } else {
                    r = mr - 1;
                }
            }
            
            out.printf("%.8f\n", Math.min(cal(l, n, m, p), cal(r, n, m, p)));
        }
        
        out.close();
    }
    
    static double cal(int k, int n, int m, double p) {
        return ((double) k * n + m) / (1.0 - Math.pow(1.0 - p, k));
    }
    
}
