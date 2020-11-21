public class MergeKSortedLists {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    public ListNode mergeKLists(ListNode[] lists) {

        if (lists.length<3) {
            if (lists.length==0) {
                return null;
            }
            if (lists.length==1) {
                return lists[1];
            }else {
                return  merge(lists[0],lists[1]);
            }
        }

        ListNode[] tempLefts = new ListNode[lists.length>>>1];

        System.arraycopy(lists,0,tempLefts,0,lists.length>>>1);

        ListNode[] tempRights = new ListNode[lists.length - (lists.length>>>1)];

        System.arraycopy(lists,lists.length>>>1,tempRights,0,lists.length - (lists.length>>>1));

        ListNode left =  mergeKLists(tempLefts);
        ListNode right =  mergeKLists(tempRights);

        return merge(left,right);
    }

    public ListNode merge(ListNode l1, ListNode l2){
        if (l1==null) {
            return l2;
        }

        if(l2==null) {
            return l1;
        }

        if(l1.val<l2.val) {
            l1.next = merge(l1.next,l2);
            return l1;
        } else {
            l2.next = merge(l2.next,l1);
            return l2;
        }
    }
}
