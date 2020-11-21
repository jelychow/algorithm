import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class AdvantageCount {

    // 优势洗牌算法
    public int[] advantageCount(int[] A, int[] B) {

        // 优势洗牌的核心 就是先排序，然后根据排序的顺序进行比较
        // 从前到后 如果小的大于 B min 则取 不然取第二个
        // 此题的思路类似于田忌赛马 但是主要流程应该抽象为  A   1 2 3  B 1 2 3
        //A 2 3 1
        //  首先要保证严格递增序列 才可以保证最大值
        int[] sortA = A.clone();
        Arrays.sort(sortA);
        int[] sortB = B.clone();
        Arrays.sort(sortB);

        /**
         *  保存大于 B 对应值的map 由于数组可能重复 所以需要用列表保存
         *  由于添加的顺序是从小到大排列的，我们出队也按照入队的方式进行排列
         *
         */
        HashMap<Integer,LinkedList<Integer>> bigger = new HashMap<>();
        // 待定元素
        LinkedList<Integer> remaining = new LinkedList<>();

        // 定义排序A 最小值指针
        int bMin = 0;

        for (int i = 0; i < sortA.length; i++) {

            if (sortA[i]>sortB[bMin]) {
              LinkedList resList =  bigger.getOrDefault(sortB[bMin],new LinkedList<>());
              resList.add(sortA[i]);
              bigger.put(sortB[bMin++],resList);
            } else {
                remaining.add(sortA[i]);
            }

        }

        int res[] = new int[sortA.length];
        for (int i = 0; i < B.length; i++) {
            // 不能用contains 避免重复元素影响
            if (bigger.getOrDefault(B[i],new LinkedList<>()).size()>0) {
                res[i] = bigger.getOrDefault(B[i],new LinkedList<>()).pop();
            } else {
                res[i] = remaining.pop();
            }
        }
        return res;
    }
}
