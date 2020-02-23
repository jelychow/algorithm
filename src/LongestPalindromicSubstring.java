public class LongestPalindromicSubstring {
    /**
     * 5. 最长回文子串
     * 中心扩散法
     * 执行过程：遍历每个节点是否是最长回文串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {

        if (s.length() < 2) {
            return s;
        }

        String maxStr = "";
        for (int i = 0; i < s.length(); i++) {
            maxStr = maxStr.length() > palindrome(s, i, i + 1).length() ? maxStr : palindrome(s, i, i + 1);
            maxStr = maxStr.length() > palindrome(s, i, i).length() ? maxStr : palindrome(s, i, i);
        }

        return maxStr;

    }

    // 寻找以 left right 为中心点的回文串,向两端扩散
    public String palindrome(String s, int left, int right) {

        while (left >= 0 && right < s.length()) {
            if (s.charAt(left) == s.charAt(right)) {

                left--;
                right++;
            } else {
                break;
            }
        }
        // 由于最后一个 left 肯定是不符合条件的元素 所以选 left+1；
        return s.substring(left + 1, right);
    }

}
