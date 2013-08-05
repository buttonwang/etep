package testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ambow.trainingengine.exam.score.ScoreUtil;


import junit.framework.TestCase;

/*
 * TestItemRegx.java
 * 
 * Created on 2009-1-12 下午04:17:14
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

public class TestItemRegx extends TestCase {

	// 计算<u>&nbsp;&nbsp;&nbsp;</u> 中间有多少个&nbsp;  
	public void testItemU() {
		String s = "配平<u><span lang=EN-US>&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=59 height=17 " +
				"src='/resource/mpc/T5gao3mo-1.files/image099.jpg'></span><span style='font-family: 宋体'>＋</span><u><span lang=EN-US>" +
				"&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=54 height=16 src='/resource/mpc/T5gao3mo-1.files/image100.jpg'>" +
				"<img width=29 height=14 src='/resource/mpc/T5gao3mo-1.files/image101.jpg'><u>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;</u><img width=26 height=13 src='/resource/mpc/T5gao3mo-1.files/image102.jpg'></span>" +
				"<span style='font-family:宋体'>＋</span><u><span lang=EN-US>&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US>" +
				"<img width=37 height=16 src='/resource/mpc/T5gao3mo-1.files/image103.jpg'></span><span style='font-family:宋体'>＋" +
				"</span><u><span lang=EN-US>&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=35 height=22 " +
				"src='/resource/mpc/T5gao3mo-1.files/image104.jpg'></span><span style='font-family:宋体'>＋</span><u><span lang=EN-US>" +
				"&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=31 height=19 src='/resource/mpc/T5gao3mo-1.files/image105.jpg'>" +
				"</span></p>  <p class=MsoNormal><span style='font-family:宋体'>";
		String replacement = "<input type='text' class='input_text' name='userAnswer#id#' value='' #replaceMe# xsize=$2 />";
		System.out.println(s);
		
		String s1 = s.replaceAll("&nbsp;", "@");
		s1 = s1.replaceAll("<u>(<span lang=EN-US>)?(@*\\s?)(</span>)?</u>", replacement);
		System.out.println(s1);
		
		Pattern p = Pattern.compile("(xsize=)(@*)");		
		Matcher m = p.matcher(s1);
		
		String temp;
		
		while (m.find()) {
			temp = m.group(2);
			s1 = s1.replaceFirst(temp, String.valueOf(temp.length()));
			System.out.println(temp);
			//m.reset(s1);
		}
		s1 = s1.replaceAll("xsize", "size");
		s1 = s1.replaceAll("@", "&nbsp;");
		
		System.out.println(s1);
	}
	
	public void testRTrim1() {
		String s1 = "1<span style='font-family:宋体'>∶</span><span lang=EN-US>1</span></p>  <p class=MsoNormal><span lang=EN-US>";
		s1 = s1.replaceFirst("</p>\\s+<p[^>]+><span[^>]+>$", "");
		System.out.println(s1);
		assertEquals("1<span style='font-family:宋体'>∶</span><span lang=EN-US>1</span>", s1);		
	}
	
	public void testRTrim2() {
		String s1 = "2<span style='font-family:宋体'>个氢原子</span></p>  <p class=MsoNormal><span style='font-family:宋体'>";
		s1 = s1.replaceFirst("</p>\\s+<p[^>]+><span[^>]+>$", "");
		System.out.println(s1);
		assertEquals("2<span style='font-family:宋体'>个氢原子</span>", s1);		
	}
	
	public void testRTrim3() {
		String s1 = "1<span style='font-family:宋体'>∶</span><span lang=EN-US>3</span></p>  <p class=MsoNormal align=left style='text-align:left;vertical-align:middle'><span style='font-family:宋体'>";
		s1 = s1.replaceFirst("</p>\\s+<p[^>]+><span[^>]+>$", "");
		System.out.println(s1);
		assertEquals("1<span style='font-family:宋体'>∶</span><span lang=EN-US>3</span>", s1);
	}
	
	public void testRTrim4() {
		String s1 = "1<span style='font-family:宋体'>∶</span><span lang=EN-US>2</span></p>  <p class=MsoNormal align=left style='text-align:left;vertical-align:middle'><span lang=EN-US>";
		s1 = s1.replaceFirst("</p>\\s+<p[^>]+><span[^>]+>$", "");
		System.out.println(s1);
		assertEquals("1<span style='font-family:宋体'>∶</span><span lang=EN-US>2</span>", s1);		
	}
		
	
	public void testRTrim5() {
		String s1 = "<span style='position:relative;top:6.0pt;vertical-align: baseline !msorm'><sub><img width=20 height=24 src=\"/resource/mpc/S4P0805.files/image008.gif\"></sub><span style='font-family:宋体'>、</span><span style='position:relative;top:5.0pt; vertical-align:baseline !msorm'><sub><img width=20 height=23 src=\"/resource/mpc/S4P0805.files/image117.gif\"></sub></span><span style='font-family:宋体'>都断路</span></p>  <p class=MsoNormal>";
		s1 = s1.replaceFirst("</p>\\s+<p[^>]+>$", "");
		System.out.println(s1);
		assertEquals("<span style='position:relative;top:6.0pt;vertical-align: baseline !msorm'><sub><img width=20 height=24 src=\"/resource/mpc/S4P0805.files/image008.gif\"></sub><span style='font-family:宋体'>、</span><span style='position:relative;top:5.0pt; vertical-align:baseline !msorm'><sub><img width=20 height=23 src=\"/resource/mpc/S4P0805.files/image117.gif\"></sub></span><span style='font-family:宋体'>都断路</span>", s1);		
	}
	
	
	public void testRTrim6() {
		String s1 = "<p class=MsoNormal align=left style='text-align:left;text-autospace:none; vertical-align:middle'><span style='font-family:宋体'>";
		s1 = s1.replaceFirst("<p[^>]+><span[^>]+>$", "");
		System.out.println(s1);
		assertEquals("", s1);
	}
	
	public void testReplaceU() {
		String s1 = "判断下列结论是否正确：（填“是”或“否”）</p>  <p class=MsoNormal><span style='font-family:宋体'>　　①分子是保持一切物质化学性质的最小微粒．</span><u><span lang=EN-US>@@@@</span></u> </p>  <p class=MsoNormal><span style='font-family:宋体'>　　②分子能保持物质的一切性质．</span><u><span lang=EN-US>@@@@</span></u></p>  <p class=MsoNormal><span style='font-family:宋体'>　　③在化学反应中原子不可分，离子也不可分．</span><u><span lang=EN-US>@@@@</span></u></p>  <p class=MsoNormal><span style='font-family:宋体'>　　④具有相同质子数的微粒必定是同种元素．</span><u><span lang=EN-US>@@@@</span></u></p>  <p class=MsoNormal><span style='font-family:宋体'>　　⑤同种元素组成的物质一定是单质．</span><u><span lang=EN-US>@@@@</span></u></p>  <p class=MsoNormal><span style='font-family:宋体'>　　⑥硫酸分子中含有两个氢元素、一个硫元素、四个氧元素．</span><u><span lang=EN-US>@@@@</span></u></p>  <p class=MsoNormal><span style='font-family:宋体'>　　⑦水受热变成水蒸气和水通电生成氢气和氧气的两个变化中，水分子本身都发生了变化．</span><u><span lang=EN-US>@@@@</span></u></p>  <p class=MsoNormal><span style='font-family:宋体'>　　⑧</span><sub><span lang=EN-US><img width=39 height=23 src='/resource/mpc/Taolinpikejiaocheng.files/image001.gif'></span></sub><span style='font-family:宋体'>与</span><sub><span lang=EN-US><img width=56 height=23 src='/resource/mpc/Taolinpikejiaocheng.files/image002.gif'></span></sub><span style='font-family:宋体'>都是离子化合物，它们都含硫离子．</span><u><span lang=EN-US>@@@@</span></u>";
		String inputStr="<input type='text' class='input_text #answerType# #rightType#' name='userAnswer#id#' value='#userAnswerStr#' #disable#  /> ";
		
		System.out.println(s1);
		
		String replacement = inputStr;
		Pattern p = Pattern.compile("<u>(<span[^>]*>)?(@*\\s?)(</span>)?</u>");
		Matcher m = p.matcher(s1);
		
		while (m.find()) {			
			s1 = m.replaceFirst(replacement);
			m.reset(s1);
		}
		
		System.out.println(s1);
		
		assertTrue(!s1.contains("<u>"));
		
	}
	
	public void testMathPrepare() {
		String s1 = "<m> </m> <m> <ss><ss> <m>";
		String s = s1.replaceAll("(>)(\\s+)(<)", "$1$3");
		System.out.println(s);
	}
	
	public void testMathTransQuotation() {
		String s1 = "<math>  <mrow>    <mi>Ba</mi>    <msub>      <mrow>        <mo stretchy='false'>(</mo>        <mi>NO</mi>       " +
				" <mo stretchy='false'>)</mo>      </mrow>      <mn>2</mn>    </msub>  </mrow></math>";
		String s = s1.replaceAll("'", "\\\\'");
		System.out.println(s);
	}
	
	public void testMath2() {
		String s1 = "<math><mrow><mi>ba</mi><msub><mrow><mo stretchy='false'>(</mo><mi>n</mi><msub><mi>o</mi><mn>2</mn></msub><mo stretchy='false'>)</mo></mrow><mn>3</mn></msub></mrow></math>";
		String s2 = "<math><mrow><mi>ba</mi><msub><mrow><mo stretchy='false'>(</mo><mi>n</mi><msub><mi>o</mi><mn>2</mn></msub><mo stretchy='false'>)</mo></mrow><mn>3</mn></msub></mrow></math>";
		
		if (s1.equals(s2)) System.out.println("Equals");
		
		if (s1.matches(s2)) System.out.println("Matches");
		else System.out.println("no Matches");
	}
	
	public void testMath3() {
		String s1 = "<math><mrow><msub><mi>K</mi><mn>2</mn></msub><mi>C</mi><msub><mi>O</mi><mn>3</mn></msub></mrow></math>";
		String s2 = "<math><mrow><msub><mi>K</mi><mn>2</mn></msub><mi>C</mi><msub><mi>O</mi><mn>3</mn></msub></mrow></math>";
		
		if (s1.equals(s2)) System.out.println("Equals");
		
		if (s1.matches("$*" + s2)) System.out.println("Matches");
		else System.out.println("no Matches");
	}
	
	public void testMath4() {
		String s1 = "<math display = 'block'>  <mrow>  <mi>S</mi>    <msub>    <mi>O</mi>    <mn>3</mn>   </msub>   <mtext>&nbsp; &nbsp; &nbsp;</mtext> </mrow> </math>";
		System.out.println(s1);
		//String s2 = ScoreUtil.answerPrepare(s1);
		//System.out.println(s2);
	}
	
	public void testItemUCount() {
		String s = "配平<u><span lang=EN-US>&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=59 height=17 " +
				"src='/resource/mpc/T5gao3mo-1.files/image099.jpg'></span><span style='font-family: 宋体'>＋</span><u><span lang=EN-US>" +
				"&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=54 height=16 src='/resource/mpc/T5gao3mo-1.files/image100.jpg'>" +
				"<img width=29 height=14 src='/resource/mpc/T5gao3mo-1.files/image101.jpg'><u>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;</u><img width=26 height=13 src='/resource/mpc/T5gao3mo-1.files/image102.jpg'></span>" +
				"<span style='font-family:宋体'>＋</span><u><span lang=EN-US>&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US>" +
				"<img width=37 height=16 src='/resource/mpc/T5gao3mo-1.files/image103.jpg'></span><span style='font-family:宋体'>＋" +
				"</span><u><span lang=EN-US>&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=35 height=22 " +
				"src='/resource/mpc/T5gao3mo-1.files/image104.jpg'></span><span style='font-family:宋体'>＋</span><u><span lang=EN-US>" +
				"&nbsp;&nbsp;&nbsp; </span></u><span lang=EN-US><img width=31 height=19 src='/resource/mpc/T5gao3mo-1.files/image105.jpg'>" +
				"</span></p>  <p class=MsoNormal><span style='font-family:宋体'>";
		
		System.out.println(s);
		
		Pattern p = Pattern.compile("</u>");
		Matcher m = p.matcher(s);
		
		int i =0;
		while (m.find()) {			
			i++;
		}		
		
		System.out.println(i);
	}
	
	public void testMath5() {
		String s1 = "<p style=\"text-align: center\"> " +
					"<embed src=\"/lylearnnet/UserFiles/Flash/3/junior/zk_nounsplural_001kt.swf\" width=\"691\" height=\"432\" " +
					"type=\"application/x-shockwave-flash\" play=\"true\" loop=\"true\" menu=\"true\">"+
					"</embed>" +
					"</p>";
		System.out.println(s1);
		String s2 = s1.replaceFirst("(.*<embed\\s+src=\")([^\"]+)(.*)", "$2");
		System.out.println("embed's src=" + s2);
	}
}
