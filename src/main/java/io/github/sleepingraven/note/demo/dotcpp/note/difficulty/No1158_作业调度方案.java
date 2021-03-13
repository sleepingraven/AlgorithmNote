package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/11/17
 */
public class No1158_作业调度方案 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 机器数m
        int machineNum = scanner.nextInt();
        // 工件数n
        int partNum = scanner.nextInt();
        // scanner.nextLine();
        
        // 工序序列
        Task[] sequence = new Task[machineNum * partNum];
        // mapper[i].get(j)表示i+1号工件j+1次工序的安排序号（0开始）
        LinkedList<Integer>[] mapper = new LinkedList[partNum];
        for (int i = 0; i < partNum; i++) {
            mapper[i] = new LinkedList<>();
        }
        for (int i = 0; i < machineNum * partNum; i++) {
            int in = scanner.nextInt() - 1;
            // 设置part
            sequence[i] = new Task(in);
            mapper[in].add(i);
        }
        
        for (int i = 0; i < partNum; i++) {
            for (int j = 0; j < machineNum; j++) {
                // 设置machineNo
                sequence[mapper[i].get(j)].machineNo = scanner.nextInt() - 1;
            }
        }
        for (int i = 0; i < partNum; i++) {
            for (int j = 0; j < machineNum; j++) {
                // 设置time
                sequence[mapper[i].get(j)].time = scanner.nextInt();
            }
        }
        
        // 各机器的空闲情况
        TreeSet<Range>[] sets = new TreeSet[machineNum];
        for (int i = 0; i < machineNum; i++) {
            sets[i] = new TreeSet<>();
            sets[i].add(new Range(0, Integer.MAX_VALUE));
        }
        // 暂存每个工件的截止时间
        int[] lasts = new int[partNum];
        Range range;
        for (Task t : sequence) {
            TreeSet<Range> set = sets[t.machineNo];
            range = Range.getInstance(0, t.time);
            // 查找空闲区域
            do {
                range = set.higher(range);
            } while (range.size - lasts[t.partNo] < t.time - range.begin);
            set.remove(range);
            // 左侧空闲区域
            if (range.begin < lasts[t.partNo]) {
                set.add(new Range(range.begin, lasts[t.partNo] - range.begin));
            }
            lasts[t.partNo] = Math.max(lasts[t.partNo], range.begin) + t.time;
            // 右侧空闲区域
            if (range.begin + range.size > lasts[t.partNo]) {
                range.begin = lasts[t.partNo];
                range.size -= lasts[t.partNo];
                set.add(range);
            }
        }
        
        Arrays.sort(lasts);
        System.out.println(lasts[lasts.length - 1]);
    }
    
    private static class Task {
        int partNo;
        int machineNo;
        int time;
        
        Task(int part) {
            this.partNo = part;
        }
        
    }
    
    private static class Range implements Comparable<Range> {
        private static final Range singletonInstance = new Range(0, 0);
        Integer begin;
        Integer size;
        
        @Override
        public int compareTo(Range o) {
            int m = size.compareTo(o.size);
            if (m != 0) {
                return m;
            }
            return begin.compareTo(o.begin);
        }
        
        static Range getInstance(int begin, int size) {
            singletonInstance.begin = begin;
            singletonInstance.size = size;
            return singletonInstance;
        }
        
        Range(Integer begin, int size) {
            this.begin = begin;
            this.size = size;
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
        
    }
    
}
