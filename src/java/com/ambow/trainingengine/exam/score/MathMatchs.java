package com.ambow.trainingengine.exam.score;

import java.util.HashMap;
import java.util.Map;

/*
 * ScoreMathSymbol.java
 * 
 * Created on 2009-2-26 下午06:27:55
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 数学公式符号对照表 
 * &le; ≤ 
 * &ge; ≥ 
 * .........
 * Changes 
 * -------
 * $log$
 */

public class MathMatchs {

	/**
	 * 待替换的数学符号  --公式型答案
	 */
	Map<String, String> mathSymbols = new HashMap<String, String>();
	
	/**
	 * 待用正则替换的符号库 --公式型答案
	 */
	Map<String, String> mathMarks = new HashMap<String, String>();
	
	
	/**
	 * 待用正则替换的符号库 -- 普通型答案
	 */
	Map<String, String> normalMarks = new HashMap<String, String>();


	public Map<String, String> getMathSymbols() {
		return mathSymbols;
	}

	public void setMathSymbols(Map<String, String> mathSymbols) {
		this.mathSymbols = mathSymbols;
	}
	
	public Map<String, String> getMathMarks() {
		return mathMarks;
	}

	public void setMathMarks(Map<String, String> mathMarks) {
		this.mathMarks = mathMarks;
	}

	public Map<String, String> getNormalMarks() {
		return normalMarks;
	}

	public void setNormalMarks(Map<String, String> normalMarks) {
		this.normalMarks = normalMarks;
	}
}
