package io.github.sleepingraven.note.demo.questions.q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Walker {
    
    /**
     * https://ac.nowcoder.com/acm/contest/9925/D
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        // int _ = 1;
        int _ = Integer.parseInt(in.readLine());
        while (_-- > 0) {
            String[] s = in.readLine().split(" ");
            double n = Double.parseDouble(s[0]);
            Traveler[] ts = new Traveler[2];
            ts[0] = new Traveler(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
            ts[1] = new Traveler(Double.parseDouble(s[3]), Double.parseDouble(s[4]));
            
            Arrays.sort(ts);
            
            double ans = 2_000_000_000;
            
            // 一个人走完全程
            ans = Math.min(ans, alone(n, ts[0].p, ts[0].v));
            ans = Math.min(ans, alone(n, ts[1].p, ts[1].v));
            
            // p1 向右走，p2 向左走
            ans = Math.min(ans, Math.max((n - ts[0].p) / ts[0].v, ts[1].p / ts[1].v));
            
            // 每人一段
            double l = ts[0].p, r = ts[1].p;
            double ans1 = 0, ans2 = 0;
            for (int i = 1; i <= 100; i++) {
                double mid = (l + r) / 2;
                ans1 = alone(mid, ts[0].p, ts[0].v);
                ans2 = alone(n - mid, n - ts[1].p, ts[1].v);
                
                if (ans1 < ans2) {
                    l = mid;
                } else {
                    r = mid;
                }
            }
            ans = Math.min(ans, Math.max(ans1, ans2));
            
            out.printf("%.8f\n", ans);
        }
        
        out.close();
    }
    
    static double alone(double n, double p, double v) {
        return Math.min((p + n) / v, (n - p + n) / v);
    }
    
    static class Traveler implements Comparable<Traveler> {
        final double p;
        final double v;
        
        public Traveler(double p, double v) {
            this.p = p;
            this.v = v;
        }
        
        @Override
        public int compareTo(Traveler t) {
            return Double.compare(p, t.p);
        }
        
    }
    
}
