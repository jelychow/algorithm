import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {

    private ReentrantLock[] locks;


    public DiningPhilosophers() {
        locks = new ReentrantLock[]{
                new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock(),
        };
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int left = philosopher + 1;
        int right = (philosopher + 1) % 5;

        // 前面的人先拿了左边的叉子，后面的人开始找右边的叉子 这样，就会形成依赖关系不会形成死锁
        if (philosopher % 2 == 0) {
            locks[left].lock();
            locks[right].lock();
        } else {
            locks[right].lock();
            locks[left].lock();
        }

        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        locks[left].unlock();
        locks[right].unlock();
    }
}