class MaxQueue {
    int[] queue;
    int[] maxQueue;
    int head = 0, tail = 0, normalTail = 0, normalHead = 0;

    public MaxQueue() {
        queue = new int[10000];
        maxQueue = new int[10000];
    }

    public int max_value() {
        if (head == tail) {
            return -1;
        }
        return maxQueue[head];
    }

    public void push_back(int value) {
        queue[normalTail++] = value;

        while (tail != head && maxQueue[tail - 1] < value) {
            tail--;
        }
        maxQueue[tail++] = value;
    }

    public int pop_front() {
        if (normalHead == normalTail) {
            return -1;
        }
        int value = queue[normalHead++];

        if (tail != head && maxQueue[head] == value) {
            head++;
        }

        return value;
    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */