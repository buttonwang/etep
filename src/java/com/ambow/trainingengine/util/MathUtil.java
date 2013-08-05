package com.ambow.trainingengine.util;

import java.math.BigDecimal;

public class MathUtil {
	public static int percent(int para1,int para2) {
		
		int rate = 0;
		
		if (para2==0) {
			return 0;
		}
		
		BigDecimal b1 = new BigDecimal(String.valueOf(para1));
		BigDecimal b2 = new BigDecimal(String.valueOf(para2));
		BigDecimal b3 = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
		BigDecimal b4 = new BigDecimal(100);
		rate = b3.multiply(b4).intValue();

		return rate;
		
	}

	public static int percent(float para1,int para2) {
		
		int rate = 0;
		
		if (para2==0) {
			return 0;
		}

		BigDecimal b1 = new BigDecimal(String.valueOf(para1));
		BigDecimal b2 = new BigDecimal(String.valueOf(para2));
		BigDecimal b3 = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
		BigDecimal b4 = new BigDecimal(100);
		rate = b3.multiply(b4).intValue();

		return rate;
		
	}
	public static int percent(float para1,float para2) {		
		int rate = 0;		
		if (para2==0) {
			return 0;
		}
		BigDecimal b1 = new BigDecimal(String.valueOf(para1));
		BigDecimal b2 = new BigDecimal(String.valueOf(para2));
		BigDecimal b3 = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
		BigDecimal b4 = new BigDecimal(100);
		rate = b3.multiply(b4).intValue();

		return rate;
		
	}
	public static int div(float para1,int para2) {
		
		int rate = 0;
		
		if (para2==0) {
			return 0;
		}

		BigDecimal b1 = new BigDecimal(String.valueOf(para1));
		BigDecimal b2 = new BigDecimal(String.valueOf(para2));
		rate = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).intValue();

		return rate;
		
	}

	public static int div(int para1,int para2) {
		
		int rate = 0;
		
		if (para2==0) {
			return 0;
		}

		BigDecimal b1 = new BigDecimal(String.valueOf(para1));
		BigDecimal b2 = new BigDecimal(String.valueOf(para2));
		rate = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).intValue();

		return rate;
		
	}
}
