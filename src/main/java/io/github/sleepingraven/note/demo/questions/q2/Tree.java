package io.github.sleepingraven.note.demo.questions.q2;

import java.util.Scanner;

/**
 * @author Carry
 * @since 2020/10/14
 */
public class Tree {
    
    /**
     * 小明和朋友们一起去郊外植树，他们带了一些在自己实验室精心研究出的小树苗。<br>小明和朋友们一共有 n 个人，他们经过精心挑选，在一块空地上每个人挑选了一个适合植树的位置，总共 n
     * 个。他们准备把自己带的树苗都植下去。<br>然而，他们遇到了一个困难：有的树苗比较大，而有的位置挨太近，导致两棵树植下去后会撞在一起。<br>他们将树看成一个圆，圆心在他们找的位置上。如果两棵树对应的圆相交，这两棵树就不适合同时植下（相切不受影响），称为两棵树冲突。<br>小明和朋友们决定先合计合计，只将其中的一部分树植下去，保证没有互相冲突的树。他们同时希望这些树所能覆盖的面积和（圆面积和）最大。<br>【输入格式】<br>输入的第一行包含一个整数
     * n ，表示人数，即准备植树的位置数。<br>接下来 n 行，每行三个整数 x, y, r，表示一棵树在空地上的横、纵坐标和半径。<br>【输出格式】<br>输出一行包含一个整数，表示在不冲突下可以植树的面积和。由于每棵树的面积都是圆周率的整数倍，请输出答案除以圆周率后的值（应当是一个整数）。<br>【样例输入】<br>6<br>1
     * 1 2<br>1 4 2<br>1 7 2<br>4 1 2<br>4 4 2<br>4 7 2<br>【样例输出】<br>12<br>【评测用例规模与约定】<br>对于 30% 的评测用例，1 &lt;= n &lt;=
     * 10；<br>对于 60% 的评测用例，1 &lt;= n &lt;= 20；<br>对于所有评测用例，1 &lt;= n &lt;= 30，0 &lt;= x, y &lt;= 1000，1 &lt;= r &lt;=
     * 1000。<br><br><br></p>
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution();
    }
    
    static class Solution {
        boolean[][] bool = new boolean[31][31];
        boolean[] vis = new boolean[31];
        int[] x = new int[31];
        int[] y = new int[31];
        int[] r = new int[31];
        int n;
        int max = -1;
        
        public void solution() {
            Scanner sc = new Scanner(System.in);
            n = sc.nextInt();
            for (int i = 1; i <= n; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
                r[i] = sc.nextInt();
            }
            
            for (int i = 1; i <= n; i++) {
                for (int j = i + 1; j <= n; j++) {
                    int a = x[i] - x[j];
                    int b = y[i] - y[j];
                    int c = r[i] + r[j];
                    boolean bo = a * a + b * b > c * c;
                    bool[i][j] = bool[j][i] = bo;
                }
            }
            sc.close();
            
            backtrace(1);
            System.out.println(max);
        }
        
        private void backtrace(int step) {
            if (step > n) {
                int sum = 0;
                for (int i = 1; i <= n; i++) {
                    if (vis[i]) {
                        sum += (r[i] * r[i]);
                    }
                }
                max = Math.max(sum, max);
                return;
            }
            
            vis[step] = false;
            backtrace(step + 1);
            for (int i = 1; i < step; i++) {
                if (vis[i] && !bool[i][step]) {
                    return;
                }
            }
            vis[step] = true;
            backtrace(step + 1);
        }
        
    }
    
}
