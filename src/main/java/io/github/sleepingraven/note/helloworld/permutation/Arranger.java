package io.github.sleepingraven.note.helloworld.permutation;

import lombok.ToString;

/**
 * @author Carry
 * @since 2020/1/6
 */
@ToString
public class Arranger implements Permutation {
    private final StringBuilder stringBuilder;
    
    public Arranger() {
        stringBuilder = new StringBuilder();
    }
    
    public Arranger(String str) {
        this.stringBuilder = new StringBuilder(str);
    }
    
    /**
     * 生成下一个排列（不计重复值）
     */
    public boolean nextPermutation(int from, int to) {
        // 找到PartitionNumber
        int p;
        for (p = to - 2; p >= from; p--) {
            if (stringBuilder.charAt(p) < stringBuilder.charAt(p + 1)) {
                break;
            }
        }
        if (p < from) {
            return false;
        }
        
        // 找到逆序中大于PartitionNumber的最小元素
        int i = to - 1;
        while (stringBuilder.charAt(i) <= stringBuilder.charAt(p)) {
            i--;
        }
        swap(i, p);
        // 将尾部的逆序变成正序
        reverse(p + 1, to);
        return true;
    }
    
    /**
     * 生成上一个排列（不计重复值）
     */
    public boolean prevPermutation(int from, int to) {
        // 找到PartitionNumber
        int p;
        for (p = to - 2; p >= from; p--) {
            if (stringBuilder.charAt(p) > stringBuilder.charAt(p + 1)) {
                break;
            }
        }
        if (p < from) {
            return false;
        }
        
        // 找到顺序中小于PartitionNumber的最大元素
        int i = to - 1;
        while (stringBuilder.charAt(i) >= stringBuilder.charAt(p)) {
            i--;
        }
        swap(i, p);
        // 将尾部的顺序变成逆序
        reverse(p + 1, to);
        return true;
    }
    
    public static void main(String[] args) {
        Arranger cer = new Arranger("00001");
        
        int count = 0;
        do {
            System.out.println(cer);
            count++;
        } while (cer.nextPermutation(0, cer.length()));
        System.out.println(count);
        
        count = 0;
        do {
            System.out.println(cer);
            count++;
        } while (cer.prevPermutation(0, cer.length()));
        System.out.println(count);
    }
    
    public int length() {
        return stringBuilder.length();
    }
    
    void swap(int i, int j) {
        char t = stringBuilder.charAt(i);
        stringBuilder.setCharAt(i, stringBuilder.charAt(j));
        stringBuilder.setCharAt(j, t);
    }
    
    /**
     * 反转fromIndex到toIndex（不包含）之间的内容
     **/
    void reverse(int from, int to) {
        for (to--; from < to; from++, to--) {
            swap(from, to);
        }
    }
    
}
