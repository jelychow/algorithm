import javafx.util.Pair;
import org.omg.CORBA.MARSHAL;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Solution1 {
    class Node {
        int val;
        Node next;
        Node random;
        int count;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
            this.count = 0;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int count;

        TreeNode(int x) {
            val = x;
            int count;
        }
    }

    public ListNode detectCycle(ListNode head) {

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
        ListNode cur = head;
        ListNode fast = cur;
        ListNode slow = cur;
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

    public ListNode sortList(ListNode head) {
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode p = dummyHead.next;
        int count = 0;
        while (p != null) {
            count++;
            p = p.next;
        }
        // 开始 cut and merge
        int size = 1;
        while (size < count) {
            ListNode cur = dummyHead.next;
            ListNode pre = dummyHead;
            while (cur != null) {

                ListNode left = cur;
                ListNode right = cut(cur, size);
                cur = cut(right, size);
                pre.next = merge(left, right);
                //寻找到最后一个非空结点
                while (pre.next != null) {
                    pre = pre.next;
                }
            }

            size <<= 1;
        }

        return dummyHead.next;
    }

    public ListNode cut(ListNode cur, int size) {
        if (size <= 0) {
            return cur;
        }
        ListNode p = cur;
        while (--size > 0 && cur != null) {
            // p 结点 是前n个结点
            p = p.next;
        }
        if (p == null) {
            return null;
        }
        ListNode next = p.next;
        // 断链
        p.next = null;
        return next;
    }

    public ListNode cut2(ListNode cur, int size) {

        if (size <= 0 || cur == null) {
            return cur;
        }

        while (--size > 0 && cur != null) {
            cur = cur.next;
        }

        if (cur == null) {
            return null;
        }

        ListNode p = cur.next;
        cur.next = null;

        return p;

    }


    public ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l2.next, l1);
            return l2;
        }
    }

    public ListNode merge2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode p = new ListNode(Integer.MIN_VALUE);
        ListNode dummyHead = new ListNode(-1);
        p = dummyHead;
        // 指针法 最小的不断添加进去
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                p.next = l1;
                l1 = l1.next;

            } else {
                p.next = l2;
                l2 = l2.next;
            }

            p = p.next;
        }

        if (l1 != null) {
            p.next = l1;
        }
        if (l2 == null) {
            p.next = l2;
        }

        return dummyHead.next;
    }

    public ListNode mergeKLists(ListNode[] lists) {

        if (lists.length < 3) {
            if (lists.length == 0) {
                return null;
            }
            if (lists.length == 1) {
                return lists[1];
            } else {
                return merge(lists[0], lists[1]);
            }
        }

        ListNode[] tempLefts = new ListNode[lists.length >>> 1];

        System.arraycopy(lists, 0, tempLefts, 0, lists.length >>> 1);

        ListNode[] tempRights = new ListNode[lists.length - (lists.length >>> 1)];

        System.arraycopy(lists, lists.length >>> 1, tempRights, 0, lists.length - (lists.length >>> 1));

        ListNode left = mergeKLists(tempLefts);
        ListNode right = mergeKLists(tempRights);

        return merge(left, right);
    }

    public ListNode merge4(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l2.next, l1);
            return l2;
        }
    }

    /**
     * 12345
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        // 后面指向前面的 依次循环

        while (cur != null) {
            ListNode temp = cur.next;
            // 旋转链表 步骤
            /**
             * temp 保存下一个变量
             * 1 当前节点指向 pre
             * 2 当前节点变成pre
             * 3 指针前移 cur = cur.next
             *
             */
            cur.next = pre;
            pre = cur;
            cur = temp;

        }
        return pre;
    }

    /**
     * 相交链表 真正的骚操作
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        ListNode preA = headA;
        ListNode preB = headB;
        while (preA != preB) {
            preA = preA == null ? headB : preA.next;
            preB = preB == null ? headA : preB.next;
        }
        return preA;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode head = new ListNode(0);
        ListNode p = head;
        int carry = 0;
        while (l1 != null && l2 != null) {
            int num1 = l1.val;
            int num2 = l2.val;
            int sum = (num1 + num2 + carry) % 10;
            carry = (num1 + num2 + carry) / 10;
            p.next = new ListNode(sum);
            p = p.next;

            l1 = l1.next;
            l2 = l2.next;

            if (l1 == null && l2 != null) {
                l1 = new ListNode(0);
            }
            if (l2 == null && l1 != null) {
                l2 = new ListNode(0);
            }

        }
        if (carry != 0) p.next = new ListNode(carry);
        return head.next;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();

        if (nums.length < 3) {
            return lists;
        }

        Arrays.sort(nums);
        if (nums[0] > 0) {
            return lists;
        }

        /**
         * 从前到后遍历依次 取低位的值
         * 固定低位值
         * left right 向里面收缩
         */
        for (int i = 0; i < nums.length - 2; i++) {

            int cur = nums[i];
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            if (nums[i] > 0 || (nums[i] + nums[i + 1] > 0)) {
                return lists;
            }

            int left = i + 1;
            int right = nums.length - 1;


            while (left < right) {
                if (cur + nums[left] + nums[right] == 0) {
                    lists.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    /**
                     * 已经用过的数不能再用了
                     * 向里面收缩
                     */
                    while (left < right && (nums[left] == nums[left + 1])) left++;
                    while (left < right && (nums[right] == nums[right - 1])) right--;
                    left++;
                    right--;
                }

                if (left < right && cur + nums[left] + nums[right] > 0) {
                    // 从高位到低位依次进行 去重
                    while (left < right && (nums[right] == nums[right - 1])) right--;

                    right--;
                }

                if (left < right && cur + nums[left] + nums[right] < 0) {
                    // 从低位 去重
                    while (left < right && (nums[left] == nums[left + 1])) left++;

                    left++;
                }

            }


        }
        return lists;

    }

    int sum = 0;

    public int maxAreaOfIsland(int[][] grid) {
        boolean[][] visitmap = new boolean[grid.length][grid[0].length];

        int max = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visitmap[i][j] && grid[i][j] == 1) {

                    helper(i, j, visitmap, grid);
                    max = Math.max(max, sum);
                    sum = 0;
                } else {
                    visitmap[i][j] = true;
                }
            }
        }

        return max;

    }

    public void helper(int i, int j, boolean[][] visitmap, int[][] grid) {

//        if (!visitmap[i][j]) {
        visitmap[i][j] = true;
//            if (grid[i][j] == 1) {
        sum++;
        if (i > 0 && !visitmap[i - 1][j] && grid[i - 1][j] == 1) {
            helper(i - 1, j, visitmap, grid);
        }
        if (i < grid.length - 1 && !visitmap[i + 1][j] && grid[i + 1][j] == 1) {
            helper(i + 1, j, visitmap, grid);
        }

        if (j > 0 && !visitmap[i][j - 1] && grid[i][j - 1] == 1) {
            helper(i, j - 1, visitmap, grid);
        }
        if (j < grid[0].length - 1 && !visitmap[i][j + 1] && grid[i][j + 1] == 1) {
            helper(i, j + 1, visitmap, grid);
        }
    }
