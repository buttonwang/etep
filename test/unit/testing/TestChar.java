package testing;

import java.util.Random;

import junit.framework.TestCase;

/*
 * TestChar.java
 * 
 * Created on 2009-3-16 上午11:24:28
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

public class TestChar extends TestCase {

	
	public void testChar() {
		String s = "&#8242;";
		System.out.println(s);
		String s1 = "" +  (char)(Integer.parseInt("8242"));	
		System.out.println("test:	" + s1);
		
		//String s2 = String.valueOf((int)( " ".charAt(0)) );
		String s2 = String.valueOf((int)( " ".charAt(0)) );
		System.out.println(s2);
	}
	
	public void testRandomAnswerOptionCode() {
		String output = "";
		Random r = new Random();
		
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<30; i++) {
			buffer.append(r.nextInt(4));
		}
		CharSequence charSeq = buffer.subSequence(3, 29);
		
		System.out.println(charSeq);
		
		for (int i=0; i<charSeq.length(); i++) {
			if (output.length()==4) break;
			if ((charSeq.charAt(i)==48)&&(!output.contains("A"))) output+="A";
			if ((charSeq.charAt(i)==49)&&(!output.contains("B"))) output+="B";
			if ((charSeq.charAt(i)==50)&&(!output.contains("C"))) output+="C";
			if ((charSeq.charAt(i)==51)&&(!output.contains("D"))) output+="D";
		}
		if (output.length()<4) output="DCBA";
		System.out.println(output);
	}
}
