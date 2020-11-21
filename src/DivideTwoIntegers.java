public class DivideTwoIntegers {
    /**
     * 两数相除
     * 终极解法
     * 首先判断正负数 通过位运算来求
     * 所有正数都变成负数进行计算避免溢出
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        // 通过最高位判断是否小于0
        boolean negtive= (dividend^ divisor)<0;

        if (dividend > 0) {
            dividend = -dividend;
        }
        if(divisor > 0) {
            divisor = -divisor;
        }

        int s = 0, tmpd, k;
        while (dividend <= divisor) {
            tmpd = divisor;
            k = 1;
            while (dividend <= (tmpd<<1) && (tmpd<<1 < 0)) {
                tmpd <<= 1;
                k <<= 1;
            }
            s = s + k;
            dividend -= tmpd;
        }
        return negtive?-s : s;
    }

    }
