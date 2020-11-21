import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Result {

    /*
     * Complete the 'findMissingLog' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY sortedLogIds as parameter.
     */

    public static int findMissingLog(List<Integer> sortedLogIds) {
        int[]temp = new int[sortedLogIds.size()+1];
        for (int i = 0; i < sortedLogIds.size()+11; i++) {
            temp[i] = i;
        }

        for (int num : sortedLogIds) {
            temp[num] = -1;
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i]!=-1) {
                return temp[i];
            }
        }
        return -1;
    }

    public List<InlinedEmoticon> parseEmoticons(String text) {
        //TODO Implement
        char[]chars = text.toCharArray();
        List<InlinedEmoticon> list = new ArrayList();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i]==':') {
                InlinedEmoticon emoticon;
                if(i+1<chars.length&& chars[i+1]==')') {
                     emoticon = new InlinedEmoticon(1,i,2);
                    list.add(emoticon);
                }else if(i+2<chars.length&& chars[i+1]=='-'&&chars[i+2]==')') {
                     emoticon = new InlinedEmoticon(1,i,3);
                    list.add(emoticon);
                }else if(i+1<chars.length&& chars[i+1]=='/') {
                     emoticon = new InlinedEmoticon(2,i,2);
                    list.add(emoticon);
                }else if(i+1<chars.length&& chars[i+1]=='(') {
                     emoticon = new InlinedEmoticon(3,i,2);
                    list.add(emoticon);
                }else if(i+2<chars.length&& chars[i+1]=='-'&&chars[i+2]=='(') {
                     emoticon = new InlinedEmoticon(3,i,3);
                    list.add(emoticon);
                }
            }
        }
        return list;
    }

    public static class InlinedEmoticon {
        final int emoticonId;
        final int position;
        final int length;

        public InlinedEmoticon(int emoticonId, int position, int length) {
            this.emoticonId = emoticonId;
            this.position = position;
            this.length = length;
        }
    }

    // 3 1 10 2
    static int maxLogs(int[] logsCount) {
        int[]dp = new int[logsCount.length];
        if (logsCount.length==0) {
            return 0;
        } else if(logsCount.length==1) {
            return logsCount[0];
        }else if(logsCount.length==2) {
            return Math.max(logsCount[0],logsCount[1]);
        }
        dp[0] = logsCount[0];
        dp[1] = Math.max(logsCount[0],logsCount[1]);
        for (int i = 2; i < logsCount.length; i++) {
            dp[i] =Math.max((dp[i-2] +logsCount[i]),dp[i-1]);
        }

        return dp[logsCount.length-1];
    }

    public static long roundPrice(int price, int n) {
        if(n==0) {
            return price;
        }

        double countN = Math.pow( 10,  n);
        while (countN>price) {
            countN = Math.pow( 10,  --n);
        }
        BigDecimal decimal = new BigDecimal(countN);
        long res = Math.round(decimal.longValue()/countN);
        long result = (long) Math.max(countN,(long) (res*countN));
        return res;
    }

    //、、150、172、163、180、178、160、172、154、165、158


}