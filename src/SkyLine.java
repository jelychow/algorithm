

import javafx.util.Pair;

import java.util.*;

public class SkyLine {

    // 线扫描法   来源公众号 @ 算法无遗策
    public List<List<Integer>> getSkyline2(int[][] buildings) {
        // 创建返回值
        List<List<Integer>> res = new ArrayList<>();
        // 保存所有的可能拐点
        Set<Pair<Integer, Integer>> pairs = new TreeSet<>(
                (o1, o2) -> !o1.getKey().equals(o2.getKey()) ? o1.getKey() - o2.getKey() : o1.getValue() - o2.getValue()); // 二元组
        // 将每一个建筑分成两个部分
        for (int[] build : buildings) {
            pairs.add(new Pair<>(build[0], -build[2]));
            pairs.add(new Pair<>(build[1], build[2]));
        }
        // 优先队列的最大堆
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1); // 最大堆
        // 记录之前的高度
        int prev = 0;
        // 遍历
        for (Pair<Integer, Integer> pair : pairs) {
            if (pair.getValue() < 0) queue.offer(-pair.getValue()); // 左端点 高度入堆
            else queue.remove(pair.getValue()); // 右端点 高度出堆
            Integer cur = queue.peek() == null ? 0 : queue.peek(); // 获取最大堆的当前顶点，当null时置为0
            if (prev != cur) {
                res.add(new ArrayList<Integer>() {{
                    add(pair.getKey());
                    add(cur);
                }});
                prev = cur;
            }
        }
        return res;
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        Set<Pair<Integer, Integer>> pairs = new TreeSet<>(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return (!o1.getKey().equals(o2.getKey()) ? o1.getKey() - o2.getKey() : o1.getValue() - o2.getValue());
            }
        });
        for (int[] build : buildings) {
            pairs.add(new Pair<>(build[0], -build[2]));
            pairs.add(new Pair<>(build[1], build[2]));
        }
        // 大顶堆 默认是小顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o2;
            }
        });
        int prev = 0;
        for (Pair<Integer, Integer> pair : pairs) {
            if (pair.getValue() < 0) {
                queue.offer(-pair.getValue());
            } else {
                queue.remove(pair.getValue());
            }
            Integer cur = queue.peek() == null ? 0 : queue.peek();
            if (prev != cur) {
                res.add(new ArrayList<Integer>() {
                    {
                        add(pair.getKey());
                        add(cur);
                    }
                });
                prev = cur;
            }
        }
        return res;
    }

}
