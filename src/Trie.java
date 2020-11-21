class Trie {

    private TrieNode head;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        head = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode node = head;
        for (int i = 0; i < word.length(); i++) {
            if (!node.contains(word.charAt(i))) {
                node.set(word.charAt(i), new TrieNode());
            }
            node = node.get(word.charAt(i));
        }
        node.isEnd = true;
    }

    public void insertWord(TrieNode node, String word, int index) {
        if (index == word.length()) {
            return;
        }
        if (node.get(word.charAt(index)) == null) {
            node.set(word.charAt(index), new TrieNode());
        }
        insertWord(node.get(word.charAt(index)), word, index + 1);
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode node = head;
        for (int i = 0; i < word.length(); i++) {
            if (node.contains(word.charAt(i))) {
                node = node.get(word.charAt(i));
            } else {
                return false;
            }
        }

        return node.isEnd;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode node = head;
        for (int i = 0; i < prefix.length(); i++) {
            if (node.contains(prefix.charAt(i))) {
                node = node.get(prefix.charAt(i));
            } else {
                return false;
            }
        }

        return true;
    }

    class TrieNode {

        int size = 26;
        // 创建 一个26size长度的数组 从低到高存储26个字母
        TrieNode[] links;

        boolean isEnd;

        public TrieNode() {
            links = new TrieNode[size];
        }

        public void set(char ch, TrieNode node) {
            links[ch - 'a'] = node;

        }

        public boolean contains(char ch) {
            return links[ch - 'a'] != null;
        }

        public TrieNode get(char ch) {
            return links[ch - 'a'];
        }

    }
}