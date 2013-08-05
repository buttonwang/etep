package testing;

import org.hibernate.validator.AssertTrue;

import junit.framework.TestCase;

/*
 * TestPickTypeCode.java
 * 
 * Created on 2008-12-31 下午07:50:25
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

public class TestPickTypeCode extends TestCase {
	
	private String pick(String typeCode) {
		return typeCode.replaceAll("([\\S]{3})([\\S]{2})", "MPC$2").replaceAll("(MPC[34])(.)", "$1X");
	}
	
	public void testMPC11() {
		String r = "J4M11";		
		assertEquals("MPC11", pick(r));
	};
	
	public void testMPC12() {
		String r = "S8P12";		
		assertEquals("MPC12", pick(r));
	};
	
	public void testMPC3X() {
		String r = "J4C38";		
		assertEquals("MPC3X", pick(r));		
	};
	
	public void testMPC4X() {
		String r = "S8C43";		
		assertEquals("MPC4X", pick(r));
	};

}
