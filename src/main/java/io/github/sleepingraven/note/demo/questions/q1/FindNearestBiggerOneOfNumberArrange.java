package io.github.sleepingraven.note.demo.questions.q1;

import io.github.sleepingraven.util.ac.ArrayUtil;

import java.util.Arrays;

/**
 * 假设给出一个正整数，找出其所有数字全排列的下一个数。
 * <p>即在全部组合中，找出一个大于且仅大于原数的新整数</p>
 * <blockquote>
 * <pre>
 * 12345 -> 12354
 * 12354 -> 12435
 * 12435 -> 12453
 * </pre>
 * </blockquote>
 *
 * @author 10132
 */
public class FindNearestBiggerOneOfNumberArrange {
    
    /**
     * 解题思路
     * <ol>
     *     <li>
     *         字典排序法
     *         <p>
     *             为了和原数接近，尽量保持高位不变，低位在最小范围内变换
     *             <blockquote>
     *                 <pre>
     * 1 2 3 5 4
     *       |_|
     *     逆序区域
     *                 </pre>
     *             </blockquote>
     *             要比原数更大，要从倒数第3位开始改变
     *             <blockquote>
     *                 <pre>
     *     &uarr;&macr;&macr;&macr;&darr;
     * 1 2 3 5 4
     *     &uarr;___&darr;
     *     &dArr;
     * 1 2 4 5 3
     *                 </pre>
     *             </blockquote>
     *             使逆序区域尽可能小
     *             <blockquote>
     *                 <pre>
     *       &uarr;&macr;&darr;
     * 1 2 4 5 3
     *       &uarr;_&darr;
     *     &dArr;
     * 1 2 4 3 5
     *                 </pre>
     *             </blockquote>
     *         </p>
     *         <p>O(n)</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        int[] numbers = new int[] { 1, 2, 3, 4, 5 };
        for (int i = 0; i < 40; i++) {
            numbers = Answer.findNearestNumber(numbers);
            System.out.println(Arrays.toString(numbers));
        }
    }
    
    private static class Answer {
        
        /**
         * @return 结果，一个新的数组，或null
         */
        private static int[] findNearestNumber(int[] numbers) {
            // 1、从后向前查找逆序区域，找到前一位，即数字的置换边界
            int boundary = findTransferPoint(numbers);
            // 是最大组合
            if (0 == boundary) {
                return null;
            }
            
            // 2、使逆序区域的前一位和逆序区域中刚好大于它的数字交换位置
            int[] numbersCopy = Arrays.copyOf(numbers, numbers.length);
            exchangePrevious(numbersCopy, boundary);
            
            // 3、把原来的逆序区域转为顺序
            ArrayUtil.reverse(numbersCopy, boundary, numbersCopy.length);
            
            return numbersCopy;
        }
        
        /**
         * @return 逆序区域首个元素的下标
         */
        private static int findTransferPoint(int[] numbers) {
            for (int i = numbers.length - 1; i > 0; i--) {
                if (numbers[i] > numbers[i - 1]) {
                    return i;
                }
            }
            
            return 0;
        }
        
        private static void exchangePrevious(int[] numbers, int boundary) {
            int previous = numbers[boundary - 1];
            for (int i = numbers.length - 1; i > 0; i--) {
                if (previous < numbers[i]) {
                    numbers[boundary - 1] = numbers[i];
                    numbers[i] = previous;
                    break;
                }
            }
        }
        
    }
    
}
