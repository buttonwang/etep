/**
 * 
 */
package com.ambow.trainingengine.util;

/**
 * @author yuanjunqi
 *
 */
public class SimilarityUtil {

    /**
     * 相似度计算
     * @param str1
     * @param str2
     * @return
     */
    public double sim(String str1, String str2) {
    	double sim = 0;
    	int length = 0;
    	if(str1.length() != str2.length()){
	    	if(str1.length() != str2.length()&&str1.length()>str2.length()){
	    		length = str1.length() - str2.length();
	    	}else if(str1.length() != str2.length()){
	    		length = str2.length() - str1.length();
	    	}
	    	sim = 1-length /Math.max(str1.length(), str2.length());
	    	if(sim < 0.9){
	    		return sim;
	    	}
    	}
    	if(Math.max(str1.length(), str2.length())<20){
    		Similarity similar = new Similarity();
    		sim = similar.sim(str1, str2);
    	}else{
    		PartSimilarity similar = new PartSimilarity();
    		sim = similar.sim(str1, str2);
    	}
    	return sim;
    }
}
