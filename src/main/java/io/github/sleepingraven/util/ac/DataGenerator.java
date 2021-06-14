package io.github.sleepingraven.util.ac;

import io.github.sleepingraven.util.ac.Leetcode.ListNode;
import io.github.sleepingraven.util.ac.Leetcode.TreeNode;
import io.github.sleepingraven.util.datastructure.binarytree.AnnotatingBinaryTreeUtil;
import io.github.sleepingraven.util.datastructure.linkedlist.AnnotatingLinkedListUtil;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Carry
 * @since 2020/11/25
 */
public class DataGenerator {
    
    public static ListNode buildList(Integer... vals) {
        return AnnotatingLinkedListUtil.generate(vals, ListNode.class);
    }
    
    public static TreeNode buildTree(Integer... vals) {
        return AnnotatingBinaryTreeUtil.generate(vals, TreeNode.class);
    }
    
    public static BitSet bitset(int initialValue, int capacity) {
        BitSet bs = new BitSet(capacity);
        while (initialValue != 0) {
            int lowest = Integer.lowestOneBit(initialValue);
            bs.set(lowest);
            initialValue ^= lowest;
        }
        return bs;
    }
    
    /**
     * 元素不能为空：「[]」
     */
    public static <A> A parseIntArray(String str) {
        str = peeling(str);
        if (str.startsWith("[")) {
            List<String> elems = partition(str);
            List<?> parsed = elems.stream().map(DataGenerator::parseIntArray).collect(Collectors.toList());
            Class<?> componentType = parsed.get(0).getClass();
            return (A) populate(parsed, componentType);
        } else {
            if (str.isEmpty()) {
                throw new IllegalArgumentException("No support for empty elements string.");
            } else {
                return (A) parseIntsNonEmpty(str);
            }
        }
    }
    
    /**
     * 这里需要传入类型，因为 int[0][] 和 int[0] 都可以表示为「[]」，此时不能自动推断
     */
    public static <A> A parseIntArray(String str, Class<A> clazz) {
        Class<?> componentType = clazz.getComponentType();
        str = peeling(str);
        if (str.startsWith("[")) {
            List<String> elems = partition(str);
            List<?> parsed =
                    elems.stream().map(str1 -> parseIntArray(str1, componentType)).collect(Collectors.toList());
            return (A) populate(parsed, componentType);
        } else {
            if (str.isEmpty()) {
                return (A) Array.newInstance(componentType, 0);
            } else {
                return (A) parseIntsNonEmpty(str);
            }
        }
    }
    
    public static <A> A parseStringArray(String str, Class<A> clazz) {
        return parseComplexArray(str, clazz, String::valueOf);
    }
    
    public static <A> A parseComplexArray(String str, Class<A> clazz, Function<String, ?> converter) {
        Class<?> componentType = clazz.getComponentType();
        str = peeling(str);
        if (str.startsWith("[")) {
            List<String> elems = partition(str);
            List<?> parsed = elems.stream()
                                  .map(str1 -> parseComplexArray(str1, componentType, converter))
                                  .collect(Collectors.toList());
            return (A) populate(parsed, componentType);
        } else {
            if (str.isEmpty()) {
                return (A) Array.newInstance(componentType, 0);
            } else {
                List<?> collect =
                        Arrays.stream(str.split(",")).map(String::trim).map(converter).collect(Collectors.toList());
                return (A) populate(collect, componentType);
            }
        }
    }
    
    /* helpers */
    
    public static String peeling(String str) {
        str = str.trim();
        str = str.substring(1, str.length() - 1);
        str = str.trim();
        return str;
    }
    
    public static List<String> partition(String str) {
        List<String> elems = new ArrayList<>();
        int balance = 0;
        int anchor = 0;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '[':
                    if (balance++ == 0) {
                        anchor = i;
                    }
                    break;
                case ']':
                    if (--balance == 0) {
                        String substring = str.substring(anchor, i + 1);
                        elems.add(substring);
                        anchor = i;
                    }
                    break;
                default:
            }
        }
        return elems;
    }
    
    public static <E> E[] populate(List<?> elems, Class<?> componentType) {
        Object a = Array.newInstance(componentType, elems.size());
        for (int i = 0; i < elems.size(); i++) {
            Array.set(a, i, elems.get(i));
        }
        return (E[]) a;
    }
    
    /**
     * str 中至少包含一个数字
     */
    public static int[] parseIntsNonEmpty(String str) {
        return Arrays.stream(str.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
    }
    
}
