import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlineTest {
    // 生产这消费者模型
    // buffer 队列 max 10  0 生产者阻塞  10 消费者阻塞

    Queue<Integer> deque = new  LinkedList();

    public static void main(String[] args) {


    }


    public class Consumer{
        Integer product;
        public void  consume() throws InterruptedException {
            synchronized (deque){
                if (deque.size()==0){
                    deque.wait();
                }
                product =  deque.poll();
            }
        }
    }

    public class Producer{
        public void product(){

            deque.offer(new Integer(1));
            deque.notify();
        }

        public Integer get() throws InterruptedException {

            Integer product;

            synchronized (deque){
                if (deque.size()==10){
                    deque.wait();
                }
                product = deque.poll();
            }

            // 如果小于 0 阻塞
           return product;
        }
    }


}
