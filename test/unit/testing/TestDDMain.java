/**
 * 
 */
package testing;

import junit.framework.TestCase;

import com.ambow.trainingengine.exam.util.ExamUtil;

/* 
 * TestDDMain.java <br/>
 * 
 * Created on Jul 16, 2008,4:57:15 PM <br/>
 *
 * Copyright(C) 2008, by Ambow Research&Development Branch. <br/>
 *
 * Original Author: Li Xin <br/>
 * Contributor(s): 参与者的名称，参与者名称2， <br/>
 *
 * Changes  <br/> 
 * -------
 * $log$ <br/>
 */
public class TestDDMain extends TestCase {
	
	public void testExamFloat(){
		Float floatValue=new Float("1.0");
		ExamUtil.getStarArray(floatValue);
	}

}
