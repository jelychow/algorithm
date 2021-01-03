package graph;

import java.util.ArrayList;
import java.util.List;

public class EdgedWeightGraph {
    private final int V;
    private int E;
    private List<Edge>[]adj;

    public EdgedWeightGraph(int v) {
        V = v;
        this.E = 0;
        adj = new List[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

    public void addEdge(Edge e){
        int v = e.ether();
        int w = e.other(v);

        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }

    public Iterable<Edge> edges(){
        List<Edge> list = new ArrayList<>();
        for (int i = 0; i < adj.length; i++) {
            for (int j = 0; j < adj[i].size(); j++) {
                list.add(adj[i].get(i));
            }
        }
        return list;
    }

}
