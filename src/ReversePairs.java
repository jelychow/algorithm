//493. 翻转对

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 翻转对即前面的大于后面的元素
 * 为了充分排序 需要从低至上进行比较
 * 先计算两侧翻转对 然后计算两个区间之间的翻转对
 * 最后排序
 */
public class ReversePairs {
    public int reversePairs(int[] nums) {

        return mergesort(nums,0,nums.length-1);
    }

    public int mergesort(int[]nums,int left,int right){
        if(left>=right){
            return 0;
        }
        int mid = (left+right)>>1;
        int count = mergesort(nums, left,mid) +  mergesort(nums, mid+1,right);
        // 计算本次merge的逆序对
        for (int i = left,j=mid+1; i <= mid; i++) {
            if(j<=right&&nums[i]*0.5>nums[j]){
                j++;
            }
            count+=j-mid-1;
        }


        Arrays.sort(nums,left,right);
        return count;
    }

    public void sort(int[] nums,int l1,int r1,int l2,int r2) {

        int[]temp = new int[r2-l1+1];
        int index = 0;
        // l1<=r1 终止条件是 r1+1
        while (l1<=r1&&l2<=r2) {

            if (nums[l1]<nums[l2]){
                temp[index] = nums[l1];
                l1++;
            } else {
                temp[index] = nums[l2];
                l2++;
            }
            index++;
        }
        // 如果还有剩余区间
        if (l1<=r1) {
            for (int i = l1; i <= r1; i++) {
                temp[index++] = nums[i];
            }
        }

        if (l2<=r2) {
            for (int i = l2; i <= r2; i++) {
                temp[index++] = nums[i];
            }
        }

        System.arraycopy(temp,0,nums,l1,r2-l1+1);

    }
}


