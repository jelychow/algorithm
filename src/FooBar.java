import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

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
                while (state!=0);
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

    Semaphore semaphore = new Semaphore(1);
    public FooBar2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            semaphore.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            while(semaphore.availablePermits()!=0);
            printBar.run();
            semaphore.release();
        }
    }
}