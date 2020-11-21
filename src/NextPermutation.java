/**
 * 31. 下一个排列
 * 解题流程
 * 首先按照题意 下一个要更大 那么必然要把后面的大元素移到前面去，
 * 1 先找到下降点 j
 * 2 确定 j-end 为 下降区间 寻找最小的 k > j 此时已然满足下降区间有序
 * 3 交换 k j
 * 4 排序 j-end 此时 较大元素在前面 i-1，排序的是元素 i -end 所以可以得出结果
 */
public class NextPermutation {

    public void nextPermutation(int[] nums) {
        boolean hasNext = false;
        for(int i = nums.length-1;i>=1;i--){
            // 找到第一个下降低
            if(nums[i]>nums[i-1]){
                int j = i;
                int k = 0;
                int end = nums.length-1;
                while (end>=j) {
                    if (nums[end]>nums[i-1]){
                        k=end;
                        break;
                    }else {
                        end--;
                    }
                }

                int temp = nums[k];
                nums[k] = nums[i-1];
                nums[i-1] = temp;

                 end = nums.length-1;
                while (j<end){
                    int tmp = nums[j];
                    nums[j] = nums[end];
                    nums[end] = tmp;
                }
                break;
            }
        }

    }

}
