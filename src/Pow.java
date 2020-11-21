public class Pow {
    public double myPow(double x, int n) {
        double res = 0;
        if (n==0) {
            return 1;
        }
        if (n>0) {
           return  help(x,n);
        } else {
            return x/help(x,1-n);
        }
    }

    public  double help(double x,int n) {
        if (n==1) {
            return x;
        }
        if (n%2==0) {
            return help(x*x,n>>>1);
        } else {
            return help(x*x,n>>>1)*x;
        }
    }
}
