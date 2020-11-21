import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;
import com.sun.tools.javac.util.Pair;
import org.omg.CORBA.MARSHAL;

import java.lang.reflect.Array;
import java.util.*;

/**
 * hash 是扰动函数 为了减少碰撞
 * 高位 与 地位 进行异或 混合减少30%碰撞 本身就是hashcode
 * index 是通过 hash 函数 与 size-1 取模来确定位置 顺便减少数据规模
 */
class Solution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public String fractionToDecimal(int numerator, int denominator) {
        if (denominator == 0) {
            return "0";
        }
        if (numerator == 0) {
            return "0";
        }
        boolean negative = (numerator ^ denominator) < 0;

        long absNum = Math.abs(Long.valueOf(numerator));
        long absDen = Math.abs(Long.valueOf(denominator));

        StringBuilder builder = new StringBuilder();
        long res = absNum / absDen;
        builder.append(res);
        if (absNum % absDen == 0) {
            return negative ? "-" + builder.toString() : builder.toString();
        }
        builder.append(".");
        long remainder = absNum % absDen;
        HashMap<Long, Integer> hashMap = new HashMap();

        while (remainder != 0) {
            if (hashMap.containsKey(remainder)) {
                builder.insert(hashMap.get(remainder), "(");
                builder.append(")");
                break;
            }

            long result = absNum % absDen;
            if (result == 0) {
                break;
            }

            hashMap.put(remainder, builder.length());
            remainder *= 10;
            builder.append(String.valueOf(remainder / absDen));
            remainder %= absDen;
        }

