import java.util.Stack;

class CQueue {

    // 用双栈实现

    /**
     *  step 1 A 用来保存原 stack 内容 B 用来保存逆序
     *  step 2 每次添加的时候数据放入 A
     *  step 3 每次移除的时候去 stack B 中寻找有没有之前需要移除的元素，有移除，没有则把 A 中元素加入B中，如果A 为空返回-1；
     */

    Stack<Integer> A ;
    Stack<Integer> B ;

    public CQueue() {
        A = new Stack<>();
        B = new Stack<>();
    }
    
    public void appendTail(int value) {
        A.push(value);
    }
    
    public int deleteHead() {

        if (!B.isEmpty()){
           return B.pop();
        }
        while (!A.isEmpty()){
            B.push(A.pop());
        }
        if(!B.isEmpty()){
            return B.pop();
        }
        return -1;
    }
}

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */