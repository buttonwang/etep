package com.ambow.studyflow.xml;

import org.dom4j.Element;

/**
 * 
 * Parsable.java:可自解析接口
 * 
 * 实现此接口的方法可以解析
 * 
 * Created on 2008-5-19
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */
public interface Parsable {
  void read(Element element, AsfXmlReader asfReader);
  void write(Element element);
}
