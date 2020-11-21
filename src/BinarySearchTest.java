public class BinarySearchTest {

    int findLeft(int nums[], int target){

        //先写普通的二分查找模板
        /**
         * 查找左侧边界就是查找 小于 target，数量
         */
        int left = 0;
        int right = nums.length;
        // 在左闭右开空间搜索
        while (left<right) {
            int mid = (left+right)>>1;
            // 可能有重复的 target
            if(target==nums[mid]) {
                // 寻找左边界 就要确定右边界
                right = mid;
            }else if(target<nums[mid]){
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    int findRight(int nums[], int target){

        //先写普通的二分查找模板
        /**
         * 查找右侧边界就是查找 <= target，数量
         */
        int left = 0;
        int right = nums.length;
        // 在左闭右开空间搜索
        while (left<right) {
            int mid = (left+right)>>1;
            // 可能有重复的 target
            if(target==nums[mid]) {
                // 寻找左边界 就要确定右边界
                left = mid+1;
            }else if(target<nums[mid]){
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left-1;
    }
}
