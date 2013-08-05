package testing;

import java.util.List;

import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * TestUpdateAnswer.java
 * 
 * Created on 2009-2-19 下午08:50:40
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

public class TestUpdateAnswer extends Zhu_BaseTest {

	/**
	 * 要替换的字符
	 * <span style='position:relative;
	 * <span style="vertical-align: baseline; position: relative; top: 12pt"><sub><img 
	 * @param content
	 * @return
	 */
	private String clearSpan(String content) {
		if (content==null) return null;
		String  rep = content;
		rep = rep.replaceAll("(<span style='position:relative;[^>]+><sub>)(<img[^>]+>)(</sub></span>)", "$2");
		rep = rep.replaceAll("<span style='position:relative;[^>]+><sub><img", "<span><sub><img");
		rep = rep.replaceAll("<span style='position:relative;[^>]+><sub><span[^>]+><img", "<span><sub><span><img");
		
		rep = rep.replaceAll("(<span [^>]+vertical-align[^>]+><sub>)(<img[^>]+>)(</sub></span>)", "$2");
		rep = rep.replaceAll ("<span [^>]+vertical-align[^>]+><sub><img", "<span><sub><img");
		rep = rep.replaceAll ("<span [^>]+vertical-align[^>]+><sub><span[^>]+><img", "<span><sub><span><img");
		
		return rep;
	}

	@SuppressWarnings("unchecked")
	public void UpdateAnswer() {
		List<AnswerOption> answerOptions = genService.find("from AnswerOption A where A.content like ?", "<span style='position:relative;%");
		assertEquals(true, answerOptions.size()>0);
		
		for (AnswerOption answerOption: answerOptions) {
			//System.out.println(answerOption.getContent());
			String content = clearSpan(answerOption.getContent());			
			System.out.println(content);
			answerOption.setContent(content);
		}
		genService.saveOrUpdateAll(answerOptions);
		System.out.println(answerOptions.size());
	}
	
	@SuppressWarnings("unchecked")
	public void UpdateItem() {
		List<Item> items = genService.find("from Item I where I.id >= 10000 and I.subject.code='M'" );
		assertEquals(true, items.size()>0);
		
		for (Item item: items) {
			System.out.println(item.getId());
			item.setContent(clearSpan(item.getContent()));
			item.setSkills(clearSpan(item.getSkills()));
			item.setHint(clearSpan(item.getHint()));
			item.setAnalysisAtLarge1(clearSpan(item.getAnalysisAtLarge1()));
			item.setAnalysisAtLarge2(clearSpan(item.getAnalysisAtLarge2()));
			item.setAnalysisAtLarge3(clearSpan(item.getAnalysisAtLarge3()));
			
			for (SubItem subItem: item.getSubItems()) {
				subItem.setContent(clearSpan(subItem.getContent()));
				subItem.setSkills(clearSpan(subItem.getSkills()));
				subItem.setHint(clearSpan(subItem.getHint()));
				subItem.setAnalysisAtLarge1(clearSpan(subItem.getAnalysisAtLarge1()));
				subItem.setAnalysisAtLarge2(clearSpan(subItem.getAnalysisAtLarge2()));
				subItem.setAnalysisAtLarge3(clearSpan(subItem.getAnalysisAtLarge3()));
			}
			
			for (AnswerOption answerOption: item.getAnswerOptions()) {											
				answerOption.setContent(clearSpan(answerOption.getContent()));
			}
			//genService.save(item);
		}
		genService.saveOrUpdateAll(items);
		System.out.println(items.size());
	}
	
}
