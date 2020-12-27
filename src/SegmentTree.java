import java.util.Arrays;

import static java.lang.Math.log;

public class SegmentTree {

    public static void main(String[] args) {
        int n = 5;
        int leftLast = 1 << (((int) (log(n) / log(2))) + 1);
        System.out.println(leftLast);
        SegmentTree segmentTree = new SegmentTree();
        segmentTree.buildTree(n, leftLast);
//        Arrays.toString(segmentTree.tree);
        for (int i = 0; i < segmentTree.tree.length; i++) {
            System.out.printf(" " + segmentTree.tree[i]);
        }
        int[] pre = new int[]{0, 1, 2, 1, 0};
        int ans[] = new int[n];
        for (int i = 4; i >= 0; i--) {
            ans[i] = segmentTree.query(1, pre[i] + 1, leftLast) - leftLast + 1;
        }
        for (int i = 0; i < ans.length; i++) {
            System.out.printf(" " + ans[i]);
        }
    }

    int max = 5;

    int tree[] = new int[max << 2];

    // lastLeft 指的是最后一排 第一个元素
    /**
     * 算法执行顺序
     * 1 按照大小排列 cow
     * 2 建立数
     * 3 根据给定的条件来查找元素 ，并且更新元素
     */

    /**
     * @param n        数量
     * @param lastLeft
     */
    public void buildTree(int n, int lastLeft) {
        for (int i = lastLeft; i < lastLeft + n; i++) {
            tree[i] = 1;
        }

        // 如果不是第一个元素就要向上递归
        while (lastLeft != 1) {
            for (int i = lastLeft >> 1; i < lastLeft; i++) {
                tree[i] = tree[i << 1] + tree[i << 1 | 1];
            }
            lastLeft >>= 1;
        }
    }

    int query(int u, int num, int lastLeft) {

        tree[u]--;
        if (tree[u] == 0 && u >= lastLeft) {
            return u;
        }

        if (tree[u << 1] >= num) {
            return query(u << 1, num, lastLeft);
        } else {
            return query(u << 1 | 1, num - tree[u << 1], lastLeft);
        }

    }

}
