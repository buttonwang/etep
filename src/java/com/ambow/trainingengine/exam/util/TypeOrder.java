package com.ambow.trainingengine.exam.util;

/* 
 * TypeOrder.java <br/>
 * 在此文件中决定顺序
 * Created on Jul 15, 2008,8:19:16 PM <br/>
 *
 * Copyright(C) 2008, by Ambow Research&Development Branch. <br/>
 *
 * Original Author: Li Xin <br/>
 * Contributor(s): 参与者的名称，参与者名称2， <br/>
 *
 * 数理化顺序 
 * 单选：11
 * 不定选：12
 * 填空（先一对一，后一对多）：33，43
 * 作图（先一对一，后一对多）：38，48
 * 实验（先一对一，后一对多）：34，44
 * 解答（先一对一，后一对多）：36，46
 * 计算（先一对一，后一对多）：35，45

 * Changes  <br/> 
 * -------
 * $log$ <br/>
 */
public enum TypeOrder {
	
	/*ky项目考研1-100*/
	val_Z1E12A(4),
	val_Z1E13A(5),
	val_Z1E25A(7),
	val_Z1E26A(6),
	val_Z1E34A(1),
	val_Z1E15A(2),
	val_Z1E34C(3),
	val_Z1E39A(13),
	val_Z1E39B(14),
	val_Z1E44A(12),
	val_Z1E47A(8),
	val_Z1E47B(9),
	val_Z1E47C(10),
	val_Z1E47D(11),
	
	/*mpc数学101-200*/
	 //中考
	 val_J4M11(1),
	 val_J4M12(2),	
	 
	 val_J4M33(3),
	 val_J4M43(4),
	 
	 val_J4M38(5),
	 val_J4M48(6),
	 
	 val_J4M34(7),
	 val_J4M44(8),
	 
	 val_J4M36(9),
	 val_J4M46(10),
	 
	 val_J4M35(11),
	 val_J4M45(12),
	 
	 val_J4M37(13),	 
	 val_J4M47(14),
	 
	//高考
	 val_S4M11(1),
	 val_S4M12(2),
	 
	 val_S4M33(3),
	 val_S4M43(4),

	 val_S4M38(5),
	 val_S4M48(6),
	 
	 val_S4M34(7),
	 val_S4M44(8),
	 		 
	 val_S4M36(9),
	 val_S4M46(10),
	 
	 val_S4M35(11),
	 val_S4M45(12),
	 
	 val_S4M37(13),	 
	 val_S4M47(14),
	 	
	/*mpc物理201-300*/
	 //中考
	 val_J4P11(1),
	 val_J4P12(2),	
	 
	 val_J4P33(3),
	 val_J4P43(4),
	 
	 val_J4P38(5),
	 val_J4P48(6),
	 
	 val_J4P34(7),
	 val_J4P44(8),
	 
	 val_J4P36(9),
	 val_J4P46(10),
	 
	 val_J4P35(11),
	 val_J4P45(12),
	 
	 val_J4P37(13),	 
	 val_J4P47(14),
	 
	//高考
	 val_S4P11(1),
	 val_S4P12(2),
	 
	 val_S4P33(3),
	 val_S4P43(4),

	 val_S4P38(5),
	 val_S4P48(6),
	 
	 val_S4P34(7),
	 val_S4P44(8),
	 		 
	 val_S4P36(9),
	 val_S4P46(10),
	 
	 val_S4P35(11),
	 val_S4P45(12),
	 
	 val_S4P37(13),	 
	 val_S4P47(14),
	
	/*mpc化学301-400*/
	 
	 //中考
	 val_J4C11(1),
	 val_J4C12(2),	
	 
	 val_J4C33(3),
	 val_J4C43(4),
	 
	 val_J4C38(5),
	 val_J4C48(6),
	 
	 val_J4C34(7),
	 val_J4C44(8),
	 
	 val_J4C36(9),
	 val_J4C46(10),
	 
	 val_J4C35(11),
	 val_J4C45(12),
	 
	 val_J4C37(13),	 
	 val_J4C47(14),
	 
	//高考
	 val_S4C11(1),
	 val_S4C12(2),
	 
	 val_S4C33(3),
	 val_S4C43(4),

	 val_S4C38(5),
	 val_S4C48(6),
	 
	 val_S4C34(7),
	 val_S4C44(8),
	 		 
	 val_S4C36(9),
	 val_S4C46(10),
	 
	 val_S4C35(11),
	 val_S4C45(12),
	 
	 val_S4C37(13),	 
	 val_S4C47(14);
	
	 private int orderValue;
	 
	 private TypeOrder(int value){
		 this.orderValue=value;
	 }
	 
	 public static int getOrderValue(String typeCode){
		 String name="val_"+typeCode.toUpperCase();
			return TypeOrder.valueOf(name).orderValue;
	 }

}
