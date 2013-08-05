package com.ambow.studyflow.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.xml.sax.InputSource;

import com.ambow.studyflow.common.NodeTypes;
import com.ambow.studyflow.domain.DecisionRecord;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeCollection;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.exception.AsfException;

/**
 * 
 * AsfXmlReader.java:解析流程定义文件
 * 
 * Created on 2008-5-13
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
public class AsfXmlReader implements ProblemListener {

	private static final long serialVersionUID = 1L;

	protected InputSource inputSource = null;
	protected List<Problem> problems = new ArrayList<Problem>();
	protected ProblemListener problemListener = null;
	protected ProcessDefinition processDefinition = null;
	protected String initialNodeName = null;

	public AsfXmlReader(InputSource inputSource) {
		this.inputSource = inputSource;
	}

	public AsfXmlReader(InputSource inputSource, ProblemListener problemListener) {
		this.inputSource = inputSource;
		this.problemListener = problemListener;
	}

	public AsfXmlReader(Reader reader) {
		this(new InputSource(reader));
	}

	public void close() throws IOException {
		InputStream byteStream = inputSource.getByteStream();
		if (byteStream != null)
			byteStream.close();
		else {
			Reader charStream = inputSource.getCharacterStream();
			if (charStream != null)
				charStream.close();
		}
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void addProblem(Problem problem) {
		problems.add(problem);
		if (problemListener != null)
			problemListener.addProblem(problem);
	}

	public void addError(String description) {
		log.error("invalid process xml: " + description);
		addProblem(new Problem(Problem.LEVEL_ERROR, description));
	}

	public void addError(String description, Throwable exception) {
		log.error("invalid process xml: " + description, exception);
		addProblem(new Problem(Problem.LEVEL_ERROR, description, exception));
	}

	public void addWarning(String description) {
		log.warn("process xml warning: " + description);
		addProblem(new Problem(Problem.LEVEL_WARNING, description));
	}

	public ProcessDefinition readProcessDefinition() {
		// create a new definition
		processDefinition = ProcessDefinition.createNewProcessDefinition();

		// initialize lists
		problems = new ArrayList<Problem>();
		try {
			// parse the document into a dom tree
			Document document = AsfParser.parse(inputSource, this);
			Element root = document.getRootElement();

			// read the process name
			parseProcessDefinitionAttributes(root);

			// get the process description
			String description = root.elementTextTrim("description");
			if (description != null) {
				processDefinition.setDescription(description);
			}
			// 读取节点
			readNodes(root);
		} catch (Exception e) {
			log.error("couldn't parse process definition", e);
			addProblem(new Problem(Problem.LEVEL_ERROR,
					"couldn't parse process definition", e));
		}

		if (Problem.containsProblemsOfLevel(problems, Problem.LEVEL_ERROR)) {
			throw new AsfException(problems);
		}

		if (problems != null) {
			Iterator<Problem> iter = problems.iterator();
			while (iter.hasNext()) {
				Problem problem = iter.next();
				log.warn("process parse warning: " + problem.getDescription());
			}
		}

		return processDefinition;
	}

	protected void parseProcessDefinitionAttributes(Element root) {
		processDefinition.setName(root.attributeValue("name"));
		initialNodeName = root.attributeValue("initial");
	}

	public void readNodes(Element element) {
		readGroup(element, processDefinition);
	}

	private void readGroup(Element groupElement, NodeCollection nodeGroup) {
		Iterator<Element> groupElementIter = groupElement.elementIterator();
		while (groupElementIter.hasNext()) {
			Element subElement = groupElementIter.next();
			String tagName = subElement.getName();
			if (tagName.equals("node-group")) {
				readSingleNodeGroup(nodeGroup, subElement);
			}
			if (NodeTypes.nodeTypeMap.containsKey(tagName)) {
				readSingleNodeElement(nodeGroup, subElement, tagName);
			}
		}
	}

	/**
	 * @param nodeGroup
	 * @param subElement
	 */
	private void readSingleNodeGroup(NodeCollection nodeGroup, Element subElement) {
		NodeGroup group = new NodeGroup();
		group.setProcessDefinition(processDefinition);
		group.setName(subElement.attributeValue("name"));
		if(nodeGroup instanceof NodeGroup)
		{	
		group.setNodeGroup((NodeGroup)nodeGroup);
		}
		nodeGroup.addNode(group);
		//解析decision
		parseDecision(subElement, group);
		setGroupOrder(subElement, group);
		readGroup(subElement, group);
	}

	/**
	 * @param nodeGroup
	 * @param subElement
	 * @param tagName
	 */
	private void readSingleNodeElement(NodeCollection nodeGroup, Element subElement,
			String tagName) {
		// 根据节点名得到节点类
		Class nodeType = NodeTypes.getNodeType(tagName);
		if (nodeType != null) {

			Node node = null;
			try {
				node = (Node) nodeType.newInstance();
			} catch (Exception e) {
				log.error("couldn't instantiate node '" + tagName
						+ "', of type '" + nodeType.getName() + "'", e);
			}

			node.setProcessDefinition(processDefinition);
			if(nodeGroup instanceof NodeGroup)
			{
				node.setNodeGroup((NodeGroup)nodeGroup);
			}
			readNode(subElement, node, nodeGroup);
			node.read(subElement, this);
		} else {
		}
	}
	
	/**
	 * @param subGroup
	 * @param group
	 */
	private void setGroupOrder(Element subGroup, NodeGroup group) {
		String orderNum=subGroup.attributeValue("order");
		if(orderNum!=null)
			group.setOrderNum(orderNum);
	}

	public void readNode(Element nodeElement, Node node,
			NodeCollection nodeCollection) {
		nodeCollection.addNode(node);

		// get the node name
		String name = nodeElement.attributeValue("name");
		String uriKey = nodeElement.attributeValue("uriKey");
		String keyId = nodeElement.attributeValue("keyId");
		String orderNum=nodeElement.attributeValue("order");
		String isStartNode = nodeElement.attributeValue("isStartNode");
		if (name != null) {
			node.setName(name);
			node.setKeyId(keyId);
			node.setUriKey(uriKey);
			String to = nodeElement.attributeValue("to");
			if (to != null)
				node.setDest(to);
			if (isStartNode != null) {
				char startNode = isStartNode.charAt(0);
				// 默认为'N',这里只判断为'Y'的情况
				if (startNode == 'Y') {
					node.setIsStartNode(startNode);
					processDefinition.setStartNode(node);
				}
			}
			// get the node description
			String description = nodeElement.elementTextTrim("description");
			if (description != null) {
				node.setDescription(description);
			}
			if(orderNum!=null)
			{
				node.setOrderNum(orderNum);
			}
		}
		
		//解析decision
		parseDecision(nodeElement, node);
	}

	/**
	 * 解析decision,并将decision加入节点
	 * @param nodeElement
	 * @param node
	 */
	private void parseDecision(Element nodeElement, Node node) {
//		Iterator<Element> nodeElementIter = nodeElement.elementIterator();
//		while(nodeElementIter.hasNext())
//		{
//			Element ele=nodeElementIter.next();
//			String decisionName=ele.attributeValue("name").trim();
//			int priority=Integer.parseInt(ele.attributeValue("priority"));
//			DecisionRecord dr=new DecisionRecord();
//			dr.setPriority(priority);
//			dr.setDecisionName(decisionName);
//			dr.setNode(node);
//			node.getDecisonRecords().add(dr);
//		}
		List<Element> decisionList=nodeElement.elements("decision");
		if(decisionList.size()>0)
		{
			for(Element ele:decisionList)
			{
			String decisionName=ele.attributeValue("name").trim();
			int priority=Integer.parseInt(ele.attributeValue("priority"));
			DecisionRecord dr=new DecisionRecord();
			dr.setPriority(priority);
			dr.setDecisionName(decisionName);
			dr.setNode(node);
			node.getDecisonRecords().add(dr);
			}
		}
		
	}

	public String getProperty(String property, Element element) {
		String value = element.attributeValue(property);
		if (value == null) {
			Element propertyElement = element.element(property);
			if (propertyElement != null) {
				value = propertyElement.getText();
			}
		}
		return value;
	}

	private static final Log log = LogFactory.getLog(AsfXmlReader.class);
}
