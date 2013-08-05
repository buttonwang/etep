package com.ambow.studyflow.xml;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * Validate an XML document using JAXP techniques and an XML Schema. This helper
 * class wraps the processing of a schema to aid in schema validation throughout
 * the product.
 * 
 */
public class AsfParser implements Serializable {

	private static final long serialVersionUID = 1L;
	static final EntityResolver ASF_ENTITY_RESOLVER = new AsfEntityResolver();
	static SAXParserFactory saxParserFactory = createSaxParserFactory();

	public static Document parse(InputSource inputSource,
			ProblemListener problemListener) throws Exception {
		Document document = null;
		SAXReader saxReader = createSaxReader(problemListener);
		document = saxReader.read(inputSource);
		return document;
	}

	public static SAXReader createSaxReader(ProblemListener problemListener)
			throws Exception {
		XMLReader xmlReader = createXmlReader();
		SAXReader saxReader = new SAXReader(xmlReader);
		saxReader.setErrorHandler(new AsfErrorHandler(problemListener));
		saxReader.setEntityResolver(ASF_ENTITY_RESOLVER);
		return saxReader;
	}

	public static XMLReader createXmlReader() throws Exception {
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();

		try {
			saxParser.setProperty(
					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
		} catch (SAXException e) {
			log
					.warn(
							"couldn't set xml parser property "
									+ "'http://java.sun.com/xml/jaxp/properties/schemaLanguage' "
									+ "to 'http://www.w3.org/2001/XMLSchema'",
							e);
		}

		try {
			saxParser
					.setProperty(
							"http://apache.org/xml/properties/schema/external-schemaLocation",
							"urn:ambow.com:asf-1.0 http://ambow.com/asf-1.0.xsd");
		} catch (SAXException e) {
			log
					.warn(
							"couldn't set xml parser property 'http://apache.org/xml/properties/schema/external-schemaLocation'",
							e);
		}

		try {
			xmlReader.setFeature(
					"http://apache.org/xml/features/validation/dynamic", true);
		} catch (SAXException e) {
			log
					.warn(
							"couldn't set xml parser feature 'http://apache.org/xml/features/validation/dynamic'",
							e);
		}
		return xmlReader;
	}

	static class AsfErrorHandler implements ErrorHandler, Serializable {
		private static final long serialVersionUID = 1L;
		ProblemListener problemListener = null;

		AsfErrorHandler(ProblemListener problemListener) {
			this.problemListener = problemListener;
		}

		public void warning(SAXParseException pe) {
			addProblem(Problem.LEVEL_WARNING, "line " + pe.getLineNumber()
					+ ": " + pe.getMessage(), pe);
		}

		public void error(SAXParseException pe) {
			addProblem(Problem.LEVEL_ERROR, "line " + pe.getLineNumber() + ": "
					+ pe.getMessage(), pe);
		}

		public void fatalError(SAXParseException pe) {
			addProblem(Problem.LEVEL_FATAL, "line " + pe.getLineNumber() + ": "
					+ pe.getMessage(), pe);
		}

		void addProblem(int level, String description, Throwable exception) {
			problemListener.addProblem(new Problem(level, description,
					exception));
		}
	}

	static class AsfEntityResolver implements EntityResolver, Serializable {
		private static final long serialVersionUID = 1L;

		public InputSource resolveEntity(String publicId, String systemId)
				throws SAXException, IOException {
			InputSource inputSource = null;
			log.debug("resolving schema reference publicId(" + publicId
					+ ") systemId(" + systemId + ")");

			if ("http://ambow.com/asf-1.0.xsd".equals(systemId)) {
				log
						.debug("providing input source to local 'jpdl-3.2.xsd' resource");
				inputSource = new InputSource(this.getClass()
						.getResourceAsStream("jpdl-3.2.xsd"));

			} else {
				log.debug("original systemId as input source");
				inputSource = new InputSource(systemId);
			}
			return inputSource;
		}
	}

	private static SAXParserFactory createSaxParserFactory() {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setValidating(true);
		saxParserFactory.setNamespaceAware(true);
		return saxParserFactory;
	}

	private static final Log log = LogFactory.getLog(AsfParser.class);
}
