package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/10
 */
public class No1108_守望者的逃离 {
    private final static int RUN = 17;
    private final static int FLASH = 60;
    private final static int COST = 10;
    private final static int RESTORE = 4;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int magic = scanner.nextInt();
        int distance = scanner.nextInt();
        int time = scanner.nextInt();
        
        int fd = 0, sum = 0;
        int it = time;
        while (it-- > 0) {
            if (magic >= COST) {
                magic -= COST;
                fd += FLASH;
            } else {
                magic += RESTORE;
            }
            sum = Math.max(sum + RUN, fd);
            
            if (sum >= distance) {
                break;
            }
        }
        
        if (sum >= distance && time >= 0) {
            System.out.println("Yes");
            System.out.println(time - it);
        } else {
            System.out.println("No");
            System.out.println(sum);
        }
    }
    
}
