import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NestedIterator implements Iterator<Integer> {

    LinkedList<Integer> items;

    public NestedIterator(List<NestedInteger> nestedList) {
        items = new LinkedList<>();
        dfs(nestedList);
    }

    public void dfs(List<NestedInteger> linkedList) {
        for (NestedInteger item : linkedList) {
            if (item.isInteger()) {
                items.offer(item.getInteger());
            } else {
                dfs(item.getList());
            }
        }
    }

    @Override
    public Integer next() {
        return items.poll();
    }

    @Override
    public boolean hasNext() {
        return items.size() > 0;
    }

    public interface NestedInteger {
        public boolean isInteger();
        public List<NestedInteger> getList();
        public Integer getInteger();
        /*
         *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
         *
         *     // @return the single integer that this NestedInteger holds, if it holds a single integer
         *     // Return null if this NestedInteger holds a nested list
         *
         *
         *     // @return the nested list that this NestedInteger holds, if it holds a nested list
         *     // Return null if this NestedInteger holds a single integer
         */
    }
}