package com.ambow.trainingengine.exam.score;

import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ambow.trainingengine.exam.util.ExamUtil;

/**
 * ScoreUtil.java
 * 
 * Created on 2008-12-9 下午02:09:25
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */


public class ScoreUtil {
	
	Logger log = Logger.getLogger(this.getClass());
	
	MathMatchs mathMatchs;
	
	public String answerPrepare(String answerType, String input) {
		
		if (answerType.equals("normal")) {
			return normalAnswerPrepare(input);
		} else {
			return formulaAnswerPrepare(input);
		}
	}
	
	/**
	 * 为MPC公式型答案比较做准备 
	 * 1. 去掉空格及中文全角空格, 去掉尾部的 ；
	 * 2. 替换回车换行符号
	 * 3. 替换标签的显示修饰符  <mo  rspace='verythickmathspace' stretchy='true' lspace='verythickmathspace' >↓</mo> 替换为 <mo>↓</mo>
	 * 3. 替换 + 为 &plus;  替换  − 为&minus;  
	 * 4. <math display = 'block'> 替换为 <math>
	 * 5. 对于公式答案去掉 > < 之间的空格 和 < > 内部的空格
	 * 6. <mtext></mtext> 之类的空白标签也去除
	 * 7. <mtext>点</mtext><mtext>燃</mtext> 替换为 <mtext>点燃</mtext>
	 * 8. 全角转半角
	 * 9. 转化为小写
	 */
	public String formulaAnswerPrepare(String input) {
		log.debug("Math Input: " + input);
		String s = input.trim();
		if (s.equals("")) return s;
		
		Map<String, String> symbols = mathMatchs.getMathSymbols();
		Map<String, String> marks   = mathMatchs.getMathMarks();
		Set<String> keySymbols = symbols.keySet();
		Set<String> keyMarks   = marks.keySet();		
		String reg = "";
		String rls = "";
					
		for (String symbol: keySymbols) {
			reg = "&" + symbol + ";";
			rls =  symbols.get(symbol);		
			log.debug("Math regex: " + reg + " replace to -> " + rls);
			s = s.replaceAll(reg, rls);
			log.debug("Math Outer: " + s);
		}
		
		for (String mark: keyMarks) {
			reg = mark.replaceAll("\\{", "<").replaceAll("\\}", ">");
			rls =  marks.get(mark);
			rls = rls.replaceAll("\\{", "<").replaceAll("\\}", ">");
			log.debug("Math Regex: " + reg + " replace to -> " + rls);	
			s = s.replaceAll(reg, rls);
			log.debug("Math Outer: " + s);
		}
		
		s = replacsUTF8Code(s);
		s = ExamUtil.ToDBC(s.trim());
		log.debug("Math Ender: " + s);
		return s;
	}
	
	/**
	 * 为MPC普通型答案比较做准备 
	 * 1. 去掉空格及中文全角空格, 去掉尾部的 ；
	 */	
	public String normalAnswerPrepare(String input) {
		log.debug("Normal Input: " + input);
		String s = input.trim();
		if (s.equals("")) return s;
		
		Map<String, String> marks   = mathMatchs.getNormalMarks();
		Set<String> keyMarks   = marks.keySet();		
		String reg = "";
		String rls = "";
		
		for (String mark: keyMarks) {
			reg = mark;
			rls = marks.get(mark);
			s = s.replaceAll(reg, rls);
		}
		
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceFirst(";$", "");
		
		s = ExamUtil.ToDBC(s.trim());
		log.debug("Normal Ender: " + s);
		return s;
	}
		
	/**
	 * 比较MPC答案  按公式和非公式来比较
	 */
	public boolean answerCompare(String answerType, String cAnswer, String uAnswer, boolean correctAnswerUsed) {		
		if (correctAnswerUsed) return false;
		
		boolean ret = false;
		if (answerType.equals("normal")) {
			if (cAnswer.equals(uAnswer)) ret = true;
			else ret = ("$" + cAnswer + "$").contains("$" + uAnswer + "$");
		} else {
			if (cAnswer.equals(uAnswer)) ret = true;
			else ret = ("$" + cAnswer + "$").contains("$" + uAnswer + "$");
		}
		return ret;
	}
	
	/**
	 * 取得用户答案在标准答案开放性答案的位置
	 * @param cAnswer
	 * @param uAnswer
	 * @return
	 */
	public int answerPosition(String cAnswer, String uAnswer) {
		int ret = 0;
		String[] cAnswers = cAnswer.split("\\$");
		int position = 1;
		for (String answer: cAnswers) {
			if (answer.equals(uAnswer)) ret = position;
			position++;
		}
		return ret;
	}
	
	/**
	 * 根据判分后的答案找到正确答案在标准开放性答案中对应的字符。
	 * @param correctAnswer
	 * @param cAnswer
	 * @param uAnswer
	 * @return
	 */
	public String matchAnswer(String correctAnswer, String cAnswer, String uAnswer) {
		int position = answerPosition(cAnswer, uAnswer);
		String[] correctAnswers = correctAnswer.split("\\$");
		return correctAnswers[position-1];
	}
	
