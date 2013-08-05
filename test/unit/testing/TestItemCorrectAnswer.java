package testing;

import junit.framework.TestCase;

import com.ambow.trainingengine.itembank.domain.Item;

public class TestItemCorrectAnswer extends TestCase{
	 
	public void test(){
		String realAnswer ;
		//                                       ABCD         
		//                                       DBAC 
		realAnswer = Item.getRandomCrrectAnswer("CBDA","AB");
		assertEquals("BD".equals(realAnswer)||"DB".equals(realAnswer),true );
		
		//试题原来的实际 ：                          ABCD	 
		//展示给用户的真实选项顺序是  ABCD 才是正确的答案ab或ba
		realAnswer =Item.getRandomCrrectAnswer("ABCD","AB");
		assertEquals("BA".equals(realAnswer)||"AB".equals(realAnswer),true );
		
		//试题原来的实际 ：                          ABCDEF	             
		//展示给用户的真实选项顺序是  DFCEAB
		realAnswer =Item.getRandomCrrectAnswer("EFCADB","AE");
		assertEquals("AD".equals(realAnswer)||"DA".equals(realAnswer),true );
	}
	
}
