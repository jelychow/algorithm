import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue {
    /**
     * 两种常见办法实现阻塞
     * 1 syncronized
     *   syncronized method
     *   {
     *      wait
     *      notifyall()
     *   }
     *  obj.wait
     *  thread.sleep
     *
     * 2 lock
     *  try {
     *     lock.lock();
     *     while(条件不满足 ){
     *         condition.await();
     *     }
     *     逻辑操作
     *
     *  } finally{
     *      lock.unlock();
     *  }
     *
     */

    private int capacity;
    private Queue<Integer> queue;
    private ReentrantLock reentrantLock;
    private Condition notEmpty,notFull;

    public BlockedQueue(int size) {
        this.capacity = size;
        queue = new LinkedList<Integer>();
        reentrantLock = new ReentrantLock();
        notEmpty = reentrantLock.newCondition();
        notFull = reentrantLock.newCondition();
    }

    public void enqueue(int obj ){

        try {
            reentrantLock.lock();
            while (queue.size()>=capacity){
                notFull.await();
            }
            queue.offer(obj);
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }

    }

    public int dequeue(){

        try {
            reentrantLock.lock();
            while (queue.size()==0){
                notEmpty.await();
            }
           int res = queue.poll();
           notFull.signal();
           return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return -1;
    }

    public int getCapacity(){
        try {
           reentrantLock.lock();
           return queue.size();
        }finally {
            reentrantLock.unlock();
        }
    }

    public synchronized void enqueue2(int obj) throws InterruptedException {

        while (queue.size()>=capacity){
            queue.wait();
        }
        queue.offer(obj);
        queue.notifyAll();
    }

    public synchronized  int dequeue2()  {
        System.out.println("");
        while (queue.size()==0){
            try {
                queue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int res =  queue.poll();
        queue.notifyAll();
        return res;
    }


}
