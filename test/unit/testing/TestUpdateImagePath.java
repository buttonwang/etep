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

public class TestUpdateImagePath extends Zhu_BaseTest {

	/**
	 * 要替换的字符
	 * <span style='position:relative;
	 * <span style="vertical-align: baseline; position: relative; top: 12pt"><sub><img 
	 * @param content
	 * @return
	 */
	private String updatePath(String content) {
		if (content==null) return null;
		String  rep = content;
	
		rep = rep.replace("file:///E:\\数理化\\html\\数学\\高考\\", "");
		rep = rep.replace("files\\image", "files/image");		
		
		return rep;
	}

	@SuppressWarnings("unchecked")
	public void UpdateAnswer() {
		List<AnswerOption> answerOptions = genService.find("from AnswerOption A where A.content like ?", "<span style='position:relative;%");
		assertEquals(true, answerOptions.size()>0);
		
		for (AnswerOption answerOption: answerOptions) {
			//System.out.println(answerOption.getContent());
			String content = updatePath(answerOption.getContent());			
			System.out.println(content);
			answerOption.setContent(content);
		}
		genService.saveOrUpdateAll(answerOptions);
		System.out.println(answerOptions.size());
	}
	
	@SuppressWarnings("unchecked")
	public void UpdateItem() {
		List<Item> items = genService.find("from Item I where I.id >= 10000 and I.subject.code='M' and I.content like '%数理化%' " );
		assertEquals(true, items.size()>0);
		
		for (Item item: items) {
			System.out.println(item.getId());
			item.setContent(updatePath(item.getContent()));
			item.setSkills(updatePath(item.getSkills()));
			item.setHint(updatePath(item.getHint()));
			item.setAnalysisAtLarge1(updatePath(item.getAnalysisAtLarge1()));
			item.setAnalysisAtLarge2(updatePath(item.getAnalysisAtLarge2()));
			item.setAnalysisAtLarge3(updatePath(item.getAnalysisAtLarge3()));
			
			for (SubItem subItem: item.getSubItems()) {
				subItem.setContent(updatePath(subItem.getContent()));
				subItem.setSkills(updatePath(subItem.getSkills()));
				subItem.setHint(updatePath(subItem.getHint()));
				subItem.setAnalysisAtLarge1(updatePath(subItem.getAnalysisAtLarge1()));
				subItem.setAnalysisAtLarge2(updatePath(subItem.getAnalysisAtLarge2()));
				subItem.setAnalysisAtLarge3(updatePath(subItem.getAnalysisAtLarge3()));
			}
			
			for (AnswerOption answerOption: item.getAnswerOptions()) {									
				answerOption.setContent(updatePath(answerOption.getContent()));
			}
			//genService.save(item);
		}
		genService.saveOrUpdateAll(items);
		System.out.println(items.size());
	}
	
}
