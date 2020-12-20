package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    int E;
    int V;
    List<Integer>[] adj;
    public Graph(int v) {
        V = v;
        adj = new List[v];
        Arrays.fill(adj,new ArrayList<>());
    }

    public void addEdge(int v,int w){
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public int getE(){
        return E;
    }

    public int getV(){
        return V;
    }

    public List adj(int v){
        return adj[v];
    }
}
