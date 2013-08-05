package testing;

import java.util.List;

import com.ambow.trainingengine.itembank.domain.Item;

/*
 * TestUpdateItemCode.java
 * 
 * Created on 2009-1-14 上午10:25:17
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

public class TestUpdateItemCode extends Zhu_BaseTest {

	@Override
	protected void setPath() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 按知识点更新试题的Code 知识点代码 + 四位顺序号
	 */
	private String fillZero(String str, int length) {
		String zero = "000000000000";
		if (str != null)
			zero += str.trim();
		return zero.substring(zero.length() - length);
	}

	@SuppressWarnings("unchecked")
	public void UpdateCode() {
		List<String> kpCodes = genService.find("select I.code from Item I where I.id >13250000 and I.subject.code = 'P' group by I.code ");
		//assertEquals(146 , kpCodes.size());
		for (String kpCode: kpCodes) {
			System.out.println(kpCode);
			int i = 1;
			String kp = kpCode.substring(0, 7);
			List<Item> items = genService.find(" from Item I where I.code = '"  + kpCode + "'");
			for (Item item: items) {
				String code = kp + fillZero(String.valueOf(i), 4);
				System.out.println(item.getId() + ": ->" + code);
				item.setCode(code);
				i++;
			}
			genService.saveOrUpdateAll(items);
			items = null;
		}
		kpCodes.clear();
	}
	
	/**
	 * 对新导入的试题修改code ： 适用MPC项目。
	 */
	@SuppressWarnings("unchecked")
	public void testUpdateCode2() {
		List<String> kpCodes = genService.find("select I.code from Item I where I.id > 76659 group by I.code ");
		//assertEquals(146 , kpCodes.size());
		int count = 1;
		for (String kpCode: kpCodes) {
			System.out.println(kpCode);
			//int i = 1;
			int i = getMaxCode(kpCode.replace("0000", ""));
			String kp = kpCode.substring(0, 7);
			List<Item> items = genService.find(" from Item I where I.id > 76659 and I.code = '"  + kpCode + "'");
			for (Item item: items) {
				String code = kp + fillZero(String.valueOf(i), 4);
				System.out.println(item.getId() + ": ->" + code);
				item.setCode(code);
				i++;
			}
			genService.saveOrUpdateAll(items);
			items = null;
			System.out.println(count++);
		}
		kpCodes.clear();
	}
	
	private int getMaxCode(String kpCode) {
		String hsql = "select max(I.code) as maxCode from Item I where I.code like '" + kpCode + "%'";
		String maxCode = (String)genService.findObjByHql(hsql);
		if (maxCode == null) maxCode = "0";
		else maxCode = maxCode.substring(7, 11);
		maxCode = maxCode.replaceFirst("^0*", "");
		if (maxCode.equals("")) maxCode = "0";
		return Integer.valueOf(maxCode) + 1;
	}
}
