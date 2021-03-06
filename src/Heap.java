import java.util.Arrays;
import java.util.Comparator;

/**
 * 用完全二叉树来构建 堆
 * 前置条件 起点为 1
 * 那么 子节点为  i <<1 和 i<<1 + 1
 * 核心方法为
 * shiftdown 交换下沉
 * shiftup 交换上浮
 * <p>
 * build 构建堆
 */

public class Heap {

    int size = 0;
    int queue[];

    public Heap(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.queue = new int[initialCapacity];
    }

    public Heap(int[] arr) {
        size = arr.length;
        queue = new int[arr.length + 1];
        int i = 1;
        for (int val : arr) {
            queue[i++] = val;
        }
    }

    public void shiftDown(int i) {

        int temp = queue[i];

        while ((i << 1) <= size) {
            int child = i << 1;
            // child!=size 判断当前元素是否包含右节点
            if (child != size && queue[child + 1] < queue[child]) {
                child++;
            }
            if (temp > queue[child]) {
                queue[i] = queue[child];
                i = child;
            } else {
                break;
            }
        }
        queue[i] = temp;
    }


    public void shiftUp(int i) {
        int temp = queue[i];
        while ((i >> 1) > 0) {
            if (temp < queue[i >> 1]) {
                queue[i] = queue[i >> 1];
                i >>= 1;
            } else {
                break;
            }
        }
        queue[i] = temp;
    }

    public int peek() {

        int res = queue[1];
        return res;
    }

    public int pop() {

        int res = queue[1];
        queue[1] = queue[size--];
        shiftDown(1);
        return res;
    }

    public void push(int val) {
        if (size == queue.length - 1) {
            queue = Arrays.copyOf(queue, size << 1+1);
        }
        queue[++size] = val;
        shiftUp(size);
    }

    public void buildHeap() {
        for (int i = size >> 1; i >= 0; i--) {
            shiftDown(i);
        }
    }

    public static void main(String[] args) {

        int arr[] = new int[]{2,7,4,1,8,1};
        Heap heap = new Heap(arr);
        heap.buildHeap();
        System.out.println(heap.peek());
        heap.push(5);
        while (heap.size > 0) {
            int num = heap.pop();
            System.out.printf(num + "");
        }
    }
}
