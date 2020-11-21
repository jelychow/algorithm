import java.math.BigDecimal;
import java.math.BigInteger;

public class KMP {
    String pat;
    private int dp[][];

    public KMP(String pat) {
        this.pat = pat;
        int M = pat.length();
        dp = new int[M][256];
        dp[0][pat.charAt(0)] = 1;
        int X = 0;

        for (int i = 1; i < M; i++) {
            for (int j = 0; j < 256; j++) {
                dp[i][j] = dp[X][j];
            }
            dp[i][pat.charAt(i)] = i + 1;
            // 更新影⼦状态
            X = dp[X][pat.charAt(i)];

        }
    }

    public void pat(String pat){
        int m = pat.length();
        int r = 256;
        int dp[][] = new int[m][r];
        dp[0][pat.charAt(0)] = 1;
        int X = 0;
        for (int i = 1; i <m ; i++) {
            for (int j = 0; j <r ; j++) {
                dp[i][j] = dp[X][j];
            }
            dp[i][pat.charAt(i)] = i+1;
            X = dp[X][pat.charAt(i)];
        }

        for (int i = 1; i < m; i++) {

            for (int j = 0; j < r; j++) {
                dp[i][j] = dp[X][j];
            }
            dp[i][pat.charAt(i)] = i+1;
            X = dp[X][pat.charAt(i)];
        }
    }

    public int search(String text) {
        int M = pat.length();
        int N = text.length();
        int j = 0;
        for (int i = 0; i < N; i++) {
            j = dp[j][text.charAt(i)];
            if (j == M) {
                return i - M + 1;
            }
        }

        return -1;
    }

    public int search1(String text){
        int m = pat.length();
        int n = text.length();
        int x = 0;
        for (int i = 0; i <n ; i++) {
            x = dp[x][text.charAt(i)];
            if (x==m){
                return i-m+1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        System.out.println(new KMP("123").search("11234"));

        System.out.println("a".compareTo("b"));

        int a = new KMP("111").lcm(6,5);
        System.out.println(a);

    }

    public boolean canMeasureWater(int x, int y, int z) {

        if(x+y<z){
            return false;
        }
        if(x==0||y==0){
            return z==0||(x+y==z);
        }
        return z%GCD(x,y)==0;
    }

    int GCD(int a,int b){
        return b==0?a:GCD(b,a%b);
    }

    int lcm(int a,int b){
        return a*b/GCD(a,b);
    }



}
