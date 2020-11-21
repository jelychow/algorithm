import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
 * 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
 * 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
 * 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/fizz-buzz-multithreaded
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class FizzBuzz {
    private int n;
    ReentrantLock lock;
    Condition threeCondition ;
    Condition fiveCondition ;
    Condition fifteenCondition ;
    Condition otherCondition ;


    public FizzBuzz(int n) {
        this.n = n;
        lock = new ReentrantLock();
        threeCondition = lock.newCondition();
        fifteenCondition = lock.newCondition();
        fiveCondition = lock.newCondition();

    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        try {
            lock.lock();
            if (n%15==0||n%3!=0){
                threeCondition.await();
            }
            printFizz.run();
        }finally {
            lock.unlock();
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        try {
            lock.lock();
            if (n%15==0||n%5!=0){
                fiveCondition.await();
            }
            printBuzz.run();
        }finally {
            lock.unlock();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        try {
            lock.lock();
            if (n%15!=0){
                fifteenCondition.await();
            }
            printFizzBuzz.run();
        }finally {
            lock.unlock();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        try {
            lock.lock();
            if (n%15==0||n%3==0||n%5==0){
                otherCondition.await();
            }
            printNumber.accept(n);
        }finally {
            lock.unlock();
        }
    }
}