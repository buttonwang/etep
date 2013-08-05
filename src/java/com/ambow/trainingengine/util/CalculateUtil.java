package com.ambow.trainingengine.util;

import java.math.BigDecimal;

public class CalculateUtil {
	/**
	 * 统计某一星级掌握题数
	 * 
	 * @param starNum
	 * @param starGrade
	 * @return
	 */
	@SuppressWarnings("unused")
	public static float getMasteryNum(int starNum, int starGrade) {
		BigDecimal b1 = new BigDecimal(String.valueOf(starGrade));
		BigDecimal b2 = new BigDecimal(String.valueOf(5));
		BigDecimal b3 = new BigDecimal(String.valueOf(1));
		BigDecimal b = b3.subtract(b1.divide(b2,2,BigDecimal.ROUND_HALF_UP));
		
		if( starGrade == 11){
			b = new BigDecimal(0.9);
		}
		BigDecimal sn = new BigDecimal(String.valueOf(starNum));
		BigDecimal rate = b.multiply(sn);
		
		return rate.floatValue();
		
	}

	/**
	 * 统计掌握度
	 * 
	 * @param questionNum
	 * @param masteryNum
	 * @return
	 */
	@SuppressWarnings("unused")
	public static int getMasteryRate(int questionNum, float masteryNum) {
		int rate = MathUtil.percent(masteryNum, questionNum);
		return rate;
	}
	
	/**
	 * 计算掌握度
	 */
	public static int masteryRate( int zeroStar,int halfStar,int oneStar,
			int twoStar,int threeStar,int fourStar,	
			int fiveStar,int questionDoNum){
		int outValue=0;
		float sumMastery = 0;
		sumMastery += getMasteryNum(zeroStar,0);
		sumMastery += getMasteryNum(halfStar,11);
		sumMastery += getMasteryNum(oneStar,1);
		sumMastery += getMasteryNum(twoStar,2);
		sumMastery += getMasteryNum(threeStar,3);
		sumMastery += getMasteryNum(fourStar,4);
		sumMastery += getMasteryNum(fiveStar,5);
		if(sumMastery>0)
			outValue = getMasteryRate(questionDoNum, sumMastery);
		else
			outValue=0;
		return outValue;
	}
}
