package graph;

public class Edge implements Comparable<Edge> {
    int weight;
    private final int v;
    private final int w;

    public Edge(int weight, int v, int w) {
        this.weight = weight;
        this.v = v;
        this.w = w;
    }

    public double weight(){
        return weight;
    }

    public int ether(){
        return v;
    }

    public int other(int vertex){
        if(v==vertex){
            return w;
        }
        if(w==vertex){
            return v;
        }
        else throw new RuntimeException("inconsistent edge");
    }

    public String toString(){
        return String.format("%d-%d %.2f",v,w,weight());
    }

    @Override
    public int compareTo(Edge o) {
        return this.weight>=o.weight?1:-1;
    }
}