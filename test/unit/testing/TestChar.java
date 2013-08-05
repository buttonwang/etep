package testing;

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
}
