package com.ambow.studyflow.domain;

import org.dom4j.Element;

import com.ambow.studyflow.xml.AsfXmlReader;

public interface Parsable {

	void read(Element element, AsfXmlReader reader);

	void write(Element element);
}
