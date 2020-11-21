import java.util.concurrent.atomic.AtomicReference;

public class ReentrantSpinLock {
    /**
     * java
     *  cas compare and swap // set
     */
    private AtomicReference<Thread> reference = new AtomicReference<>();
    // 额外获取了多少次锁 就要对应释放多少次锁
    private int count = 0;

    public void lock(){
        Thread t = Thread.currentThread();
        // 如果是当前线程已经获取锁，那么 ++；无需自旋
        if (t==reference.get()){
            count++;
            return;
        }
        // 自旋等待 当前引用不是 null 那么就不断重试也就是自旋
        while (!reference.compareAndSet(null,t)){
            System.out.println("开始自旋了");
        }
    }

    public void unlock(){
        Thread t = Thread.currentThread();
        if(t==reference.get()){
            if(count>0){
                count--;
            }else {
                reference.set(null);
            }
        }
    }
}
