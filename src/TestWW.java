import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class TestWW {
    public static void main(String[] args) {

        int a = 4;
        int b = 0;
        /**
         * 000000000000000000001 >>
         */
        System.out.println(a&b);
        System.out.println(a|b);
        System.out.println(a^b);
        System.out.println(a>>>2);

        System.out.println(a<<1);

        a<<=1;
        System.out.println(a);

        System.out.println("time".indexOf("me"));

        // hash capcity 2 n
        // 2n -1  1111111111  100000000000000

//        System.out.println(16&(16-1));
        //
        int state = 1;


        switch (state){
            case 1>>2:

                break;
            case 1<<1:
                break;

        }

        // &  ^ |
        /**
         * &
         */

    }

    public int minimumLengthEncoding(String[] words) {

        int total = 0;
//        for (int i = 0; i < words.length; i++) {
//            String S = words[i];
//            words[i] = new StringBuilder(S).reverse().toString();
//        }

        for (int i = 0; i <words.length ; i++) {
            if(i+1<words.length&&words[i+1].indexOf(words[i])+words[i].length()==words[i+1].length()){

            }else {
                total+=words[i].length()+1;
            }
        }
        return total;
    }

    public int minimumLengthEncoding2(String[] words) {

        HashSet<String> set = new HashSet(Arrays.asList(words));

        for (int i = 0; i < words.length; i++) {

            for (int j = 1; j < words[i].length(); j++) {
                set.remove(words[i].substring(j));
            }
        }
        int total = 0;

        for (String s:set) {
            total+=s.length()+1;
        }
        return total;
    }


    }
