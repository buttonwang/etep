package testing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import junit.framework.TestCase;

/*
 * TestCommon.java
 * 
 * Created on 2009-2-23 下午07:48:31
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

public class TestCommon  extends TestCase {
	
	public void testA() {
		String s = "<math><mrow><msub><mi>h</mi><mn>2</mn></msub><mi>o</mi></mrow></math>\r\n"+
				   "$<math><mrow><mi>n</mi><msup><mi>a</mi><mo>+</mo></msup></mrow></math>\r\n"+
				   "$<math><mrow><mi>c</mi><msup><mi>l</mi><mo>?</mo></msup></mrow></math>\r\n";
		System.out.println(s);
		
		String s1  = s.replaceAll("\r\n", "");
		System.out.println(s1);
	}
	
	public void testB() {
		String s = "<p>content xdsfas </p>";
		System.out.println(s);
		
		String s1 = s;
		s1 = s1.replaceFirst("^<p>",  "");
		s1 = s1.replaceFirst("</p>$", "");
		System.out.println(s1);
	}
	
	public void testC() {
		String s = "<mrow><mtext>点</mtext><mtext>燃</mtext><mtext>吗</mtext></mrow>";
		System.out.println(s);
		
		String s1 = s;
		s1 = s1.replaceAll("</mtext><mtext>",  "");
		
		System.out.println(s1);
	}
	
	public void testD() {
		//String s = "<mo lspace='verythickmathspace' rspace='verythickmathspace' stretchy='true'>↓</mo><mo>";
		String s = "<mo  rspace='verythickmathspace' stretchy='true' lspace='verythickmathspace' >↓</mo>";
		System.out.println(s);
		
		String s1 = s;
		s1 = s1.replaceAll("\\s*lspace[^>]+>",  ">");
		s1 = s1.replaceAll("\\s*rspace[^>]+>",  ">");
		s1 = s1.replaceAll("\\s*stretchy[^>]+>",  ">");
		
		System.out.println(s1);
	}
	
	public void testE() {
		//String s = "<mo lspace='verythickmathspace' rspace='verythickmathspace' stretchy='true'>↓</mo>";
		String s = "<math>  <mrow>    <msub>      <mi>C</mi>      <mn>3</mn>    </msub>    <msub>      <mi>H</mi>      <mn>7</mn>    </msub>    <msub>      <mi>O</mi>      <mn>2</mn>    </msub>    <mi>N</mi>  </mrow></math>";
		System.out.println(s);
		
		String s1 = s;
		String reg = "(>)(\\s+)(<)";		
		System.out.println("reg:" + reg);
		String rls = "$1$3";
		s1 = s1.replaceAll(reg,  rls);
			
		System.out.println(s1);
	}
	
	public void testG() {
		String s = "  A, B, C,   ";
		System.out.println(s);
		String s1 = s.replaceAll("[^A-Z]", "");
		System.out.println(s1);
	}
	
	
	public void testH() {
		String s = "&#8242;";
		System.out.println(s);
		String s1 = "" +  (char)(Integer.parseInt("8242"));	
		System.out.println("testH:	" + s1);
		
		String s2 = String.valueOf((int)( "¯".charAt(0)) );
	    //String s2 = String.valueOf((int)("".charAt(0)) );
		System.out.println(s2);
	}

	public String replacsUTF8Code(String input) {
		String s = input;
		Pattern p = Pattern.compile("(&#)(\\d+)(;)");
		Matcher m = p.matcher(s);
		
		while (m.find()) {
			String g = m.group(2);
			g = "" +  (char)(Integer.parseInt(g));
			System.out.println(g);
			s = m.replaceFirst(g);
			m.reset(s);
		}
		return s;
	}
	
	public void testI() {
		String s = "<math> <mrow>  <mn>3</mn><mi>&deg;</mi><msup>  <mn>37</mn> <mo>&#8242;</mo>  </msup>  <msup>	 <mn>12</mn> <mo>&#8243;</mo> </msup> </mrow></math>";
		System.out.println(s);
		
		System.out.println(replacsUTF8Code(s));
	}
	
	public void testJ() {
		String s = "<math><mrow><mn>x</mn><mo>></mo> <mn>11</mn><mo>×</mo><mn>29</mn><mo><</mo><mn>12</mn><mo>×</mo><mn>28</mn><mo><</mo><mn>13</mn><mo>×</mo><mn>27</mn><mo><</mo><mn>14</mn><mo>×</mo><mn>26</mn><mo><</mo><mn>15</mn><mo>×</mo><mn>25</mn><mo><</mo><mn>16</mn><mo>×</mo><mn>24</mn><mo><</mo><mn>17</mn><mo>×</mo><mn>23</mn><mo><</mo><mn>18</mn><mo>×</mo><mn>22</mn><mo><</mo><mn>19</mn><mo>×</mo><mn>21</mn><mo><</mo><mn>20</mn><mo>×</mo><mn>20</mn></mrow></math>";
		System.out.println(s);
		
		String s1 = s.replaceAll("><<", ">&lt;<");
		s1 = s1.replaceAll(">><", ">&gt;<");
		System.out.println(replacsUTF8Code(s1));
	}
	
	//若函数<span lang=EN-US><sub><img width=80 height=21 src="/resource/mpc/file:///E:\数理化\html\数学\高考\q019.files\image041.gif"></sub></span><span style='font-family:宋体'>的图像与函数</span><span lang=EN-US><sub><img width=84 height=25 src="/resource/mpc/file:///E:\数理化\html\数学\高考\q019.files\image042.gif"></sub></span><span style='font-family:宋体'>的图像关于直线</span><span lang=EN-US><sub><img width=39 height=17 src="/resource/mpc/file:///E:\数理化\html\数学\高考\q019.files\image043.gif"></sub></span><span style='font-family:宋体'>对称，则</span><span lang=EN-US><sub><img width=48 height=21 src="/resource/mpc/file:///E:\数理化\html\数学\高考\q019.files\image044.gif"></sub>(&nbsp; )</span>
	
	public void testK() {
		String s = "若函数<span lang=EN-US><sub><img width=80 height=21 src='/resource/mpc/file:///E:\\数理化\\html\\数学\\高考\\q019.files\\image041.gif'></sub></span><span style='font-family:宋体'>的图像与函数</span><span lang=EN-US><sub><img width=84 height=25 src='/resource/mpc/file:///E:\\数理化\\html\\数学\\高考\\q019.files\\image042.gif'></sub></span><span style='font-family:宋体'>的图像关于直线</span><span lang=EN-US><sub><img width=39 height=17 src='/resource/mpc/file:///E:\\数理化\\html\\数学\\高考\\q019.files\\image043.gif'></sub></span><span style='font-family:宋体'>对称，则</span><span lang=EN-US><sub><img width=48 height=21 src='/resource/mpc/file:///E:\\数理化\\html\\数学\\高考\\q019.files\\image044.gif'></sub>(&nbsp; )</span>";
		System.out.println(s);
		
		String s1 = s.replace("file:///E:\\数理化\\html\\数学\\高考\\", "");
		s1 = s1.replace("files\\image", "files/image");
		System.out.println(replacsUTF8Code(s1));
	}
	
	public void testL() {
		String s = "所以本{B}题选C。错选原因一是认为粒子的核外{D}电子数相同就是同种原子，从而导致错选A";
		System.out.println(s);
		
		String s1 = s.replaceAll("([^\\{])([A-D])([^\\}]?)", "$1{$2}$3");
		System.out.println(s1);
		
		String s2 = s1.replaceAll("\\{C\\}", "X").replaceAll("\\{A\\}", "Y");
		System.out.println(s2);
	}
	
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	public void testM() {
		String s = "9，-9";
		System.out.println(s);
		
		String s1 = ToDBC(s);
		System.out.println(s1);
	}
	
	public void testN() {
		String s = "两个分力的大小确定后，两分力的夹角越大，合力越小。两分力同向时，合力最大，两分力反向时，合力最小。选{<span lang=EN-US>B</span><span style='font-family:宋体'>}。</span>";
		//String s = "两个分力的大小确定后，两分力的夹角越大，合力越小。两分力同向时，合力最大，两分力反向时，合力最小。选{<span lang=EN-US><span lang=EN-US>B</span><span style='font-family:宋体'>}。</span>";
		//String s = "两个分力的大小确定后，两分力的夹角越大，合力越小。两分力同向时，合力最大，两分力反向时，合力最小。选{<span lang=EN-US><span lang=EN-US>B}。</span>";
		//String s = "两个分力的大小确定后，两分力的夹角越大，合力越小。两分力同向时，合力最大，两分力反向时，合力最小。选{B}。</span>";
		//String s = "两个分力的大小确定后，两分力的夹角越大，合力越小。两分力同向时，合力最大，两分力反向时，合力最小。选{B</span><span style='font-family:宋体'>}。</span>";
		System.out.println(s);
		
		String s1 = s.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$1$3$5");
		//String s1 = s.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$3");
		System.out.println(s1);
	}
	
	public void testO() {
		System.out.println((17 / 10 * 10));
	}
	
	public void testP() {
		String content = "<u>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   </u>";
		content = content.replaceAll("\\s+(&nbsp;)", "$1");
		content = content.replaceAll("(&nbsp;)\\s+", "$1");
	    System.out.println("test P:	" + content);
	}
	
	public void testTrim() {
		String r = "S8　　　　　　　　　3ｄｄｄｄ";
		String p = r.replaceAll("　+", " ");
		System.out.println(p);
	}
	
	public void testDateFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
	    String ctime = formatter.format(new Date()); 
	    System.out.println(ctime);
	}
	
	public void testQ() {
		String r = "ok${system_type}";
		String p = r.replaceAll("\\$.*sys", "9");
		System.out.println(p);
	}
	
	public void testR() {
		float r = 9.001f + 0.5f - 1/99999;
		int i = Math.round(r);
		System.out.println(i);
		System.out.println(Math.rint(r));
		
		System.out.println("凑整:Math.ceil(2)=" + (int)Math.ceil(2.00));

		System.out.println("凑整:Math.ceil(-2.1)=" + (int)Math.ceil(-2.1));
		System.out.println("凑整:Math.ceil(9.1)=" + Math.ceil(9.0001));
	}
	
	public void testS() {
		String s ="<br /> \r\t" +
				" <br /> \r\t" +
				" <br /> \r\t" +
				"IT snowed heavily last week in northern China. Big snows fell on Beijing, Hebei, Shanxi," +
				" Ningxia and Xinjiang. For many cities, it was the biggest snowfall in 50 years. " +
				"The snow made traveling hard. Many schools had to close for days.<br /> <br /> Jia Aizhen, 14, " +
				"lives in Taiyuan, Shanxi. She should have a mid-term (期中的) test on Wednesday. Because of the snow, they had the tes";
		System.out.println(s); 
		String r = s.replaceAll("<br />\\s*<br />\\s*(<br />)*\\s*", "<br />");
		System.out.println(r);
	}
	
	public void testT() {
		Pattern p = Pattern.compile("\\ba*b\\B");
		Matcher m = p.matcher("seek food aaaaabb b abcebook!!");
		System.out.println(m.matches());
		
		String s = "seek food aaaaabb b abcebook!!".replaceAll("\\ba*b\\B", "x");
		System.out.println(s);
	}
	
	public void testU() {
		String s = "style='font-size:10.5pt'>5</span><u>  <span><span style='font-size:10.5pt;font-family: 宋体'>整除的概率为</span><u> <span lang=EN-US style='font-size:10.5pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></u><span style='font-size:10.5pt;font-family:宋体'>；</span></p>";
		System.out.println(s);
		s = s.replaceAll("<u>\\s*", "<u>");
		System.out.println(s);
	}
	
}
