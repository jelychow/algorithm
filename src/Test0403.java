import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// [1 2 4 98 3 48  ]
public class Test0403 {
    public static void main(String[] args) {

    }

    // 暴力法
    public static int getK(int[] nums, int target) {

        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            map.put(i, nums[i]);
        }
        // 总共数量
        int sum = 0;
        // 循环遍历
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        sum++;
                    }
                }
            }

        }
        return sum;
    }

    // 用hashmap 来优化
    public static int getK2(int[] nums, int target) {
        // 重复数据
        HashMap<Integer, ArrayList<Integer>> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            map.getOrDefault(nums[i], new ArrayList<>()).add(i);
        }
        // 总共数量
        int sum = 0;
        // 循环遍历
        Arrays.sort(nums);

        int index = Arrays.binarySearch(nums,target-2);

        for (int i = 0; i <= index; i++) {
            int left = i+1;
            int right = index;
            while (left<right){
                if (nums[i]+nums[left]+nums[right]==target){
                    sum++;
                }else if (nums[i]+nums[left]+nums[right]<target){
                    left++;
                }else if (nums[i]+nums[left]+nums[right]>target){
                    right--;
                }
            }
        }


        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                ArrayList<Integer> list = map.getOrDefault(target - nums[i] - nums[j], new ArrayList<>());
                list.remove((Integer) i);
                list.remove((Integer) j);
                sum += list.size();
            }
        }
        return sum;
    }
}
