package com.emall.util;

import java.math.BigDecimal;

/**
 * 用来进行价格精确计算的工具类
 *
 * @author Harry Chou
 * @date 2019/5/27
 */
public class BigDecimalUtil {
    private BigDecimalUtil() {
    }

    // 加法
    public static BigDecimal add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    // 减法
    public static BigDecimal sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    // 乘法
    public static BigDecimal mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    // 除法
    public static BigDecimal div(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        // 四舍五入，保留两位小数
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
    }
}
