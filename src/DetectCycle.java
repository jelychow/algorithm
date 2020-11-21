import java.util.function.BinaryOperator;

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

        if (head == null) {
            return null;
        }
        Solution1.ListNode cur = head;
        Solution1.ListNode fast = cur;
        Solution1.ListNode slow = cur;
        while (true) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }


        }
        fast = cur;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    //R 车 B 象 p卒
    public int numRookCaptures(char[][] board) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'R') {
                    // 前后左右依次遍历

                    int row = i;
                    int col = j;
                    while (--row >= 0) {
                        if (board[row][j] == 'B') {
                            break;
                        }
                        if (board[row][j] == 'p') {
                            sum++;
                            break;
                        }
                    }
                    row = i;
                    col = j;
                    while (++row < board.length) {
                        if (board[row][j] == 'B') {
                            break;
                        }
                        if (board[row][j] == 'p') {
                            sum++;
                            break;
                        }
                    }
                    row = i;
                    col = j;
                    while (--col >= 0) {
                        if (board[row][col] == 'B') {
                            break;
                        }
                        if (board[row][col] == 'p') {
                            sum++;
                            break;
                        }
                    }

                    row = i;
                    col = j;
                    while (++col < board[i].length) {
                        if (board[row][col] == 'B') {
                            break;
                        }
                        if (board[row][col] == 'p') {
                            sum++;
                            break;
                        }
                    }
                }
            }

        }
        return sum;
    }

}