        return negative ? "-" + builder.toString() : builder.toString();
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 4, 2, 3, 67, 2332, 767, 332, 11, 322, 43};
//        new Solution().insertSort(nums);
//        solve(4, new int[]{1, 2, 3});
//
//        Pair<Integer, Integer>[] pairs = new Pair[5];
//        pairs[0] = new Pair<>(5, 1);
//        pairs[1] = new Pair<>(4, 2);
//        pairs[2] = new Pair<>(3, 3);
//        pairs[3] = new Pair<>(2, 4);
//        pairs[4] = new Pair<>(1, 5);
//        int res = new Solution().maxValue(pairs, 10);
//        System.out.println(res);
//        System.out.println(new Solution().longestCommonSubsequence("abce", "abed"));
        System.out.println(manacher("abba"));
    }

    public int maxSubArray(int[] nums) {

        int dp[] = new int[nums.length];
        dp[0] = nums[0];
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    // 贪心 + 二分来 求最长上升子序列
    public int lengthOfLIS(int[] nums) {
        // 寻找第一个大于 poker 的元素
        int[] pile = new int[nums.length];
        int lastHead = 0;
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];
            int left = 0;
            int right = lastHead;
            while (left < right) {
                int mid = (left + right) >>> 1;
                /**
                 * 寻找第一个可以替换的元素 大于或者等于当前元素
                 */
                if (poker <= pile[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            if (left == lastHead) {
                lastHead++;
            }
            pile[left] = poker;
        }

        return lastHead;
    }

    TreeNode parent;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        parent = root;
        helper(root, p, q);
        return parent == null ? root : parent;
    }

    public int helper(TreeNode root, TreeNode p, TreeNode q) {
        int res = 0;
        if (root == p || root == q) {
            res++;
        }
        int left = 0;
        if (root.left != null) {
            left = helper(root.left, p, q);
        }

        int right = 0;
        if (root.right != null) {
            right = helper(root.right, p, q);
        }
        if (res + left + right > 1) {
            parent = root;
        }
        // 是否包含 ，不能返回 大于 1 的值
        return res + left + right > 0 ? 1 : 0;
    }


    // 利用回溯来解决问题
    String origin;

    public List<String> restoreIpAddresses1(String s) {
        if (s == null) {
            return new ArrayList<>();
        }
        // 向前走一步 剩余的数量小于<3*step 有解
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        origin = s;
        backTrack(0, 0, list, builder);
        return list;
    }

    public void backTrack(int start, int time, List<String> list, StringBuilder builder) {
        if (time > 4) {
            return;
        }
        if (time == 4 && start == origin.length()) {
            list.add(builder.substring(1));
        }

        for (int i = start; i < origin.length() && i < start + 3; i++) {
            String temp = origin.substring(start, i + 1);
            if (temp.length() > 1 && temp.charAt(0) == '0') {
                return;
            }
            if (i - start == 2) {
                int sum = (origin.charAt(start) - '0') * 100 + (origin.charAt(start + 1) - '0') * 10 + (origin.charAt(start + 2) - '0');
                if (sum > 255) {
                    return;
                }
            }
            backTrack(i + 1, time + 1, list, builder.append(".").append(temp));
            builder.delete(builder.lastIndexOf("."), builder.length());
        }
    }


    public String reverseWords(String s) {

        if (s == null) {
            return " ";
        }
        if (s.trim().isEmpty()) {
            return s;
        }
        String[] strs = s.split(" ");
        Stack<String> stringStack = new Stack<>();
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].trim().isEmpty()) {
                stringStack.push(strs[i]);
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!stringStack.isEmpty()) {
            builder.append(stringStack.pop()).append(" ");
        }
        builder.delete(builder.lastIndexOf(" "), builder.length());
        return builder.toString();
    }


    public int minCostClimbingStairs(int[] cost) {

        // 定义最大值 dp
        int dp[] = new int[cost.length];
        if (cost.length == 0) {
            return 0;
        } else if (cost.length == 1) {
            return cost[0];
        } else if (cost.length == 2) {
            return Math.max(cost[0], cost[1]);
        }
        dp[0] = cost[0];
        dp[1] = Math.max(cost[0], cost[1]);

        for (int i = 2; i < cost.length; i++) {
//            dp[i] = dp[i-1]>dp[i-2]?;
        }
        return dp[cost.length - 1];
    }

    public int lengthOfLIS2(int[] nums) {
        // dp[i] 为第 i 个的最大值
        /**
         * if(num[i]>num[i]){
         * dp[i] = max(dp[i],d[j]+1)
         */
        if (nums.length == 0) {
            return 0;
        }
        int dp[] = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 更新最大值
                    max = Math.max(max, dp[i]);
                }
            }
        }

        return max;

    }

    // 删除数组重复项

    /**
     * 双指针 执行过程
     * 1 快指针探路 寻找第一个不等于 满指针的元素
     * 2 慢指针前移一步/慢的是已经排序的好的，
     * 3 慢指针赋值
     * 4 快指针继续移动
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return 1;
        }
        int[] temp = nums.clone();
        int p = 0;
        int q = 1;
        while (q < temp.length) {
            if (nums[p] != nums[q]) {
                nums[p] = nums[q];
            }
            q++;
        }
        return p + 1;
    }

    //旋转矩阵
    public void rotate(int[][] matrix) {
        int n = matrix.length - 1;

        for (int i = 0; i <= (n + 1) / 2; i++) {
            for (int j = 0; j <= n - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j][i];
                matrix[n - j][i] = matrix[n - i][n - j];
                matrix[n - i][n - j] = matrix[j][n - i];
                matrix[j][n - i] = temp;
            }
        }
    }

    // return 只能return 当前方法
    public List<Integer> preorderTraversal(TreeNode root) {

        List list = new ArrayList();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack();
        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return list;
    }

    public void moveZeroes(int[] nums) {

        int p = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[p] = nums[i];
                p++;
            }
        }

        for (int i = p; i < nums.length; i++) {
            nums[i] = 0;
        }

    }

    public boolean increasingTriplet(int[] nums) {

        int min = Integer.MAX_VALUE;
        int max = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            } else if (nums[i] > max) {
                max = nums[i];
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * coin change
     * solution 1 dp[i] = min dp[i-coin[i]] +1
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {

        int[] dp = new int[amount + 1];
//        Arrays.sort(coins);
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]]);
                }
            }
            if (dp[i] != Integer.MAX_VALUE) {
                dp[i]++;
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        // 不相交就是在前后左右
        return (!(rec1[0] >= rec2[0] || rec1[2] >= rec2[0] || rec1[1] >= rec2[3] || rec1[3] <= rec2[1]));
    }

    public int subarraySum(int[] nums, int k) {
        int sum = 0;
        HashMap map = new HashMap();
        int num = 0;
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k || map.containsKey(sum - k)) {
                num++;
            }
            map.put(sum, i);
        }
        return num;
    }

    public int longestPalindrome(String s) {

        char[] chars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            map.put(chars[i], map.getOrDefault(chars[i], 0) + 1);
        }

        ArrayList values = (ArrayList) map.values();
        int sum = 0;

        for (int num : map.values()) {
            sum += (num >> 1 << 1);
            if ((sum & 1) == 0 && (num & 1) == 1) {
                sum += 1;
            }
        }

        return sum;
    }

    public String addBinary(String a, String b) {
        char[] charA = a.toCharArray();
        char[] charB = b.toCharArray();

        int lengthA = charA.length - 1;
        int lengthB = charB.length - 1;

        int max = Math.max(lengthA, lengthB);
        StringBuilder builder = new StringBuilder();

        int carry = 0;
        while (max >= 0) {
            int numa = 0, numb = 0;
            if (lengthA >= 0) {
                numa = charA[lengthA] - '0';
                lengthA--;
            }
            if (lengthB >= 0) {
                numb = charB[lengthB] - '0';
                lengthB--;
            }
            int sum = numa + numb + carry;
            int result = sum % 2;
            carry = sum / 2;
            builder.append(result);
            max--;
        }
        if (carry > 0) {
            builder.append(carry);
        }
        return builder.reverse().toString();
    }

    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        int res[] = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }

    public boolean canMeasureWater(int x, int y, int z) {
        if (z == 0) {
            return true;
        }
        if (x + y < z) {
            return false;
        }

        if ((x != 0 && z == x) || (y != 0 && z == y) || ((x + y) != 0 && z == (x + y)) || ((x - y != 0) && z == (Math.abs(x - y)))) {
            return true;
        }

        if (x > y && y != 0) {
            int nextx = x % y;
            if (z - x == nextx || z - y == nextx) {
                return true;
            }
            return canMeasureWater(nextx, y, z);
        } else if (x < y && x != 0) {
            int nexty = y % x;
            if (z - x == nexty || z - y == nexty) {
                return true;
            }
            return canMeasureWater(x, nexty, z);
        }
        return false;
    }

    public int minIncrementForUnique(int[] A) {
        HashMap map = new HashMap<>();
        Arrays.sort(A);
        int count = 0;
        if (A.length < 1) {
            return 0;
        }
        int max = -1;
        for (int i = 0; i < A.length; i++) {
            if (A[i] <= max) {
                int ai = max + 1;
                count += (ai - A[i]);
                A[i] = ai;
            }
            max = Math.max(max, A[i]);

        }

        return count++;
    }

    ArrayList res = new ArrayList();

    public void getNum(Stack<Integer> stack) {

        res.add(stack.pop());
        getNum(stack);
    }

    /**
     * 13452
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            int val = nums2[i];
            while (!stack.isEmpty() && stack.peek() <= val) {
                stack.pop();
            }
            if (!stack.isEmpty() && stack.peek() > val) {
                map.put(val, stack.peek());
            } else {
                map.put(val, -1);
            }
            stack.push(val);
        }

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }

    ArrayList<List<Integer>> lists;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        lists = new ArrayList<>();

        Arrays.sort(candidates);

        combinationSum2Bfs(candidates, 0, 0, new LinkedList<>(), target);
        return lists;
    }

    public void combinationSum2Bfs(int[] candicates, int start, int sum, LinkedList<Integer> res, int target) {

        if (sum == target) {
            lists.add(new ArrayList<>(res));
            return;
        }
        for (int i = start; i < candicates.length; i++) {
            if (candicates[i] > target) {
                return;
            }
            if (i > start && candicates[i] == candicates[i - 1]) {
                continue;
            }
            int cur = candicates[i];
            if (cur + sum <= target) {
                res.addLast(cur);
                combinationSum2Bfs(candicates, ++i, cur + sum, res, target);
                res.removeLast();
            } else {
                return;
            }
        }
    }

    public List<String> topKFrequent(String[] words, int k) {

        HashMap<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            countMap.put(words[i], countMap.getOrDefault(words[i], 0) + 1);
        }

        List<String> candidates = new ArrayList(countMap.keySet());

        Collections.sort(candidates, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return countMap.get(o1).equals(countMap.get(o2)) ? o1.compareTo(o2) : countMap.get(o2) - countMap.get(o1);
            }
        });
        return candidates.subList(0, k);
    }

    public String mostCommonWord(String paragraph, String[] banned) {
        paragraph.toLowerCase();//!?',;.
        paragraph = paragraph + ".";
        int max = 0;
        String res = "";
        HashSet<String> set = new HashSet(Arrays.asList(banned));
        HashMap<String, Integer> keys = new HashMap();
        char[] chars = paragraph.toLowerCase().toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char item = chars[i];
            if (Character.isLetterOrDigit(item)) {
                builder.append(item);
            } else if (builder.length() > 0) {
                String word = builder.toString();
                if (!set.contains(word)) {
                    int count = keys.getOrDefault(word, 0) + 1;
                    if (count > max) {
                        res = word;
                        max = count;
                    }
                    keys.put(word, count);
                }
                builder = new StringBuilder();
            }
        }
        return res;
    }

    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });

        PriorityQueue<Integer> meet = new PriorityQueue();
        for (int i = 0; i < intervals.length; i++) {
            int startTime = intervals[i][0];
            if (meet.size() > 0 && startTime >= meet.peek()) {
                meet.poll();
            }
            meet.offer(intervals[i][1]);
        }

        return meet.size();
    }

    public int superEggDrop(int K, int N) {

        // 计算 m 步 最多可以覆盖多少楼层
        int x = 1;
        while (superEggDropHelp(K, x) <= N) {
            x++;
        }

        int dp[][] = new int[K + 1][N + 1];
        int m = 0;
        while (dp[K][m] < N) {
            // 鸡蛋为k 的时候 能走的最大步数
            m++;
            for (int k = 0; k <= K; k++) {
                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
            }
        }
        return m;

//        return x;

    }


    public int superEggDropHelp(int k, int m) {
        if (k == 1 || m == 1) {
            return m + 1;
        }

        return superEggDropHelp(k - 1, m - 1) + superEggDropHelp(k, m - 1);
    }

//    public int subarraySum(int[] nums, int k) {
//        HashMap <Integer,Integer>preMap = new HashMap();
//
//        int sum = 0;
//        // base case
//        preMap.put(0,1);
//        int num = 0;
//        for (int i = 0; i <nums.length ; i++) {
//            sum+=nums[i];
//            if (preMap.containsKey(sum-k)){
//                // 存在多少个这样/sum -k 的前缀 那么就存在几个这样的区间
//                num+=preMap.getOrDefault(sum,0);
//            }
//            preMap.put(sum,preMap.getOrDefault(sum,0)+1);
//        }
//
//        return num;
//    }

    //去除无用括号
    public String minRemoveToMakeValid(String s) {
        char[] chars = s.toCharArray();
        Stack<int[]> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            if (!stack.isEmpty() && chars[i] == ')' && stack.peek()[0] == '(') {
                stack.pop();
            } else if (chars[i] == '(' || chars[i] == ')') {
                stack.push(new int[]{chars[i], i});
            }
        }

        HashMap indexsMap = new HashMap();
        if (stack.isEmpty()) {
            return s;
        }
        while (!stack.isEmpty()) {
            indexsMap.put(stack.pop()[1], 1);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (!indexsMap.containsKey(i)) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    public int[][] kClosest(int[][] points, int K) {

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] * o1[0] + o1[1] * o1[1] - o2[0] * o2[0] - o2[1] * o2[1];
            }
        });

        int[][] ks = new int[K][2];
        System.arraycopy(points, 0, ks, 0, K);
        return ks;
    }

    //121 回文数
    public boolean isPalindrome(int x) {
        ArrayList<Integer> res = new ArrayList<>();
        int temp = x;
        while (temp > 0) {
            int tail = x % 10;
            temp = temp / 10;
            res.add(tail);
        }
        for (int i = 0; i < res.size() / 2; i++) {
            if (res.get(i).intValue() != res.get(res.size() - 1 - i).intValue()) {
                return false;
            }
        }
        return true;
    }

    public int maximalSquare(char[][] matrix) {

        int dp[][] = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') {
                    if (i > 0 && j > 0) {
                        dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                    } else {
                        dp[i][j] = 1;
                    }
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max * max;
    }

    public double myPow(double x, int n) {
        // 递归终止条件
        if (n == 1) {
            return x;
        }
        if (n == -1) {
            return 1.0 / x;
        }
        if (n == 0) {
            return 1;
        }
        double half = myPow(x, n / 2);
        double rest = myPow(x, n % 2);

        return half * half * rest;
    }

    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int index = 0;
        int nums[] = new int[m * n];
        boolean flag = true;
        for (int i = 0; i < m + n + 1; i++) {
            int pm = flag ? m : n;
            int pn = flag ? n : m;
            int x = i > pm ? i : pm - 1;
            int y = i - x;
            while (x >= 0 && y < pn) {
                nums[index++] = flag ? matrix[x][y] : matrix[y][x];
                x--;
                y++;
            }
        }
        return nums;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length < 1) {
            return new ArrayList<>();
        }
        int[][] offset = new int[][]{new int[]{0, 1}, new int[]{1, 0}, new int[]{0, -1}, new int[]{-1, 0}};
        boolean visit[][] = new boolean[matrix.length][matrix[0].length];
        int x = 0, y = 0;
        ArrayList res = new ArrayList();
        int index = 0;
        while (!visit[x][y]) {
            res.add(matrix[x][y]);
            visit[x][y] = true;
            x = x + offset[index % 4][0];
            y += offset[index % 4][1];
            int tempX = x + offset[index % 4][0];
            int tempY = y + offset[index % 4][1];

            if (x >= 0 && y >= 0 && x < matrix.length && y > matrix[0].length && !visit[x][y]) {
                x = tempX;
                y = tempY;
            } else {
                index++;
                x = x + offset[index % 4][0];
                y += offset[index % 4][1];

            }
            System.out.println("X" + x);
            System.out.println("Y" + y);
        }

        return res;
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            ArrayList<Integer> levels = new ArrayList();
            if (i != 0) {
                List<Integer> last = res.get(i - 1);
                levels.add(1);
                for (int j = 1; j < last.size(); j++) {
                    levels.add(last.get(j) + last.get(j - 1));
                }
            }
            levels.add(1);
            res.add(levels);
        }
        return res;
    }

    /**
     * @param a
     * @param b 输入: a = "11", b = "1"
     *          输出: "100"
     * @return
     */
    public String addBinary1(String a, String b) {

        char[] charA = a.toCharArray();
        char[] charB = b.toCharArray();
        if (charA.length < charB.length) {
            char[] temp = charA;
            charA = charB;
            charB = temp;
        }
        int index = charA.length - 1;
        int indexB = charB.length - 1;
        int carry = 0;
        StringBuilder builder = new StringBuilder();
        while (index >= 0) {
            char ca = charA[index--];
            char cb = (indexB >= 0) ? charB[indexB--] : '0';
            int current = (ca - '0' + cb - '0' + carry) % 2;
            carry = (ca - '0' + cb - '0' + carry) / 2;
            builder.append(current);
        }
        if (carry > 0) {
            builder.append(carry);
        }
        return builder.reverse().toString();
    }

    // 123456
    public int subarraySum2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        map.put(0, 1);
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (map.containsKey(sum - k)) {
                count += map.getOrDefault(sum - k, 0);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> levelOrders = new ArrayList<>();
        if (root == null) {
            return levelOrders;
        }
        levelOrder(root, levelOrders, 0);
        return levelOrders;
    }

    public void levelOrder(TreeNode root, List<List<Integer>> lists, int level) {
        if (root == null) {
            return;
        }

        if (lists.size() == level) {
            lists.add(new ArrayList<>());
        }
        List levels = lists.get(level);
        levels.add(root.val);

        levelOrder(root.left, lists, level + 1);
        levelOrder(root.right, lists, level + 1);
    }


    public void insertSort(int nums[]) {
        for (int i = 1; i < nums.length; i++) {

            // 选取插入元素
            int pivot = nums[i];
            // 定义插入区间
            int j = i - 1;
            // 如果j 大于当前元素 那么便不断后移元素
            while (j >= 0 && nums[j] > pivot) {
                nums[j + 1] = nums[j];
                j--;
            }
            // 终止条件 j 是不需要插入的 j+1 是空 槽位
            nums[j + 1] = pivot;

        }
        System.out.println(Arrays.toString(nums));
    }

    public int singleNumber(int[] nums) {

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i];
        }

        return res;

    }

