package testing;

import java.io.IOException;
import java.io.Writer;

import com.generationjava.io.xml.SimpleXmlWriter;
import com.generationjava.io.xml.XmlWriter;

import junit.framework.TestCase;

/*
 * TestXMLWriter.java
 * 
 * Created on 2010-1-7 下午03:51:36
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

public class TestXMLWriter extends TestCase {

	public void testWriter() throws IOException {
        Writer writer = new java.io.StringWriter();
        XmlWriter xmlwriter = new SimpleXmlWriter(writer);
        //xmlwriter.writeXmlVersion();
        xmlwriter.writeXmlVersion("1.0", "utf-8");
        xmlwriter.writeComment("Example of XmlWriter running");
        xmlwriter.writeEntity("person");
        xmlwriter.writeAttribute("name", "fred");
        xmlwriter.writeAttribute("age", "12");
        xmlwriter.writeEntity("phone");
        xmlwriter.writeText("4254343");
        xmlwriter.endEntity();
        xmlwriter.writeComment("Examples of empty tags");
        xmlwriter.writeEntity("friends");
        xmlwriter.writeEmptyEntity("bob");
        xmlwriter.writeEmptyEntity("jim");
        xmlwriter.endEntity();
        xmlwriter.writeEntityWithText("foo","This is an example.");
        xmlwriter.endEntity();
        xmlwriter.close();
        System.out.println(writer.toString());
	}
	
	public void testWriterUserTest() throws IOException {
        Writer writer = new java.io.StringWriter();
        XmlWriter xmlwriter = new SimpleXmlWriter(writer);
        xmlwriter.writeXmlVersion("1.0", "utf-8");
        xmlwriter.writeEntity("Ambow");
        xmlwriter.writeEntity("UserTests");
        xmlwriter.writeEntity("UserTest");
        xmlwriter.writeEntityWithText("TestName","组测试");
        xmlwriter.writeEntityWithText("TestDate","2009-05-16");
        xmlwriter.writeEntityWithText("TestBeginTime","15:30");
        xmlwriter.writeEntityWithText("TestEndTime","17:00");
        xmlwriter.writeEntityWithText("FixTime","22分钟12秒");
        xmlwriter.writeEntityWithText("RealTime","20分钟55秒");
        xmlwriter.writeEntityWithText("RightRate","55%");
        xmlwriter.endEntity();
        xmlwriter.endEntity();
        xmlwriter.endEntity();
        xmlwriter.close();
        System.out.println(writer.toString());
	}
}
