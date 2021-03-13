package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 大整数求和
 *
 * @author 10132
 */
public class SumOfDecimals {

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         使用数组。倒序存储，更符合习惯
     *         <p>O(n)</p>
     *     </li>
     *     <li>
     *         拆分到可以直接计算的程度即可（int为9位即可）（略）
     *         <p>BigInteger和BigDecimal底层实现类似</p>
     *     </li>
     * </ol>
     */
    public static void main(String[] args) {
        System.out.println(bigNumberSum("426709752318", "95481253129"));
    }

    /**
     * 转为数组，更加直观
     */
    private static String bigNumberSum(String n1, String n2) {
        // 1、用数组逆序存储，长度加1
        int maxLength = Math.max(n1.length(), n2.length());
        int[] array1 = new int[maxLength + 1];
        int i = n1.length();
        for (char c : n1.toCharArray()) {
            array1[--i] = c - '0';
        }
        int[] array2 = new int[maxLength + 1];
        i = n2.length();
        for (char c : n2.toCharArray()) {
            array2[--i] = c - '0';
        }

        // 2、构建result数组
        int[] result = new int[maxLength + 1];

        // 3、按位相加
        for (int j = 0; j < result.length; j++) {
            int temp = result[j] + array1[j] + array2[j];
            // 进位
            if (temp >= 10) {
                temp -= 10;
                result[j + 1] = 1;
            }
            result[j] = temp;
        }

        // 4、再次逆序
        StringBuilder sb = new StringBuilder();
        boolean findFirst = false;
        for (int j = result.length - 1; j >= 0; j--) {
            if (!findFirst) {
                if (result[j] == 0) {
                    continue;
                }
                findFirst = true;
            }
            sb.append((result[j]));
        }

        return sb.toString();
    }

}
