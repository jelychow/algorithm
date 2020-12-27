class NumArray1 {

    int tree[];
    int val[];
    public NumArray1(int[] nums) {
        if (nums.length>0){
            val = nums;
            tree = new int[nums.length<<2];
            build(0,nums.length-1,1);
        }

    }

    public void update(int i, int val) {
        int left = 0;
        int right = this.val.length-1;
        int index = 1;
        while (left!=right){
            int mid = (left+right)>>>1;
            if (mid==i){
                index<<=1;
                right = mid;
            }else if (i<mid){
                right = mid;
                index<<=1;
            }else {
                left=mid+1;
                index=index<<1|1;
            }
        }
        int last = tree[index];
        tree[index]=val;

        int diff = last-val;

        while (index!=1){
            tree[index>>1] -= diff ;
            index = index>>1;
        }
    }

    public void update(int i,int j, int val) {

    }

    public int sumRange(int i, int j) {
        return query(0,val.length-1,i,j,1);
    }

    public void pushUp(int index){
        tree[index] = tree[index<<1]+ tree[index<<1|1];
    }
    public void build(int left,int right,int index){
        if (left==right){
            tree[index] = val[left];
        }else {
            int mid = (left+right)>>1;
            build(left,mid,index<<1);
            build(mid+1,right,index<<1|1);
            pushUp(index);
        }
    }

    public int query(int left,int right,int sa,int sb,int index){
        if (sa==left&&sb==right){
            return tree[index];
        }
        int mid = (left+right)>>>1;
        if (sb<=mid){
          return  query(left,mid,sa,sb,index<<1);
        }else if (sa>mid){
            return query(mid+1,right,sa,sb,index<<1|1);
        }else {
            return query(left,mid,sa,mid,index<<1)+query(mid+1,right,mid+1,sb,index<<1|1);
        }
    }
}