//    public int subarraySum(int[] nums, int k) {
//
//        int sum = 0;
//        HashMap<Integer,Integer> map = new HashMap();
//        map.put(0,1);
//        int res = 0;
//        for (int i = 0; i <nums.length ; i++) {
//            sum+=nums[i];
//            // 查询的是前缀和的个数 所以不能包含自身
//            // 是当前元素sum - 之前的前缀的
//            if (map.containsKey(sum-k)){
//                k +=map.get(sum-k);
//            }
//            map.put(sum,map.getOrDefault(sum,0)+1);
//
//        }
//
//        return res;
//    }


    // 解题思路类似于桶排序，每个 item 放在指定项目上
    // 第一个数字是1 即 num i = num[i] - 1; 当前数字 放置在 item+1 index
    public int firstMissingPositive(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 1;
        }
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
                // 将其 item 放到指定 index ，然后循环判断当前元素是否还需要继续计算
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return 1;
    }


    public void swap(int[] nums, int p, int q) {
        if (p == q) {
            return;
        }

        int temp = nums[p];
        nums[p] = nums[q];
        nums[q] = temp;
    }


    public int largestRectangleArea(int[] heights) {

        int[] temp = new int[heights.length + 2];
        System.arraycopy(heights, 0, temp, 1, heights.length);

        Stack<Integer> stack = new Stack<>();
        int area = 0;
        for (int i = 0; i < temp.length; i++) {
            // maintain a stack
            // 大于当前元素的都需要出栈
            while (!stack.isEmpty() && temp[stack.peek()] > temp[i]) {
                int height = temp[stack.pop()];
                area = Math.max(area, (i - stack.peek() + 1) * height);
            }
            stack.push(i);
        }
        return area;
    }

    public int lengthOfLongestSubstring(String s) {

        int ans = 0;
        int start = 0;
//        int end = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                start = Math.max(start, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            ans = Math.max(ans, i - start + 1);
        }
        return ans;
    }

