/**
 * 1114. 按序打印
 * 本题解题小技巧
 * 巧妙运用位运算来解决设置变量
 * 获取 某位 是否为 1   1<<n & state
 * n 是 从 0 开始 1<<0 等价于 1
 * 然后 用对象作为 锁 利用wait  notify 机制来解决问题
 */
class Foo {
    private Object lock;
    private int state;
    public Foo() {
        lock = new Object();
        state = 0;
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            state|=1;
            lock.notifyAll();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            while ((state&(1))<1){
                // printSecond.run() outputs "second". Do not change or remove this line.
                lock.wait();
            }
            printSecond.run();
            state|=1<<1;
            lock.notifyAll();

        }

    }

    public void third(Runnable printThird) throws InterruptedException {

        synchronized (lock) {
            while ((state&(1<<1))<1){
                // printThird.run() outputs "third". Do not change or remove this line.
               lock.wait();
            }
            printThird.run();
            lock.notifyAll();

        }

    }
}