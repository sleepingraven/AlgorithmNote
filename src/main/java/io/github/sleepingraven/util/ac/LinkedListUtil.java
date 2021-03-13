package io.github.sleepingraven.util.ac;

import io.github.sleepingraven.util.ac.Leetcode.ListNode;

/**
 * @author Carry
 * @since 2021/2/12
 */
public class LinkedListUtil {
    
    public static ListNode mergeSortRecursion(ListNode head) {
        return mergeSortRecursion(head, null);
    }
    
    public static ListNode mergeSortRecursion(ListNode head, ListNode stop) {
        if (head == null) {
            return null;
        }
        if (head.next == stop) {
            head.next = null;
            return head;
        }
        
        ListNode mid = rearHead(head, stop);
        ListNode list1 = mergeSortRecursion(head, mid);
        ListNode list2 = mergeSortRecursion(mid, stop);
        return mergeSortedRecursion(list1, list2);
    }
    
    public static ListNode sortListIteration(ListNode head) {
        if (head == null) {
            return null;
        }
        
        int len = 0;
        for (ListNode p = head; p != null; p = p.next) {
            len++;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        for (int subLen = 1; subLen < len; subLen <<= 1) {
            ListNode prev = dummy, curr = dummy.next;
            while (curr != null) {
                ListNode head1 = curr;
                for (int i = 1; i < subLen && curr.next != null; i++) {
                    curr = curr.next;
                }
                
                ListNode head2 = curr.next;
                curr.next = null;
                curr = head2;
                ListNode next = null;
                if (curr != null) {
                    for (int i = 1; i < subLen && curr.next != null; i++) {
                        curr = curr.next;
                    }
                    next = curr.next;
                    curr.next = null;
                }
                
                prev.next = mergeSortedIteration(head1, head2);
                while (prev.next != null) {
                    prev = prev.next;
                }
                curr = next;
            }
        }
        return dummy.next;
    }
    
    public static ListNode mergeSortedRecursion(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        
        if (l1.val <= l2.val) {
            l1.next = mergeSortedRecursion(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeSortedRecursion(l2.next, l1);
            return l2;
        }
    }
    
    public static ListNode mergeSortedIteration(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode p = dummy, p1 = head1, p2 = head2;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        p.next = p1 == null ? p2 : p1;
        return dummy.next;
    }
    
    public static ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    /**
     * 如果节点数为奇数，包括中间节点
     */
    public static ListNode frontTail(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    
    /**
     * 如果节点数为奇数，包括中间节点
     */
    public static ListNode rearHead(ListNode head, ListNode stop) {
        ListNode slow = head, fast = head;
        while (fast != stop && fast.next != stop) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    public static int length(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }
    
}
