
package org.lansir.beautifulgirls.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * All about deal with numbers, floats, doubles.
 * Date: 12-4-16
 * Time: 下午1:35
 *
 * @author zhe.yangz
 */
public class NumberUtil {

    /**
     * 使数字按最小保留小数位数格式化,3个为一组添加逗号
     * @param num 需要格式化数字
     * @param digits 最小保留小数位数
     * @return
     */
    public static String fractionDigits(float num, int digits) {
        NumberFormat nbf = NumberFormat.getInstance(Locale.US);
        nbf.setMinimumFractionDigits(digits);
        nbf.setMaximumFractionDigits(digits);
        nbf.setGroupingUsed(true);
        return nbf.format(num);
    }

    public static String fractionDigits(double num, int digits) {
        NumberFormat nbf = NumberFormat.getInstance(Locale.US);
        nbf.setMinimumFractionDigits(digits);
        nbf.setMaximumFractionDigits(digits);
        nbf.setGroupingUsed(true);
        return nbf.format(num);
    }

    private NumberUtil() {}
}
