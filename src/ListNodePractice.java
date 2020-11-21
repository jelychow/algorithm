public class ListNodePractice {

    public static void main(String[] args) {

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        // 1->2->3->4
        // 4-3-2-1-null
        // pre cur
        // revrse a listnode  null<-1 <- 2->3->4
        printNode(node1);

        printNode(reverseNode(node1));
        // .next

        // 7812345  12233345 k
        int[] nums = new int[]{1,2,3,3,4,4,4,4,5};
        System.out.println("getLeft: "+getLeft(nums,0,nums.length,5));
    }


    public static int getLeft(int[]nums,int left,int right,int target){
        // [ ) left 包含在 right 不包括在内
        // 通过比较区间来减少数据规模
        // 区间要有序
        // left 区间  right 区间
       // [left] [right]
        while (left<right){
            int mid = (left+right)>>1;
            // == target
            if(target==nums[mid]){
                right = mid;
                // 落在左区间
            }else if (target<=nums[mid]){
                 right = mid;
                // 落在右区间
            }else {
                left = mid+1;
            }
        }

        return left;
    }

    public static void printNode(ListNode node){

        ListNode cur  = node;
        while (cur!=null){
            System.out.println(cur.val);
            cur = cur.next;
        }
    }

    public static ListNode reverseNode(ListNode node){
        ListNode pre = null;
        ListNode cur = node;
        while (cur!=null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        // 循环终止的时候 cur 必定为空 null
        return pre;

    }

    public static class ListNode {
        ListNode next;
        int val;

        public ListNode(int val) {
            this.val = val;
        }
    }


}
