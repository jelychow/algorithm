public class EditDistance {

    /**
     * 72. 编辑距离
     * 编辑距离 首先要简历 dp 表
     * 然后自低向上计算距离
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int dp[][] = new int[m+1][n+1];

        // 填表
        for (int i = 1; i <=m ; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i <=n ; i++) {
            dp[0][i] = n;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <=n ; j++) {
                // 添加 删除 跳过操作
                if(word1.charAt(i-1)==word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    // 比较插入删除替换 也就是dp 表相邻的三个节点
                    dp[i][j] = min(dp[i][j-1]+1,dp[i-1][j]+1,dp[i-1][j-1]+1);
                }
            }
        }

        return dp[m][n];
    }

    public int min(int a,int b,int c) {
        return Math.min(a,Math.min(b,c));
    }
}
