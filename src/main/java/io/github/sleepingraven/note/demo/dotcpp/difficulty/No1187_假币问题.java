package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/12/27
 */
public class No1187_假币问题 {
    
    /**
     * 将硬币平均分成三堆：A，B，C，若不能平分，需保证A，B两堆数目相同，且A，B的数量比C多，
     * 其中AB用于称重比较。<p>
     * 对于一次称重，若A == B，则假币在C；若A != B，则假币在轻的那堆。
     * 然后对假币所在的那堆，继续分三堆处理……
     * 直到找到假币。<p>
     * 注意：虽然分两堆，也能找到假币，但显然需要称重的次数要比分三堆的次数多一些，不符合题目要求。
     * A，B的数量要比C多，是为了保证算法的正确性，比如 N = 8，可分成，2 2 4 或者 3 3 2。
     * 若按照 3 3 2分堆，则总共需要称重2次，若分成2 2 4分堆，则总共需要称重3次，显然不是最优解。
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            if (n == 0) {
                break;
            }
            
            // 对数法
            System.out.println((int) Math.ceil(Math.log(n) / Math.log(3)));
            
            int count = 0;
            while (n != 1) {
                // 把假币分成3堆
                if (n % 3 == 0) {
                    // 如果能被3整除,正好分成3堆
                    n = n / 3;
                } else {
                    // 若不能被3整除，保证A，B两堆的硬币数比C堆的硬币数大
                    n = n / 3 + 1;
                }
                count++;
            }
            // System.out.println(count);
        }
    }
    
}
