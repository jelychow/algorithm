class NumArray {
    int max ;
    int[]tree;
    int values[];
    public NumArray(int[] nums) {
        if(nums.length>0){
            max = nums.length;
            values = nums;
            tree = new int[max<<2];
            buildTree(0,nums.length-1,0);
        }

    }
    
    public int sumRange(int i, int j) {

      return query(0,0,values.length-1,i,j);
    }

    void pushUp(int index){
        tree[index] = tree[(index<<1)+1]+tree[(index<<1)+2];
    }

    public void buildTree(int left,int right,int index){
        if (left==right){
            tree[index] = values[left];
        }else {
            int m = (left+right)>>>1;
            buildTree(left,m,(index<<1)+1);
            buildTree(m+1,right,(index<<1)+2);
            pushUp(index);
        }
    }

    public int query(int index,int left,int right,int queryL,int queryR){

        if (left==queryL&&right==queryR){
            return tree[index];
        }
        int m = (left+right)>>>1;
        if (queryR<m){
            return query((index<<1)+1,left,m,queryL,queryR);
        }else if(queryL>m){
            return query((index<<1)+2,m+1,right,queryL,queryR);
        }

       return  query((index<<1)+1,left,m,queryL,m) +  query((index<<1)+2,m+1,right,m+1,queryR);
    }
}