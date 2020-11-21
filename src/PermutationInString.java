import java.util.Arrays;

public class PermutationInString {

    private int[] smap1 = new int[26];
    private int[] smap2 ;
    public boolean checkInclusion(String s1, String s2) {

        int l1 = s1.length();
        for (int i = 0; i < l1; i++) {
            smap1[s1.charAt(i)-'a']++;
        }

        for (int i = 0; s2.length()>=l1&&i < s2.length()-l1+1; i++) {
            smap2 = new int[26];
            for (int j = 0; j < l1; j++) {
                smap2[s2.charAt(i+j)-'a']++;
            }
            if (Arrays.equals(smap1,smap2)){
                return true;
            }
        }
        return false;
    }
}