	/**
	 * 句子打分--关键词，用户答案 ，分值
	 */
	public Float scoreSubject(String keyWord, String answerStr,
			Float itemValue) {
		if (keyWord == null || answerStr == null
				|| keyWord.trim().length() == 0
				|| answerStr.trim().length() == 0)
			return 0f;
		keyWord = ExamUtil.ToDBC(keyWord);
		keyWord = keyWord.replaceAll(" ", "");
		keyWord = keyWord.replaceAll("\\(", "");
		keyWord = keyWord.replaceAll("\\)", "");
		
		answerStr = ExamUtil.ToDBC(answerStr);
		answerStr = answerStr.replaceAll("\\(", "");
		answerStr = answerStr.replaceAll("\\)", "");
		Float score = null;
		String[] keyWords = keyWord.split(";");
		if (keyWords.length == 0 || answerStr.trim().length() == 0) {
			score = 0f;
			return score;
		}
		int keyWordIterator = 0;
		int position = 0;
		int currentPosition = 0;
		String matchStr = answerStr;
		int matchSize = 0;
	
		while (position < answerStr.length()
				&& keyWordIterator < keyWords.length) {
			currentPosition = getMatchPosition(keyWords[keyWordIterator],
					matchStr);
			if (currentPosition > -1) {
				matchSize++;
				matchStr = matchStr.substring(currentPosition, matchStr
						.length());
			}

			keyWordIterator++;
			
		}

		score = matchSize * itemValue / keyWords.length;

		return ExamUtil.roundFloat(score);

	}
	
	/**
	 * 作文的打分逻辑
	 */
	public Float compositionScore(String keyWordStr, int requireSize,
			Float itemValue, String userAnswer) {
		Float score = 0f;
		if (userAnswer == null || userAnswer.trim().length() == 0)
			return score;
		int userSize = userAnswer.split("[ ]+").length;
		double lengthPercentage = (double) userSize / (double) requireSize;
		int keyWordSize = keyWordStr.split(";").length;
		int userHitSize = compositionKeyWordHit(keyWordStr, userAnswer);
		double keyWordPercentage = (double) userHitSize / (double) keyWordSize;
		double lengthFactor = 0;
		double keyWordFactor = 0;
		if ((0 <= lengthPercentage && lengthPercentage < 0.2)
				|| lengthPercentage > 1.8) {
			lengthFactor = 0.08;
		}
		if ((0.2 <= lengthPercentage && lengthPercentage < 0.4)
				|| (1.6 < lengthPercentage && lengthPercentage <= 1.8)) {
			lengthFactor = 0.16;
		}
		if ((0.4 <= lengthPercentage && lengthPercentage < 0.6)
				|| (1.4 < lengthPercentage && lengthPercentage <= 1.6)) {
			lengthFactor = 0.24;
		}
		if ((0.6 <= lengthPercentage && lengthPercentage < 0.8)
				|| (1.2 < lengthPercentage && lengthPercentage <= 1.4)) {
			lengthFactor = 0.32;
		}
		if ((0.8 <= lengthPercentage && lengthPercentage < 1.2)) {
			lengthFactor = 0.4;
		}
		keyWordFactor = keyWordPercentage * 0.6;
		Double userScore = (lengthFactor + keyWordFactor) * itemValue;
		score = ExamUtil.roundFloat(userScore.floatValue());
		return score;
	}
	
	/**
	 * 文章的关键词命中
	 */
	public int compositionKeyWordHit(String keywordStr, String answerStr) {
		int hit = 0;
		keywordStr = ExamUtil.ToDBC(keywordStr);
		keywordStr = keywordStr.replaceAll(" ", "");
		keywordStr = keywordStr.replaceAll("\\(", "");
		keywordStr = keywordStr.replaceAll("\\)", "");
		// log.debug("KKK"+keywordStr);
		answerStr = ExamUtil.ToDBC(answerStr);
		answerStr = answerStr.replaceAll(" ", "");
		String[] keywords = keywordStr.split(";");
		int position = 0;
		// log.debug("str:"+answerStr);
		// log.debug("Match begin:");
		// log.debug("");
		for (String keyword : keywords) {
			position = getMatchPosition(keyword, answerStr);
			if (position > -1)
				hit = hit + 1;
		}
		// log.debug("");
		// log.debug("Match end.");
		return hit;
	}
	
	private static int getMatchPosition(String keyWord, String matchStr) {
		int position = -1;
		keyWord = keyWord.replaceAll(",", "|");
		Pattern p = Pattern.compile(keyWord);
		Matcher m = p.matcher(matchStr);
		boolean result = m.find();
		if (result) {
			MatchResult mResult = m.toMatchResult();
			position = mResult.end();
		}
		return position;
	}
	
	//替换UTF8code为真实字符
	public String replacsUTF8Code(String input) {
		String s = input;
		Pattern p = Pattern.compile("(&#)(\\d+)(;)");
		Matcher m = p.matcher(s);
		
		while (m.find()) {
			String g = m.group(2);
			g = "" +  (char)(Integer.parseInt(g));
			s = m.replaceFirst(g);
			m.reset(s);
		}
		return s;
	}
	
	public MathMatchs getMathMatchs() {
		return mathMatchs;
	}

	public void setMathMatchs(MathMatchs mathMatchs) {
		this.mathMatchs = mathMatchs;
	}

}
