public class FindTarget {
    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *  本题有几个常见的解题套路
     *  利用二分查找求左边界 发现 target 定义为右边届 继续二分
     *  利用二分查找求右边界 发现 target 向右收敛 最终 右边界 = left -1
     *  if(left<right) 这理的定义是 左闭右开 即右边不包括在内
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums==null||nums.length==0) {
            return new int[]{-1,-1};
        }
        int left = findLeft(nums,target);
        if (left==-1) {
            return new int[]{-1,-1};
        }
        int right = findRight(nums,target);
        if (right==-1) {
            return new int[]{-1,-1};
        }
        return new int[]{left,right};
    }

    public int findLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        while (left<right){
            int mid = (left+right)>>>1;
            if (nums[mid]==target) {
                right = mid;
            }else if(nums[mid]>target){
                right = mid;
            } else {
                left = mid+1;
            }
        }
        if(left>=nums.length) {
            return -1;
        }
        return nums[left]==target?left:-1;
    }

    public int findRight(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        while (left<right){
            int mid = (left+right)>>>1;
            if (nums[mid]==target) {
                left = mid+1;
            }else if(nums[mid]>target){
                right = mid;
            } else {
                left = mid+1;
            }
        }

        return nums[left-1]==target?left-1:-1;
    }
}
