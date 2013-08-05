package testing;

import java.util.List;

import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.util.ItemRegx;

/*
 * TestUpdateItemInvalidateChar.java
 * 
 * Created on 2009-1-13 下午11:20:15
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

public class TestUpdateItemInvalidateChar extends Zhu_BaseTest {

	@Override
	protected void setPath() {
		// TODO Auto-generated method stub

	}
	
	/** 对MPC系列的题修改 
	 *  1. 去除HTML代码末尾无效的字符
	 *  2. 方法与技巧转化为纯文本的格式 
	 *  3. 非选择题正确答案设置为答案原型的值（去掉html标签） 
	 */
	@SuppressWarnings("unchecked")
	public void testFixMPCItem() {
		List<Item> items = genService.find("from Item I where I.id > 76659");
		System.out.println(items.size());
		for(Item item: items) {
			System.out.println(item.getId());
			item.setContent(ItemRegx.rightTrim(item.getContent()));
			item.setHint(ItemRegx.rightTrim(item.getHint()));
			item.setAnalysisAtLarge1(ItemRegx.rightTrim(item.getAnalysisAtLarge1()));
			item.setAnalysisAtLarge2(ItemRegx.rightTrim(item.getAnalysisAtLarge2()));
			item.setAnalysisAtLarge3(ItemRegx.rightTrim(item.getAnalysisAtLarge3()));
			item.setAnswerPrototype(ItemRegx.rightTrim(item.getAnswerPrototype()));
			item.setSkills(ItemRegx.clearItemHtmlSign(item.getSkills()));
			
			if ((item.getCorrectAnswer()==null)||(item.getCorrectAnswer().equals("")))
				item.setCorrectAnswer(ItemRegx.clearItemHtmlSign(item.getAnswerPrototype()));
			
			for (SubItem subItem: item.getSubItems()) {
				subItem.setContent(ItemRegx.rightTrim(subItem.getContent()));
				subItem.setHint(ItemRegx.rightTrim(subItem.getHint()));
				subItem.setAnalysisAtLarge1(ItemRegx.rightTrim(subItem.getAnalysisAtLarge1()));
				subItem.setAnalysisAtLarge2(ItemRegx.rightTrim(subItem.getAnalysisAtLarge2()));
				subItem.setAnalysisAtLarge3(ItemRegx.rightTrim(subItem.getAnalysisAtLarge3()));
				subItem.setAnswerPrototype(ItemRegx.rightTrim(subItem.getAnswerPrototype()));
				subItem.setSkills(ItemRegx.clearItemHtmlSign(subItem.getSkills()));
				
				if ((subItem.getCorrectAnswer()==null)||(subItem.getCorrectAnswer().equals("")))
					subItem.setCorrectAnswer(ItemRegx.clearItemHtmlSign(subItem.getAnswerPrototype()));
			}
			
			for(AnswerOption answerOption: item.getAnswerOptions()) {
				answerOption.setContent(ItemRegx.rightTrim(answerOption.getContent()));
			}
			
			//genService.save(item);
		}
		genService.saveOrUpdateAll(items);
		System.out.println("finally update !");
	}
	
	public void AtestFixAltHint() {
		Item item = null;
		for(int i=1; i<76000; i++) {
			item = genService.get(Item.class, i);
			if (item==null) continue;
			System.out.println(item.getId());
			item.setContent(ItemRegx.clearImgAlt(item.getContent()));
			item.setHint(ItemRegx.clearImgAlt(item.getHint()));
			item.setAnalysisAtLarge1(ItemRegx.clearImgAlt(item.getAnalysisAtLarge1()));
			item.setAnalysisAtLarge2(ItemRegx.clearImgAlt(item.getAnalysisAtLarge2()));
			item.setAnalysisAtLarge3(ItemRegx.clearImgAlt(item.getAnalysisAtLarge3()));
			item.setAnswerPrototype(ItemRegx.clearImgAlt(item.getAnswerPrototype()));
			
			for (SubItem subItem: item.getSubItems()) {
				subItem.setContent(ItemRegx.clearImgAlt(subItem.getContent()));
				subItem.setHint(ItemRegx.clearImgAlt(subItem.getHint()));
				subItem.setAnalysisAtLarge1(ItemRegx.clearImgAlt(subItem.getAnalysisAtLarge1()));
				subItem.setAnalysisAtLarge2(ItemRegx.clearImgAlt(subItem.getAnalysisAtLarge2()));
				subItem.setAnalysisAtLarge3(ItemRegx.clearImgAlt(subItem.getAnalysisAtLarge3()));
				subItem.setAnswerPrototype(ItemRegx.clearImgAlt(subItem.getAnswerPrototype()));
			}
			
			for(AnswerOption answerOption: item.getAnswerOptions()) {
				answerOption.setContent(ItemRegx.clearImgAlt(answerOption.getContent()));
			}
			
			genService.save(item);
			genService.clear();
		}
	}
	
	public void AtestReplaceAlt() {
		String s= "<img alt= \"xxxxxx\"> <img alt=\"sdf\" alt=\"高考资源网( www.ks5u.com)，中国最大的高考网站，您身边的高考专家。\" width=\"104\"  " +
				"src=\"/resource/mpc/T181.files/image122.gif\" />   alt = \"设计放到放到发 \"";
		
		s = ItemRegx.clearImgAlt(s);
		System.out.println(s);
	}
}
