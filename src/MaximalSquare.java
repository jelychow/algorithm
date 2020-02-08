public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        if (matrix.length==0){
            return 0;
        }
        int length = matrix.length;
        int dp[][] = new int[length][matrix[0].length];
        int max = 0;

        for (int i = 0; i <length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]=='1'){
                    if(i-1>=0&&j-1>=0){
                        dp[i][j] = Math.min(dp[i-1][j-1],Math.min(dp[i-1][j],dp[i][j-1])) +1;
                    } else {
                        dp[i][j] = 1;
                    }
                    System.out.println( dp[i][j]+"i:"+i+"j"+j);
                    max = Math.max(dp[i][j],max);
                }
            }
        }

        return max*max;
    }
}
