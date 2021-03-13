package io.github.sleepingraven.note.demo.questions.q1;

/**
 * 有一单向链表，判断是否有环
 * <blockquote>
 * <pre>
 *          1<-8
 *          &darr;  &uarr;
 * 5->3->7->2->6
 * </pre>
 * </blockquote>
 *
 * @author 10132
 */
public class ListCycleJudgement {

    /**
     * 解题思路
     * <ol>
     *     <li>
     *         直接遍历
     *         <p>O(n2) O(1)</p>
     *     </li>
     *     <li>
     *         使用HashSet遍历
     *         <p>O(n)  O(n)</p>
     *     </li>
     *     <li>
     *         追及问题
     *         <p>O(n)  O(1)</p>
     *     </li>
     * </ol>
     * <span>扩展问题</span>
     * <ol>
     *     <li>如果有环，求环长度</li>
     *     <li>如果有环，求入环点</li>
     * </ol>
     */
    public static void main(String[] args) {
        LinkedListNode node1 = new LinkedListNode(5);
        LinkedListNode node2 = new LinkedListNode(3);
        LinkedListNode node3 = new LinkedListNode(7);
        LinkedListNode node4 = new LinkedListNode(2);
        LinkedListNode node5 = new LinkedListNode(6);
        LinkedListNode node6 = new LinkedListNode(8);
        LinkedListNode node7 = new LinkedListNode(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node4;

        System.out.println(hasCycle(node1));
    }

    private static boolean hasCycle(LinkedListNode head) {
        LinkedListNode p1 = head;
        LinkedListNode p2 = p1;
        while (null != p2 && null != p2.next) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                return true;
            }
        }
        return false;
    }

    static class LinkedListNode {
        int data;
        LinkedListNode next;

        public LinkedListNode(int data) {
            this.data = data;
        }

    }

}
