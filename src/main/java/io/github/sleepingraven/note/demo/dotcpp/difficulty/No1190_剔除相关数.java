package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2019/12/28
 */
public class No1190_剔除相关数 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            if (n == 0) {
                break;
            }
            
            TreeMap<NumCounter, Integer> map = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                int src = scanner.nextInt();
                char[] chars = String.valueOf(src).toCharArray();
                Arrays.sort(chars);
                int num = Integer.parseInt(String.valueOf(chars));
                
                NumCounter floorKey = map.floorKey(NumCounter.getInstance(num));
                if (floorKey != null && floorKey.formattedNum.equals(num)) {
                    floorKey.multiple = true;
                } else {
                    map.put(new NumCounter(num), src);
                }
            }
            
            map.entrySet().removeIf(entry -> entry.getKey().multiple);
            
            if (map.size() > 0) {
                TreeSet<Integer> set = new TreeSet<>(map.values());
                for (Integer i : set) {
                    System.out.print(i + " ");
                }
            } else {
                System.out.print("None");
            }
            System.out.println();
        }
    }
    
    private static class NumCounter implements Comparable<NumCounter> {
        private static final NumCounter singletonInstance = new NumCounter();
        
        Integer formattedNum;
        boolean multiple;
        
        static NumCounter getInstance(int num) {
            singletonInstance.formattedNum = num;
            return singletonInstance;
        }
        
        @Override
        public int compareTo(NumCounter o) {
            return formattedNum.compareTo(o.formattedNum);
        }
        
        public NumCounter() {
        }
        
        public NumCounter(Integer formattedNum) {
            this.formattedNum = formattedNum;
        }
        
        @Override
        public int hashCode() {
            return formattedNum.hashCode();
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NumCounter) {
                return formattedNum.equals(((NumCounter) obj).formattedNum);
            }
            return false;
        }
        
    }
    
}