//    public int maxProfit(int[] prices) {
//
//        // 定义 dp[i][j] 为当天买入或者卖出的状态
//        int dp[][] = new int[prices.length][2];
//
//        for (int i = 0; i <prices.length ; i++) {
//
//            // 买入
//            dp[i][0] =  dp[i-2]
//
//
//        }
//    }

    public int findLUSlength(String a, String b) {

        if (a.equals(b)) {
            return -1;
        }
        return Math.max(a.length(), b.length());
    }

    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> list = new ArrayList<>();
        if (n == 0) {
            return list;
        }
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> list = new ArrayList<>();
        if (start > end) {
            list.add(null);
            return list;
        }

        if (start == end) {
            TreeNode node = new TreeNode(start);
            list.add(node);
            return list;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> lefts = generateTrees(start, i - 1);
            List<TreeNode> rights = generateTrees(i + 1, end);

            for (int j = 0; j < lefts.size(); j++) {
                for (int k = 0; k < rights.size(); k++) {
                    TreeNode node = new TreeNode(i);
                    node.left = lefts.get(j);
                    node.right = rights.get(k);
                    list.add(node);
                }
            }
        }

        return list;
    }

    public boolean isInterleave(String s1, String s2, String s3) {

        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        // 填dp 表 定义 dp[i][j] 为 s1 第i 个元素 与 s2 中第 j 能否到达 s3 第i+j
        boolean dp[][] = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= s1.length(); i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        for (int i = 1; i <= s2.length(); i++) {
            dp[0][i] = dp[0][i - 1] && s2.charAt(i - 1) == s3.charAt(i - 1);
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                // i,j 可能有状态 dp[i-1][j] 转移过来那么 转移到 i 需要比较 s[i-1]/ 第i 个元素 与 s3 的 i+j-1 元素比较
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public boolean isMatch(String s, String p) {

        // 定义状态 p >> s
        boolean dp[][] = new boolean[p.length() + 1][s.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= p.length(); i++) {
            if (i > 1 && p.charAt(i - 1) == '*') {
                dp[i][0] = dp[i - 2][0];
            }
        }

        for (int i = 1; i <= p.length(); i++) {
            for (int j = 1; j <= s.length(); j++) {
                if (p.charAt(i - 1) == s.charAt(j - 1) || p.charAt(i - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                if (p.charAt(i - 1) == '*') {
                    if (p.charAt(i - 2) == s.charAt(j - 1) || p.charAt(i - 2) == '.') {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1] || dp[i - 2][j];
                    } else {
                        dp[i][j] = dp[i - 2][j];
                    }
                }
            }
        }
        return dp[p.length()][s.length()];
    }

    /**
     * 思路推导过程
     * 首先找到最后戳破气球 然后求左右最后戳破气球
     * 那么 便存在 求 dp[i][j] 区间最大值的的转移方程
     * 首先两个循环确定 左右区间边界
     * 然后一个循环寻找最后消除的气球的可以获得的最大值
     *
     * @param nums
     * @return
     */
    public int maxCoins(int[] nums) {

        int temp[] = new int[nums.length + 2];
        temp[0] = 1;
        temp[nums.length + 1] = 1;
        System.arraycopy(nums, 0, temp, 1, nums.length);
        int n = temp.length;
        int dp[][] = new int[n][n];
        dp[0][0] = 0;
        // 划定区间左边界
        for (int i = nums.length; i >= 0; i--) {
            // 选取区间右边界
            for (int j = i + 1; j < n; j++) {
                // 最后戳的气球 气球在区间 (i,j) 之间
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + temp[k] * temp[i] * temp[j]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * @param numBottles
     * @param numExchange
     * @return
     */
    public int numWaterBottles(int numBottles, int numExchange) {
        return numWaterBottles(numBottles, numExchange, 0);
    }


    public int numWaterBottles(int numBottles, int numExchange, int left) {

        int extra = (numBottles + left) / numExchange;

        left = (numBottles + left) % numExchange;
        if (extra == 0) {
            return numBottles;
        }
        return numBottles + numWaterBottles(extra, numExchange, left);
    }

    public int[] twoSum(int[] numbers, int target) {

        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }
        return new int[]{-1, -1};
    }


    public List<TreeNode> getNodes(int left, int right) {
        List<TreeNode> list = new ArrayList<>();
        if (left < right) {
            list.add(null);
            return list;
        }
        if (left == right) {
            list.add(new TreeNode(left));
            return list;
        }

        for (int i = left; i <= right; i++) {

            List<TreeNode> leftTrees = getNodes(left, i - 1);
            List<TreeNode> rightTrees = getNodes(i + 1, right);

            for (int j = 0; j < leftTrees.size(); j++) {
                for (int k = 0; k < rightTrees.size(); k++) {
                    TreeNode node = new TreeNode(i);
                    node.left = leftTrees.get(j);
                    node.right = rightTrees.get(k);
                    list.add(node);
                }
            }

        }
        return list;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int length = nums.length;
        if (length < 4) {
            return res;
        }

        Arrays.sort(nums);
        for (int i = 0; i < length - 3; i++) {
            while (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }


                int l = j + 1;
                int h = length - 1;
                if (nums[i] + nums[j] + nums[l] + nums[h] > target) {
                    break;
                }
                if (nums[i] + nums[length - 1] + nums[length - 2] + nums[length - 3] < target) {
                    break;
                }
                while (l < h) {
                    int sum = nums[i] + nums[j] + nums[h] + nums[l];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[h]));
                        while (l < h && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < h && nums[h] == nums[h - 1]) {
                            h--;
                        }

                    } else if (sum > target) {
                        h--;
                    } else {
                        l++;
                    }
                }
            }

        }
        return res;
    }

    public int minArray(int[] numbers) {

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int mid = (left + right) >>> 1;
            if (numbers[mid] > numbers[right]) {
                left = mid + 1;
            } else if (numbers[mid] < numbers[right]) {
                right = mid;
            } else {
                right--;
            }
        }
        return numbers[left];
    }

    public int minArray2(int[] numbers) {

        int left = 0;
        int right = numbers.length;

        while (left < right) {
            int mid = (left + right) >>> 1;
            if (numbers[mid] > numbers[right - 1]) {
                left = mid + 1;
            } else if (numbers[mid] < numbers[right - 1]) {
                right = mid;
            } else {
                right--;
            }
        }
        return numbers[left];
    }

    public int minPathSum(int[][] grid) {

        if (grid.length == 0) {
            return 0;
        }
        int[][] dp = new int[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + grid[i][j];
                }

            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

//    public int search(int[] nums, int target) {
//
//        int left = 0;
//        int right = nums.length;
//
//        while (left < right) {
//            int mid = (left + right) >>> 1;
//            if (nums[mid] >= target) {
//                right = mid;
//            } else {
//                left = mid + 1;
//            }
//        }
//        return (left < nums.length && nums[left] == target) ? left : -1;
//    }

//    public int guessNumber(int n) {
//
//        int left = 1;
//        int right = n;
//
//        while (left<=right){
//            int mid = (left+right)>>>1;
//            int res = guess(mid);
//            if (res==0){
//                return mid;
//            }else if(mid>0){
//                left = mid+1;
//            }else {
//                right = mid-1;
//            }
//        }
//        return -1;
//    }

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        int time = 0;

        LinkedList<String> linkedList = new LinkedList();
        restoreIpAddressesDfs(res, time, 0, s, linkedList);
        return res;
    }

    public void restoreIpAddressesDfs(List<String> res, int time, int index, String s, LinkedList<String> linkedList) {
        if (time == 4 && index == s.length()) {
            StringBuilder builder1 = new StringBuilder();

            for (int i = 0; i < linkedList.size(); i++) {
                builder1.append(linkedList.get(i)).append(".");
            }
            builder1.delete(builder1.length() - 1, builder1.length());
            res.add(builder1.toString());
            return;
        } else if (time >= 4) {
            return;
        }
        for (int i = index; i < index + 3 && i < s.length(); i++) {

            String code = s.substring(index, i + 1);
            if (code.length() > 1 && code.charAt(0) == '0') {
                return;
            }

            if (Integer.parseInt(code) > 255) {
                return;
            }
            linkedList.add(s.substring(index, i + 1));
            restoreIpAddressesDfs(res, time + 1, i + 1, s, linkedList);
            linkedList.removeLast();
        }
    }

