import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MergeArray {
    /**
     * 56. 合并区间
     * 本题算法小技巧
     * 1 while 循环依次寻找下一目标， 找不到继续外层循环
     * 2 Arrays.sort 方法使用
     * 3 list.toArray 方法 里面加上类型 转成指定类型
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals==null||intervals.length==0) {
            return new int[0][];
        }
        ArrayList<int[]> list = new ArrayList<>();

        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return (o1[0]-o2[0]);
            }
        });
        int i = 0;
        while (i<intervals.length) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            // 由于左边界可变 右边也可变 每次变化都需要比较新的数组左边界与右边届
            while (i+1<intervals.length&& right>=intervals[i+1][0]) {
                right = Math.max(right,intervals[++i][1]);
            }
            list.add(new int[]{left,right});
            i++;
        }
        return list.toArray(new int[0][]);
    }

}
