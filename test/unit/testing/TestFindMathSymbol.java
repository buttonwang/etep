package testing;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



import com.ambow.trainingengine.exam.score.ScoreUtil;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * TestFindMathSymbol.java
 * 
 * Created on 2009-3-25 下午04:26:25
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

public class TestFindMathSymbol extends Zhu_BaseTest {

	Map<String, String> symbols;
	
	Set<String> keySymbols;
	
	Map<String, String> mathSymbols = new HashMap<String, String>();
	

	public void  setUp(){		
		super.setUp();
		
		ScoreUtil scoreUtil = (ScoreUtil)getBean("scoreUtil");
		
		symbols = scoreUtil.getMathMatchs().getMathSymbols();
		keySymbols = symbols.keySet();
	}
	
	private String hasSymbol(String s) {
		String ret = "";
		for (String match: keySymbols) {
			if (s.contains(match)){
				ret = match;
				mathSymbols.put(match, symbols.get(match));
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public void testFindItem() {
		List<Item> items = genService.find("from Item I where I.id >= 10000 and SUBSTRING(I.itemType.code, -2, 1) =3 " );
		assertEquals(true, items.size()>0);
		String r;
		for (Item item: items) {
			hasSymbol(item.getCorrectAnswer());
			//if (!r.equals(""))
			//	System.out.println(item.getCode() + "----->" + r);
			
			for (SubItem subItem: item.getSubItems()) {
				hasSymbol(subItem.getCorrectAnswer());
			}
		}
		
		System.out.println(items.size());
		
		Set<String> keys = mathSymbols.keySet();
		for (String key: keys) {
			System.out.println(key + "<----->" + mathSymbols.get(key));
		}
		
		String u1 = "update item     set correct_answer = replace(correct_answer, '>value<', '>&key;<')  where correct_answer like  '%>value<%';";
		String u2 = "update sub_item set correct_answer = replace(correct_answer, '>value<', '>&key;<')  where correct_answer like  '%>value<%';";
		
		for (String key: keys) {
			String value = mathSymbols.get(key);
			String update1 = u1.replace("key", key).replace("value", value);
			String update2 = u2.replace("key", key).replace("value", value);
			System.out.println(update1);
			System.out.println(update2);
		}
	}
}
