import java.util.*;

public class LongestSubstring {
    /**
     * 3. 无重复字符的最长子串
     * 本题解题 牢记滑动窗口解决
     * 检查前面是否存在该字符，存在就删除前面的字符，不断向前移动字符 ，
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s==null||s.length()==0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int max = 0;
        ArrayList list = new ArrayList();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (!list.contains(c)){
                list.add(c);
                max = Math.max(max,list.size());
            } else {
                int index = list.indexOf(c);
                list.add(c);
                List subList = list.subList(index+1,list.size());
                list = new ArrayList(subList);
            }
        }
        return max;
    }

    public int longestPalindrome(String s) {
        char[]chars = s.toCharArray();
        HashMap<Character,Integer> map = new HashMap();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))){
                map.put(chars[i],(int)map.get(chars[i])+1);
            } else {
                map.put(chars[i],1);
            }
        }

        int max = 0;
       for(int num:map.values()){
           max = num>>1<<1;
           if (max%2==0&&num%2==1){
               max++;
           }
       }
        return max;
    }

    public static String findNumber(List<Integer> arr, int k) {
        // Write your code here


      return arr.contains(k)?"YES":"NO";

    }
}