//        }
//    }

    public int longestConsecutive(int[] nums) {
        Set set = new HashSet();

        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }

        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 1;
            // 找到 最小值向上循环
            int cur = nums[i];
            if (!set.contains(nums[i] - 1)) {
                while (set.contains(++cur)) {
                    sum++;
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    int count;
    //123456

    /**
     * 取巧来做，先算 factorial
     *
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {

        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }

        int facbo[] = new int[n];
        facbo[0] = 1;
        //每条列表数量 和 fb(n-1)
        for (int i = 1; i < n; i++) {
            facbo[i] = i * facbo[i - 1];
        }
        // 每条 fibo 和 n - 1 相关
        n--;
        StringBuilder builder = new StringBuilder();
        while (n >= 0) {
            int nf = facbo[n];
            int index = (int) (Math.ceil((double) k / (double) nf) - 1);
            // 如果正好整除
            if (index == -1) {
                index = nums.size() - 1;
            }
            builder.append(nums.get(index));
            // 删除使用过的元素
            nums.remove((Integer) nums.get(index));
            n--;
            k %= nf;
        }

        return builder.toString();
    }


    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        List<List<Integer>> lists = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        dfs(nums, lists, visited, linkedList);

        return lists;
    }

    public void dfs(int[] nums, List<List<Integer>> lists, boolean[] visited, LinkedList<Integer> linkedList) {
        if (linkedList.size() == visited.length) {
            lists.add(new ArrayList<>(linkedList));
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {

                visited[i] = true;
                linkedList.add(nums[i]);

                dfs(nums, lists, visited, linkedList);

                linkedList.removeLast();
                visited[i] = false;
            }
        }
    }

    public int findLengthOfLCIS(int[] nums) {

        int max = 1;
        int sum = 1;
        for (int i = 1; i < nums.length; i++) {

            if (nums[i] > nums[i - 1]) {
                sum++;
                max = Math.max(sum, max);
            } else {
                sum = 1;
            }
        }
        return max;
    }

    public int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        k = nums.length - k;
        int partition = partition(nums, left, right);
        while (true) {
            if (partition > k) {
                right = partition - 1;
                partition = partition(nums, left, right);
            } else if (partition < k) {
                left = partition + 1;
                partition = partition(nums, left, right);
            } else {
                return nums[partition];
            }
        }
    }

    public int partition(int[] nums, int left, int right) {
        /**
         * partition 方法 主要流程
         * 1 目的 寻找到 标杆元素 index 然后根据标杆元素进行递归重新 partition 快排是原地排序，对数组分段排序，需要传入边界值
         * 2 流程 1 随机选定标杆元素 防止劣化
         *   流程 2 循环遍历 对比标杆元素
         *   流程 3 循环结束，标杆元素 下标 与 index 交换
         */

        if (left < right) {
            int random = left + 1 + new Random().nextInt(right - left);
            swap(nums, left, random);
        }

        int p = nums[left];
        int index = left + 1;
        // 分区排序
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] < p) {
                swap(nums, i, index++);
            }
        }
        // swap 主要目的是要标杆元素出于分界线中间位置，把小于标杆元素排前面
        swap(nums, left, index);

        return index;
    }

    public void swap(int[] nums, int p, int q) {
        if (p == q) {
            return;
        }

        int temp = nums[p];
        nums[p] = nums[q];
        nums[q] = temp;
    }


    public int findKthLargest2(int[] nums, int k) {
        int len = nums.length;
        // 使用一个含有 len 个元素的最小堆，默认是最小堆，可以不写 lambda 表达式：(a, b) -> a - b
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(len, (a, b) -> b - a);
        for (int i = 0; i < len; i++) {
            minHeap.add(nums[i]);
        }
        for (int i = 0; i < k; i++) {
            minHeap.poll();
        }
        return minHeap.peek();
    }

    int maxDepth = 0;

    /**
     * 二叉树最大高度 == max(left,right)
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return maxDepth = Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    int maxPathSum = Integer.MIN_VALUE;


    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 计算当前结果
        int left = dfs(root.left);
        int right = dfs(root.right);
        maxPathSum = Math.max(maxPathSum, Math.max(left, 0) + Math.max(0, right) + root.val);

        return Math.max(0, Math.max(left, right)) + root.val;
    }

    public int climbStairs(int n) {

        int dp[] = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i] = (i - 1 >= 0 ? dp[i - 1] : 0) + (i - 2 >= 0 ? dp[i - 2] : 0);
        }
        return dp[n];
    }


    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;


        // 如何找到反转点
        /**
         * 首先确定反转点条件是什么
         * 反转点 是 nums[point] > nums[point+1]
         * 即 当前点 大于 下一个值 由于是反转点 我们可以
         * 推导 nums[point] > nums[right]
         */
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        int splitPoint = left;
        if (nums[splitPoint] == target) {
            return splitPoint;
        }
        int index;
        if (nums[nums.length - 1] >= target && nums[splitPoint] <= target) {
            index = Arrays.binarySearch(nums, splitPoint + 1, nums.length, target);
        } else {
            index = Arrays.binarySearch(nums, 0, splitPoint, target);
        }

        return index < 0 ? -1 : index;
    }


    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            root.left = invertTree(root.left);
            root.right = invertTree(root.right);
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
        }

        return root;
    }

    public TreeNode sortedArrayToBST(int[] nums) {

        if (nums.length == 0) {
            return null;
        }

        return helper(nums, 0, nums.length - 1);

    }

    public TreeNode helper(int[] nums, int left, int right) {
        //取靠左结点
        int mid = (right + left) >>> 1;
        TreeNode node = new TreeNode(nums[mid]);

        if (mid - 1 >= left) node.left = helper(nums, left, mid - 1);
        if (mid + 1 <= right) node.right = helper(nums, mid + 1, right);

        return node;
    }


    public String simplifyPath(String path) {
        String[] files = path.split("/");
        LinkedList<String> linkedList = new LinkedList();
        for (int i = 0; i < files.length; i++) {
            if ("..".equals(files[i])) {
                if (!linkedList.isEmpty()) {
                    linkedList.removeLast();
                } else if (!files[i].isEmpty() && !files[i].equals(".")) {
                    linkedList.add(files[i]);
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!linkedList.isEmpty()) {
            builder.append("/").append(linkedList.removeLast());
        }
        return builder.length() == 0 ? "/" : builder.toString();
    }

    public ListNode rotateRight(ListNode head, int k) {

        if (head == null || k < 0) {
            return head;
        }

        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode cur = dummyHead;
        ListNode slow = dummyHead;
        ListNode fast = dummyHead;
        int length = 0;

        while (cur.next != null) {
            cur = cur.next;
            ++length;
        }

        if (k == 0 || length == 0) {
            return head;
        }
        k = k % length;

        while (fast.next != null && k > 0) {
            fast = fast.next;
            k--;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        fast.next = dummyHead.next;
        dummyHead.next = slow.next;
        slow.next = null;

        return dummyHead.next;
    }


    public static void main(String[] a) {
        char[][] board = new char[][]{
                new char[]{'A', 'B', 'C', 'E'}, new char[]{'S', 'F', 'C', 'S'}, new char[]{'A', 'D', 'E', 'E'}
        };
        boolean res = new Solution1().exist(board, "aaaaaaaaaaaaaa");
        System.out.println(res);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        /**
         * 确定奇偶数的中间位置
         * (left + right+1)>>>1 偶数值 左值
         * (left + right+2)>>>1 右值
         */

        int left = (nums1.length + nums2.length + 1) >> 1;
        int right = (nums1.length + nums2.length + 2) >> 1;

        return (findKth(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, left) + findKth(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, right)) * 0.5;
    }

    int findKth(int[] nums1, int l1, int r1, int[] nums2, int l2, int r2, int k) {

        // 第一步确定 nums1 与 nums2 位置 进行调换 短的放在前面先执行
        int len1 = r1 - l1 + 1;
        int len2 = r2 - l2 + 1;
        if (len1 > len2) {
            return findKth(nums2, l2, r2, nums1, l1, r1, k);
        }

        // 前面的已经执行完了
        if (len1 == 0) {
            // 第 k 大 都需要 -1 还原自身位置
            return nums2[l2 + k - 1];
        }
        // 找到元素
        if (k == 1) {
            return Math.min(nums1[l1], nums2[l2]);
        }

        // 当前指针位置
        int p1 = l1 + Math.min(len1, k >> 1) - 1;
        int p2 = l2 + Math.min(len2, k >> 1) - 1;

        // 比较指针位置 删除较小的一遍
        if (nums1[p1] > nums2[p2]) {
            //指针向前移动 删除 指针前面元素 包括指针 所以 p2+1
            return findKth(nums1, l1, r1, nums2, p2 + 1, r2, k - (p2 + 1 - l2));
        } else {
            return findKth(nums1, p1 + 1, r1, nums2, l2, r2, k - (p1 + 1 - l1));
        }
    }

    private HashMap<TreeNode, Integer> nodeMap;
    private TreeNode rootNode;

    public boolean checkEqualTree(TreeNode root) {

        // 首先判断左右 是否可以
        /**
         * 首先去掉一条边意味着 树一分为二
         * t
         * 子树的 sum = total/2
         * 所以奇数情况直接忽略
         * 递归寻找子树
         *
         */
        nodeMap = new HashMap<>();
        rootNode = root;
        int sum = getSum(root);
        if ((sum % 2) == 1) {
            return false;
        }

        return dfs(root, sum >> 1);

    }

    public boolean dfs(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        int sum = getSum(root);
        if (sum == target && rootNode != root) {
            return true;
        }
//        if ((sum%2)==1){
//            return false;
//        }

        boolean left = dfs(root.left, target);
        boolean right = dfs(root.right, target);
        if (left || right) {
            return true;
        }
        return false;
    }

    public int getSum(TreeNode root) {
        if (nodeMap.containsKey(root)) {
            return nodeMap.get(root);
        }
        if (root == null) {
            return 0;
        }

        int rootVal = root.val;
        int leftVal = getSum(root.left);
        int rightVal = getSum(root.right);
        int sum = rootVal + leftVal + rightVal;
        nodeMap.put(root, sum);
        return sum;

    }


    public void moveZeroes(int[] nums) {
        for (int i = 0; i < nums.length; i++) {

            int cur = i;
            int pre = i - 1;

            while (pre >= 0 && (nums[cur] != 0 && nums[pre] == 0)) {
                swap(nums, pre, cur);
                cur = pre;
                pre = --cur;
            }

        }

    }

    // 最大盛水容器

    /**
     * 主要思想是贪心
     * 只要有一边的高度较高，
     * 那么想获得最大值只能移动较小的边来实现
     * <p>
     * 如何反证： 如果移动较大的边，宽度 缩小 高度缩小
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int maxArea = 0;
        int area;
        while (i < j) {
            area = (j - i) * (Math.min(height[i], height[j]));
            maxArea = Math.max(area, maxArea);
            if (height[i] > height[j]) {
                j--;
            } else {
                i++;
            }
        }
        return maxArea;
    }

    // 主要目的是求取最大值

    /**
     * 考虑到负数 以及 0 的存在
     * 当前值 = cur*max cur*min 与自身的比较
     * 然后保存 min  max
     * 继续计算
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int max = 0;
        int min = 0;
        int maxRes = nums[0];
        min = nums[0];
        max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int cur = nums[i];
            int res1 = cur * max;
            int res2 = cur * min;
            max = Math.max(Math.max(res1, res2), cur);
            min = Math.min(Math.min(res1, res2), cur);

            maxRes = Math.max(maxRes, max);
        }

        return maxRes;
    }

    public int kthSmallest(int[][] matrix, int k) {
        if (matrix.length == 0) {
            return 0;
        }
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        int n = matrix.length;
        // 先计算 k 落在第几行
        int col = (int) (Math.ceil((double) k / (double) n) - 1);
        int row = k % n - 1;
        return matrix[col][row];
    }

    public Node copyRandomList(Node head) {

        HashMap<Node, Node> map = new HashMap<>();
        Node p = head;
        while (p != null) {
            map.put(p, new Node(p.val));
            p = p.next;
        }

        p = head;

        while (p != null) {

            map.get(p).next = map.get(p.next);
            map.get(p).random = map.get(p.random);
            p = p.next;
        }

        return map.get(head);
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) {
            return new int[]{};
        }
        int[] res = new int[nums.length - k + 1];


        ArrayDeque<Integer> deqeue = new ArrayDeque();

        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];

            // 维持顺序 小的依次弹出
            while (!deqeue.isEmpty() && (nums[deqeue.getLast()] < cur)) {
                deqeue.pollLast();
            }

            if (!deqeue.isEmpty() && k + deqeue.peekFirst() == i) {
                deqeue.pollFirst();
            }

            deqeue.addLast(i);

            if (i + 1 >= k) {
                res[i + 1 - k] = nums[deqeue.peekFirst()];
            }

            if (i == nums.length - 1) {
                if (i + 1 < k) {
                    return new int[]{nums[deqeue.pollFirst()]};
                }
            }

        }

        return res;
    }

    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> orderList = new ArrayList<>();

        int m = matrix.length;
        int n = matrix[0].length;
        if (m == 1) {
            for (int i = 0; i < matrix[0].length; i++) {
                orderList.add(matrix[0][i]);
            }
        }
        boolean[][] visited = new boolean[m][n];

        spiralOrderHelper(matrix, orderList, 0, 0, visited);
        return orderList;

    }

    void spiralOrderHelper(int[][] matrix, List<Integer> orderList, int i, int j, boolean[][] visited) {

        // 搜索顺序 1 先  j++ -> 2 先列递增 i++  -> 3 j-- -> 4 i--

        int count = 1;
        while (i >= 0 && j >= 0 && !visited[i][j]) {
            System.out.println(matrix[i][j]);

            System.out.println("i " + i + "  j " + j);
            orderList.add(matrix[i][j]);
            visited[i][j] = true;
            switch (count % 4) {
                case 1:

                    if ((j + 1 < matrix[0].length && visited[i][j + 1]) || j == matrix[0].length - 1) {
                        count++;
                        i++;
                        break;
                    } else {
                        j++;
                    }
                    break;
                case 2:

                    if ((i + 1 < matrix.length && visited[i + 1][j]) || i == matrix.length - 1) {
                        count++;
                        j--;
                        break;

                    } else {
                        i++;
                    }

                    break;
                case 3:
                    if ((j - 1 >= 0 && visited[i][j - 1]) || j == 0) {
                        count++;
                        i--;
                        break;
                    } else {
                        j--;
                    }

                    break;
                case 0:

                    if ((i - 1 >= 0 && visited[i - 1][j]) || i == 0) {
                        count++;
                        j++;
                        break;
                    } else {
                        i--;
                    }

                    break;
            }


        }
    }

    HashMap<Node, Node> map = new HashMap<>();

    public Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        if (map.containsKey(head)) {
            return map.get(head);
        }

        Node node = new Node(head.val);
        map.put(head, node);
        node.next = copyRandomList(head.next);
        node.random = copyRandomList(head.random);
        return map.get(head);
    }

    public ListNode oddEvenList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = head;
        ListNode even = head.next;
        ListNode evenHead = head.next;
        while (p.next != null && even.next != null) {
            // 1 奇数结点指向偶数结点 next 2 前进奇数结点 3 偶数结点指向 奇数结点next 4 前进偶数结点 5 结合 6 返回
            // tip 链表移动之前要修改next 指针
            p.next = even.next;
            p = p.next;
            even.next = p.next;
            even = evenHead.next;
        }
        even.next = evenHead;

        return head;
    }

    ArrayList<Integer> list = new ArrayList<>();

    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        ArrayList<Integer> list = new ArrayList<>();
        kthSmallestHelp(rootNode);
        return list.get(k);
    }

    public void kthSmallestHelp(TreeNode rootNode) {
        if (rootNode != null) {
            kthSmallestHelp(rootNode.left);
            list.add(rootNode.val);
            kthSmallestHelp(rootNode.right);

        } else {
            return;
        }
    }

    public int numSquares(int n) {
        int a = (int) Math.sqrt(n);

        int count = a * a;
        int time = 0;
        while (a > 0 && n > 0) {
            time++;
            if (a * a == n) {
                return time;
            } else if (a * a < n) {
                a = (int) Math.sqrt(n - a * a);
            }
        }
        return -1;
    }

    public int maxPathSum(TreeNode root) {

        if (root == null) {
            return 0;
        }
        int left = Math.max(0, maxPathSum(root.left));
        int right = Math.max(0, maxPathSum(root.right));
        int sum = left + right + root.val;
        return Math.max(sum, Math.max(left, right));
    }


    public int rob(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[1], nums[0]);
        }
        int dp[] = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], nums[0]);
        int max1 = 0;
        for (int i = 2; i < nums.length - 1; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        max1 = dp[nums.length - 2];

        int max2 = 0;
        dp[1] = nums[1];
        dp[2] = Math.max(nums[1], nums[2]);
        for (int i = 3; i < nums.length - 1; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        max2 = dp[nums.length - 1];

        return Math.max(max1, max2);
    }

    HashMap<TreeNode, Integer> memo = new HashMap<>();

    public int rob(TreeNode root) {

        if (root == null) {
            return 0;
        }
        if (memo.containsKey(root)) {
            return memo.get(root);
        }
        int money = root.val;
        if (root.left != null) {
            money += rob(root.left.left) + rob(root.left.right);
        }

        if (root.right != null) {
            money += rob(root.right.left) + rob(root.right.right);
        }
        int max = Math.max(money, rob(root.left) + rob(root.right));
        memo.put(root, max);
        return max;
    }

    //定义状态 偷 未偷 此时结果 为 res[0] res[1]
    public int rob2(TreeNode root) {

        int[] res = robHelp(root);

        return Math.max(res[0], res[1]);
    }

    public int[] robHelp(TreeNode root) {
        if (root == null) {
            return new int[2];
        }
        int res[] = new int[2];

        int[] left = robHelp(root.left);
        int[] right = robHelp(root.right);


        res[1] = root.val + left[0] + right[0];
        res[0] = Math.max(left[1], left[0]) + Math.max(right[0], right[1]);

        return res;

    }

    public boolean isPalindrome(int x) {

        // 123454321
        int revertNumber = 0;
        if (x != 0 && x % 10 != 0 && x > 0) {

            while (x > revertNumber) {
                int y = x % 10;
                // 每次把求余结果放后面前面 *10
                revertNumber = revertNumber * 10 + y;
                if (revertNumber == x || revertNumber / 10 == x) {
                    return true;
                }
            }
        }

        return false;
    }

    public int getLeft(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        System.out.println(target);

        System.out.println(left);

        LinkedHashMap linkedHashMap = new LinkedHashMap(5, 0.75f) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 5;
            }
        };

        return left;
    }


    public int getPoint(int[] nums) {

        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) >>> 1;
            // 反转点在左侧
            if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        System.out.println(left);
        return left;

    }

    //1, 4, 9, 16

    /**
     * 13 = 4 + 9.
     * <p>
     * dp[13] = dp[9]+dp[4]
     * 推导出 只要 i - j*j 原有的结果减去一个平方数
     * 那么 结果 = dp[i - j*j] + 1
     * 由于有多个平方数 所以需要遍历求出最小解
     *
     * @param n
     * @return
     */
    public int numSquares2(int n) {

        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 0; i <= n; i++) {
            dp[i] = i;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i && i >= j * j; j++) {
                dp[i] = Math.min(dp[i - j * j] + 1, dp[i]);
            }
        }
        return dp[n];

    }

    // 计算质数 只需要计算 sqrt n 就可以排除
    public int countPrimes(int n) {

        boolean[] prime = new boolean[n];
        Arrays.fill(prime, true);

        for (int i = 0; i * i < n; i++) {
            // 因为 i 之前的元素都已经遍历过了 所以 j=i*i 然后每次加 i 个 成为 i 的倍数都添加了
            for (int j = i * i; j < n; j += i) {
                if (prime[j]) {
                    prime[j] = false;
                    j *= i;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (prime[i]) {
                count++;
            }
        }

        return count;

    }

    /**
     * 287. 寻找重复数
     * [1,3,4,2,2]
     * 利用二分法想中间收敛
     * 小于 mid 的数 应该为 mid 个
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {

        int left = 1;
        int right = nums.length - 1;

        while (left < right) {
            int mid = (left + right) >>> 1;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= mid) {
                    count++;
                }
            }

            if (count <= mid) {
                left = mid + 1;
                continue;
            } else {
                right = mid;
            }
        }

        return left;

    }

    public String largestNumber(int[] nums) {

        String[] strNums = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strNums[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strNums, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o1 + o2).compareTo(o2 + o1);
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

        StringBuffer buffer = new StringBuffer();
        for (int i = nums.length; i >= 0; i--) {
            buffer.append(strNums[i]);
        }

        if (strNums[nums.length - 1].equals("0")) {
            return "0";
        }

        return buffer.toString();
    }

    public int partition3(int[] nums, int left, int right) {
        int pivot = nums[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] < pivot) {
                swap(nums, i, ++j);
            }
        }
        swap(nums, left, j);
        return j;
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null && head.next == null)
            return head;
        ListNode next = head.next;
        head.next = swapPairs(next);
        next.next = head;
        return next;
    }


    public void wiggleSort(int[] nums) {

        int[] copy = nums.clone();
        Arrays.sort(copy);
        // 由于是摆动排序，小的要在前面，所以要保证小的数量大于等于 大的那部分
        int res[] = new int[copy.length];
        // 先开始添加小的部分
        // res i = i+2
        int len = copy.length;
        int[] smaller = new int[len % 2 == 0 ? len / 2 : (len / 2 + 1)], bigger = new int[len / 2];
        System.arraycopy(nums, 0, smaller, 0, smaller.length);
        System.arraycopy(nums, smaller.length, bigger, 0, bigger.length);

        for (int i = 0; i < len; i++) {
            if ((i & 1) == 1) {
                System.out.printf("key" + (bigger.length - 1 - (i >>> 1)));

                System.out.printf("value" + bigger[bigger.length - 1 - (i >>> 1)]);
                nums[i] = bigger[bigger.length - 1 - (i >>> 1)];
            } else {
                nums[i] = smaller[smaller.length - 1 - (i >>> 1)];
            }
        }

    }

    public List<Integer> countSmaller(int[] nums) {
        // 从后向前遍历
        Integer res[] = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = 0;
        }
        TreeNode root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            insert(root, new TreeNode(nums[i]), res, i);
        }
        return Arrays.asList(res);
    }


    public TreeNode insert(TreeNode root, TreeNode node, Integer[] res, int i) {
        if (root == null) {
            root = node;
            return root;
        }

        // 按照题中条件把树分成两块，然后去插入指定root
        if (node.val <= root.val) {
            root.count++;
            root.left = insert(root.left, node, res, i);
        } else {
            // 父节点的左子树 和 自己的左子树之和
            res[i] += root.count + 1;
            root.right = insert(root.right, node, res, i);
        }

        return root;
    }

    public void removeArr(int arr[], int num) {
        // 从后往前移动  定义 fromindex = arr.length-nums  toindex = arr.length-1
    }

    public int searchInsert(int[] nums, int target) {

        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 16. 最接近的三数之和
     * 本题解题技巧
     * 由于是求三个数 所以要用双指针进行求解
     * 先确定一个点，然后确定另外两个边界，在计算过程中求最接近的值。
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {

        if (nums.length < 3) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            return sum;
        }

        Arrays.sort(nums);


        int res = (nums[0] + nums[1] + nums[nums.length - 1]);
        for (int i = 0; i < nums.length - 2; i++) {
            int right = nums.length - 1;
            int left = i + 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(res - target)) {
                    res = sum;
                }
                if (sum == target) {
                    return sum;
                }
                if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return res;
    }

    List<List<Integer>> combinationSum;
    private HashSet<Set<Integer>> resSet = new HashSet<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        combinationSum = new ArrayList<>();
        // 伪代码
        // 1 保存下探遍历数组 满足 res == target return list
        // 2 添加list
        // 3 return list
        LinkedList linkedList = new LinkedList<Integer>();
        backTrack(linkedList, candidates, target, 0, 0);
        return combinationSum;
    }

    public void backTrack(LinkedList<Integer> res, int[] candidates, int target, int sum, int start) {
        if (sum == target) {
//            if (resSet.contains(new HashSet(res))) {
//                return;
//            }
//            resSet.add(new HashSet(res));
            combinationSum.add(new ArrayList<>(res));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            int num = candidates[i];
            res.offerLast(num);
            backTrack(res, candidates, target, sum + num, i);
            res.pollLast();
        }
    }

    public void merge(int[] A, int m, int[] B, int n) {
        int lengthA = A.length;
        int lengthB = B.length;
        int[] temp = new int[m + n];
        int i = 0, j = 0;
        while (i < m && j < n) {

            if (A[i] < B[j]) {
                temp[i + j] = A[i];
                i++;
            } else {
                temp[i + j] = B[j];
                j++;
            }

        }
        if (i != m) {
            for (int k = i; k < m; k++) {
                temp[k + j] = A[k];
            }
        }

        if (j != n) {
            for (int k = j; k < n; k++) {
                temp[k + i] = B[k];
            }
        }

        A = temp;
    }


    public int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        int K = 2;
        // k = 1,k=2; k!=0 肯定会有交易
        int dp[][][] = new int[n][K + 1][2];

        for (int i = 0; i < n; i++) {
            for (int k = 1; k < k + 1; k++) {
                if (i == 0) {
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }

        return dp[n - 1][2][0];

    }

    public int diameterOfBinaryTree(TreeNode root) {
        return diameterOfBinaryTreeHelp(root);
    }

    int diametor;

    public int diameterOfBinaryTreeHelp(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = diameterOfBinaryTreeHelp(root.left);
        int right = diameterOfBinaryTreeHelp(root.right);
        // 计算每个节点的最大直径
        diametor = Math.max(diametor, left + right + 1);
        // 最大高度取决于递归的深度
        return Math.max(left, right) + 1;
    }

    public int minPathSum(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                    continue;
                }
                if (i == 0 && j > 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                    continue;
                }
                if (j == 0 && i > 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                    continue;
                }
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[grid.length - 1][grid[0].length - 1];
    }

    // 双指针碰撞
    public boolean canThreePartsEqualSum(int[] A) {
        int i = 1, j = A.length - 2;
        int leftSum = A[0];
        int rightSum = A[A.length - 1];
        int sum = 0;
        for (int k = 0; k < A.length; k++) {
            sum += A[k];
        }

        while (i + 1 < j) {
            if (leftSum == sum / 3 && rightSum == sum / 3) {
                System.out.println("leftsum" + leftSum);
                System.out.println("rightsum" + rightSum);
                System.out.println("i" + i);
                System.out.println("j" + j);
                return true;
            }
            if (leftSum != sum / 3) {
                leftSum += A[i];
                if (leftSum != sum / 3) {
                    i++;
                }
            }
            if (rightSum != sum / 3) {
                rightSum += A[j];
                if (rightSum != sum / 3) {
                    j--;
                }

            }

        }
        return false;
    }

    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        int i = 0, j = 0, k = 0;
        List<Integer> integers = new ArrayList<>();
        while (i < arr1.length && j < arr2.length && k < arr3.length) {
            if (arr1[i] == arr2[j] && arr1[i] == arr3[k]) {
                integers.add(arr1[i]);
                i++;
                j++;
                k++;
            } else if (arr1[i] <= arr2[j] && arr1[i] <= arr3[k]) {
                i++;
            } else if (arr2[j] <= arr1[i] && arr2[j] <= arr3[k]) {
                j++;
            } else if (arr3[k] <= arr1[i] && arr3[k] <= arr2[j]) {
                k++;
            }
        }
        return integers;
    }

    public String gcdOfStrings(String str1, String str2) {

        if (str2.length() < 1) {
            return "";
        }

        int length = str2.length();
        while (str1.length() > 0) {
            String temp = str1.substring(0, length);
            if (!str2.equals(temp)) {
                return "";
            }
            str1 = str1.substring(length);
        }
        int i = 0, j = str2.length() - 1;
        String real = str2;
        while (i < j) {
            if (str2.substring(0, i + 1).equals(str2.substring(j))) {
                real = str2.substring(0, i + 1);
            }
            i++;
            j--;
        }
        return real;
    }

    public int lastStoneWeight(int[] stones) {

        int sum = 0;
        for (int i = 0; i < stones.length; i++) {
            sum ^= stones[i];
        }

        return sum;
    }

    public int getDairyCow(int n) {
        int dp[] = new int[n];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            dp[i] = i > 2 ? dp[i - 3] + i : i;
        }

        return dp[n - 1];
    }

    public int lengthOfLIS1(int[] nums) {
        int length = nums.length;
        int[] piles = new int[length];
        int index = 0;

        for (int i = 0; i < length; i++) {
            int target = nums[i];
            int left = getLeft(target, piles, index + 1);
            nums[left] = target;
            if (left == index) {
                index++;
            }
        }
        return index;
    }

    public int getLeft(int target, int[] nums, int end) {
        int left = 0;
        int right = end;

        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] == target) {
                right = mid;
            } else if (target < nums[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 桶排序每个nums[i] 都对应
     * num -1 bucket
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            while (nums[i] > 0 && nums[i] <= len && (nums[nums[i] - 1] != nums[i])) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1;
    }

    /**
     * 1->1->2
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode p = head;
        while (p.next != null) {
            if (p.val == p.next.val) {
                ListNode temp = p.next.next;
                p.next = null;
                p.next = temp;
            }
            p = p.next;
        }

        return head;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        dp[0][0] = 1;
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[i].length; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }

                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                } else if (i == 0 && j == 0) {

                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }

            }
        }
        return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public int massage(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int dp[] = new int[nums.length];
        if (nums.length == 1) {
            return nums[0];
        }
        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], nums[0]);

        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[nums.length - 1];
    }

    /**
     * 给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/daily-temperatures
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param T
     * @return
     */
    public int[] dailyTemperatures(int[] T) {
        int res[] = new int[T.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = T.length - 1; i >= 0; i--) {
            int curVal = T[i];
            while (!stack.isEmpty() && curVal > T[stack.peek()]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return res;
    }

    public int maxDistance(int[][] grid) {
        if (grid.length == 0) {
            return -1;
        }
        ArrayList<Pair<Integer, Integer>> lands = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> xMap = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> yMap = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    lands.add(new Pair<>(i, j));
//                    xMap.getOrDefault(i,new ArrayList<>()).add(j);
//                    yMap.getOrDefault(j,new ArrayList<>()).add(i);
                }
            }
        }
        if (lands.size() == 0 || lands.size() == grid.length * grid[0].length) {
            return -1;
        }
        int max = -1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    continue;
                }
                int distance = Integer.MAX_VALUE;
                int lastX = 0, lastY = 0;
                for (int k = 0; k < lands.size(); k++) {
                    int curX = lands.get(k).getKey();
                    int curY = lands.get(k).getValue();

                    int curDis = Math.abs(curX + curY - i - j);
                    distance = Math.min(distance, curDis);
                }
                max = Math.max(max, distance);
            }
        }

        return max;
    }

    public int maxDistance2(int[][] grid) {
        if (grid.length == 0) {
            return -1;
        }
        LinkedList<int[]> lands = new LinkedList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    lands.add(new int[]{i, j});
                }
            }
        }
        if (lands.size() == 0 || lands.size() == grid.length * grid[0].length) {
            return -1;
        }
        int count = 0;
        while (!lands.isEmpty()) {
            count++;
            int size = lands.size();
            for (int k = 0; k < size; k++) {
                int[] item = lands.pollFirst();
                int i = item[0];
                int j = item[1];
                if (i - 1 >= 0 && grid[i - 1][j] == 0) {
                    grid[i - 1][j] = 2;
                    lands.offerLast(new int[]{i - 1, j});
                }
                if (i + 1 < grid.length && grid[i + 1][j] == 0) {
                    grid[i + 1][j] = 2;

                    lands.offerLast(new int[]{i + 1, j});
                }
                if (j - 1 >= 0 && grid[i][j - 1] == 0) {
                    grid[i][j - 1] = 2;

                    lands.offerLast(new int[]{i, j - 1});
                }
                if (j + 1 < grid.length && grid[i][j + 1] == 0) {
                    grid[i][j + 1] = 2;

                    lands.offerLast(new int[]{i, j + 1});
                }
            }
        }
        return count == 0 ? -1 : count;
    }

    public int[][] generateMatrix(int n) {

        int[][] matrix = new int[n][n];
        int l = 0, r = n - 1, t = 0, b = n - 1;
        int val = 1;
        while (val < n * n) {
            for (int i = l; i <= r; i++) {
                matrix[t][i] = val++;
            }
            t++;
            for (int i = t; i <= b; i++) {
                matrix[i][r] = val++;
            }
            r--;
            for (int i = r; i >= l; i--) {
                matrix[b][i] = val++;
            }
            b--;
            for (int i = b; i >= t; i--) {
                matrix[i][l] = val++;
            }
            l++;
        }
        return matrix;
    }


    public List<Integer> spiralOrder2(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        int width = matrix.length;
        if (width == 0) {
            return list;
        }
        int height = matrix[0].length;
        int[] dw = {1, 0, -1, 0};
        int[] dh = {0, 1, 0, -1};
        boolean[][] visit = new boolean[width][height];
        int r = 0, c = 0, di = 0;

        for (int i = 0; i <= width * height; i++) {
            list.add(matrix[r][c]);
            visit[r][c] = true;
            int cr = r + dw[di];
            int cc = c + dh[di];
            if (cr >= 0 && cr < width && cc >= 0 && cc < height && !visit[cc][cr]) {
                r = cr;
                c = cc;
            } else {
                //方向改变
                di = (++di) % 4;
            }
        }
        return list;
    }


    public void quickSort(int[] nums, int left, int right) {
        if (left > right) {
            return;
        }
        int p = partition(nums, left, right);
        quickSort(nums, left, p - 1);
        quickSort(nums, p + 1, right);
    }

    public int partition2(int[] nums, int left, int right) {
        int l = left;
        int r = right;
        int pivot = nums[left];
        int j = l;
        while (l <= r) {
            for (int i = l + 1; i <= right; i++) {
                if (nums[i] < pivot) {
                    swap(nums, ++j, i);
                }
            }
            swap(nums, l, j);
        }
        return j;
    }

    public int[] maxDepthAfterSplit(String seq) {
        char[] chars = seq.toCharArray();
        int[] res = new int[chars.length];
        // 按照括号奇偶性分配
        for (int i = 0; i < chars.length; i++) {
            int tem = chars[i] == '(' ? (i & 1) : ((i + 1 & 1));
            res[i] = tem;
        }

        return res;
    }

    // 5 4 3 2 1s
    public int getLast(Stack stack) {
        int top = (int) stack.pop();
        if (stack.isEmpty()) {
            return top;
        } else {
            int last = getLast(stack);
            stack.push(top);
            return last;
        }
    }

    //  stack   2 3 4 1  stack

    /**
     * @param stack
     */
    public void reverseStack(Stack stack) {
        if (stack.isEmpty()) {
            return;
        }


        int last = getLast(stack);
        System.out.println(last);
        reverseStack(stack);
        stack.push(last);
    }

    double pre = -Double.MAX_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (isValidBST(root.left)) {
            if (root.val > pre) {
                pre = root.val;
                return isValidBST(root.right);
            }
        }
        return false;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return mirror(root, root);
    }

    public boolean mirror(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        if (node1.val == node2.val) {
            return mirror(node1.right, node2.left) && mirror(node1.left, node2.right);
        }
        return false;
    }

    public void gameOfLife(int[][] board) {
        int[][] copyBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copyBoard[i][j] = board[i][j];
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int liveNeighbors = state(board, i, j);
                // 规则 1 或规则 3
                if ((copyBoard[i][j] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    board[i][j] = 0;
                }
                // 规则 4
                if (copyBoard[i][j] == 0 && liveNeighbors == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

    public int state(int[][] board, int i, int j) {
        int countLive = 0;
        if (i - 1 >= 0 && board[i - 1][j] == 1) {
            countLive++;
        }
        if (j - 1 >= 0 && board[i][j - 1] == 1) {
            countLive++;
        }
        if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == 1) {
            countLive++;
        }

        if (i + 1 < board.length && board[i + 1][j] == 1) {
            countLive++;
        }
        if (i + 1 < board.length && j + 1 < board[i].length && board[i + 1][j + 1] == 1) {
            countLive++;
        }
        if (i + 1 < board.length && j - 1 >= 0 && board[i + 1][j - 1] == 1) {
            countLive++;
        }
        if (j + 1 < board[i].length && i - 1 >= 0 && board[i - 1][j + 1] == 1) {
            countLive++;
        }
        if (j + 1 < board[i].length && board[i][j + 1] == 1) {
            countLive++;
        }
        return countLive;
    }

    List<LinkedList<Integer>> res = new ArrayList<>();

    public List<Integer> rightSideView(TreeNode root) {
        rightSideViewHelp(root, 0);
        ArrayList result = new ArrayList();
        for (int i = 0; i < res.size(); i++) {
            result.add(res.get(i).pollLast());
        }
        return result;
    }

    public void rightSideViewHelp(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (res.size() == level) {
            res.add(new LinkedList<>());
        }
        res.get(level).addLast(root.val);
        rightSideViewHelp(root.left, level + 1);
        rightSideViewHelp(root.right, level + 1);
    }


    public boolean isSubtree(TreeNode s, TreeNode t) {
        return isTreeEqual(s, t) || isSubtree(s.left, t) || isSubtree(s.right, t);
    }


    public boolean isTreeEqual(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return true;
        }
        // terminate
        return s.val == t.val && isTreeEqual(s.left, t.left) && isTreeEqual(s.right, t.right);
    }

//    public ListNode deleteDuplicates(ListNode head) {
//
//        ListNode cur = head;
//        ListNode last = null;
//        int lastVal=-1;
//        while (cur!=null){
//            if (cur.val==lastVal){
//
//            }
//
//            lastVal = cur.val;
//            last = cur;
//        }
//    }

    /**
     * 基因编辑
     * 脑中先脑补 base case dp table
     * 思考另一个dp 临近状态转化图  从临近三个地方转换成 dp[i][j]
     *
     * @param s1
     * @param s2
     * @return
     */
    int minDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m][n];
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        for (int i = 0; i < m; i++) {
            dp[0][i] = i;
        }
        for (int i = 0; i < n; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (chars1[i] == chars2[j]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public ListNode deleteDuplicates2(ListNode head) {
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode cur = dummyHead;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                // 移除后续节点
                ListNode temp = cur.next;
                while (temp.next != null && temp.val == temp.next.val) {
                    temp = temp.next;
                }
                cur.next = temp.next;
            } else {
                cur = cur.next;
            }

        }
        return dummyHead.next;
    }

    int duplicateNum;

    public ListNode deleteDuplicates3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        head.next = deleteDuplicates3(head.next);

        if (head.next != null && head.val == head.next.val) {
            duplicateNum = head.val;
        }
        while (head != null && head.val == duplicateNum) {
            head = head.next;
        }
        return head;
    }

    public ListNode partition(ListNode head, int x) {

        ListNode bigHead = new ListNode(1);
        ListNode curBig = bigHead;
        ListNode smallHead = new ListNode(-1);
        ListNode curSmall = smallHead;

        while (head != null) {
            if (head.val > x) {
                curBig.next = head;
                curBig = curBig.next;
            } else {
                curSmall.next = head;
                curSmall = curSmall.next;
            }

            head = head.next;
        }
        curSmall.next = bigHead.next;
        curBig.next = null;
        return smallHead.next;
    }


    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode last = reverseList1(head.next);

        head.next.next = head;
        head.next = null;
        return last;

    }

    ListNode successor;

    public ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        ListNode last = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = null;
        return last;
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m == 1) {
            return reverseN(head, n);
        }
        head.next = reverseBetween(head.next, m - 1, n - 1);
        return head;
    }

    public void rotate(int[][] matrix) {

        int length = matrix.length;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
            for (int j = 0; j < length >> 1; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][length - 1 - j];
                matrix[i][length - 1 - j] = temp;
            }
        }

        int n = matrix.length;
        // 水平翻转
        for (int i = 0; i < n / 2; ++i) {

            int[] temp = matrix[i];
            matrix[i] = matrix[n - i - 1];
            matrix[n - i - 1] = temp;
        }
        // 主对角线翻转
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSubtree(p.left, q.left) && isSubtree(p.right, q.right);
    }

    //最近二叉搜索树
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root != null && p != null && q != null) {
            if (root.val > q.val && root.val < p.val || (root.val > p.val && root.val < q.val)) {
                return root;
            }
            if (root.val > q.val && root.val > p.val) {
                return lowestCommonAncestor(root.left, p, q);
            } else {
                return lowestCommonAncestor(root.right, p, q);
            }
        }

        return root;
    }


    public List<String> binaryTreePaths(TreeNode root) {
        List list = new ArrayList();
        LinkedList linkedList = new LinkedList();
        binaryTreePathsHelp(list, root, linkedList);

        return list;
    }

    public void binaryTreePathsHelp(List<String> res, TreeNode rootNode, LinkedList linkedList) {
        if (rootNode == null) {

            return;

        }
        if (rootNode.left == null && rootNode.right == null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < linkedList.size(); i++) {
                if (i != linkedList.size() - 1) {
                    builder.append(linkedList.get(i));
                    builder.append("->");
                } else {
                    builder.append(linkedList.get(i));
                }
            }
            res.add(builder.toString());
            return;
        }
        linkedList.addLast(rootNode.val);

        if (rootNode.left != null) {
            binaryTreePathsHelp(res, rootNode.left, linkedList);
            linkedList.pollLast();
        }

        if (rootNode.right != null) {
            binaryTreePathsHelp(res, rootNode.right, linkedList);
            linkedList.pollLast();
        }
    }

    // z 变换 首先先确定有几行 然后 1-2-3-2-1 用偏移量来代替
    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }

        ArrayList<StringBuilder> list = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }
        char[] chars = s.toCharArray();
        // 行号
        int y = 0;
        int dy = -1;
        for (Character ch : chars) {
            StringBuilder builder = list.get(y);
            if (y == 0 || y == numRows - 1) {
                dy = -dy;
            }
            y += dy;
            builder.append(ch);
        }
        StringBuilder builder = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            builder.append(list.get(i));
        }
        return builder.toString();
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {

        ArrayDeque<Integer> linkedList1 = new ArrayDeque();
        ArrayDeque<Integer> linkedList2 = new ArrayDeque();

        while (l1 != null) {
            linkedList1.offer(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            linkedList2.offer(l2.val);
            l2 = l2.next;
        }


        ListNode dummy = new ListNode(-1);
        int carry = 0;
        ListNode last = null;
        while (!linkedList1.isEmpty() || !linkedList2.isEmpty()) {
            int n1 = linkedList1.isEmpty() ? 0 : linkedList1.pollLast();
            int n2 = linkedList2.isEmpty() ? 0 : linkedList2.pollLast();
            int sum = (n1 + n2 + carry) % 10;
            carry = (n1 + n2 + carry) / 10;

            ListNode node = new ListNode(sum);
            node.next = last;
            last = node;
        }
        if (carry > 0) {
            ListNode node = new ListNode(carry);
            node.next = last;
            last = node;
        }
        return last;
    }

    int updateMatrixMax = 0;

    public String intToRoman(int num) {
        int index = 0;
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder stringBuilder = new StringBuilder();
        while (index < 13) {
            while (num >= nums[index]) {
                stringBuilder.append(romans[index]);
                num -= nums[index];
            }
            index++;
        }

        return stringBuilder.toString();
    }

    public int numTrees(int n) {
        long C = 1;
        for (int i = 0; i < n; i++) {
            C = C * 2 * (i * 2 + 1) / (i + 2);
        }
        return (int) C;
    }


    public int numTrees2(int n) {
        // Note: we should use long here instead of int, otherwise overflow
        long C = 1;
        for (int i = 0; i < n; ++i) {
            C = C * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) C;
    }

    public int[][] merge(int[][] intervals) {

        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });
        int index = 1;
        ArrayList<int[]> res = new ArrayList<>();

        while (index < intervals.length) {
            int left = intervals[index][0];
            int right = intervals[index][1];
            while (index + 1 < intervals.length && intervals[index + 1][0] <= right) {
                index++;
                right = Math.max(right, intervals[index][1]);
            }
            res.add(new int[]{left, right});
            index++;
        }

        return res.toArray(new int[0][]);
    }

    /**
     * 跳跃函数
     * 不断计算当前元素可以跳跃的最大的值
     * 当前元素可以跳跃的最大值 = i + nums[i]
     * 更新最大值
     * 只有在可以跳跃的范围内才可以跳跃
     * 不然就返回false
     */
    public boolean canJump(int[] nums) {

        int length = nums.length;
        int max = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i <= max) {
                max = Math.max(max, nums[i] + i);
            } else {
                return false;
            }
        }
        return true;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {

        // 寻找第一个大于 cost 的item 然后不断向前判断是否存在cursum> COST
        int sum = 0, costSum = 0;
        int curSum = 0, start = 0;

        for (int i = 0; i < gas.length; i++) {
            sum += gas[i];
            costSum += cost[i];
            curSum += gas[i] - cost[i];
            // 如果当前容量大于0 那么便以当前开始 否则 刷新起始位置
            // 反之刷新当前容量
            if (curSum < 0) {
                curSum = 0;
                start = i + 1;
            }
        }
        if (sum < costSum) {
            return -1;
        }
        // 最后是否还剩下油
        return curSum >= 0 ? start : -1;
    }

    public int[] nextTemp(int[] temps) {
        // 以倒序放入元素 当前元素和站内元素相比，如果栈内元素大于当日温度那么 返回 index 之差
        // 反之 返回0
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[temps.length];
        for (int i = temps.length - 1; i >= 0; i--) {

            // 找到第一个大于今日温度的元素
            while (!stack.isEmpty() && temps[stack.peek()] <= temps[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                res[i] = 0;
            } else {
                res[i] = stack.peek() - i;
            }

            stack.push(i);
        }
        return res;
    }

    public int expectNumber(int[] scores) {

        HashSet set = new HashSet();
        for (int i = 0; i < scores.length; i++) {
            set.add(scores[i]);
        }

        return set.size();
    }

    //小张制定了 LeetCode 刷题计划，他选中了 LeetCode 题库中的 n 道题，编号从 0 到 n-1，
    // 并计划在 m 天内按照题目编号顺序刷完所有的题目（注意，小张不能用多天完成同一题）
    // 我们定义 m 天中做题时间最多的一天耗时为 T（小杨完成的题目不计入做题总时间）。请你帮小张求出最小的 T是多少。
    //输入：time = [1,2,3,3,6,4,2], m = 2 用m 来切分数组 切分完毕之后求区间最大值

    //


    public int integerBreak(int n) {
        // 切分 最小单元是3
        if (n < 3) {
            return n - 1;
        }
        // nums of 3
        int a = n / 3;
        // lefts
        int b = n % 3;
        // 0
        if (b == 0) {
            return (int) Math.pow(3, a);
        }
        //1
        if (b == 1) {
            return (int) Math.pow(3, a - 1) * 4;
        }
        //2
        return (int) Math.pow(3, a) * 2;

    }

    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode temp = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(temp);

        return root;
    }

    public ListNode deleteNode(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p = dummy;
        while (p != null && p.next != null) {
            if (p.next.val == val) {
                p.next = p.next.next;
            }
            p = p.next;
        }
        return dummy.next;
    }

    ListNode su;

    public ListNode reverseN2(ListNode head, int m) {
        if (m == 1) {
            su = head.next;
            return head;
        }
        ListNode last = reverseN2(head.next, m - 1);
        head.next.next = head;
        head.next = su;

        return last;
    }


    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(0);
        for (int i = 0; i < n; i++) {
            for (int j = res.size() - 1; j >= 0; j--) {
                res.add((1 << n) ^ res.get(j));
            }
        }
        return res;
    }

    public int minMeetingRooms(int[][] intervals) {
        // 视频会议 首先要排序 然后通过优先队列保存会议结束时间
        // 如果当前会议开始时间大于最先结束的会议 说明有会议室可用 poll 前一个会议室 添加当前会议室结束时间
        // 如果当前会议开始时间小于最先结束的会议那么 直接添加该会议的结束时间

        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] == b[0] ? a[1] - b[1] : a[0] - b[0];
            }
        });

        PriorityQueue<Integer> meetings = new PriorityQueue<>();
        for (int i = 0; i < intervals.length; i++) {
            int[] currentMeeting = intervals[i];
            // 如果当前会议开始时间大于 最先结束的会议时间 那么 就可以合并会议
            if (meetings.size() > 0 && currentMeeting[0] >= meetings.peek()) {
                meetings.poll();
            }
            meetings.offer(currentMeeting[1]);
        }

        return meetings.size();
    }

    public int mincostTickets(int[] days, int[] costs) {

        int maxDay = days[days.length - 1];
        int mindDay = days[0];
        int dp[] = new int[395];
        int i = days.length - 1;
        for (int d = maxDay; d >= mindDay; d--) {
            if (days[i] == d) {
                // 后面1天买一张票 后面7天 七天票
                dp[d] = Math.min(dp[d + 1] + costs[0], costs[1] + dp[d + 7]);
                dp[d] = Math.min(dp[d], costs[2] + dp[d + 30]);
                i--;
            } else {
                dp[i] = dp[i + 1];
            }

        }

        return dp[mindDay];
    }

    public int kthSmallest2(TreeNode root, int k) {
        return inorderTraversal(root, k, 0);
    }

    public int inorderTraversal(TreeNode root, int k, int cur) {
        if (root == null) {
            return -1;
        }
        if (root.left != null) {
            int r = inorderTraversal(root.left, k, cur);
            if (r != -1) {
                return r;
            }
        }
        cur++;
        if (cur == k) {
            return root.val;
        }

        if (root.right != null) {
            int r = inorderTraversal(root.right, k, cur);
            if (r != -1) {
                return r;
            }
        }

        return -1;
    }


    public int traverseBST(TreeNode root, ArrayList<Integer> res, int k) {

        if (root.left != null) {
            int r = traverseBST(root.left, res, k);
            if (r != -1) {
                return r;
            }
        }

        res.add(root.val);
        System.out.println(res.toString());
        if (res.size() == k) {
            return root.val;
        }

        if (root.right != null) {
            int r = traverseBST(root.right, res, k);
            if (r != -1) {
                return r;
            }
        }

        return -1;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {

            int size = queue.size();
            List<Integer> subs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                subs.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.addFirst(subs);
        }

        return res;
    }

    // 区间是 [left,right)
    public int mySqrt(int x) {
        int left = 0;
        int right = Integer.MAX_VALUE;
        while (left < right) {
            long mid = (left + right + 1) >>> 1;
            if (mid * mid > x) {
                right = (int) (mid - 1);
            } else {
                left = (int) mid;
            }
        }
        return left;
    }

    public int[] plusOne(int[] digits) {
        int carry = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            carry = digits[i] / 10;
            digits[i] = digits[i] % 10;
            // 只要当前不进位 就返回结果
            if (digits[i] != 0) {
                return digits;
            }
        }
        // case 2 进位了 极端情况 999
        int res[] = new int[digits.length + 1];
        res[0] = 1;
        return res;
    }


    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode temp = root.left;
        root.left = invertTree2(root.right);
        root.right = invertTree2(temp);
        return root;
    }

    public int compareVersion(String version1, String version2) {

        String v1[] = version1.split("\\.");
        String v2[] = version2.split("\\.");

        int l1 = v1.length, l2 = v2.length;
        for (int i = 0; i < Math.max(l1, l2); i++) {
            String s1 = "0";
            String s2 = "0";

            if (i < l1) {
                s1 = v1[i];
            }
            if (i < l2) {
                s2 = v2[i];
            }
            System.out.println(s1);
            System.out.println(s2);

            if (Double.parseDouble(s1) > Double.parseDouble(s2)) {
                return 1;
            } else if (Double.parseDouble(s1) < Double.parseDouble(s2)) {
                return -1;
            }

        }
        return 0;
    }

    public int superEggDrop(int K, int N) {
        // 定义一个dp dp[i][j] 代表 k 个鸡蛋扔 m 次
        int dp[][] = new int[K + 1][N + 1];
        int m = 0;
        while (dp[K][m] < N) {
            m++;
            for (int i = K; i <= K; i++) {
                dp[i][m] = dp[i][m - 1] + dp[i - 1][m - 1] + 1;
            }
        }
        return m;
    }

    public int minDistance2(String word1, String word2) {

        int l1 = word1.length();
        int l2 = word2.length();
        int dp[][] = new int[l1 + 1][l2 + 1];
        for (int i = 1; i <= l1; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= l2; i++) {
            dp[0][l2] = i;
        }
        for (int i = 1; i < l1; i++) {
            for (int j = 1; j < l2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[l1][l2];
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode a, b;
        a = b = head;
        // 1-2-3-4-5  k =4 是四个一组 那么肯定不包括最后一个
        for (int i = 0; i < k; i++) {
            b = b.next;
            if (b == null) {
                return head;
            }
        }
        // 返回新的头结点
        ListNode newHead = revserN(a, b);
        // 尾节点肯定是 a
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    public ListNode revserN(ListNode a, ListNode b) {
        ListNode pre = null;
        ListNode cur = a;
        while (cur != b) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public String addStrings(String num1, String num2) {

        char[] chars1 = num1.toCharArray();
        char[] chars2 = num2.toCharArray();
        if (chars1.length < chars2.length) {
            char[] temp = chars2;
            chars2 = chars1;
            chars1 = temp;
        }
        int index = chars1.length - 1;
        int index2 = chars2.length - 1;
        StringBuilder builder = new StringBuilder();
        int carry = 0;
        while (index >= 0) {
            char c1 = chars1[index--];
            char c2;
            if (index2 >= 0) {
                c2 = chars2[index2--];
            } else {
                c2 = '0';
            }
            int res = (c1 + c2 - '0' - '0' + carry) % 10;
            carry = (c1 + c2 - '0' - '0' + carry) / 10;
            builder.append(res);
        }
        if (carry > 0) {
            builder.append(carry);
        }

        return builder.reverse().toString();
    }

    public List<String> topKFrequent(String[] words, int k) {

        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], map.getOrDefault(words[i], 0) + 1);
        }
        PriorityQueue<String> queue = new PriorityQueue<>(k, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // case1 数量相等 题中要求 是 i 在 love 后面 按照堆的特性
                if (map.get(a).equals(map.get(b))) {
                    return b.compareTo(a);
                }
                // case2 按照数量排序
                return map.get(a).compareTo(map.get(b));
            }
        });

        ArrayList<String> keys = new ArrayList<>(map.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (queue.size() < k) {
                queue.offer(key);
            } else {
                if (map.get(key) > map.get(queue.peek())) {
                    queue.poll();
                    queue.offer(key);
                } else if (queue.comparator().compare(key, queue.peek()) > 0) {
                    queue.poll();
                    queue.offer(key);
                }
            }
        }
        System.out.println(Arrays.toString(queue.toArray()));
        LinkedList<String> res = new LinkedList();
        while (!queue.isEmpty()) {
            res.addFirst(queue.poll());
        }
        return res;
    }

    public int minTime(int[] time, int m) {
        // 本质上是将时间分到 m 天里面
        // 需要确定每一天能分的上限最大值
        // 题意是求 每天最的值 最大 然后和最小
        /**
         * 二分 加上贪心
         * 先确定最大上限，然后不断收缩
         * 确定是否能被分割
         *
         */
        int left = 0;
        int right = Integer.MAX_VALUE;

        while (left < right) {
            int mid = (left + right) >> 1;
            if (canSplit(time, m, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // 分割到 m 天 top 每天最大值
    public boolean canSplit(int nums[], int day, int top) {
        int curMax = 0;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            sum += v;
            curMax = Math.max(v, curMax);
            // 换行
            if (sum - curMax > top) {
                if (count++ == day) {
                    return false;
                }
                sum = v;
                curMax = v;
            }
        }

        return true;
    }

    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((i & 1) != 1) {
                sum += nums[i];
            }
        }
        return sum;
    }

    // 拓扑排序

    /**
     * @param numCourses
     * @param prerequisites [1,0] 代表1 依赖于 0 课程1 的入度为 1
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int degrees[] = new int[numCourses];

        for (int i = 0; i < prerequisites.length; i++) {
            // cal course in degree 指定课程的入度
            degrees[prerequisites[i][0]]++;
        }
        ArrayDeque<Integer> deque = new ArrayDeque();

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < degrees.length; i++) {
            // 当前课程入度为0
            if (degrees[i] == 0) {
                deque.add(i);
            }
        }
        while (!deque.isEmpty()) {
            int course = deque.poll();
            res.add(course);
            // 所有条件为 course 的 入度 -1
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] != course) {
                    continue;
                }
                // 寻找满足条件的课程 入度为0 说明约束消失
                if (--degrees[prerequisites[i][0]] == 0) {
                    deque.offer(prerequisites[i][0]);
                }
            }
        }

        int results[] = new int[numCourses];
        if (res.size() == numCourses) {
            results = res.stream().mapToInt(Integer::intValue).toArray();
//            for (int i = 0; i <res.size() ; i++) {
//                results[i] = res.get(i);
//            }
            System.out.println();
            return results;
        } else {
            return new int[0];
        }
    }

    public int maxProduct2(int[] nums) {

        int max = Integer.MIN_VALUE, imax = 1, imin = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                int temp = imax;
                imax = imin;
                imin = temp;
            }
            // 当前最小值 与之比较
            imax = Math.max(imax * nums[i], nums[i]);
            imin = Math.min(imin * nums[i], nums[i]);
            max = Math.max(imax, max);
        }

        return max;

    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        int preL = preorder.length - 1;
        int inL = inorder.length - 1;
        TreeNode node = buildTreeNode(preorder, 0, preL, inorder, 0, inL);
        return node;
    }

    public TreeNode buildTreeNode(int[] preorder, int pl, int pr, int[] inorder, int inl, int inr) {

        if (pl > pr || inl > inr) {
            return null;
        }
        int root = preorder[pl];
        TreeNode node = new TreeNode(root);
        int leftLength = 0;
        while (inorder[inl + leftLength] != root) {
            leftLength++;
        }

        node.left = buildTreeNode(preorder, pl + 1, pl + leftLength, inorder, inl, inl + leftLength - 1);
        node.right = buildTreeNode(preorder, pl + leftLength + 1, pr, inorder, inl + leftLength + 1, inr);
        return node;
    }

    public String reverseWords(String s) {

        String[] words = s.split("\\s");
        System.out.println(Arrays.toString(words));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = word.length() - 1; j >= 0; j--) {
                builder.append(word.charAt(j));
            }
            if (i != words.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    public ListNode removeDuplicateNodes(ListNode head) {
        HashSet<Integer> set = new HashSet<>();
        ListNode p = head;
        while (p != null && p.next != null) {
            set.add(p.val);
            if (set.contains(p.next.val)) {
                p.next = p.next.next;
            } else {
                p = p.next;
            }
        }
        return head;
    }

    public int[] intersection(int[] nums1, int[] nums2) {

        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set1.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            set2.add(nums2[i]);
        }

        set1.retainAll(set2);
        int res[] = new int[set1.size()];
        int index = 0;
        for (Integer num : set1) {
            res[index++] = (int) num;
        }
        return res;
    }

    public int findUnsortedSubarray(int[] nums) {

        int temp[] = Arrays.copyOf(nums, nums.length);
        Arrays.sort(temp);
        int left = 0, right = nums.length - 1;

        while (left < right && nums[left] == temp[left]) {
            left++;
        }
        while (left < right && nums[right] == temp[right]) {
            right--;
        }
        if (nums[left] != temp[left] && nums[right] != temp[right]) {
            return right - left + 1;
        }

        return right - left;
    }

    public boolean exist(char[][] board, String word) {
        if (board.length == 0 || word.length() == 0) {
            return false;
        }
        boolean[][] visited = new boolean[board.length][board[0].length];
        if (word.length() > board.length * board[0].length) {
            return false;
        }
        char[] chars = word.toCharArray();
        char[] list = new char[chars.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (existDfs(board, visited, chars, i, j, list, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    int[][] offset = new int[][]{new int[]{0, 1}, new int[]{0, -1}, new int[]{-1, 0}, new int[]{1, 0}};

    public boolean existDfs(char[][] board, boolean[][] visited, char[] chars, int row, int column, char[] list, int index) {

        if (row < 0 || row >= board.length || column < 0 || column >= board[0].length || visited[row][column]) {
            return false;
        }
        visited[row][column] = true;

        list[index] = board[row][column];

        if (chars[index] != list[index]) {
            visited[row][column] = false;
            list[index] = '#';

            return false;
        }

        if (index == chars.length - 1) {
            return true;
        }

        for (int k = 0; k < 4; k++) {
            int newR = row + offset[k][0];
            int newC = column + offset[k][1];
            if (existDfs(board, visited, chars, newR, newC, list, index + 1)) {
                return true;
            }
        }

        list[index] = '#';
        visited[row][column] = false;
        return false;
    }


    // 保留 第i 行的皇后在第几列
    int row[];
    int totalNQueens = 0;

    public int totalNQueens(int n) {
        row = new int[n];
        totalNQueensDfs(0, n);
        return totalNQueens;
    }

    public void totalNQueensDfs(int r, int n) {
        if (r == n) {
            totalNQueens++;
        }
        for (int i = 0; i < n; i++) {
            row[r] = i;
            if (check(r)) {
                totalNQueensDfs(r + 1, n);
            }
        }
    }

    private boolean check(int r) {
        for (int i = 0; i < r; i++) {
            if (row[i] == row[r] || Math.abs(r - i) == Math.abs(row[r] - row[i])) {
                return false;
            }
        }
        return true;
    }


    private int size;
    //        private int count;
    private int n;

    Stack<Integer> stack = new Stack();

    private void solve(int row, int ld, int rd) {
        if (row == size) {
            totalNQueens++;
            return;
        }
        int pos = size & (~(row | ld | rd));
        while (pos != 0) {
            int p = pos & (-pos);
            pos -= p; // pos &= pos - 1;
            stack.add(p);
            solve(row | p, (ld | p) << 1, (rd | p) >> 1);
            stack.pop();

        }
    }

    public int solveNQueens(int n) {
//            count = 0;
        size = (1 << n) - 1;
        this.n = n;
        solve(0, 0, 0);
        return totalNQueens;
    }
//
//    public TreeNode invertTree(TreeNode root) {
//        if (root==null){
//            return null;
//        }
//        TreeNode temp = root.left;
//        root.left = invertTree(root.right);
//        root.right = invertTree(temp);
//        return root;
//    }

    public int calculate(String s) {
        return (int) Math.pow(2, s.length());
    }

    List<List<Integer>> permuteUnique;

    public List<List<Integer>> permuteUnique(int[] nums) {
        permuteUnique = new ArrayList<>();
//        visitSet = new HashSet<>();
        permuteUniqueDfs(nums, new LinkedList<>());
        return permuteUnique;
    }

    public void permuteUniqueDfs(int[] nums, LinkedList<Integer> res) {

        if (res.size() == nums.length) {
            permuteUnique.add(new ArrayList<>(res));
            return;
        }
        HashSet set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i]) || nums[i] == Integer.MIN_VALUE) {
                continue;
            }
            set.add(nums[i]);
//            visitSet.add(i);
            res.addLast(nums[i]);
            nums[i] = Integer.MIN_VALUE;
            permuteUniqueDfs(nums, res);
            nums[i] = res.peekLast();
            res.pollLast();
        }
    }

    public int numWays(int n) {
        long dp[] = new long[n + 1];
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return 1;
        }
        dp[0] = 1;
        dp[1] = dp[0];

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return (int) (dp[n] % 1000000007);
    }

    // [0,1,2,3,4,5,6,7,9]
    public int missingNumber(int[] nums) {

        int left = 0, right = nums.length;
        if (right == nums.length - 1) {

        }
        while (left <= right) {
            int mid = (left + right) >> 1;
            // 搜索左侧区间
            if (nums[mid] == mid) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    public int sumOfLeftLeaves(TreeNode root) {
        sum = 0;
        sumOfLeftLeavesDfs(root, sum);
        return sum;
    }

    public void sumOfLeftLeavesDfs(TreeNode root, int sum) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            if (root.left.left == null && root.left.right == null) {
                System.out.println(root.left.val);
                sum += root.left.val;
            }
        }
        System.out.println(root.left.val);

        sumOfLeftLeavesDfs(root.right, sum);
        System.out.println(root.left.val);

        sumOfLeftLeavesDfs(root.left, sum);
        System.out.println(root.left.val);
    }

    List<List<Integer>> subsets;

    public List<List<Integer>> subsets(int[] nums) {
        subsets = new ArrayList<>();

        subsetsDfs(nums, subsets, 0, new ArrayList<>());
        return subsets;
    }

    public void subsetsDfs(int[] nums, List<List<Integer>> subsets, int index, List<Integer> res) {
        subsets.add(new ArrayList<>(res));
        for (int i = index; i < nums.length; i++) {
            res.add(nums[i]);
            subsetsDfs(nums, subsets, i + 1, res);
            res.remove(res.size() - 1);
        }
    }

    public int paintingPlan(int n, int k) {

        int dp[][] = new int[n + 1][k + 1];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            }
        }
        return dp[n][k];
    }

    public int breakfastNumber(int[] staple, int[] drinks, int x) {

        int[] res = new int[x + 1];
        for (int i = 0; i < staple.length; i++) {
            if (staple[i] < x) {
                res[staple[i]] += 1;
            }
        }

        for (int i = 2; i < x; i++) {
            res[i] += res[i - 1];
        }
        int sum = 0;
        for (int i = 0; i < drinks.length; i++) {
            int left = x - drinks[i];
            if (left > 0) {
                sum += res[left];
            }
        }
        return sum;
    }

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode node = new TreeNode(0);
        node.val = t1.val + t2.val;
        node.left = mergeTrees(t1.left, t2.left);
        node.right = mergeTrees(t1.right, t2.right);
        return node;
    }

    public int numWays = 0;
    int[] numMap;

    public int numWays(int n, int[][] relation, int k) {
        numMap = new int[n];
        for (int i = 0; i < relation.length; i++) {
            int[] item = relation[i];
            numMap[item[0]] = 1 << item[1] | numMap[item[0]];
        }
        numWaysDfs(n, k, new ArrayList<>(), 0);

        return numWays;
    }

    public void numWaysDfs(int n, int k, List<Integer> res, int index) {
        if (res.size() == k && res.get(k - 1) == n - 1) {
            numWays++;
            return;
        }
        if (res.size() >= k) {
            return;
        }
        for (int i = 0; i < n; i++) {
            if ((numMap[index] & (1 << i)) >= 1) {

                numWaysDfs(n, k, res, i);

            }
        }
    }

    public int[] findMode(TreeNode root) {
        ArrayList res = new ArrayList();
        findMode(root, res);
        int[] arr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            arr[i] = (int) res.get(i);
        }
        return arr;
    }

    int maxTime = 0;
    int curTime = 0;
    TreeNode preNode;

    public void findMode(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        findMode(root.left, res);
        int val = root.val;
        if (preNode != null && val == preNode.val) {
            curTime++;
        } else {
            curTime += 1;
        }
        if (curTime == maxTime) {
            res.add(val);
        }
        if (curTime > maxTime) {
            maxTime = curTime;
            res.clear();
            res.add(val);
        }
        preNode = root;
        System.out.println(preNode.val);
        findMode(root.right, res);

    }

//    public int[] maxSlidingWindow(int[] nums, int k) {
//
//        int len = nums.length;
//        Deque<Integer> deque = new ArrayDeque<>();
//        int max = 0;
//        for (int i = 1; i < k; i++) {
//            if (nums[i] > nums[max]) {
//                max = i;
//            }
//        }
//        int[] res = new int[len - k];
//        int index = 0;
//        deque.offer(max);
//        res[index++] = nums[max];
//        for (int i = k; i < len; i++) {
//            int val = nums[i];
//            if (deque.isEmpty()) {
//                deque.offer(i);
//            }
//
//            if (i - deque.getFirst() >= k) {
//                deque.pollFirst();
//            }
//
//            while (!deque.isEmpty() && nums[deque.getLast()] < val) {
//                deque.poll();
//            }
//            deque.offer(i);
//            System.out.println(deque);
//            res[index++] = nums[deque.getFirst()];
//        }
//        return res;
//    }


    public TreeNode buildTree1(int[] inorder, int[] postorder) {

        if (inorder.length == 0) {
            return null;
        }

        int rootVal = postorder[postorder.length - 1];
        TreeNode node = new TreeNode(rootVal);

        int mid = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                mid = i;
            }
        }

        int[] leftIn = mid > 0 ? new int[mid] : new int[0];
        int[] rightIn = mid < inorder.length - 1 ? new int[inorder.length - mid - 1] : new int[0];
        System.arraycopy(inorder, 0, leftIn, 0, mid);
        System.arraycopy(inorder, mid + 1, rightIn, 0, inorder.length - mid - 1);

        int[] leftPost = mid > 0 ? new int[mid] : new int[0];
        int[] rightPost = mid < inorder.length - 1 ? new int[inorder.length - mid - 1] : new int[0];
        System.arraycopy(postorder, 0, leftPost, 0, mid);
        System.arraycopy(postorder, mid, rightPost, 0, postorder.length - mid - 1);

        node.left = buildTree(leftIn, leftPost);
        node.right = buildTree(rightIn, rightPost);
        return node;
    }

    List<List<Integer>> pathSum;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        pathSum = new ArrayList<>();
        pathSumDfs(root,sum,new ArrayList<>(),0);
        return pathSum;
    }

    public void pathSumDfs(TreeNode root, int sum,List<Integer>res,int curSum) {
        if (root==null){
            return;
        }

        if (curSum>sum){
            return;
        }

        curSum+=root.val;
        res.add(root.val);
        if (curSum==sum&&root.left==null&&root.right==null){
            pathSum.add(new ArrayList<>(res));

        }
        pathSumDfs(root.left,sum,res,curSum);
        pathSumDfs(root.right,sum,res,curSum);
        curSum-=root.val;;
        res.remove(res.size()-1);
    }


}