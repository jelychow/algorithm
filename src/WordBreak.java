import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 139. 单词拆分
 *
 * 本题解析： 方法很容易想到就是回溯
 * 最难的还是在于  aaaaaaaaaaa 这种超长的字符串
 * 很容易引起堆栈溢出。发生超时
 *
 * 解题小技巧 ： 理解 尾递归的含义 有助于解决本题
 *
 * 通过不断的保存上级计算结果 然后来确定返回数据
 *
 * 本题优化技巧 通过 memo 来保存每个 index那个点是否可以执行
 * 主要是不可以执行的 index ，结果储存起来 然后下一级调用
 *
 */



public class WordBreak {

    Boolean[] cache;

    public boolean wordBreak(String s, List<String> wordDict) {

        if (s == null || s.length() == 0 || wordDict.size() == 0) {
            return false;
        }

        cache = new Boolean[s.length()];

        HashMap<String, String> wordMap = new HashMap<>();
        for (int i = 0; i < wordDict.size(); i++) {
            wordMap.put(wordDict.get(i), "1");
        }
        return wordBreakHelper(s, 0, wordMap);

    }

    public boolean wordBreakHelper(String s, int i, HashMap<String, String> wordMap) {

        Math a;
        if (i == s.length()) {
            return true;
        }
        if (cache[i]!=null) {
            return cache[i];
        }

        for (int k = i; k < s.length(); k++) {
            String temp = s.substring(i, k + 1);
            if (wordMap.containsKey(temp)) {
                boolean res = wordBreakHelper(s, k + 1, wordMap);
                if (res) {
                    return cache[i]=true;
                }
            }
        }

        return cache[i]=false;
    }

    // dp[i]  首先定义 dp[i]为 当前节点是否可以访问到
    /**
     * 可以访问到就标记 为 true
     * dp[i] = true
     * 先判断内层循环 是 true 可以访问 再确定外层循环
     * substring(i+1,right)
     * dp[r] = true;
     * 依次遍历
     * 为true 便退出
     */
}
