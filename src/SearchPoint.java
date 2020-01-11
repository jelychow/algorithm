class SearchPoint {
    /**
     * 33. 搜索旋转排序数组
     * 本题主要目标 在反转数组里面搜索目标
     * 但是先决条件要达到 log N 明显暗示我们只能使用二分查找
     *
     * 首先 我们需要查找反转点，然后在有序数组里面进行寻找
     *
     * 反转点的条件 1，大于 后序元素， 如何寻找反转点？
     * 如果前半部有序，那么反转点在后面 确定后半部 左边界 left = mid+1；
     * 反之 反转点在左侧 左侧右边界 right = mid -1；
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        int point = -1;
        while (left<=right) {
            int mid = (left+right)>>>1;
            if (mid-1>=0&&(nums[mid-1]>nums[mid])) {
                point = mid;
            }
            if (mid+1<nums.length&&(nums[mid]>nums[mid+1])) {
                point = mid+1;
            }
            if (nums[mid]>=nums[left]) {
                left = mid+1;
            } else {
                right = mid-1 ;
            }
        }
        if (point==-1){
            return binarySearch(nums,0,nums.length-1,target);

        } else {
            if(nums[point]<=target&&nums[nums.length-1]>=target) {
                return binarySearch(nums,point,nums.length-1,target);
            } else {
                return binarySearch(nums,0,point-1,target);
            }
        }
    }

    int  binarySearch(int[]nums,int left,int right,int target) {
        while (left<=right) {
            int mid = (left+right)>>>1;
            if (nums[mid]==target) {
                return mid;
            }
            if (nums[mid]>target) {
                right = mid-1;
            } else {
                left = mid +1;
            }
        }
        return -1;
    }
}