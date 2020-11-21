import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

class ZeroEvenOdd {

    private int n;
    private Semaphore z, e, o;

    public ZeroEvenOdd(int n) {
        this.n = n;
        z = new Semaphore(1);
        e = new Semaphore(0);
        o = new Semaphore(0);

    }

    public static void main(String[] arss) {
        IntConsumerImpl intConsumer = new IntConsumerImpl();

        ZeroEvenOdd odd = new ZeroEvenOdd(5);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    odd.zero(new IntConsumerImpl());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    odd.even(new IntConsumerImpl());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    odd.odd(new IntConsumerImpl());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            z.acquire();
            printNumber.accept(0);
            // 奇数 当前数为奇数打印偶数的 放开偶数的信号
            if ((i & 1) == 1) {
                e.release();
            } else {
                o.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i = i + 2) {
            e.acquire();
            printNumber.accept(i);
            z.release();
        }

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i = i + 2) {
            o.acquire();
            printNumber.accept(i);
            z.release();
        }

    }

    static class IntConsumerImpl implements IntConsumer {

        int value;

        @Override
        public void accept(int value) {
            this.value = value;
            System.out.println(value);
        }

        @Override
        public IntConsumer andThen(IntConsumer after) {
            return null;
        }
    }

    class ZeroEvenOdd2 {

        private int n;
        private volatile int start = 1;

        private Lock reentrant = new ReentrantLock();
        private Condition z = reentrant.newCondition();
        private Condition e = reentrant.newCondition();
        private Condition o = reentrant.newCondition();
        private volatile int state = 0;

        public ZeroEvenOdd2(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            reentrant.lock();
            try {
                while (start <= n) {
                    if (state != 0) {
                        z.await();
                    }
                    printNumber.accept(0);
                    if (start % 2 == 0) {
                        state = 2;
                        e.signal();
                    } else {
                        state = 1;
                        o.signal();
                    }
                    z.await();
                }
                o.signal();
                e.signal();
            } finally {
                reentrant.unlock();
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            reentrant.lock();
            try {
                while (start <= n) {
                    if (state != 2) {
                        e.await();
                    } else {
                        printNumber.accept(start++);
                        state = 0;
                        z.signal();
                    }
                }
            } finally {
                reentrant.unlock();
            }
        }


        public void odd(IntConsumer printNumber) throws InterruptedException {
            reentrant.lock();
            try {
                while (start <= n) {
                    if (state != 1) {
                        o.await();
                    }

                    printNumber.accept(start++);
                    state = 0;
                    z.signal();
                }

            } finally {
                reentrant.unlock();
            }
        }
    }

}