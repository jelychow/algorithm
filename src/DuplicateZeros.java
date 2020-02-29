/**
 * 1089. 复写零
 * 解题思路 102033022
 * 1 记录 0 的个数，注意边界条件上的
 * [1,0,2,3,0,4,5,0]
 * 遍历数组 确定 fromindex  然后从 fromindex -1 开始计算复制0
 */
public class DuplicateZeros {
    public void duplicateZeros(int[] arr) {

        int n = arr.length;
        int i = 0;
        int j = 0;
        // 快指针
        while (j<n){
            if(arr[i]==0){
                j++;
            }
            i++;
            j++;
        }
        i--;
        j--;

        while (i>=0){
            arr[j] = arr[i];
            if (arr[i]==0){
                arr[--j] = 0;
            }
            i--;
            j--;
        }

    }

    public int removeElement(int[] nums, int val) {
        int i = 0;
        int end = nums.length-1;

        for ( i = 0; i <= end; ) {
            if (nums[i]==val){
                nums[i] = nums[end];
                end--;
            } else {
                i++;
            }
        }
        return end;
    }

    }
