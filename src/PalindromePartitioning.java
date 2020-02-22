import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 131. 分割回文串
 * 解题思路
 * 首先对字符串进行处理
 * 对 dp 进行填表
 * 然后用回溯法进行
 */
public class PalindromePartitioning {
    boolean dp[][];
    String origin;

    public List<List<String>> partition(String s) {
        origin = s;
        int length = s.length();
        if (length==0) {
            return new ArrayList<>();
        }
        dp = new boolean[length][length];
        // 表长度默认为1

        for (int right = 0; right < origin.length(); right++) {
            for (int left = 0; left <= right; left++) {
                dp[left][right] = (s.charAt(left) == s.charAt(right)) && (right-left <= 2||dp[left+1][right]);
            }
        }
        List<List<String>> lists = new ArrayList<>();
        ArrayDeque<String> result = new ArrayDeque<>();
        backTrack(0, lists, result);
        return lists;
    }

    public void backTrack(int start, List<List<String>> lists, ArrayDeque<String> result) {

        if (start == origin.length()) {
            lists.add(new ArrayList<>(result));
            return;
        }

        for (int i = start; i < origin.length(); i++) {
            if (dp[start][i]) {
                String temp = origin.substring(start, i);
                result.addLast(temp);
                // 回溯的状态是以 for 循环 index 收敛 做控制的
                backTrack(i + 1, lists, result);
                result.removeLast();
            }
        }
    }
}
