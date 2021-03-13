package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 一个无序数组里有99个不重复的正整数，范围是1-100，唯独缺少1-100中的一个整数。找出缺失的数。
 *
 * @author 10132
 */
public class MissingNumber {

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         创建Hash表，以数值为key，遍历数组并删除key。
     *         <p>O(n) O(n)</p>
     *     </li>
     *     <li>
     *         排序
     *         <p>O(n*log n) O(1)</p>
     *     </li>
     *     <li>
     *         算出1-100的累加和，依次减去数组元素
     *         <p>O(n) O(1)</p>
     *     </li>
     * </ol>
     * 问题扩展：<br />
     * 一个无序数组里有若干正整数，1-100，其中99个整数都出现了偶数次，找到出现奇数次的整数。
     */
    public static void main(String[] args) {
        expansionFirstTime();
        expansionSecondTime();
    }

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         把数组元素依次异或，得到结果
     *         <p>O(n) O(1)</p>
     *     </li>
     * </ol>
     * 问题扩展：<br />
     * 如果有两个整数出现了奇数次，找出整两个数。
     */
    private static void expansionFirstTime() {
    }

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         分治法，把数组分成两部分，保证每一部分包含一个奇数次整数
     *         <p>先遍历并异或，结果至少有一位是1。将数组按该位分组</p>
     *         <p>O(n) O(1)</p>
     *     </li>
     * </ol>
     */
    private static void expansionSecondTime() {
        int[] array = { 4, 1, 2, 2, 5, 1, 4, 3 };
        int[] result = findLostNum(array);
        assert result != null;
        System.out.println(result[0] + ", " + result[1]);
    }

    private static int[] findLostNum(int[] array) {
        int[] result = new int[2];

        int xorResult = 0;
        for (int value : array) {
            xorResult ^= value;
        }
        if (xorResult == 0) {
            return null;
        }

        int separator = 1;
        while ((xorResult & separator) == 0) {
            separator <<= 1;
        }

        for (int value : array) {
            if ((value & separator) == 0) {
                result[0] ^= value;
            } else {
                result[1] ^= value;
            }
        }

        return result;
    }

}
