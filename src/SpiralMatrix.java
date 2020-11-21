import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 螺旋数组 最关键的 取模过程
 * 1234 第四步 模为 0
 * 容易忽略
 *
 * 过滤边界调节
 * length = 0；
 * length = 1；
 *
 */
class SpiralMatrix {


    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {

        }
        List<Integer> orderList = new ArrayList<>();
        if (matrix.length == 0) {
            return orderList;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        if (m == 1) {
            for (int i = 0; i < matrix[0].length; i++) {
                orderList.add(matrix[0][i]);
            }
            return orderList;
        }
        boolean[][] visited = new boolean[m][n];

        spiralOrderHelper(matrix, orderList, 0, 0, visited);
        return orderList;

    }

    void spiralOrderHelper(int[][] matrix, List<Integer> orderList, int i, int j, boolean[][] visited) {

        // 搜索顺序 1 先  j++ -> 2 先列递增 i++  -> 3 j-- -> 4 i--

        int count = 1;
        while (i >= 0 && j >= 0 && !visited[i][j]) {
            // System.out.println("i "+i+"  j "+j);

            switch (count % 4) {
                case 1:
                    orderList.add(matrix[i][j]);
                    visited[i][j] = true;

                    if ((j + 1 < matrix[0].length && visited[i][j + 1]) || j == matrix[0].length - 1) {
                        count++;
                        new LinkedList<>().pop();
                        i++;
                        break;
                    } else {
                        j++;
                    }
                    break;
                case 2:
                    orderList.add(matrix[i][j]);
                    visited[i][j] = true;

                    if ((i + 1 < matrix.length && visited[i + 1][j]) || i == matrix.length - 1) {
                        count++;
                        j--;
                        break;

                    } else {
                        i++;
                    }

                    break;
                case 3:
                    orderList.add(matrix[i][j]);
                    visited[i][j] = true;

                    if ((j - 1 >= 0 && visited[i][j - 1]) || j == 0) {
                        count++;
                        i--;
                        break;
                    } else {
                        j--;
                    }

                    break;
                case 0:
                    orderList.add(matrix[i][j]);
                    visited[i][j] = true;

                    if ((i - 1 >= 0 && visited[i - 1][j]) || i == 0) {
                        count++;
                        j++;
                        break;
                    } else {
                        i--;
                    }

                    break;
            }

        }
    }
}