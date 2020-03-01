import java.util.LinkedList;
import java.util.Queue;

/**
 * 225. 用队列实现栈
 * 用单队列实现栈
 */
class MyStack {
    private Queue<Integer> queue;
    int size;
    /** Initialize your data structure here. */
    public MyStack() {
        queue = new LinkedList<>();
        size = 0;
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        queue.offer(x);
        size++;
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        int time = size;
        while (time<size) {
            queue.offer(queue.poll());
            time--;
        }

        size--;
        return queue.poll();
    }
    
    /** Get the top element. */
    public int top() {
        int time = size;
        while (time<size) {
            queue.offer(queue.poll());
            time--;
        }

        size--;
        int res = queue.poll();
        queue.offer(queue.poll());

        return res;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}