//    public Node cloneGraph(Node node) {
//
//    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        flood(image, sr, sc, newColor, image[sr][sc]);
        return image;
    }

    public void flood(int[][] image, int x, int y, int newColor, int origin) {

        if (x < 0 || y < 0 || x >= image.length || y >= image[0].length) {
            return;
        }
        if (image[x][y] == newColor || image[x][y] != origin) {
            return;
        }
        image[x][y] = origin;
        flood(image, x + 1, y, newColor, origin);
        flood(image, x - 1, y, newColor, origin);
        flood(image, x, y + 1, newColor, origin);
        flood(image, x, y - 1, newColor, origin);
    }

    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length == 0) {
            return true;
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        for (int i = 0; i < intervals.length; i++) {
            priorityQueue.offer(intervals[i]);
        }
        int[] last = priorityQueue.poll();
        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll();

            if (cur[1] < last[1]) {
                return false;
            }
            last = cur;
        }
        return true;
    }

    boolean isBalanced = true;

    public boolean isBalanced(TreeNode root) {
        isBalancedHelper(root);
        return isBalanced;

    }

    public int isBalancedHelper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = isBalancedHelper(node.left);
        int right = isBalancedHelper(node.right);
        if (Math.abs(left - right) > 1) {
            isBalanced = false;
        }
        return Math.max(left, right) + 1;
    }

    public TreeNode sortedListToBST(ListNode head) {

        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        if (list.size() == 0) {
            return null;
        }
        return buildTreeNode(list, 0, list.size());

    }

    public TreeNode buildTreeNode(List<Integer> list, int start, int end) {
        if (start >= end) {
            return null;
        }

        TreeNode node = new TreeNode(list.get((end + start) >> 1));
        System.out.println(list.get((end + start) >> 1));
        node.left = buildTreeNode(list, start, ((end + start) >> 1) - 1);
        node.left = buildTreeNode(list, ((end + start) >> 1) + 1, end);
        return node;
    }


    public int rangeBitwiseAnd(int m, int n) {
        while (n > m) {
            n = n & (n - 1);
        }

        return n;
    }


    public boolean repeatedSubstringPattern(String s) {

        char[] chars = s.toCharArray();
        int sum = 0;
        for (char aChar : chars) {
            sum += aChar;
        }

        int item = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            item += chars[i];
            if (sum % item == 0) {
                String itemStr = s.substring(0, i + 1);
                StringBuilder builder = new StringBuilder(s);
                while (builder.length() != 0) {
                    if (builder.toString().startsWith(itemStr)) {
                        builder.delete(0, i + 1);
                    } else {
                        break;
                    }

                }

                if (builder.length() == 0) {
                    return true;
                }
            }

        }
        return false;
    }


    HashMap<Integer, Integer> visited = new HashMap();

    public List<List<Integer>> findSubsequences(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        findSubsequencesDfs(res, nums, 0, new ArrayList<>());

        return res;
    }

    public void findSubsequencesDfs(List<List<Integer>> res, int[] nums, int start, List<Integer> last) {

        for (int i = start; i < nums.length; i++) {
            if (start == 0) {
                visited.clear();
            }

            if (last.size() > 0 && visited.containsKey(last.get(0)) && i == visited.getOrDefault(nums[i], -1)) {
                return;
            }

            List<Integer> temp = new ArrayList<>(last);
            temp.add(nums[i]);
            if (temp.size() > 1) {
                res.add(temp);
            }
            visited.put(nums[i], i);
            if (i != nums.length - 1) {
                findSubsequencesDfs(res, nums, i + 1, temp);
            }
        }
    }

    public int coinChange2(int[] coins, int amount) {
        int dp[] = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        int[] path = new int[amount + 1];
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                if (dp[j - coins[i]] + 1 < dp[j]) {
                    path[j] = coins[i];
                    dp[j] = dp[j - coins[i]] + 1;
                }
            }
        }
        if (dp[amount] == amount + 1) {
            return -1;
        }
        int money = amount;
        while (money > 0) {
            System.out.println(path[money]);
            money -= path[money];
        }

        return dp[amount];
    }

    public int change(int amount, int[] coins) {

        int dp[][] = new int[amount + 1][amount + 1];
        dp[0][0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                for (int k = coins[i]; k <= amount; k++) {
                    /**
                     * 总金额为 k 次数为 j 的情况能得到的总数量
                     * 其由各种硬币转换而来
                     *
                     */
                    dp[k][j] += dp[k - coins[i]][j - 1];
                }
            }
        }

        int res = 0;
        /**
         * 最终计算总金钱 各个次数的总和就是结果
         */
        for (int i = 0; i <= 100; i++) {
            res += dp[amount][i];
        }

        return res;
    }

    public int change1(int amount, int[] coins) {

        int dp[] = new int[amount + 1];
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

    public static int solve(int target, int[] arr) {
        // dp[target] = dp[target-nums[i]]

        int dp[] = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i >= arr[j]) {
                    dp[i] += dp[i - arr[j]];
                }

            }
        }
        System.out.println(dp[target]);
        return dp[target];
    }

    public boolean judgeCircle(String moves) {

        char[] chars = moves.toCharArray();
        int x = 0;
        int y = 0;
        for (int i = 0; i < chars.length; i++) {

            switch (chars[i]) {
                case 'L':
                    x--;
                    break;
                case 'R':
                    x++;
                    break;

                case 'U':
                    y++;
                    break;

                case 'D':
                    y--;
                    break;

            }
        }
        return x == 0 && y == 0;
    }


    public boolean canPartition2(int[] nums) {

        int sum = 0;
        int len = nums.length;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        sum = sum >>> 1;
        if ((sum & 1) == 1) {
            return false;
        }
        boolean dp[] = new boolean[sum + 1];

        for (int i = 0; i < len; i++) {
            for (int j = sum; j >= 1; j--) {
                if (j - nums[i] >= 0) {
                    dp[j] = dp[j] || dp[j - nums[i]];
                }
            }
        }
        return dp[sum];
    }

    public int change3(int amount, int[] coins) {
        int type = coins.length;
        if (type == 0) {
            if (amount == 0) {
                return 1;
            }
            return 0;
        }
        //dp[i][j] 代表取 i 种硬币，状态转换成当前
        int dp[][] = new int[type][amount + 1];
        dp[0][0] = 1;

        for (int i = coins[0]; i <= amount; i += coins[0]) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < type; i++) {
            for (int j = 1; j <= amount; j++) {
                // 少一种硬币的组合 例如 4种 是从 3种转换而来
                dp[i][j] = dp[i - 1][j];
                if (j - coins[i] >= 0) {
                    // 不断的
                    dp[i][j] += dp[i][j - coins[i]];
                }
            }
        }
        return dp[type - 1][amount];
    }


    public int findTargetSumWays(int[] nums, int S) {

        int len = nums.length;
        int dp[][] = new int[len + 1][S + 2000];
        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] += 1;
        for (int i = 1; i < len; i++) {
            for (int j = -1000; j <= S; j++) {
                if (dp[i - 1][j + 1000] < 0) {
                    System.out.println(dp[i - 1][j + 1000]);
                }
                if (dp[i - 1][j + 1000] > 0) {
                    dp[i][j + nums[i] + 1000] += dp[i - 1][j + 1000];
                    dp[i][j - nums[i] + 1000] += dp[i - 1][j + 1000];
                }
            }
        }
        return dp[len][S];
    }

    public int findTargetSumWays2(int[] nums, int S) {

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (S > sum) {
            return 0;
        }
        int dp[][] = new int[nums.length + 1][2 * sum + 1];

//      把起点转移到中间位置
        dp[0][sum] = 1;

        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= 2 * sum; j++) {
                int left = Math.max(j - nums[i - 1], 0);
                int right = Math.min(j + nums[i - 1], 2 * sum);
                dp[i][j] = dp[i - 1][left] + dp[i - 1][right];
            }
        }

        return dp[nums.length][S + sum];
    }

    public int maxValue(Pair<Integer, Integer>[] pairs, int capacity) {
        int dp[][] = new int[pairs.length + 1][capacity + 1];
        for (int i = 0; i <= capacity; i++) {
            if (i - pairs[0].fst > 0) {
                dp[0][i] = pairs[0].snd;
            } else {
                dp[0][i] = 0;
            }

        }
        for (int i = 1; i <= pairs.length; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (j - pairs[i - 1].fst < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j - pairs[i - 1].fst] + pairs[i - 1].snd;
                }
            }
        }

        return dp[pairs.length][capacity];
    }


    public int longestCommonSubsequence(String text1, String text2) {

        int len1 = text1.length();
        int len2 = text2.length();

        int dp[][] = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        String res = lcs(text1, text2, dp);
        System.out.println(res);
        return dp[len1][len2];
    }

    public String lcs(String text1, String text2, int dp[][]) {
        int i = text1.length(), j = text2.length(), c = 0;
        char[] res = new char[dp[text1.length()][text2.length()]];

        while (c < res.length) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                res[c++] = text1.charAt(--i);
                j--;
            } else if (dp[i][j - 1] > dp[i - 1][j]) {
                j--;
            } else if (dp[i][j - 1] < dp[i - 1][j]) {
                i--;
            } else {
                i--;
                j--;
            }
        }

        return new StringBuilder(new String(res)).reverse().toString();
    }


    public boolean canVisitAllRooms(List<List<Integer>> rooms) {

        int len = rooms.size();
        if (len == 0) {
            return true;
        }

        return canVisitAllRoomsDfs(rooms, 0, new HashMap(), new HashSet());

    }

    public boolean canVisitAllRoomsDfs(List<List<Integer>> rooms, int key, HashMap visited, HashSet set) {

        System.out.println(set.toString());
        System.out.println(set.size());

        if (set.size() == rooms.size() - 1) {
            return true;
        }
        if (!visited.containsKey(key)) {
            List<Integer> keys = rooms.get(key);
            for (int i = 0; i < keys.size(); i++) {
                if (visited.containsKey(keys.get(i))) {
                    continue;
                }
                visited.put(keys.get(i), 1);
                set.add(keys.get(i));
                if (keys.get(i) > 0) {
                    canVisitAllRoomsDfs(rooms, keys.get(i), visited, set);
                }
                set.remove(keys.get(i));
                visited.remove(keys.get(i));
            }
        }

        return false;
    }

    public boolean isNumber(String s) {
        Map[] states = {
                new HashMap() {{
                    put(' ', 0);
                    put('s', 1);
                    put('d', 2);
                    put('.', 4);
                }}, // 0.
                new HashMap() {{
                    put('d', 2);
                    put('.', 4);
                }},                           // 1.
                new HashMap() {{
                    put('d', 2);
                    put('.', 3);
                    put('e', 5);
                    put(' ', 8);
                }}, // 2.
                new HashMap() {{
                    put('d', 3);
                    put('e', 5);
                    put(' ', 8);
                }},              // 3.
                new HashMap() {{
                    put('d', 3);
                }},                                        // 4.
                new HashMap() {{
                    put('s', 6);
                    put('d', 7);
                }},                           // 5.
                new HashMap() {{
                    put('d', 7);
                }},                                        // 6.
                new HashMap() {{
                    put('d', 7);
                    put(' ', 8);
                }},                           // 7.
                new HashMap() {{
                    put(' ', 8);
                }}                                         // 8.
        };
        int p = 0;
        char t;
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') t = 'd';
            else if (c == '+' || c == '-') t = 's';
            else if (c == 'e' || c == 'E') t = 'e';
            else if (c == '.' || c == ' ') t = c;
            else t = '?';
            if (!states[p].containsKey(t)) return false;
            p = (int) states[p].get(t);
        }
        return p == 2 || p == 3 || p == 7 || p == 8;
    }

    /**
     * 有限状态机思路：
     * 1 首先确定状态有多少个，
     * 2 其次确定每个状态可达的范围
     * 3 遍历 遇到不可达状态返回 false
     * 4 校验最终结果
     * <p>
     * 0 开始的空格
     * 1 幂符号前的正负号
     * 2 小数点前的数字
     * 3 小数点、小数点后的数字
     * 4 当小数点前为空格时，小数点、小数点后的数字
     * 5 幂符号
     * 6 幂符号后的正负号
     * 7 幂符号后的数字
     * 8 结尾的空格
     */
