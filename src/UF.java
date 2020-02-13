import java.util.HashMap;

/**
 * 连通性算法
 */
public class UF {

    private int count;
    private int[] parent;

    public UF(int count) {
        this.count = count;
        parent = new int[count];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
        }
    }

    public int getCount() {
        return count;
    }

    public void union(int p,int q) {
        p = find(p);
        q = find(q);
        parent[p] = q;
        count--;
    }

    public int find(int p) {

        while (parent[p]!=p) {
            p = parent[p];
        }
        return p;
    }

    public boolean connect(int p,int q){
        return find(p)==find(q);
    }

    public int findCircleNum(int[][] M) {
        int len =M.length;
        UF uf = new UF(len);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < M[i].length; j++) {
                if (M[i][j]==1){
                    uf.union(i,j);
                }
            }
        }
        return uf.getCount();
    }

    public boolean equationsPossible(String[] equations) {
        UF uf = new UF(equations.length*2);
        int index  = -1;
        HashMap <Character,Integer>keymap = new HashMap();
        for (int i = 0; i < equations.length; i++) {
            String item = equations[i];
            char head = item.charAt(0);
            char tail = item.charAt(item.length()-1);
            if (!keymap.containsKey(head)) {
                keymap.put(head,++index);
            }
            if (!keymap.containsKey(tail)) {
                keymap.put(tail,++index);
            }
            if (item.contains("==")) {
                uf.union(keymap.get(head),keymap.get(tail));
            }
        }

        for (int i = 0; i < equations.length; i++) {
            String item = equations[i];
            char head = item.charAt(0);
            char tail = item.charAt(item.length()-1);

            if (item.contains("!=")) {
                if (uf.connect(keymap.get(head),keymap.get(tail))) {
                    return false;
                }
            }
        }

        return true;
    }
}
