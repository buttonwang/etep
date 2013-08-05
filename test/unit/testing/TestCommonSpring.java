package testing;

import com.ambow.trainingengine.exam.score.ScoreUtil;

/*
 * TestCommonSpring.java
 * 
 * Created on 2009-3-4 上午09:18:36
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

public class TestCommonSpring extends Zhu_BaseTest {

	
	public void testAnswerPrepare() {
		ScoreUtil scoreUtil = (ScoreUtil)getBean("scoreUtil");
		//String s = "<mo lspace='verythickmathspace' rspace='verythickmathspace' stretchy='true' > &darr; </mo >< mo > <mo> <mtext>点</mtext><mtext>燃</mtext><mtext>吗</mtext>";
		//String s = "<math>  <mrow>    <msub>      <mi>C</mi>      <mn>3</mn>    </msub>    <msub>      <mi>H</mi>      <mn>7</mn>    </msub>    <msub>      <mi>O</mi>      <mn>2</mn>    </msub>    <mi>N</mi>  </mrow></math>";
		/*String s = "<math>  " +
				   "<mrow>  " +
    "<mo stretchy='false'>(</mo>  " +
    "<mo>−</mo>  " +
    "<mi></mi> " +
    "<mtext>∞</mtext>  " +
    "<mtext>，</mtext>  " +
    "<mo>−</mo>  " +
    "<mn>5</mn>  " +
    "<mo stretchy='false'>]</mo>  " +
    "<mo>∪</mo>  " +
    "<mo stretchy='false'>[</mo>  " +
    "<mn>5</mn>  " +
    "<mtext>，</mtext>  " +
    "<mo>&plus;</mo>  " +
    "<mi></mi>  " +
    "<mtext>∞</mtext>  " +
    "<mo stretchy='false'>)</mo>  " +
    "</mrow>  " +
    "</math>";*/
		//String s = "<math><mrow><mo stretchy='false'>(</mo><mo>&minus;</mo><mi>&infin;</mi><mo>,</mo><mo>&minus;</mo><mn>5</mn><mo stretchy='false'>]</mo><mo>&cup;</mo><mo stretchy='false'>[</mo><mn>5</mn><mo>,</mo><mo>+</mo><mi>&infin;</mi><mo stretchy='false'>)</mo></mrow></math>";

		//String s = "<math>  <mrow>    <mfrac>      <mi>a</mi>      <mrow>        <mn>100</mn>        <mo>+</mo>        <mi>a</mi>      </mrow>    </mfrac>    <mo>&times;</mo>    <mn>100</mn>    <mo>%</mo>  </mrow></math>$<math>  <mrow>    <mfrac>      <mrow>        <mn>100</mn>        <mi>a</mi>      </mrow>      <mrow>        <mn>100</mn>        <mo>+</mo>        <mi>a</mi>      </mrow>    </mfrac>    <mo>%</mo>  </mrow></math>"; 
		//String s = "<math><mrow><mfrac><mrow><mn>100</mn><mi>a</mi></mrow><mrow><mn>100</mn><mo>&plus;</mo><mi>a</mi></mrow></mfrac><mtext>％</mtext></mrow></math>"; 
		
		//String s = "<math><mrow><mn>2</mn><mi>KMn</mi><msub><mi>O</mi><mn>4</mn></msub><munder><munder><mrow><mtext>&#21152;</mtext><mtext>&#28909;</mtext></mrow><mo>&macr;</mo></munder><mo>&macr;</mo></munder><msub><mi>K</mi><mn>2</mn></msub><mi>Mn</mi><msub><mi>O</mi><mn>4</mn></msub><mo>&plus;</mo><mi>Mn</mi><msub><mi>O</mi><mn>2</mn></msub><mo>&plus;</mo><msub><mi>O</mi><mn>2</mn></msub><mo lspace='verythickmathspace' rspace='verythickmathspace' stretchy='true'>&#8593;</mo></mrow></math>"; 
		//String s = "<math>  <mrow>    <mn>2</mn>    <mi>KMn</mi>    <msub>      <mi>O</mi>      <mn>4</mn>    </msub>    <munder>      <munder>        <mtext>加热</mtext>        <mo>&macr;</mo>      </munder>      <mo>&macr;</mo>    </munder>    <msub>      <mi>K</mi>      <mn>2</mn>    </msub>    <mi>Mn</mi>    <msub>      <mi>O</mi>      <mn>4</mn>    </msub>    <mo>+</mo>    <mi>Mn</mi>    <msub>      <mi>O</mi>      <mn>2</mn>    </msub>    <mo>+</mo>    <msub>      <mi>O</mi>      <mn>2</mn>    </msub>    <mo lspace='4px' rspace='4px'>&uarr;</mo>  </mrow></math>"; 
		
		//String s = "<math><mrow><mi>Zn</mi><mo>&plus;</mo><mn>2</mn><mi>HCI</mi><mo>=</mo><mi>ZnC</mi><msub><mi>I</mi><mn>2</mn></msub><mo>&plus;</mo><msub><mi>H</mi><mn>2</mn></msub><mo lspace='verythickmathspace' rspace='verythickmathspace'>↑</mo></mrow></math>"; 		 
		//String s = "<math>  <mrow>    <mi>Zn</mi>    <mo>+</mo>    <mn>2</mn>    <mi>HCl</mi>    <mo>=</mo>    <mi>ZnC</mi>    <msub>      <mi>l</mi>      <mn>2</mn>    </msub>    <mo>+</mo>    <msub>      <mi>H</mi>      <mn>2</mn>    </msub>    <mo lspace='4px' rspace='4px'>&uarr;</mo>  </mrow></math>"; 

		//String s1 = "<math><mrow><mn>11</mn><mo>×</mo><mn>29</mn><mo>&lt;</mo><mn>12</mn><mo>×</mo><mn>28</mn><mo>&lt;</mo><mn>13</mn><mo>×</mo><mn>27</mn><mo>&lt;</mo><mn>14</mn><mo>×</mo><mn>26</mn><mo>&lt;</mo><mn>15</mn><mo>×</mo><mn>25</mn><mo>&lt;</mo><mn>16</mn><mo>×</mo><mn>24</mn><mo>&lt;</mo><mn>17</mn><mo>×</mo><mn>23</mn><mo>&lt;</mo><mn>18</mn><mo>×</mo><mn>22</mn><mo>&lt;</mo><mn>19</mn><mo>×</mo><mn>21</mn><mo>&lt;</mo><mn>20</mn><mo>×</mo><mn>20</mn></mrow></math>"; 
		//String s2 = "<math>  <mrow>    <mn>11</mn>    <mo>&times;</mo>    <mn>29</mn>    <mo>&lt;</mo>    <mn>12</mn>    <mo>&times;</mo>    <mn>28</mn>    <mo>&lt;</mo>    <mn>13</mn>    <mo>&times;</mo>    <mn>27</mn>    <mo>&lt;</mo>    <mn>14</mn>    <mo>&times;</mo>    <mn>26</mn>    <mo>&lt;</mo>    <mn>15</mn>    <mo>&times;</mo>    <mn>25</mn>    <mo>&lt;</mo>    <mn>16</mn>    <mo>&times;</mo>    <mn>24</mn>    <mo>&lt;</mo>    <mn>17</mn>    <mo>&times;</mo>    <mn>23</mn>    <mo>&lt;</mo>    <mn>18</mn>    <mo>&times;</mo>    <mn>22</mn>    <mo>&lt;</mo>    <mn>19</mn>    <mo>&times;</mo>    <mn>21</mn>    <mo>&lt;</mo>    <mn>20</mn>    <mo>&times;</mo>    <mn>20</mn>  </mrow></math>"; 

		//String s1 = "<math>  <mrow>    <mn>2</mn>    <mi>KMn</mi>    <msub>      <mi>O</mi>      <mn>4</mn>    </msub>    <munder>      <munder>        <mtext>加热</mtext>        <mo>ˉ</mo>      </munder>      <mo>ˉ</mo>    </munder>    <msub>      <mi>K</mi>      <mn>2</mn>    </msub>    <mi>Mn</mi>    <msub>      <mi>O</mi>      <mn>4</mn>    </msub>    <mo>+</mo>    <mi>Mn</mi>    <msub>      <mi>O</mi>      <mn>2</mn>    </msub>    <mo>+</mo>    <msub>      <mi>O</mi>      <mn>2</mn>    </msub>    <mo lspace='4px' rspace='4px'>↑</mo>  </mrow></math>"; 
		//String s2 = "<math><mrow><mn>2</mn><mi>KMn</mi><msub><mi>O</mi><mn>4</mn></msub><munder><munder><mtext>加热</mtext><mo>&macr;</mo></munder><mo>&macr;</mo></munder><msub><mi>K</mi><mn>2</mn></msub><mi>Mn</mi><msub><mi>O</mi><mn>4</mn></msub><mo>+</mo><mi>Mn</mi><msub><mi>O</mi><mn>2</mn></msub><mo>+</mo><msub><mi>O</mi><mn>2</mn></msub><mo lspace='4px' rspace='4px'>&uarr;</mo></mrow></math>";
		
		//String s1 = "<math>   <mrow>     <mn>4</mn>     <mmultiscripts>       <mrow>         <mi>H</mi>         <mo>→</mo>         <mmultiscripts>           <mi>He</mi>           <mprescripts/>           <mn>2</mn>           <mn>4</mn>         </mmultiscripts>       </mrow>       <mprescripts/>       <mn>1</mn>       <mn>1</mn>     </mmultiscripts>     <mo>+</mo>     <mn>2</mn>     <msup>       <mi>e</mi>       <mo>?</mo>     </msup>   </mrow> </math> "; 
		//String s2 = "<math><mrow><mn>4</mn><mmultiscripts><mi>H</mi><mprescripts/><mn>1</mn><mn>1</mn></mmultiscripts><mo>&rarr;</mo><mmultiscripts><mi>H</mi><mprescripts/><mn>2</mn><mn>4</mn></mmultiscripts><mi>e</mi><mo>+</mo><mn>2</mn><msup><mi>e</mi><mo>&minus;</mo></msup></mrow></math>";
		

		//String s1 = "<math>  <mrow>    <mo lspace='0px' rspace='0px' fence='true'>&lcub;</mo>    <mi>y</mi>    <mo lspace='thickmathspace' rspace='thickmathspace' fence='false'>&verbar;</mo>    <mi>y</mi>    <mo>&ne;</mo>    <mo>&minus;</mo>    <mn>1</mn>    <mo lspace='0px' rspace='0px' fence='true' form='postfix'>&rcub;</mo>  </mrow></math>"; 
		//String s2 = " <math><mrow><mo lspace='0px' rspace='0px' fence='true'>&lcub;</mo><mi>y</mi><mo>|</mo><mi>y</mi><mo>&ne;</mo><mo>&minus;</mo><mn>1</mn><mo lspace='0px' rspace='0px' fence='true' form='postfix'>&rcub;</mo></mrow></math>";

		//String s1 = "<math display = 'block'>   <mrow>     <mn>2</mn>     <mi>Na</mi>     <mo>&plus;</mo>     <mn>2</mn>     <mi>HCl</mi>     <mo>=</mo>     <mn>2</mn>     <mi>NaCl</mi>     <mo>&plus;</mo>     <msub>       <mi>H</mi>       <mn>2</mn>     </msub>     <mo>&uarr;</mo>   </mrow> </math>"; 
		//String s2 = "<math><mrow><mn>2</mn><mi>Na</mi><mo>+</mo><mn>2</mn><mi>HCl</mi><mo>=</mo><mn>2</mn><mi>NaCl</mi><mo>+</mo><msub><mi>H</mi><mn>2</mn></msub><mo lspace='4px' rspace='4px'>&uarr;</mo></mrow></math>";
	
		String s1 = "<math><mrow><mn>2</mn><mi>CuO</mi><mo>&plus;</mo><mi>C</mi><mo>&trie;</mo><mn>2</mn><mi>Cu</mi><mo>&plus;</mo><mi>C</mi><msub><mi>O</mi><mn>2</mn></msub><mo lspace='verythickmathspace' rspace='verythickmathspace'>&uarr;</mo></mrow></math>"; 
		String s2 = "<math><mrow><mn>2</mn><mi>CuO</mi><mo>+</mo><mi>C</mi><mo>&trie;</mo><mn>2</mn><mi>Cu</mi><mo>+</mo><mi>C</mi><msub><mi>O</mi><mn>2</mn></msub><mo lspace='4px' rspace='4px'>&uarr;</mo></mrow></math>";
		
		s1 = scoreUtil.answerPrepare("formula", s1);		
		s2 = scoreUtil.answerPrepare("formula", s2);
		
		System.out.println(s1);
		System.out.println(s2);
		
		assertEquals(s1, s2);
		
	}
	
	public void testNormalAnswerPrepare() {
		ScoreUtil scoreUtil = (ScoreUtil)getBean("scoreUtil");
				
		//String s1 = "34∶39；";
		//String s2 = "34：39"; 
		
		String s1 = "9，-9";
		String s2 = "9,-9"; 
		
		
		s1 = scoreUtil.answerPrepare("normal", s1);		
		s2 = scoreUtil.answerPrepare("normal", s2);
		
		System.out.println(s1);
		System.out.println(s2);
		
		assertEquals(s1, s2);		
	}
	

	public void testManyChoose() {
		String currentAnswer = "；";
		String standAnswer = " A；B；C；";
		
		//String currentAnswer = ">";
		//String standAnswer = "&gt;";
		
		boolean rightFlag =false;
		boolean error = false;
		float score = 0f;
		String cAnswer = currentAnswer.replaceAll("[^A-Z]", "");
		String sAnswer = standAnswer.replaceAll("[^A-Z]", "");
		char[] c = cAnswer.toCharArray();
		int i;
		for (i = 0; i<c.length; i++) {
			if (sAnswer.indexOf(c[i])==-1) {
				error = true;
				break;
			}
		}
		
		if (cAnswer.trim().equals("")) error =true;
		
		if (error) {
			rightFlag = false; score = 0f;
		} else if (i < sAnswer.length() ) {
			rightFlag = false; score = 0.5f;
		} else {
			rightFlag = true;  score = 1f;
		}
		
		System.out.println(score);		
		
	}
}
