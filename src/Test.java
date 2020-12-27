import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class Test {
    private Map<String,Integer> map;
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException {
        Class clazz = Test.class;
        clazz.getComponentType();
        Field field = clazz.getDeclaredField("map");
        Type type = field.getGenericType();
        if(type instanceof ParameterizedType) {
            System.out.println(clazz.getGenericSuperclass().getTypeName());
            System.out.println(Class.forName("Test"));
            System.out.println(((ParameterizedType) type).getRawType());
            System.out.println(((ParameterizedType) type).getActualTypeArguments()[0]);
        }

    }

    List<String> list = new ArrayList<>();
    HashMap<Integer,List<String>> cacheMap = new HashMap<>();

    public List<String> wordBreak(String s, List<String> wordDict) {

        if (s==null||s.length()==0||wordDict.size()==0) {
            return new ArrayList<>();
        }

        HashMap<String,String> wordMap = new HashMap<>();
        for (int i = 0; i < wordDict.size(); i++) {
            wordMap.put(wordDict.get(i),"1");
        }
        StringBuilder builder = new StringBuilder();
        wordBreakHelper(s,0,wordMap,builder);
        return list;
    }

    public void wordBreakHelper(String s, int i, HashMap<String,String> wordMap,StringBuilder builder){

        if (cacheMap.containsKey(i)){
            LinkedList <String> linkedList = (LinkedList<String>) cacheMap.get(i);

        }

        if (i==s.length()) {
            builder.delete(builder.lastIndexOf(" "),builder.length());
            list.add(builder.toString());
            return;
        }

        for (int k = i; k < s.length(); k++) {
            String temp = s.substring(i,k+1);
            if (wordMap.containsKey(temp)) {
                wordBreakHelper(s,k+1,wordMap,builder.append(temp).append(" "));
                int index = builder.lastIndexOf(temp);
                cacheMap.getOrDefault(i,new LinkedList<>()).add(builder.toString());
                builder.delete(index,builder.length());

            }
        }
    }

    int[]s;
    int vals[];
    public Test(int[] nums) {
        s = new int[nums.length+1];
        vals = nums;
        for (int i = 1; i <= s.length; i++) {
            init(i,nums[i-1]);
        }
    }

    public int lowbit(int x){
        return x&(-x);
    }

    public void init(int i, int val) {
        while (i<s.length){
            s[i]+=val;
            i+=lowbit(i);
        }
    }

    public void update(int i, int val) {
        vals[i] = val;
        i++;
        int diff = val- vals[i-1];
        while (i<s.length){
            s[i]+=diff;
            i+=lowbit(i);
        }
    }

    public int sumRange(int i, int j) {

        /**
         * 不+1 是因为区间要包括 i
         */
        return sum(j+1) - sum(i);
    }

    public int sum(int x){
        int sum = 0;
        while (x>0){
            sum+=s[x];
            x-=lowbit(x);
        }
        return sum;
    }

    public boolean lemonadeChange(int[] bills) {
        int fiveCount= 0,tenCount= 0;

        for (int i = 0; i < bills.length; i++) {
            if (bills[i]==20){
                if  (!(fiveCount>=1&&tenCount>=1||fiveCount>=3)){
                    return false;
                }else {
                    if (tenCount>=1){
                        tenCount--;
                        fiveCount--;
                    }else {
                        fiveCount-=3;
                    }
                }
            }else if(bills[i]==10){
                if (fiveCount<1){
                    return false;
                }else {
                    fiveCount--;
                }
                tenCount++;
            }else {
                fiveCount++;
            }
        }
        return true;
    }

    public int wiggleMaxLength(int[] nums) {
        if(nums.length<2){
            return nums.length;
        }
        int up = 1,down = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i]-nums[i-1]>0){
                up = down+1;
            }else if (nums[i]-nums[i-1]<0){
                down = up+1;
            }
        }

        return Math.max(up,down);
    }

    public int maxProfit(int[] prices, int fee) {
        if (prices.length<2){
            return 0;
        }
        int dp[][] = new int[prices.length][2];
        // 0 不持有  1 持有
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(prices[i]-fee+dp[i-1][1],dp[i-1][0]);
            dp[i][1] = Math.max(-prices[i]+dp[i-1][0],dp[i-1][1]);

        }
        return dp[prices.length-1][0];

    }

    public char findTheDifference(String s, String t) {
        int mapS[] = new int[26];
        int mapT[] = new int[26];
        for (int i = 0; i < s.length(); i++) {
            mapS[s.charAt(i)-'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            mapT[t.charAt(i)-'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (mapS[i]!=mapT[i]){
                return (char) ('a' + i);
            }
        }
        return 'a';
    }

    public void rotate(int[][] matrix) {
        int index = 0;
        while(index<matrix.length/2){
            int [] temp = matrix[index];
            matrix[index] = matrix[matrix.length-1-index];
            matrix[matrix.length-1-index] = temp;
            index++;
        }

        for (int i = 0; i <matrix.length ; i++) {
            for (int j = i+1; j < matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}
