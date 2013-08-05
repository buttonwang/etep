package testing;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * testCheckFillItem.java
 * 
 * Created on 2009-3-3 上午09:38:08
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

public class TestCheckFillItem extends Zhu_BaseTest {

	private int getFillCount(String fill) {
		if (fill == null) return 0;
		Pattern p = Pattern.compile("</u>");
		Matcher m = p.matcher(fill);
		
		int i =0;
		while (m.find()) {			
			i++;
		}
		return i;
	}
	
	private int getAnswerCount(String fill) {
		int i =0;
		if (fill == null) return 0;
		String s = fill.trim().replaceAll("；$", "");
		i = s.split("；").length;		
		return i;
	}
	
	private int getScoreCount(String fill) {
		int i =0;
		if (fill == null) return 0;
		String s = fill.trim().replaceAll("；$", "");
		i = s.split("；").length;		
		return i;
	}
	
	public void testCheck3x() {
		List<Item> items = genService.find("from Item I where I.id >= 10000 and I.subject.code='P' and I.itemType.code like '%3_' " );
		assertEquals(true, items.size()>0);
		
		int fillCount = 0;
		int answerCount = 0;
		int scoreCount = 0;
		boolean error = false;
		for (Item item: items) {
			error = false;			
			fillCount = getFillCount(item.getContent());
			answerCount = getAnswerCount(item.getCorrectAnswer());
			scoreCount = getScoreCount(item.getScore2());
			
			if (fillCount!=answerCount)  error =true;
			if (fillCount!=scoreCount)   error =true;
			if (answerCount!=scoreCount) error =true;
			
			if (error) {
				System.out.println(item.getCode() + "	：" + String.valueOf(fillCount) + 
						"	：" + String.valueOf(answerCount)  + 
						"	：" + String.valueOf(scoreCount) );
			}	
		}
		
		System.out.println(items.size());
	}
	
	public void testCheck4x() {
		List<Item> items = genService.find("from Item I where I.id >= 10000 and I.subject.code='P' and I.itemType.code like '%4_' " );
		assertEquals(true, items.size()>0);
		
		int fillCount = 0;
		int answerCount = 0;
		int scoreCount = 0;
		boolean error = false;
		for (Item item: items) {									
			for (SubItem subItem: item.getSubItems()) {
				error = false;
				fillCount = getFillCount(subItem.getContent());
				answerCount = getAnswerCount(subItem.getCorrectAnswer());
				scoreCount = getScoreCount(subItem.getScore2());
				
				if (fillCount!=answerCount)  error =true;
				if (fillCount!=scoreCount)   error =true;
				if (answerCount!=scoreCount) error =true;
				
				if (error) {
					System.out.println(item.getCode() + " . 子题  " + subItem.getOrderNum() +  
							"	：" + String.valueOf(fillCount) + 
							"	：" + String.valueOf(answerCount)  + 
							"	：" + String.valueOf(scoreCount) );
				}
			}				
		}
		
		System.out.println(items.size());
	}
}
