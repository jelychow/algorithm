import java.util.concurrent.atomic.AtomicReference;

public class ReentrantSpinLock {
    private AtomicReference<Thread> reference = new AtomicReference<>();
    // 额外获取了多少次锁 就要对应释放多少次锁
    private int count = 0;

    public void lock(){
        Thread t = Thread.currentThread();
        // 如果是当前线程已经获取锁，那么 ++；
        if (t==reference.get()){
            count++;
        }
        // 自旋等待
        while (reference.get()!=null){
            reference.compareAndSet(null,t);
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
