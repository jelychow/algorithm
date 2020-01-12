import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupAnagrams {
    /**
     * 异位词
     * 先排序，把同一个 key 的用hashmap 里面list，进行保存，然后最后取出来
     *
     * 解题小技巧， hashmap 用 key value，一个 key 对应一个 list 可以分组保存数据，
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {

        HashMap<String,List<String>> map = new HashMap();

        for (int i = 0; i < strs.length; i++) {
            char[]temp = strs[i].toCharArray();
            Arrays.sort(temp);
            String key = new String(temp);
            if (!map.containsKey(key)) {
                ArrayList list1 = new ArrayList();
                list1.add(strs[i]);
                map.put(key,list1);
            } else if(map.containsKey(key)) {
                map.get(key).add(strs[i]);
            }
        }
       return new ArrayList<List<String>>(map.values());
    }
}
