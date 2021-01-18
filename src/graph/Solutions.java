package graph;

import javafx.util.Pair;

import java.util.*;

public class Solutions {

    /**
     * 1.3.1
     */
    class MedianFinder {
        private Queue<Long> minHeap = new PriorityQueue(),
                maxHeap = new PriorityQueue();

        public void addNum(int num) {
            minHeap.add((long) num);
            maxHeap.add(-minHeap.poll());
            if (minHeap.size() < maxHeap.size())
                minHeap.add(-maxHeap.poll());
        }

        public double findMedian() {
            return minHeap.size() > maxHeap.size()
                    ? minHeap.peek()
                    : (minHeap.peek() - maxHeap.peek()) / 2.0;
        }
    };

    /**
     * 代码1.3.2
     *
     */
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        int n = quality.length;
        // 单位薪资
        Pair<Double, Integer>[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            Pair<Double, Integer> pair = new Pair(wage[i] / (quality[i] * 1d), quality[i]);
            pairs[i] = pair;
        }
        Arrays.sort(pairs, (o1, o2) -> o1.getKey() - o2.getKey() > 0 ? 1 : -1);

        double ans = Double.MAX_VALUE;
        // 总工时
        int sumQuilty = 0;
        PriorityQueue<Integer> pq = new PriorityQueue();
        for (int i = 0; i < n; i++) {
            Pair<Double, Integer> p = pairs[i];
            // 这里偷懒直接使用了最小堆 借鉴官方题解，用最大堆也可以的
            pq.offer(-p.getValue());
            sumQuilty += p.getValue();
            if (pq.size() > K)
                // 如果超过k就要把当前工时/工资最高的赶走，这样才符合资本家剥夺剩余价值
                sumQuilty += pq.poll();
            if (pq.size() == K)
                ans = Math.min(ans, sumQuilty * p.getKey());
        }
        return ans;
    }

    //代码1.3.4
    public int kthSmallest(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;
        PriorityQueue<Pair<Integer, int[]>> pq = new PriorityQueue<>((a, b) -> a.getKey() - b.getKey());
        int[] sum = new int[m];
        int total = 0;
        for (int i = 0; i < m; i++) {
            total += mat[i][0];
        }
        Pair<Integer, int[]> pair = new Pair<>(total, sum);
        pq.offer(pair);
        Set<String> seen = new HashSet<>();

        // 小顶堆 执行k次 把前面k小的数组移除 之后栈顶便是我们所求之结果
        while (--k > 0) {
            Pair<Integer, int[]> cur = pq.poll();
            // 行号
            for (int i = 0; i < m; i++) {
                if (cur.getValue()[i] < n - 1) {
                    int[] arr = Arrays.copyOf(cur.getValue(), m);
                    // 列号
                    arr[i]++;
                    if (!seen.contains(Arrays.toString(arr))) {
                        seen.add(Arrays.toString(arr));
                        int next = cur.getKey() - mat[i][cur.getValue()[i]] + mat[i][arr[i]];
                        pq.offer(new Pair<>(next, arr));
                    }
                }
            }
        }
        return pq.peek().getKey();
    }

    // 代码1.3.6
    public int smallestDistancePair(int[] nums, int k) {
        // 先排序
        Arrays.sort(nums);
        // 小顶堆
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(a -> (nums[a.getValue()] - nums[a.getKey()])));
        // i 于 i+1 可以找到最小的元素
        for (int i = 0; i + 1 < nums.length; i++) {
            Pair pair = new Pair(i, i + 1);
            pq.offer(pair);
        }
        Pair<Integer, Integer> pair = null;
        /**
         * 实现原理：
         * 数组按顺序排序
         * 那么最小的差 以及第二小的差 在第一轮 也就是上面那个 for 里面可以求出来，
         * 由于后续元素是在 原有基础上添加 后续元素肯定是每一轮逐渐增大的，也就是说上一轮集合小于下一轮（同样的起点情况）
         * 证明：原有集合为 i->j  新增集合为  i->j+1 原有集合是新集合的子集，所以新的集合差肯定大于上一轮 ，新的集合同时覆盖（i->j）and (i+1->j+1) 两个集合
         * 这样可以保证即便弹出一个元素 pq 中的堆顶依然是最小元素
         * 如此反复
         * 堆顶元素便是我们所求之元素
         */
        while (k > 0) {
            k--;
            pair = pq.poll();
            if (pair.getValue() + 1 < nums.length) {
                pq.offer(new Pair(pair.getKey(), pair.getValue() + 1));
            }
        }
        return nums[pair.getValue()] - nums[pair.getKey()];
    }

    // 代码1.3.7
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);

        int l = 0;
        int r = nums[nums.length - 1] - nums[0];
        while (l < r) {
            int mi = (l + r) / 2;
            int count = 0, left = 0;
            for (int right = 0; right < nums.length; ++right) {
                while (nums[right] - nums[left] > mi) left++;
                count += right - left;
            }
            //count = number of pairs with distance <= mi
            if (count >= k) r = mi;
            else l = mi + 1;
        }
        return r;
    }

    // 代码1.3.8
    public int[] smallestRange(List<List<Integer>> nums) {

        int l = -1000000000, r = 1000000000, max = -1000000000;

        // 用一个 int[3] 数组来保存 len， height 以及 val
        PriorityQueue<int[]> pq = new PriorityQueue((Comparator<int[]>) (a, b) -> a[2] - b[2]);

        for (int i = 0; i < nums.size(); i++) {
            pq.offer(new int[]{i, 0, nums.get(i).get(0)});
            max = Math.max(max, nums.get(i).get(0));
        }

        while (!pq.isEmpty()) {
            int[] item = pq.poll();
            int minV = item[2], row = item[0], col = item[1];
            if (max - minV < r - l) {
                l = minV;
                r = max;
            }
            if (col == nums.get(row).size() - 1) {
                return new int[]{l, r};
            }
            int next = nums.get(row).get(col + 1);
            pq.offer(new int[]{row, col + 1, next});
            max = Math.max(next, max);
        }

        return new int[]{l, r};
    }

    // 代码1.3.9
    public int minimumDeviation(int[] nums) {
        int n = nums.length;
        List<List<Integer>> arr = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            arr.add(new ArrayList<>());
            if ((nums[i] & 1) == 1) {
                arr.get(i).add(nums[i]);
                arr.get(i).add(nums[i] * 2);
            } else {
                while ((nums[i] & 1) != 0) {
                    arr.get(i).add(nums[i]);
                    nums[i] >>= 1;
                }
                arr.get(i).add(nums[i]);
                Collections.reverse(arr.get(i));
            }
        }

        int[] res = smallestRange(arr);

        return res[1] - res[0];
    }


    // 代码1.3.10 todo
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (stations.length == 0) {
            return startFuel >= target ? 0 : -1;
        }

        // 定义次数 ans，车内油量 cur
        int ans = 0, cur = startFuel;
        // 定义 pq 用来在车厢箱保存燃油
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

        for (int i = 0; i < stations.length; i++) {
            // 车上没油啦 需要到车厢找最大的一桶油来加满
            while (cur < stations[i][0]) {
                Integer fuel = pq.poll();
                // 车厢没油了 mission failed
                if (fuel == null) {
                    return -1;
                }
                // 行驶距离
                cur += fuel;
                ans++;
            }
            pq.offer(stations[i][1]);
        }
        // 判断是否到达目的地
        while (cur < target) {
            Integer fuel = pq.poll();
            if (fuel == null) {
                return -1;
            }
            cur += fuel;
            ans++;
        }
        return ans;
    }

    // 代码1.3.12
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        if (heights.length < 1) {
            return heights.length;
        }
        int needBrick = 0;

        PriorityQueue<Integer> pq = new PriorityQueue();
        for (int i = 1; i < heights.length; i++) {
            int diff = heights[i] - heights[i - 1];
            if (diff > 0) {
                pq.offer(diff);
                if (pq.size() > ladders) {
                    int brick = pq.poll();
                    needBrick+=brick;
                }
                if (needBrick>bricks){
                    return i-1;
                }
            }
        }
        return heights.length-1;
    }

}
