package io.github.sleepingraven.draft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * AC template with I/O
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int _ = 1;
        // int _ = Integer.parseInt(in.readLine());
        turn:
        while (_-- > 0) {
            String[] s = in.readLine().split(" ");
            double n = Double.parseDouble(s[0]);
            int[] a = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            System.out.println(Integer.toBinaryString(100000));
            
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    for (int k = j + 1; k < n; k++) {
                        for (int m = k + 1; m < n; m++) {
                            if ((a[i] ^ a[j] ^ a[k] ^ a[m]) == 0) {
                                out.println("Yes");
                                break turn;
                            }
                        }
                    }
                }
            }
            
            out.println("No");
        }
        
        out.close();
    }
    
}
