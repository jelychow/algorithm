import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1188. 设计有限阻塞队列
 * 基本上阻塞队列都是采用 lock 来实现的
 * 题中加上了 一个条件 有界 说明是有容量的
 * 队列默认的条件 是 empty不能消费
 * 有界队列添加一个条件 full
 */
class BoundedBlockingQueue {

    ReentrantLock lock;
    Condition notEmpty;
    Condition notFull;
    Queue<Integer> queue;
    int size = 0;
    public BoundedBlockingQueue(int capacity) {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        queue = new LinkedList<>();
        size = capacity;
    }
    // 入队条件之一是不满 才能入队 否则就 await
    public void enqueue(int element) throws InterruptedException {
        try {
            lock.lock();
            // 多线程记得用 while 判断 不然 不能在原地等待
            while (queue.size()>=size){
                notFull.await();
            }
            queue.offer(element);
            notEmpty.signal();

        }finally {
            lock.unlock();
        }
    }
    // 出队的时候 首要满足条件就是队列不为空
    public int dequeue() throws InterruptedException {
        try {
            lock.lock();
            // 多线程记得用 while 判断 不然 不能在原地等待
            while (queue.isEmpty()){
                notEmpty.await();
            }
           int res = queue.poll();
           notFull.signal();
            return res;
        }finally {
            lock.unlock();
        }
    }
    
    public int size() {
        try {
           lock.lock();
           return queue.size();
        }finally {
            lock.unlock();
        }
    }

    class Producer implements Runnable{
        private BoundedBlockingQueue queue;

        public Producer(BoundedBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                this.queue.enqueue((int) Math.random());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable{
        private BoundedBlockingQueue queue;

        @Override
        public void run() {
            try {
                this.queue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // 用 wait notifyAll 实现 阻塞队列  生产消费模型  生产这 消费者 用两个 runnable 进行模拟
    class WaitBlockingQueue {
        int size ;
        Queue<Integer> queue;
        public WaitBlockingQueue(int capacity) {
            queue = new LinkedList<>();
            size = capacity;
        }

        public synchronized void  enqueue(int element) throws InterruptedException {
            while (queue.size()==size){
                wait();
            }
            queue.offer(element);
            notifyAll();
        }

        public synchronized int dequeue() throws InterruptedException {
            while (queue.isEmpty()){
                wait();
            }
            int res = queue.poll();
            notifyAll();
            return res;
        }

        public synchronized int size() {
            return queue.size();
        }
    }
}