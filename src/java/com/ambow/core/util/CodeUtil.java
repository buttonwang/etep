package com.ambow.core.util;

public class CodeUtil {
	/**
	 * 转换为html实体编码
	 * @param input
	 * @return
	 */	
	public static String htmlEntitiesEncrypting(String input){
		String output = "";
		for (int i=0;i<input.length();i++) {
			output= output + "&#"+String.valueOf((int) (input.charAt(i)))+";";
		}
		return output;
	}
	/**
	 * 由html实体编码转换回来
	 * @param input
	 * @return
	 */
	public static String htmlEntitiesDecrypting(String input){
		int index=0;
		int index2=0;
		String outputString2="";
		while (index2<input.length()-1) {
			index=input.indexOf("&#",index)+2;
			index2=input.indexOf(";",index);
			
			String subString = input.substring(index,index2);
			outputString2=outputString2+(char)(Integer.parseInt(subString));
		}
		return outputString2;
	}
	
	public static void main(String[] a){
		System.out.println(htmlEntitiesEncrypting("啊"));		
		System.out.println(htmlEntitiesDecrypting("&#21834;"));	
	}
	
}
