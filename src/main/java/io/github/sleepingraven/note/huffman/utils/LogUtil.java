package io.github.sleepingraven.note.huffman.utils;

import io.github.sleepingraven.util.common.ArrayStack;
import io.github.sleepingraven.util.common.Stack;

/**
 * 控制台打印工具类
 *
 * @author Carry
 * @since 2020/6/8
 */
public class LogUtil {
    // todo 可以重构
    /**
     * 记录每个任务开始时的时间戳
     */
    private static final Stack<Long> TIMESTAMPS = new ArrayStack<>();
    /**
     * 每个任务是否带有状态显示器。如果带有，在结束时需要换行
     */
    private static final Stack<Boolean> WITH_STATUS = new ArrayStack<>();
    
    public static void begin(String msg, boolean lineWrapExtra, int taskLevel, boolean withStatus) {
        System.out.println(getTitle(msg, taskLevel));
        if (lineWrapExtra) {
            System.out.println();
        }
        TIMESTAMPS.push(System.currentTimeMillis());
        WITH_STATUS.push(withStatus);
    }
    
    public static void status(String msg, long current, long total) {
        if (msg == null) {
            msg = "当前进度";
        }
        double percent = (double) current / total * 100;
        String percentString = String.format("[%.2f%%]", percent);
        System.out.printf("\r%s：%9s %s %,d/%,d", msg, percentString, STATUS_BAR[(int) Math.round(percent)], current,
                          total);
    }
    
    public static void end(String msg, boolean lineWrapExtra, int taskLevel) {
        if (WITH_STATUS.pop()) {
            System.out.println();
        }
        System.out.println(getTitle(msg, taskLevel));
        System.out.printf("用时：%,11dms%n", System.currentTimeMillis() - TIMESTAMPS.pop());
        if (TIMESTAMPS.isEmpty()) {
            System.out.println();
        }
        if (lineWrapExtra) {
            System.out.println();
        }
    }
    
    /* 工具 */
    
    private static final char PROCESS_ICON_HALF = '▢';
    private static final char PROCESS_ICON_WHOLE = '▧';
    private static final String[] STATUS_BAR = new String[100 + 1];
    
    static {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        for (int i = 0; i < 50; i++) {
            buffer.append(' ');
        }
        buffer.append(']');
        STATUS_BAR[0] = buffer.toString();
        for (int i = 2; i <= 100; i += 2) {
            buffer.setCharAt(i / 2, PROCESS_ICON_HALF);
            STATUS_BAR[i - 1] = buffer.toString();
            buffer.setCharAt(i / 2, PROCESS_ICON_WHOLE);
            STATUS_BAR[i] = buffer.toString();
        }
    }
    
    private static String getTitle(String msg, int taskLevel) {
        if (msg.length() >= 10) {
            return msg;
        } else {
            String separator;
            switch (taskLevel) {
                case 1:
                    separator = "**********";
                    break;
                case 2:
                    separator = "==========";
                    break;
                default:
                    separator = "----------";
            }
            String padding = separator.substring(msg.length());
            return padding + msg + padding;
        }
    }
    
}
