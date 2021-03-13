package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/1/2
 */
public class No1240_生日日数 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int y = scanner.nextInt();
            int m = scanner.nextInt();
            int d = scanner.nextInt();
            
            fun1(y, m, d);
            fun2(y, m, d);
        }
    }
    
    private static void fun1(int y, int m, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d);
        calendar.add(Calendar.DATE, 10000);
        System.out.println(String.format("%d-%d-%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                                         calendar.get(Calendar.DAY_OF_MONTH)));
    }
    
    private static void fun2(int y, int m, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d);
        calendar.setTimeInMillis(calendar.getTimeInMillis() + 10000L * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }
    
}
