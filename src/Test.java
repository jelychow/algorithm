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

}
