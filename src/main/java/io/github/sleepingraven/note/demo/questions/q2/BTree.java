package io.github.sleepingraven.note.demo.questions.q2;

/**
 * @author Carry
 * @since 2020/10/14
 */
public class BTree {
    
    /**
     * 【问题描述】<br>一棵包含有2019个结点的二叉树，最多包含多少个叶结点？<br>【答案提交】<br>这是一道结果填空的题，你只需要算出结果后提交即可。本题的结果为一个整数，在提交答案时只填写这个整数，填写多余的内容将无法得分。<br><br><br></p>
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution();
    }
    
    private static class Solution {
        
        /**
         * 度为2的结点数：1009个
         * 度为1的结点数：0个（因为2019是奇数，所以此二叉树可以没有度为1的结点）
         * 度为0的结点数（叶子结点数）：1010个（等于度为2的结点数+1）
         */
        public void solution() {
        }
        
    }
    
}
