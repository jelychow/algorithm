public class TestA {

    // a b 求 a%b  不能用乘法 和 加法
    // 假设 a = 100 b 14  a%b
    // 先对 b 进行左移 直到 b>a 计算出 最接近 a
    // step 2  a-b
    // step 3  重复
    // step 4  a<b return a;

    // users
    public int help(int a,int b) {
        if (a<b){
            return a;
        }
        if (a==b) {
            return 0;
        }

        int tempB = b;
        int index = 0;
        while (a>tempB) {
            tempB = tempB<<1;
            index++;
        }
        tempB = b;

        int leftNum = a - (tempB<<(index-1));
        return help(leftNum,b);

    }



    public static void main(String[] args) {

        assert new TestA().help(1989777,14)==1989777%14;
//        System.out.println();
//
//        System.out.println();

    }

    /**
     *
     *  location[i][j] user
     *  dfs(location,i,j,list) {
     *      draw text
     *  }
     *
     *
     */


}
