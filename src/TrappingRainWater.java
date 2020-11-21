public class TrappingRainWater {
    // 暴力法进行计算 计算没一个柱的高度

    /**
     * 当前柱子容积 == maxheight - height[i]
     * 如何寻找 maxheight max = max(maxl,maxr);
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {

        int res = 0;
        for (int i = 0; i < height.length; i++) {
            int maxL = 0;
            int maxR = 0;
            int itemHeight = height[i];
            // 计算左边 max
            for (int j = 0; j <= i; j++) {
                maxL = Math.max(maxL, height[j]);
            }
            // 计算右边 max
            for (int j = i; j < height.length; j++) {
                maxR = Math.max(maxR, height[j]);
            }
            res += (Math.min(maxL, maxR) - itemHeight);
        }
        return res;
    }

    /**
     * 对暴力法进行优化
     * 需要对 没一个maxheight 进行cache
     * 预先计算 maxl  maxr
     * 然后再进行计算
     *
     * @param height
     * @return
     */
    public int trap2(int[] height) {

        if (height.length < 3) {
            return 0;
        }

        int res = 0;
        int[] maxL = new int[height.length];
        int[] maxR = new int[height.length];

        // base case
        maxL[0] = height[0];
        maxR[height.length - 1] = height[height.length - 1];
        //计算左侧
        for (int i = 1; i < height.length; i++) {
            maxL[i] = Math.max(maxL[i - 1], height[i]);
        }

        // 计算右侧
        for (int i = height.length - 2; i >= 0; i--) {
            maxR[i] = Math.max(height[i], maxR[i + 1]);
        }

        for (int i = 0; i < height.length; i++) {
            res += (Math.min(maxL[i], maxR[i]) - height[i]);
        }
        return res;
    }

    /**
     * 双指针法     1 2324
     * 双指针分布在两侧 是两边最高点  只要有一边变化那么就确定了 此时确定外围池塘深度，向内收缩确定里面池塘深度
     * 相当于从外向内搭建池塘
     *
     * @param height
     * @return
     */
    public int trap3(int[] height) {

        int ans = 0;
        int maxL = height[0];
        int maxR = height[height.length - 1];

        int left = 0;
        int right = height.length - 1;
        int i = 0;
        while (left <= right) {
            maxL = Math.max(maxL, height[left]);
            maxR = Math.max(maxR, height[right]);
            if (maxL < maxR) {
                // max 减去当前值
                ans += maxL - height[left];
                left++;
            } else {
                ans += maxR - height[right];
                right++;
            }
            i++;
        }

        return ans;

    }


}
