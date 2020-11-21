import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {
    private int n;
    private CyclicBarrier cyclicBarrier;
    private volatile int state = 0;

    public FooBar(int n) {
        this.n = n;
        cyclicBarrier = new CyclicBarrier(2);
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.
            try {
                while (state != 0) ;
                printFoo.run();
                state = 1;
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            {

                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                printBar.run();
                state = 0;

            }
        }
    }
}


class FooBar2 {
    private int n;

    public FooBar2(int n) {
        this.n = n;
    }

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    volatile boolean isFinish = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        lock.lock();
        for (int i = 0; i < n; i++) {

            try {
                while (!isFinish) {
                    condition.await();
                }
                printFoo.run();
                isFinish = false;
                condition.signal();
            } finally {

            }
        }
        lock.unlock();
    }

    public void bar(Runnable printBar) throws InterruptedException {
        lock.lock();
        for (int i = 0; i < n; i++) {

            try {
                while (isFinish) {
                    condition.await();
                }
                printBar.run();
                isFinish = true;
                condition.signal();
            } finally {

            }
        }
        lock.unlock();
    }
}