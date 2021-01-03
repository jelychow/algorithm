package graph;

import java.util.ArrayDeque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class LazyPrimMST {

    // 保存结果
    private Queue <Edge> queue;
    private boolean marked[];
    // 保存横切边
    private PriorityQueue<Edge> minQ;

    public LazyPrimMST(EdgedWeightGraph graph) {
        marked = new boolean[graph.getV()];
        queue = new ArrayDeque();
        minQ = new PriorityQueue();
        visit(graph,0);

        while (!minQ.isEmpty()){
            // 核心代码 用最小堆来保存横切边 每次弹出最小的 而不是最近的边。
            Edge edge = minQ.poll();
            int v = edge.ether();
            int w = edge.other(v);
            if (marked[v]&&marked[w]){
                continue;
            }
            queue.offer(edge);

            if (!marked[v]){
                visit(graph,v);
            }
            if (!marked[w]){
                visit(graph,w);
            }
        }
    }

    public void visit(EdgedWeightGraph graph,int v){
        List<Edge> list = (List) graph.adj(v);
        marked[v] = true;
        for (int i = 0; i < list.size(); i++) {
            Edge edge = list.get(i);
            int w = edge.other(v);
            if (!marked[w]){
                minQ.offer(edge);
            }
        }
    }
}


