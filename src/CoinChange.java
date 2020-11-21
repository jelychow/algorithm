import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 322. 零钱兑换
 */
public class CoinChange {

    public int coinChange(int[] coins, int amount) {

        /**
         *  定义状态为指定 amount 钱的的长度
         *
         */
        if (amount == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // base case
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0) {
                    // 如果上一步没有找到答案 说明找不到答案
                    if (dp[i - coins[j]] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i - coins[j]] + 1, min);
                    }
                }
            }

        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    /**
     * 152. 乘积最大子序列
     * [2,3,-2,4]
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {

        // 定义 dp[i] 为当前最大值
        // dp[i] = max( nums[i]* min,nums[i]* max)

        int dp[] = new int[nums.length];
        int max = nums[0];
        int min = nums[0];
        dp[0] = nums[0];
        int result = 0;
        for (int i = 1; i < nums.length; i++) {
            int maxRes = nums[i]*max;
            int minRes = nums[i]*min;
            dp[i] = Math.max(maxRes,minRes);
            dp[i] = Math.max(dp[i],nums[i]);
            min = Math.min(nums[i],Math.min(minRes,maxRes));
            max =dp[i];
            result = Math.max(result,max);
        }


        return result;

    }

//    public int findMin(int[] nums) {
//
//    }



}
