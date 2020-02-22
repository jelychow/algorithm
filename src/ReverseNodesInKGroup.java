/**
 *
 */
public class ReverseNodesInKGroup {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {

        if (head==null) {
            return head;
        }
        ListNode a = head;
        ListNode b = head;
        for (int i = 0; i < k; i++) {
            // 如果b ==null 说明 剩余结点不够 k 个
            if (b==null) {
                return head;
            }
            b = b.next;
        }
        ListNode pre = reverseNode(a,b);
        // 反正后 a 在翻转尾
        a.next = reverseKGroup(b,k);

        return pre;
    }

    public ListNode reverseNode(ListNode a,ListNode b){

        ListNode pre = null;
        ListNode cur = a;

        while (cur!=null&&cur!=b) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        // 翻转之后 pre 指向的 是b 的前一个结点
        /**
         * 此时 pre 是头结点 翻转 [a,b)
         * 最后的结果 是 pre 头结点
         * a 尾节点
         * 下一次分组的开始便是 b 节点
         */
        return pre;
    }

}
