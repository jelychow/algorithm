import java.util.Stack;

class MinStack {

    /**
     * 155. 最小栈
     * 最小栈的解题 关键在于
     * push 添加的时候 如果入队有多个最小元素，那么 辅助栈中都要放入 该元素
     * 不然在 pop 的时候 弹出一个 最小元素，数据结构的最小栈将发生改变
     */

    private Stack<Integer> normalStack;
    private Stack<Integer> minStack;
    /** initialize your data structure here. */
    public MinStack() {
        normalStack = new Stack();
        minStack = new Stack();
    }
    
    public void push(int x) {
        normalStack.push(x);
        if (minStack.isEmpty()||minStack.peek()>=x){
            minStack.push(x);
        }
    }

    /**
     * 23411
     * 21
     */
    public void pop() {
       if(normalStack.pop().intValue()==minStack.peek().intValue()){
           minStack.pop();
       }
    }
    
    public int top() {
        return normalStack.peek();
    }
    
    public int getMin() {
      return   minStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */