import java.util.Arrays;
import java.util.Comparator;

/**
 * 俄罗斯套娃问题
 * 354. 俄罗斯套娃信封问题
 */
public class RussianDollEnvelopes {

    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                /**
                 * 解题小技巧 由于 同样宽度套娃只能使用一次，优先使用宽的那个 所以采用降序
                 * 降序排序 是 o2 - o1
                 */
                return o1[0]==o2[0]?(o2[1]-o1[1]):o1[0]-o2[0];
            }
        });
        int temp[] = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            // 当前套娃最大值 = 前面最大值 +1 || 当前
            temp[i] = envelopes[i][1];
        }
        return lengthOfLIS(temp);
    }

    public int lengthOfLIS(int[] nums) {
        // 寻找第一个大于 poker 的元素
        int[]pile = new int[nums.length];
        int lastHead = 0;
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];
            int left = 0;
            int right = lastHead;
            while (left<right) {
                int mid = (left+right)>>>1;
                if (poker<=pile[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            if (left==lastHead) {
                lastHead++;
            }
            pile[left] = poker;
        }

        return lastHead;
    }
}
