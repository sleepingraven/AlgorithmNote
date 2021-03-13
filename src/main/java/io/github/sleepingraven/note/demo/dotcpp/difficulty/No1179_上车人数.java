package io.github.sleepingraven.note.demo.dotcpp.difficulty;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2019/11/22
 */
public class No1179_上车人数 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int x = scanner.nextInt();
        
        NumNode[] res = new NumNode[n];
        res[1] = new NumNode(1, 0, 1, 0);
        res[2] = new NumNode(1, 0, 0, 1);
        for (int i = 3; i < n; i++) {
            res[i] = new NumNode(res[i - 2].aNew + res[i - 1].aNum, res[i - 2].hNew + res[i - 1].hNum,
                                 res[i - 2].aNew + res[i - 1].aNew, res[i - 2].hNew + res[i - 1].hNew);
        }
        
        int h = (m - res[n - 1].aNum * a) / res[n - 1].hNum;
        System.out.println(res[x].aNum * a + res[x].hNum * h);
    }
    
    private static class NumNode {
        int aNum;
        int hNum;
        int aNew;
        int hNew;
        
        NumNode(int aNum, int hNum, int aNew, int hNew) {
            this.aNum = aNum;
            this.hNum = hNum;
            this.aNew = aNew;
            this.hNew = hNew;
        }
        
    }
    
}
