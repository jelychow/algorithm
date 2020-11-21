import java.util.Arrays;
import java.util.Comparator;

public class MinimumLengthEncoding {

    public int minimumLengthEncoding(String[] words) {

        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length()-o1.length();
            }
        });
        Trie trie = new Trie();
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            count += trie.insert(words[i]);
        }
        return count;
    }


    class Trie {
        TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        TrieNode node;
        public int insert(String word) {
            node = root;
            char[] chars = word.toCharArray();
            boolean isNew = false;
            for (int i = chars.length-1; i >=0; i--) {
                char item = chars[i];
                if(node.children[item-'a']==null){
                    isNew = true;
                    node.children[item-'a'] = new TrieNode();
                }
                node = node.children[item-'a'];
            }
            return isNew?word.length()+1:0;

        }
    }

    class TrieNode {
        char val;
        TrieNode[] children = new TrieNode[26];

        public TrieNode() {
        }
    }

}
