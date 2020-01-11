public class SearchMatrix {
    /**
     *  搜索二维矩阵 II
     * [
     *   [1,   4,  7, 11, 15],
     *   [2,   5,  8, 12, 19],
     *   [3,   6,  9, 16, 22],
     *   [10, 13, 14, 17, 24],
     *   [18, 21, 23, 26, 30]
     * ]
     * 算法思路 先 判断在不在 第一行
     * 在的化判断在前半区 还是 后半区
     * 如果在前半区，则 前半区查找，依次查找后序数组前半区（先判断在不在数组里面）
     * 如果在后半区 判断是否存在当前数组后半区，然后循环第二组
     */

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length==0||matrix[0].length==0) {
            return false;
        }

        int length = matrix.length;
        int lastMid = -1;
        for (int i = 0; i < length; i++) {
            int[] arr = matrix[i];
            if (arr[0]<=target&&arr[arr.length-1]>=target) {

                if(lastMid==-1) {
                   lastMid = binarySearch(arr,0,arr.length-1,target);
                   if (arr[lastMid]==target) {
                       return true;
                   }
                } else {
                    if (arr[lastMid]>target) {
                        lastMid = binarySearch(arr,0,lastMid,target);
                        if (arr[lastMid]==target) {
                            return true;
                        }
                    } else {
                        lastMid = binarySearch(arr,lastMid,arr.length-1,target);
                        if (arr[lastMid]==target) {
                            return true;
                        }
                    }

                }
            }
        }

        return false;

    }

    int  binarySearch(int[]nums,int left,int right,int target) {
        int res = 0;
        while (left<=right) {
            int mid = (left+right)>>>1;
            res = mid;
            if (nums[mid]==target) {
                return mid;
            }
            if (nums[mid]>target) {
                right = mid-1;
            } else {
                left = mid +1;
            }
        }
        return res;
    }
}
