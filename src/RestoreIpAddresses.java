import java.util.ArrayList;
import java.util.List;

/**
 * 复原IP地址
 * ip 地址最大 255
 *
 * 本题解题小技巧：
 * 充分理解回溯思想
 * 回溯就是一种试错 然后回来重试
 * 可以利用堆栈来优化回溯
 * 每次执行完毕 要对上次结果进行pop
 */
class RestoreIpAddresses {
    class Solution {
        // 利用回溯来解决问题
        String origin;
        public List<String> restoreIpAddresses(String s) {
            if (s==null) {
                return new ArrayList<>();
            }
            // 向前走一步 剩余的数量小于<3*step 有解
            List<String> list = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            origin = s;
            backTrack(0,0,list,builder);
            return list;
        }

        public void backTrack(int start,int time,List<String> list,StringBuilder builder){
            if(time>4) {
                return;
            }
            if (time==4&&start==origin.length()){
                list.add(builder.substring(1).toString());
            }

            for (int i = start; i < origin.length()&&i<start+3; i++) {
                String temp = origin.substring(start,i+1);
                if (temp.length()>1&&temp.charAt(0)=='0') {
                    return;
                }
                if (i-start==2) {
                    int sum = (origin.charAt(start)-'0')*100+ (origin.charAt(start+1)-'0')*10+ (origin.charAt(start+2)-'0');
                    if (sum>255){
                        return;
                    }

                }
                backTrack(i+1,time+1,list,builder.append(".").append(temp));
                builder.delete(builder.lastIndexOf("."),builder.length());
            }
        }
    }
}