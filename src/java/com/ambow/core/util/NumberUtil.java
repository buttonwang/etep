/**
 * 
 */
package com.ambow.core.util;

import java.math.BigDecimal;

/**
 * @author yuanjunqi
 *
 */
public class NumberUtil {
	/**
	 * 高精度数字计算
	 * 
	 * @param value
	 * @return
	 */
	public static double getDecimal(double value, int num) {
		double retNum = 0;

		// 高精度数字计算,double存在精度误差
		BigDecimal decimal = new BigDecimal(value + "");

		//四舍五入
		Double dd = new Double(decimal.setScale(num, BigDecimal.ROUND_HALF_UP)
				.toString());

		retNum = dd.doubleValue();

		return retNum;
	}
}