//
//    public boolean isNumber1(String s) {
//
//        Map[] state = new Map[]{
//                new HashMap() {{
//                      put(' ',0);
//                      put('s',1);
//                      put('d',2);
//                      put('.',4);
//                    }},
//
//                new HashMap() {{
//
//                    put('d',2);
//                    put('.',4);
//                }}
//
//        };
//    }
    public void calTime(List<int[]> lists) {
        int len = lists.get(0).length;
        int n = lists.size();
        int dp[] = new int[len];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            // 交给几号设备来处理
            dp[0] = Math.min(dp[0], lists.get(i)[0]);
        }        // 取任务
        for (int i = 1; i < len; i++) {
            // 交给几号设备来处理
            for (int j = 0; j < n; j++) {
                dp[i] = Math.min(dp[i - 1] + lists.get(j)[i], dp[i]);
            }
        }

    }

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        // 遍历map，用最小堆保存频率最大的k个元素
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return -map.get(a) + map.get(b);
            }
        });
        for (Integer key : map.keySet()) {
            pq.add(key);
        }
        // 取出最小堆中的元素
        int[] result = new int[k];
        int index = 0;
        while (!pq.isEmpty()) {
            if (index == k) {
                break;
            }
            result[index++] = pq.poll();
        }

        return result;
    }

    public boolean isPalindrome(String s) {
        if (s == null || s.equals("") || s.equals(" ")) {
            return true;
        }
        int len = s.length();
        int left = 0, right = len - 1;
        s = s.toLowerCase();
        char[] chars = s.toCharArray();
        while (left < right) {
            if (!Character.isLetterOrDigit(chars[left])) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(chars[right])) {
                right--;
                continue;
            }
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return chars[left] == chars[right];
    }

    public int trap(int[] height) {

        int left = 0, right = height.length - 1;
        int sum = 0;
        while (left < right) {
            int lower = Math.min(height[left], height[right]);
            if (height[left] - height[right] <= 0) {
                left++;
                if (height[left] < lower) {
                    sum += (lower - height[left++]);
                }
            } else {
                right--;
                if (lower > height[right]) {
                    sum += (lower - height[right--]);
                }
            }
        }
        return sum;
    }

    List<List<Integer>> combines = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        for (int i = 0; i < n; i++) {
            combineDfs(n, k, i, new ArrayList<>());
        }
        return combines;
    }

    public void combineDfs(int n, int k, int cur, List<Integer> res) {
        if (res.size() == k) {
            combines.add(new ArrayList<>(res));
            return;
        }
        for (int i = cur + 1; i <= n; i++) {
            res.add(i);
            System.out.println(res.toString());
            combineDfs(n, k, i, res);
            res.remove(res.size() - 1);
        }
    }

    int maxN, idaIndex;
    int[] val = new int[1010];

    boolean ida(int cur, int depth) {
        if (cur > depth) {
            return false;
        }
        if (val[idaIndex] << (depth - cur) < maxN) {
            return false;
        }
        if (val[idaIndex] == maxN) {
            return true;
        }
        idaIndex++;

        for (int i = 0; i < idaIndex; i++) {
            val[idaIndex] = val[idaIndex - 1] + val[i];
            if (ida(cur + 1, depth)) {
                return true;
            }

            val[idaIndex] = val[idaIndex - 1] - val[i];
            if (ida(cur + 1, depth)) {
                return true;
            }
        }
        idaIndex--;
        return false;
    }

    public void idaSolve() {

        for (int i = 0; i < 1000; i++) {
            val[0] = 0;
            ida(0, i);
        }

    }

    List<List<Integer>> combinationSum;
    int sum = 0;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        combinationSum = new ArrayList<>();
        combinationSumDfs(candidates, target, 0, sum, new ArrayList<>());
        return combinationSum;
    }

    public void combinationSumDfs(int[] candidates, int target, int index, int sum, List<Integer> list) {
        if (sum == target) {
            combinationSum.add(new ArrayList<>(list));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (i > 0 && candidates[i - 1] == candidates[i]) {
                continue;
            }
            sum += candidates[i];
            list.add(candidates[i]);
            combinationSumDfs(candidates, target, index + 1, sum, list);
            sum -= candidates[i];
            list.remove(list.size() - 1);
        }
    }

    public int lastStoneWeightII(int[] stones) {

        int sum = 0;
        for (int i = 0; i < stones.length; i++) {
            sum += stones[i];
        }
        int mid = sum / 2;
        int res = 0;

        int dp[] = new int[mid + 1];

        for (int i = 0; i < stones.length; i++) {
            for (int j = mid; j >= 0; j--) {
                if (stones[i] > j) {
                    dp[j] = dp[j - 1];
                    continue;
                }
                dp[j] = Math.max(dp[j - stones[i]] + stones[i], dp[j]);
            }
        }

        return sum - 2 * dp[mid];
    }

    List<List<Integer>> combinationSum2;

    public List<List<Integer>> combinationSum2_(int[] candidates, int target) {

        Arrays.sort(candidates);
        combinationSum2 = new ArrayList<>();
        combinationSum2Dfs(candidates, target, 0, 0, new ArrayList<>());
        return combinationSum2;
    }

    public void combinationSum2Dfs(int[] candidates, int target, int index, int sum, List<Integer> list) {
        if (sum == target) {
            combinationSum2.add(new ArrayList<>(list));
        }
        if (sum > target) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            // 如果是 0 那么相邻元素都不能访问
            // 如果是 index 那么则约束该层的遍历只能取一次
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            // 剪枝
            if (sum + candidates[i] > target) {
                break;
            }
            list.add(candidates[i]);
            sum += candidates[i];
            combinationSum2Dfs(candidates, target, i + 1, sum, list);
            sum -= candidates[i];
            list.remove(list.size() - 1);
        }
    }

    List<List<Integer>> combinationSum3;

    public List<List<Integer>> combinationSum3(int k, int n) {
        combinationSum3 = new ArrayList<>();
        combinationSum3Dfs(1, k, n, new ArrayList<>(), 0);
        return combinationSum3;
    }

    public void combinationSum3Dfs(int index, int k, int n, List<Integer> list, int sum) {

        if (sum == n && list.size() == k) {
            combinationSum3.add(new ArrayList<>(list));
            return;
        }
        if (sum > n || list.size() >= k) {
            return;
        }
        for (int i = index; i <= 9; i++) {
            if (sum + i > n) {
                break;
            }
            sum += i;
            list.add(i);
            combinationSum3Dfs(i + 1, k, n, list, sum);
            list.remove(list.size() - 1);
            sum -= i;
        }
    }

    List<List<Integer>> subsetsWithDup;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        subsetsWithDup = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDupDfs(nums, 0, new ArrayList<>());
        return subsetsWithDup;
    }

    public void subsetsWithDupDfs(int[] nums, int index, List<Integer> list) {
        subsetsWithDup.add(new ArrayList<>(list));
        for (int i = index; i < nums.length; i++) {

            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            subsetsWithDupDfs(nums, i + 1, list);
            list.remove(list.size() - 1);
        }
    }


    public int lengthOfLastWord(String s) {
        s = s.trim();
        for (int i = s.length() - 1; i >= 0; i--) {

            if (!Character.isLetterOrDigit(s.charAt(i))) {
                return s.length() - 1 - i;
            }
            if (i == 0) {
                return s.length() - i;
            }
        }
        return 0;
    }

    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Double> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            double sum = 0;
            double size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (i == size - 1) {
                    res.add(sum / size);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return res;
    }

    public static String manacher(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("$#");
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i)).append('#');
        }
        sb.append('.');
        int p[] = new int[sb.length()];

        int c = 0, mx = 0;
        int maxLen = 0, maxC = 0;
        for (int i = 1; i < sb.length() - 1; i++) {

            p[i] = mx > i ? (Math.min(mx - i, p[c * 2 - i])) : 0;
            while (sb.charAt(i - p[i] - 1) == sb.charAt(i + p[i] + 1)) {
                p[i]++;
            }
            if (p[i] + i > mx) {
                mx = p[i] + i;
                c = i;
            }
            if (p[i] > maxLen) {
                maxLen = p[i];
                maxC = i;
            }
        }
        return s.substring((maxC - maxLen) / 2, (maxC - maxLen) / 2 + maxLen);
    }

    public String shortestPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("$#");
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i)).append('#');
        }
        sb.append('.');
        int p[] = new int[sb.length()];

        int c = 0, mx = 0;
        int maxLen = 0, maxC = 0;
        for (int i = 1; i < sb.length() - 1; i++) {

            p[i] = mx > i ? (Math.min(mx - i, p[c * 2 - i])) : 0;
            while (sb.charAt(i - p[i] - 1) == sb.charAt(i + p[i] + 1)) {
                p[i]++;
            }
            if (p[i] + i > mx) {
                mx = p[i] + i;
                c = i;
            }

            if (p[i] > maxLen) {
                int start = (i - p[i]) / 2;
                if (start == 0) {
                    maxLen = p[i];
                    maxC = i;
                }
            }


        }
        return new StringBuilder(s.substring(maxLen)).reverse().append(s).toString();
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        TreeNode pre = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            if (p.right == null || p.right == pre) {
                res.add(p.val);
                pre = p;
                p = null;
            } else {
                stack.push(p);
                p = p.right;
            }
        }
        return res;
    }

    public boolean hasCycle(ListNode head) {

        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    public boolean search(int[] nums, int target) {

        if (nums.length == 0) {
            return false;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] < nums[right]) {
                if (nums[mid] <= target && nums[right] >= target) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            } else if (nums[mid] > nums[right]) {
                if (nums[mid] >= target && nums[left] <= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] == target) {
                    return true;
                }
                if (nums[mid] == nums[right]) {
                    right = right - 1;
                }
            }
        }
        return nums[left] == right;
    }

    public boolean canPartition(int[] nums) {

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if ((sum & 1) == 1) {
            return false;
        }
        int target = sum >>> 1;

        // dp[][] 当前金额能否到达
        boolean dp[][] = new boolean[target + 1][nums.length];
        if (nums[0] <= target) {
            dp[nums[0]][0] = true;
        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > target) {
                continue;
            }
            for (int j = 1; j <= target; j++) {
                dp[j][i] = dp[j][i - 1];
                if (nums[i] == j) {
                    dp[j][i] = true;
                    continue;
                }
                if (nums[i] < j) {
                    dp[j][i] = dp[j - nums[i]][i - 1] || dp[j][i - 1];
                }
            }
        }
        return dp[target][nums.length - 1];
    }

    public int getMinimumDifference(TreeNode root) {
        getMinimumDifferenceDfs(root);

        return diff;
    }

    int pre = -1;
    int diff = Integer.MAX_VALUE;

    public void getMinimumDifferenceDfs(TreeNode root) {
        if (root == null) {
            return;
        }
        getMinimumDifferenceDfs(root.left);
        if (pre != -1) {
            diff = Math.min(root.val - pre, diff);
        }
        pre = root.val;
        getMinimumDifferenceDfs(root.right);
    }

