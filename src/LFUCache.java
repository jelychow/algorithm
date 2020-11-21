import java.util.*;

class LFUCache {

    // 按照访问顺序 排列 最少的在最前面
    /**
     * 如何实现 o1 get 已知 min 时间 只需要修改 min 位置的node 即可 或者在min+1 位置上操作node
     * put 操作 如果链表存在该节点 name 访问次数 +1 然后超出 capacity 需要 trim
     * addnode 这时候如果达到 capacity 那么就remove min  赋值成 min 如果 min node size 大于1
     * 那么 min 就不改变 然后给新添加的node
     * <p>
     * // 节点的操作分文  包含节点 update  不包括 add  put时候 节点不为空 那么 更新节点 min 否则 移除最少节点 添加节点 更新min
     */


    HashMap<Integer, Node> cache;
    HashMap<Integer, LinkedList<Node>> freq;
    int min;
    int capacity;

    public LFUCache(int capacity) {
        cache = new HashMap<>(capacity);
        freq = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (cache.get(key) == null) {
            return -1;
        }

        updateNode(key);
        return cache.get(key).value;
    }

    public void put(int key, int value) {
        if(capacity==0){
            return;
        }
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            updateNode(key);
        } else {
            if (cache.size() == capacity) {

                Node remove = removeNode(min);
                cache.remove(remove.key);
//                capacity--;
            }
            Node newNode = new Node(key, value);

            cache.put(key, newNode);
            addNode(newNode);

        }
    }

    public void updateNode(int key) {
        Node node = cache.get(key);
        LinkedList linkedHashSet = freq.get(node.freq);
        linkedHashSet.remove(node);

        if (linkedHashSet.size() == 0 && node.freq == min) {
            min++;
        }
        node.freq=node.freq+1;
        LinkedList set = freq.getOrDefault(node.freq, new LinkedList<>());
        set.add(node);
        freq.put(node.freq, set);
    }

    public void addNode(Node node) {
        LinkedList linkedHashSet = freq.getOrDefault(1, new LinkedList<>());
        linkedHashSet.addLast(node);
        freq.put(1, linkedHashSet);
        min = 1;
    }

    public Node removeNode(int key) {
        LinkedList<Node> linkedHashSet = freq.get(min);
        Node node = linkedHashSet.pollFirst();
//        linkedHashSet.remove(node);
//        freq.put(key, linkedHashSet);
        return node;
    }

    class Node {
        int key;
        int value;
        int freq = 1;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */