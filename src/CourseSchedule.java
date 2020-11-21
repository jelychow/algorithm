import java.util.Arrays;
import java.util.LinkedList;

/**
 * 207. 课程表
 * 处理流程
 * 1 统计结点的入度
 * 2 统计入度为 0 的结点 压入队列 注意是结点 不是入度表的count
 * 3 弹出队列 ，发现入度为零的结点 入队 课程数量 -1
 * 4 统计剩余课程量
 *
 */
public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int [] degrees = new int[numCourses];
        LinkedList<Integer> res = new LinkedList();


        LinkedList<Integer> deque = new LinkedList();
        for (int i = 0; i < prerequisites.length; i++) {
            degrees[prerequisites[i][0]]++;
        }

        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i]==0){
                deque.add(i);
            }
        }
        while (!deque.isEmpty()) {
            int course = deque.removeFirst();
            res.add(course);

            numCourses--;
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1]!=course)
                    continue;
                if(-- degrees[prerequisites[i][0]]==0){
                    deque.add(prerequisites[i][0]);
                }
            }
        }


        System.out.println(res);


        return numCourses==0;
    }
}
