import java.util.concurrent.CopyOnWriteArraySet;

public class Sqrt {
    /**
     * 计算并返回 x 的平方根，其中 x 是非负整数。
     * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     * 采用二分进行解题
     * 思路 如果 mid 平方大于 x 那么 确定 x 坐落在左边 确定右边界
     * 反之 确定左边界
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int mid = 1;
        /**
         * 条件筛选
         * mid-1 X mid -1 <x
         */
        int left = 0;
        int right = x;
        while (true) {
            mid = (left+right)>>>1;
            if ((Math.pow(mid,2)==x)) {
                return mid;
            }
            if (Math.pow(mid,2)>x&&Math.pow(mid-1,2)<x) {
                return mid-1;
            }
            if(Math.pow(mid,2)>x) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
    }

}
