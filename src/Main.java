import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        System.out.println("Hello World!");
        Node node0 = new Node(1);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        node0.next = node1;
        node1.next = node2;
        node2.next = node3;

        Node res = reverse(node0);
        printNode(res);

        // 000022898222000 把0 移动到前面 保证非0 元素相对位置不变
        int[]nums = new int[]{0,0,122,3,0,2,34,0};
        moveZero(nums);
    }
    
    public static void moveZero(int[]nums){
        int zeroNum = 0;

        // 
        //  元素 i 元素作为指针
        int j = nums.length-1;
        for(int i=nums.length-1;i>=0;i--){
             // 000022898222000
            if (nums[i]!=0) {
                swap(nums,i,j--);
            }
        }
 
        for(int i=0;i<nums.length;i++){
             System.out.printf(""+nums[i]+ " ");
        }
    }
    
    public static void swap(int[]nums,int p,int q){
        if(p==q){
            return;
        }
        int temp = nums[p];
        nums[p] = nums[q];
        nums[q] = temp;
    }
    
  static  class Node{
        int val;
        Node next;
      public Node(int val){
          this.val = val;
      }
    }
    public static void printNode(Node node){
        Node p = node;
        while(p!=null){
           System.out.println(""+p.val);
           p = p.next;
        }
        
    }
    
    public static Node reverse(Node node){
        Node pre = null;
        Node cur = node;
        // 1->2->3->4    1<-2<-3
        while(cur!=null){
            Node temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
        
    }


}