//    public List<String> commonChars(String[] A) {
//
//        if (A.length==0){
//            return new ArrayList<>();
//        }
//        ArrayList<Character> list = new ArrayList();
//        for (int i = 0; i < A[0].length(); i++) {
//            list.add(A[0].charAt(i));
//            System.out.println(A[0].charAt(i));
//        }
//
//        boolean flags[] = new boolean[list.size()];
//        for (int i = 1; i < A.length; i++) {
//
//            for (int j = 0; j <A[i].length() ; j++) {
//                int index = list.indexOf(A[i].charAt(j));
//                if(flags[index]&& index>-1){
//                    flags[index] = true;
//
//                }
//            }
//
//        }
//        ArrayList<String> strings = new ArrayList<>();
//        for (Character c:list) {
//            strings.add(c.toString());
//        }
//        return strings;
//    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        if (root.left != null) {
            root.left.next = root.right;
            if (root.next != null) {
                root.right.next = root.next.left;
            }
        }

        connect(root.right);
        connect(root.left);
        return root;
    }

    public boolean backspaceCompare(String S, String T) {

        int t = T.length() - 1;
        int s = S.length() - 1;

        int skipT = 0;
        int skipS = 0;

        while (t >= 0 || s >= 0) {
            while (s >= 0) {
                if (S.charAt(s) == '#') {
                    skipS++;
                    s--;
                } else if (skipS > 0) {
                    skipS--;
                    s--;
                } else {

                    break;
                }


            }
            while (t >= 0) {
                if (T.charAt(t) == '#') {
                    skipT++;
                    t--;
                } else if (skipT > 0) {
                    skipT--;
                    t--;
                } else {

                    break;
                }
            }

            if (s >= 0 && t >= 0) {
                if (S.charAt(s) != T.charAt(t)) {
                    return false;
                }
            } else {
                if (s >= 0 || t >= 0) {
                    return false;
                }

            }
            t--;
            s--;
        }
        return true;
    }

    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode p = head;
        ListNode end = null;
        if (head.next != null && head.next.next != null) {
            while (p.next != null && p.next.next != null) {
                p = p.next;
            }
            end = p.next;
            p.next = null;
        } else {
            return;
        }
        reorderList(head.next);
        end.next = head.next;
        head.next = end;
    }

    public void reorderList1(ListNode head) {

        LinkedList<ListNode> listNodes = new LinkedList<>();
        while (head != null) {
            listNodes.offer(head);
            head = head.next;
        }
        ListNode p = null, q = null;
        while (!listNodes.isEmpty()) {
            q = null;
            p = listNodes.pollFirst();
            if (!listNodes.isEmpty()) {
                q = listNodes.pollLast();
                q.next = p.next;
                p.next = q;
            }
        }
        if (q != null) {
            q.next = null;
        } else if (p != null) {
            p.next = null;
        }
    }

    public boolean isLongPressedName(String name, String typed) {

        int len1 = name.length() - 1;
        int len2 = typed.length() - 1;

        while (len1 >= 0 && len2 >= 0) {
            if (typed.charAt(len2) == name.charAt(len1)) {
                len1--;
                len2--;
            } else {
                if (len2 != typed.length() - 1 && typed.charAt(len2) == typed.charAt(len2 + 1)) {
                    len2--;
                } else {
                    return false;
                }
            }
        }
        System.out.println(len1);
        System.out.println(len2);

        if (len1 > len2) {
            return false;
        }

        while (len2 >= 0) {
            if (len2 != typed.length() - 1 && typed.charAt(len2) != typed.charAt(len2 + 1)) {
                return false;
            }
            len2--;
        }
        return true;
    }

    public List<Integer> partitionLabels(String S) {

        // 第一步 循环 只要字符在原有列表里面那么 则清空list 添加字符串
        List<Integer> list = new ArrayList<>();
        int end = 0;
        int start = 0;
        char[] chars = S.toCharArray();
        int[] map = new int[26];
        for (int i = 0; i < chars.length; i++) {
            map[chars[i] - 'a'] = i;
        }
        for (int i = 0; i < chars.length; i++) {
            int last = map[chars[i] - 'a'];
            if (last > end) {
                end = last;
            }
            if (i == end) {
                list.add(S.length() - start);
                start = end + 1;
            }

        }
        return list;
    }

    public List<Integer> partitionLabels1(String S) {
        // 第一步 循环 只要字符在原有列表里面那么 则清空list 添加字符串
        List<Integer> list = new ArrayList<>();
        int end = -1, start = 0;
        char[] chars = S.toCharArray();
        int[] map = new int[26];
        for (int i = 0; i < chars.length; i++) {
            map[chars[i] - 'a'] = i;
        }
        for (int i = 0; i < chars.length; i++) {
            int last = map[chars[i] - 'a'];
            if (last > end) {
                end = last;
            }
            if (i == end) {
                list.add(end - start + 1);
                start = end + 1;
            }
        }
        return list;
    }

    public boolean isPalindrome(ListNode head) {

        if (head == null) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 偶数
        slow = slow.next;
        fast = head;
//        if (fast.next!=null){
//
//        }
        ListNode pre = null;
        ListNode cur = slow;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }


        while (pre != null) {
            if (pre.val != fast.val) {
                return false;
            }
            pre = pre.next;
            fast = fast.next;
        }
        return true;
    }

    public int videoStitching(int[][] clips, int T) {

        int map[] = new int[T];

        int pre = 0, last = 0, res = 0;

        for (int i = 0; i < clips.length; i++) {
            int[] clip = clips[i];
            if (clip[0] < T) {
                map[clip[0]] = Math.max(map[clip[0]], clip[1]);
            }
        }
        for (int i = 0; i < T; i++) {
            if (map[i] > last) {
                last = map[i];
            }
            if (i == last) {
                return -1;
            }
            if (i == pre) {
                res++;
                pre = last;
            }
        }
        return res;
    }

    public int videoStitching1(int[][] clips, int T) {

        // dp[i] 能够 0-i 的最少次数
        int[] dp = new int[T];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        for (int i = 0; i <= T; i++) {
            for (int j = 0; j < clips.length; j++) {
                // i 要在 clip 之间能到达 即能到达 i
                if (clips[j][0] < i && clips[j][1] >= i) {
                    dp[i] = Math.min(dp[i], dp[clips[j][0]] + 1);
                }
            }
        }
        return dp[T] == Integer.MAX_VALUE - 1 ? -1 : dp[T];
    }

    public int longestMountain(int[] A) {
        // 定义 dp[i] 为 i 最长上升的序列
        // 定义 fp[i] 为 i 最长上升的序列
        int dp[] = new int[A.length];
        int[] fp = new int[A.length];
        Arrays.fill(dp, 1);
        Arrays.fill(fp, 1);
        for (int i = 1; i < A.length; i++) {
            if (A[i] > A[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            }
        }

        for (int i = A.length - 2; i >= 0; i--) {
            if (A[i] > A[i + 1]) {
                fp[i] = fp[i + 1] + 1;
            }
        }
        int max = 1;
        for (int i = 0; i < A.length; i++) {
            if (dp[i] > 1 && fp[i] > 1) {
                max = Math.max(max, dp[i] + fp[i]);
            }
        }
        return max;
    }

    // 保存 每行安排的皇后 是第几列
    int col[] = new int[12];
    int totalNQueens = 0;

    public int totalNQueens(int n) {
        totalNQueensDfs(0, n);
        return totalNQueens;
    }

    public void totalNQueensDfs(int r, int length) {
        if (r == length) {
            totalNQueens++;
            return;
        }
        for (int i = 0; i < length; i++) {
            if (check(i, r)) {
                col[r] = i;
                totalNQueensDfs(r + 1, length);
            }
        }
    }

    public boolean check(int c, int r) {
        // 同一行就不需要安排
        for (int i = 0; i < r; i++) {
            // 不同行如果安排同一列就 return false
            if (col[i] == c || Math.abs(col[i] - c) == Math.abs(i - r)) {
                return false;
            }
        }
        return true;
    }

    public int videoStitching2(int[][] clips, int T) {
        int dp[] = new int[T + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        for (int i = 0; i <= T; i++) {
            for (int[] clip : clips) {
                // 题中是要求要有重叠区间才可以组合 如果等于 i 那么必定有 clip[1] = i 那么则不需要==i,
                if (i < clip[0] && i <= clip[1]) {
                    dp[i] = Math.min(dp[i], dp[clip[0]] + 1);
                }
            }
        }
        return dp[T] == Integer.MAX_VALUE - 1 ? -1 : dp[T];
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode fast = head;
        int index = 0;
        while (fast != null) {
            fast = fast.next;
            index++;
            if (index == n) {
                break;
            }
        }
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        if (slow.next == null) {
            return null;
        }
        slow.next = slow.next.next;

        return head;
    }


    public boolean uniqueOccurrences(int[] arr) {

        int map[] = new int[2001];
        for (int i = 0; i < arr.length; i++) {
            map[arr[i] + 1000]++;
        }
        int set[] = new int[1001];
        for (int i = 0; i < map.length; i++) {
            if (set[map[i]] != 0) {
                System.out.println(map[i]);
                return false;
            }
            set[map[i]] = 1;
        }
        return true;
    }

    List<Integer> sumNumbers = new ArrayList<>();

    public int sumNumbers(TreeNode root) {
        sumNumbersDfs(root, new StringBuilder());
        int sum = 0;
        for (int i = 0; i < sumNumbers.size(); i++) {
            sum += sumNumbers.get(0);
        }
        return sum;
    }

    public void sumNumbersDfs(TreeNode root, StringBuilder builder) {
        if (root == null) {
            return;
        }
        builder.append(root.val);
        if (root.left == null && root.right == null) {
            sumNumbers.add(Integer.valueOf(builder.toString()));
        }
        sumNumbersDfs(root.left, builder);

        sumNumbersDfs(root.right, builder);
        builder.deleteCharAt(builder.length() - 1);
    }

    public int islandPerimeter(int[][] grid) {
        int offset[][] = new int[][]{new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}};
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    for (int k = 0; k < offset.length; k++) {
                        int newX = i + offset[k][0];
                        int newY = j + offset[k][1];
                        if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid[0].length) {
                            continue;
                        }
                        if (grid[newX][newY] == 1) {
                            sum++;
                        }
                    }
                } else {
                    if (i == 0 || i == grid.length || j == 0 || j == grid[0].length) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public boolean validMountainArray(int[] A) {
        if (A.length < 3) {
            return false;
        }
        int a[] = new int[A.length];
        int max = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i] > A[i - 1]) {
                a[i] = a[i - 1] + 1;
                if (a[i] > max) {
                    max = a[i];
                }
            }
            if (A[i] < A[i - 1]) {
                a[i] = a[i - 1] > 0 ? 0 : a[i - 1] - 1;
            }
        }
        return (max - a[A.length - 1] + 1 == A.length) && a[A.length - 1] < 0 && a[A.length - 1] < 0 && max > 0;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        // 取 右下角开始遍历
        int i = matrix.length - 1;
        int j = 0;
        while (matrix[i][j] != target) {

            if (matrix[i][j] > target) {
                i--;
                if (i < 0) {
                    return false;
                }
            } else if (matrix[i][j] < target) {
                j++;
                if (j >= matrix[0].length) {
                    return false;
                }
            }
        }
        return matrix[i][j] == target;
    }

    public ListNode insertionSortList(ListNode head) {

        ListNode cur = head;
        ListNode dummyHead = new ListNode(0);
        ListNode pre = dummyHead;
        ListNode tail = new ListNode(Integer.MIN_VALUE);
        // x-3214
        while (cur != null) {
            ListNode temp = cur.next;
            if (cur.val > tail.val) {
                tail.next = cur;
                tail = cur;
                cur = temp;
            } else {
                tail.next = temp;
                while (pre.next != null && pre.next.val < cur.val) {
                    pre = pre.next;
                }
                cur.next = pre.next;
                pre.next = cur;
                cur = temp;
                pre = dummyHead;
            }
        }
        return dummyHead.next;
    }

    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode root_parent = null;
        TreeNode root_right = null;

        while (root != null) {
            TreeNode root_left = root.left;
            root.left = root_right;
            root_right = root.right;
            root.right = root_parent;
            root_parent = root;
            root = root_left;
        }
        return root_parent;
    }

    public int[][] imageSmoother(int[][] M) {
        if (M.length == 0) {
            return M;
        }
        int res[][] = new int[M.length][M[0].length];
        int offset[][] = new int[][]{new int[]{0, 1}, new int[]{0, -1}, new int[]{1, 1}, new int[]{1, 0},
                new int[]{1, -1}, new int[]{-1, 1}, new int[]{-1, 0}, new int[]{-1, -1},};
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                int count = 0;
                int sum = 0;
                sum += M[i][j];
                count++;
                for (int k = 0; k < 8; k++) {
                    int newX = i + offset[k][0];
                    int newY = j + offset[k][1];
                    if (newX < 0 || newX >= M.length || newY < 0 || newY >= M[0].length) {
                        continue;
                    }
                    count++;
                    sum += M[newX][newY];
                }
                res[i][j] = sum / count;
            }
        }
        return res;
    }

    public int[] sortArrayByParityII(int[] A) {
        int i = 0, j = 1;
        while (i < A.length && j < A.length) {
            while (i < A.length) {
                if ((A[i] & 1) != 0) {
                    break;
                }
                i += 2;
            }
            while (j < A.length) {
                if ((A[j] & 1) == 0) {
                    break;
                }
                j += 2;
            }
            if (i >= A.length || j >= A.length) {
                break;
            }
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
            i += 2;
            j += 2;
        }
        return A;
    }

    public String removeKdigits(String num, int k) {

        LinkedList<Character> stack = new LinkedList<>();

        char[] chars = num.toCharArray();
        int len = num.length();

        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            while (count < k && i - 1 >= 0 && !stack.isEmpty() && chars[i] < stack.peekLast()) {
                stack.pollLast();
                count++;
            }
            stack.offerLast(chars[i]);
        }

        stack = new LinkedList<>(stack.subList(0, len - k));

        StringBuilder builder = new StringBuilder();

        while (!stack.isEmpty()) {
            builder.append(stack.pollFirst());
        }

        while (builder.length() > 0 && builder.charAt(0) == '0') {
            builder.deleteCharAt(0);
        }
        if (builder.length() == 0) {
            return "0";
        }
        return builder.toString();
    }

    //[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

    /**
     * 先 安排 4，4  然后依次排列剩下的 ，50 第一个
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        if (people.length == 0) {
            return people;
        }

        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                }
                return b[0] - a[0];
            }
        });

        LinkedList<int[]> list = new LinkedList<>();

        for (int i = 0; i < people.length; i++) {
            list.add(people[i][1], people[i]);
        }
        return list.toArray(new int[list.size()][2]);
    }

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {

        int[][] res = new int[R][C];

        Queue<int[]> queue = new LinkedList();
        queue.add(new int[]{r0, c0});
        int[][] offset = new int[][]{new int[]{0, 1}, new int[]{0, -1}, new int[]{1, 0}, new int[]{-1, 0}};
        int index = 0;
        while (!queue.isEmpty()) {
            int[] item = queue.poll();
            res[index++] = item;
            for (int i = 0; i < 4; i++) {
                int newR = item[0] + offset[i][0];
                int newC = item[1] + offset[i][1];

                if (newR < 0 || newR >= R || newC < 0 || newC >= C) {
                    continue;
                }
                queue.offer(new int[]{newR, newC});

            }
        }
        return res;
    }

    public ListNode sortList(ListNode head) {

        return sortList(head,null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {

        if(head==null){
            return head;
        }
        if (head.next==null){
            return head;
        }
        ListNode fast = head,slow = head;

        while (fast!=tail){
            fast = fast.next;
            slow = slow.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode mid = slow;

        ListNode left = sortList(head,mid);
        ListNode right = sortList(mid,tail);
        ListNode node = merge(left,right);
        return node;
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
}