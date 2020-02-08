import java.util.List;

public class Triangle {

    /**
     *[2],
     *[3,4],
     *[6,5,7],
     *[4,1,8,3]
     *
     * dp 求解 虚构了 一排最低部的数组 为 全为 0 的
     * 从最底部依次向上递推
     *
     */

    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        if (size<1) {
            return 0;
        }
        int[] dp = new int[size+1];

        for (int i = size-1; i >=0;  i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                // 当前元素最小值
                dp[j] =  Math.min(dp[j],dp[j+1]) +triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}
