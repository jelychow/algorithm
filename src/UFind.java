import java.lang.reflect.Array;
import java.util.Arrays;

public class UFind {
    int count;
    int[]parent;
    int[] rank;

    public UFind(int size){
        this.count = size;
        parent = new int[count];
        rank = new int[count];
        Arrays.fill(rank,1);
        for (int i = 0; i < count; i++) {
            // 默认指向自己
            parent[i] = i;
        }
    }

    public void union(int p,int q){
        if (connect(p,q)){
            return;
        }
        p = find(p);
        q = find(q);
        if (rank[p]>rank[q]){
            parent[q] = p;
            rank[p]+=rank[q];
        } else{
            parent[p] = q;
            rank[q]+=rank[p];
        }

        count--;
    }

    public int find(int p) {
        while (parent[p]!=p) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public int getCount(){
        return count;
    }

    public boolean connect(int p,int q) {
        return find(p)==find(q);
    }
}
