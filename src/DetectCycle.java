public class DetectCycle {
    public class ListNode {
        int val;
        Solution1.ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public class TreeNode {
        int val;
        Solution1.TreeNode left;
        Solution1.TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public Solution1.ListNode detectCycle(Solution1.ListNode head) {

        // 链表有环
        /**
         * 解题分两步走
         * 1 能够相交 有环 记录 slow 位置 此时
         * 2 从头开始从新开始执行 两者会在环的交叉点集合
         *  f = 2b
         *  s = b
         *  s = a + n
         *  f = a + 2n
         *  b = n;
         * 快慢指针 用于确定环的中点 所以第二次循环会相交。
         */

        if (head==null) {
            return null;
        }
        Solution1.ListNode cur = head;
        Solution1.ListNode fast = cur;
        Solution1.ListNode slow = cur;
        while (true) {
            if (fast.next==null||fast.next.next==null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
            if(slow==fast) {
                break;
            }


        }
        fast = cur;
        while (slow!=fast) {
            fast = fast.next;
            slow = slow.next;
        }

        return fast;

    }

}
