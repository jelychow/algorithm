import java.util.Random;

public class Util {
    public static int[] getTestArray(int nums,int bund) {
        Random random = new Random();
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(bund)+1;
        }
        return arr;
    }

    public static void swap(int[]arr,int i,int j) {
        if (i==j) {
            return;
        }
        int temp = i;
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
