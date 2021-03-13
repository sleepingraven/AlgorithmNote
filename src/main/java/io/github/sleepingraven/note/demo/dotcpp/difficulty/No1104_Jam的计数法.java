package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author 10132
 */
public class No1104_Jam的计数法 {
    private static final char C0 = 'a' - 1;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char begin = (char) (C0 + scanner.nextInt());
        char end = (char) (C0 + scanner.nextInt());
        int n = scanner.nextInt();
        
        char[] chars = scanner.next().toCharArray();
        for (int i = 0; i < 5; i++) {
            int j;
            for (j = n - 1; j >= 0; j--) {
                if (chars[j] != end + j - (n - 1)) {
                    chars[j]++;
                    for (int k = j + 1; k < n; k++) {
                        chars[k] = (char) (chars[k - 1] + 1);
                    }
                    break;
                }
            }
            
            if (j < 0) {
                return;
            } else {
                System.out.println(new String(chars));
            }
        }
    }
    
}
