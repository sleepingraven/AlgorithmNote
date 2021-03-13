package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 给出一个整数，从中选取并去掉k个数字，要求剩下的数字形成的新整数尽可能小。
 * <p>其中整数的长度大于或等于k，给出的整数大小可以超过long</p>
 * <blockquote>
 * <pre>
 * 3 -> 1 5 9 3 2 1 2
 *        &uarr; &uarr; &uarr;
 *            &dArr;
 *      1 2 1 2
 * </pre>
 * </blockquote>
 * <blockquote>
 * <pre>
 * 1 -> 3 0 2 0 0
 *      &uarr;
 *          &dArr;
 *          2 0 0
 * </pre>
 * </blockquote>
 * <blockquote>
 * <pre>
 * 2 -> 1 0
 *      &uarr; &uarr;
 *       &dArr;
 *        0
 * </pre>
 * </blockquote>
 * <blockquote>
 * <pre>
 * 2 -> 3 5 4 9
 *        &uarr;
 *         &dArr;
 *      3 4 9
 * </pre>
 * </blockquote>
 *
 * @author 10132
 */
public class FindMinAfterRemoveSeveralDigits {

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         <p>优先降低高位数字</p>
     *         <p>求得局部最优解，最终得到全局最优解，即贪心算法</p>
     *         <p>1、每一次内层循环都从头开始</p>
     *         <p>2、substring()性能不高</p>
     *         <p>O(kn)</p>
     *     </li>
     *     <li>
     *         遍历数字为外循环，k为内循环
     *         <blockquote>
     *             <pre>
     *   3 -> 5 4 1 2 7 0 9 3 6
     *                &dArr;
     *        &darr;
     * string 5 4 1 2 7 0 9 3 6
     * stack  5 _ _ _ _ _ _ _ _
     *                &dArr;
     *          &darr;<
     * string 5 4 1 2 7 0 9 3 6
     * stack  4 _ _ _ _ _ _ _ _
     *                &dArr;
     *            &darr;<
     * string 5 4 1 2 7 0 9 3 6
     * stack  1 _ _ _ _ _ _ _ _
     *                &dArr;
     *                &darr;>
     * string 5 4 1 2 7 0 9 3 6
     * stack  1 2 7 _ _ _ _ _ _
     *                &dArr;
     *                  &darr;<
     * string 5 4 1 2 7 0 9 3 6
     * stack  1 2 0 _ _ _ _ _ _
     *                &dArr;
     * string 5 4 1 2 7 0 9 3 6
     * stack  1 2 0 9 3 6 _ _ _
     *             </pre>
     *         </blockquote>
     *         <p>O(n) O(n)</p>
     *     </li>
     *     <li>更多优化（略）</li>
     * </ol>
     */
    public static void main(String[] args) {
        System.out.println(removeDigits("1593212", 3));
        System.out.println(removeDigits("30200", 1));
        System.out.println(removeDigits("10", 2));
        System.out.println(removeDigits("541270936", 3));

        System.out.println();

        System.out.println(removeDigitsOptimized("1593212", 3));
        System.out.println(removeDigitsOptimized("30200", 1));
        System.out.println(removeDigitsOptimized("10", 2));
        System.out.println(removeDigitsOptimized("541270936", 3));
    }

    private static String removeDigits(String sequence, int k) {
        if (k < 0 || k > sequence.length()) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < k; i++) {
            boolean hasCut = false;
            // 从左向右遍历，找到比右侧大的数字并删除
            for (int j = 0; j < sequence.length() - 1; j++) {
                if (sequence.charAt(j) > sequence.charAt(j + 1)) {
                    sequence = sequence.substring(0, j) + sequence.substring(j + 1);
                    hasCut = true;
                    break;
                }
            }
            // 如果没有找到，则删除最后一个
            if (!hasCut) {
                sequence = sequence.substring(0, sequence.length() - 1);
            }
            // 清除左侧的0
            sequence = removeFrontZero(sequence);
        }

        // 如果全部删除，返回"0"
        if (sequence.isEmpty()) {
            return "0";
        }

        return sequence;
    }

    private static String removeFrontZero(String sequence) {
        for (int i = 0; i < sequence.length() - 1; i++) {
            if (sequence.charAt(0) != '0') {
                break;
            }
            sequence = sequence.substring(1);
        }

        return sequence;
    }

    private static String removeDigitsOptimized(String sequence, int k) {
        if (k < 0 || k > sequence.length()) {
            throw new IndexOutOfBoundsException();
        }

        // 新整数的长度
        int lengthAfterCut = sequence.length() - k;
        // 接收数字
        char[] stack = new char[sequence.length()];

        int top = 0;
        // 遍历序列
        for (char c : sequence.toCharArray()) {
            // 若栈顶数字大于当前数字，出栈（即删除数字）
            while (top > 0 && stack[top - 1] > c && k > 0) {
                top--;
                k--;
            }
            // 当前数字入栈
            stack[top++] = c;
        }

        // 从首个非零数字开始，构建新序列
        int offset = 0;
        while (offset < lengthAfterCut && stack[offset] == '0') {
            offset++;
        }

        return offset == lengthAfterCut ? "0" : new String(stack, offset, lengthAfterCut - offset);
    }

}
