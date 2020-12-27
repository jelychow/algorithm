package graph;

import java.util.List;

public class GraphDFS {
    private Graph graph;
    private boolean[]marked;
    public GraphDFS(Graph g) {
        graph = g;
        marked = new boolean[g.getV()];
    }

    public void dfs(int v){
        marked[v] = true;
        List adj = graph.adj(v);
        for (int i = 0; i < adj.size(); i++) {
            if (!marked[(int) adj.get(i)]){
                dfs((Integer) adj.get(i));
            }

        }
    }

    public boolean marked(int w){
        return marked[w];
    }

}
