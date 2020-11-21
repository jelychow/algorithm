import java.lang.reflect.Array;
import java.util.*;

/**
 * 俄罗斯套娃问题
 * 354. 俄罗斯套娃信封问题
 */
public class RussianDollEnvelopes {

    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                /**
                 * 解题小技巧 由于 同样宽度套娃只能使用一次，优先使用宽的那个 所以采用降序
                 * 降序排序 是 o2 - o1
                 */
                return o1[0]==o2[0]?(o2[1]-o1[1]):o1[0]-o2[0];
            }
        });
        int temp[] = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            // 当前套娃最大值 = 前面最大值 +1 || 当前
            temp[i] = envelopes[i][1];
        }
        return lengthOfLIS(temp);
    }

    public int lengthOfLIS(int[] nums) {

        /**
         *
         * 本质上是寻找到第一个可以替换的元素
         * 替换条件 第一个大于 target 的元素，或者等于 target 的元素
         * 区别于一般二分查找 如果目标不存在 边界情况要调整
         * 二分查找的一般步骤
         * 1求 mid
         * 2确定终止条件
         * 3确定落在左区间条件
         * 4确定落在右区间条件
         *
         * 确定最终的 left
         *
         **/


        int[]pile = new int[nums.length];
        int lastHead = 0;
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];
            int left = 0;
            int right = lastHead;
            while (left<right) {
                int mid = (left+right)>>>1;
                if (poker==pile[mid]){
                    left = mid;
                    break;
                }else if (poker<pile[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            if (left==lastHead) {
                lastHead++;
            }
            pile[left] = poker;
        }

        return lastHead;
    }

    /**
     * 利用库函数 建堆 来计数
     * @param nums
     * @return
     */
    public int lengthOfLIS3(int[] nums) {
        // 寻找第一个大于 poker 的元素
        int[]pile = new int[nums.length];
        if(nums.length==0) {
            return 0;
        }
        ArrayList list = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];
            int index = Collections.binarySearch(list,poker);
            if (index<0){
                list.add(-index-1,poker);
                if(-index<list.size()) {
                    list.remove(-index);
                }
            }
        }

        return list.size();
    }

    /**
     *
     * @param nums
     * @return
     * 解题思路 先定义dp 第i个元素  即i个 最大的上升序列
     * 遍历前面的元素 比之小 那么 其 等于 dp[i] = max(dp[i],dp[j]+1)
     *
     */
    public int lengthOfLIS2(int[] nums) {
        if (nums.length==0) {
            return 0;
        }
        int dp[] = new int[nums.length];
        Arrays.fill(dp,1);
        int max = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 求最长上升子序列一定要满足条件上升
                if(nums[i]>nums[j]) {
                    dp[i] = Math.max(dp[i],dp[j]+1);
                    max = Math.max(dp[i],max);
                }
            }
        }

        return max;

    }


}
