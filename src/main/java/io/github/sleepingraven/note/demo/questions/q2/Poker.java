package io.github.sleepingraven.note.demo.questions.q2;

/**
 * @author Carry
 * @since 2020/1/17
 */
public class Poker {

    /**
     * 0 1 2 3 4 5 6 7 8
     * 1 1 1 1 1 0 0 0 0
     */
    public static void main(String[] args) {
        int[] ns = new int[55];
        ns[0] = 1;
        for (int i = 1; i <= 13; i++) {
            for (int j = 4 * i; j >= 1; j--) {
                for (int k = Math.max(0, j - 4); k < j; k++) {
                    ns[j] += ns[k];
                }
            }
        }

        System.out.println(ns[13]);
    }

}
