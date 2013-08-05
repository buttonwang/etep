/**
 * 
 */
package com.ambow.trainingengine.util;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

/**
 * @author yuanjunqi
 *
 */
public class SimpleWordSegment {
	private SimpleAnalyzer analyzer = null;
	
	/**
	 * 构造函数
	 * 
	 */
	public SimpleWordSegment(){
		analyzer = new SimpleAnalyzer();
	}
	
	/**
	 * 对文本进行分词处理
	 * 
	 * @param text
	 * @param separator
	 * @return
	 */
	public String segment(String text, String separator) throws IOException {
		String result ="";
		TokenStream stream = analyzer.tokenStream("contents", new StringReader(
				text));
		while (true) {

			Token token = stream.next();

			if (token == null)
				break;

			result = result + token.termText()+separator;

		}

		return result;
	}
}
