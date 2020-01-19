public class ExcelSheetColumnNumber {
    public static int titleToNumber(String s) {

        char[] chars = s.toCharArray();
        int res = 0;
        int carry = 1;
        for (int i = chars.length-1; i >=0; i--) {
            carry = (int)Math.pow(26,chars.length-1-i);
            int num = (chars[i]-'A'+1);
            res += num*carry;
        }
        return res;
    }

    public static void main(String[] args) {
        String a = "BA";
        titleToNumber(a);
    }
}
