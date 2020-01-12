import java.util.HashSet;
import java.util.Set;

public class SetMatrixZeroes {
    /**
     * 73. 矩阵置零
     * 本题 算法小技巧
     * 1. 学习使用纵轴 横轴 来解析矩阵
     * 2. 巧用set 去重
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        if(matrix==null||matrix.length==0) {
            return;
        }
        Set rowset = new HashSet();
        Set columnset = new HashSet();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]==0) {
                    rowset.add(i);
                    columnset.add(j);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (rowset.contains(i)||columnset.contains(j)) {
                    matrix[i][j]=0;
                }
            }
        }
    }

}
