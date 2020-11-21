/**
 * 75. 颜色分类
 * 输入: [2,0,2,1,1,0]
 * 输出: [0,0,1,1,2,2]
 * <p>
 * 执行过程
 * 三指针排序
 * left 指针后面为0，right 后面为 2
 * cur 指针遍历元素
 * 终止条件 cur == right
 */
public class SortColors {
    public void sortColors(int[] nums) {

        int left = 0;
        int right = nums.length - 1;
        int cur = 0;

        while (cur <= right) {
            if (nums[cur]==0){
                swap(nums,left++,cur++);
            } else if(nums[cur]==2){
                swap(nums,cur,right--);
            } else {
                cur++;
            }
        }
    }


    public void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
