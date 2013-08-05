/**
 * 
 */
package com.ambow.trainingengine.util;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

/**
 * @author yuanjunqi
 *
 */
public class ExtraHtmlParser {
	/*
	 * 设置要读取文档的默认编码格式
	 */
	private static final String DEFAULT_ENCODING = "GBK";
	
	/**
	 * 初始化解析器
	 * @param inputHtml
	 * @param encoding
	 * @return
	 * @throws ParserException
	 */
	private static Parser getParser(String inputHtml,String encoding) throws ParserException{
		Parser parser = null;
		parser = new Parser(inputHtml);
		if(encoding != null){
			parser.setEncoding(encoding);
		}else{
			parser.setEncoding(DEFAULT_ENCODING);
		}
		return parser;
	}
	
	/**
	 * 获取标签下过滤后ＨＴＭＬ标签的文本
	 * @param inputHtml
	 * @return
	 * @throws ParserException 
	 * @throws Exception
	 */
	public static String extractTextByVisitor(String inputHtml,String encoding) throws ParserException{

		Parser parser = getParser(inputHtml,encoding);

		TextExtractingVisitor visitor = new TextExtractingVisitor();
		
		// 遍历所有的节点
		parser.visitAllNodesWith(visitor);

		return visitor.getExtractedText();
	}
}
