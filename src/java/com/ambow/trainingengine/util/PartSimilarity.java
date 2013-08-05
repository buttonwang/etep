/**
 * 
 */
package com.ambow.trainingengine.util;

import java.util.Random;

/**
 * @author yuanjunqi
 *
 */
public class PartSimilarity {
    /**
     * 相似度计算
     * @param str1
     * @param str2
     * @return
     */
    public double sim(String str1, String str2) { 
    	double sim = 0;
    	if(Math.min(str1.length(), str2.length())<40){
    		sim = compare(str1,str2,4);
    	}else if(Math.min(str1.length(), str2.length())<100){
    		sim = compare(str1,str2,10);
    	}else if(Math.min(str1.length(), str2.length())>=100){
    		sim = compare(str1,str2,20);
    	}
    	
    	return sim;
    }
    
    /**
     * 比较
     * @param str1
     * @param str2
     * @param size
     * @return
     */
    public double compare(String str1, String str2,int size){
    	int count =0;
    	int min = Math.min(str1.length(), str2.length());
    	int begin = min-(min/size+1);
    	Random ran = new Random(begin);
    	for(int i=0;i<size;i++){
    		double d = ran.nextDouble();
    		int r = Integer.parseInt(""+Math.floor(d*begin));
    		String sub1 = str1.substring(r, r+min/size);
    		String sub2 = str2.substring(r, r+min/size);
    		if(sub1.equals(sub2)){
    			count++;
    		}
    	}
    	return count/size;
    }
    

}
