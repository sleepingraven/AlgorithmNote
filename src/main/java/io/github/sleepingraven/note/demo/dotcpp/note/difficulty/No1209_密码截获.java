package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/30
 */
public class No1209_密码截获 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            
            int m = 1;
            StringBuilder stringBuilder = new StringBuilder(s);
            do {
                int j;
                StringBuilder sbCopy = new StringBuilder(stringBuilder);
                while ((j = sbCopy.lastIndexOf(String.valueOf(sbCopy.charAt(0)))) > 0) {
                    j++;
                    sbCopy.delete(j, sbCopy.length());
                    if (new StringBuilder(sbCopy).reverse().toString().contentEquals(sbCopy)) {
                        if (j > m) {
                            m = j;
                        }
                        break;
                    }
                    sbCopy.deleteCharAt(sbCopy.length() - 1);
                }
                stringBuilder.deleteCharAt(0);
            } while (stringBuilder.length() > m);
            
            System.out.println(m);
        }
    }
    
}
