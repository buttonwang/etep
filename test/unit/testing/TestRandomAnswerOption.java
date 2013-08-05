package testing;

import java.util.Random;

import junit.framework.TestCase;

/*
 * TestRandomAnswerOption.java
 * 
 * Created on 2009-3-23 下午03:41:08
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

public class TestRandomAnswerOption extends TestCase {

	public void A() {
		String input = "ABCD";
		String output = "";
		Random r = new Random();
		
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<30; i++) {
			buffer.append(r.nextInt(4));
		}
		CharSequence charSeq = buffer.subSequence(3, 29);
		int i = 0 ;
		for (i=0; i<charSeq.length(); i++) {
			if (output.length()==4) break;
			if ((charSeq.charAt(i)==48)&&(!output.contains("A"))) output+="A";
			if ((charSeq.charAt(i)==49)&&(!output.contains("B"))) output+="B";
			if ((charSeq.charAt(i)==50)&&(!output.contains("C"))) output+="C";
			if ((charSeq.charAt(i)==51)&&(!output.contains("D"))) output+="D";
		}
		System.out.println(i);
		if (output.length()<4) output="DCBA";
		System.out.println(output);
	}
	
	public void testCount() {
		for (int i=0; i<1000; i++) 
			A();
	}
}
