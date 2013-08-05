/**
 * 
 */
package com.ambow.studyflow.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.dom4j.Element;
import org.hibernate.annotations.Cascade;

import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.studyflow.xml.AsfXmlReader;

/**
 * @author suxiaoyong
 * 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "asf_node")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "node_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("NODE")
public class Node implements Parsable {

	protected long id;
	protected String name;
	/**
	 * 默认下一个节点
	 */
	protected String dest;
	protected String uriKey;
	protected String keyId;
	protected String description;
	protected ProcessDefinition processDefinition;
	/**
	 * 'Y' or 'N'
	 */
	protected char isStartNode = 'N';
	protected NodeGroup nodeGroup;
	protected List<DecisionRecord> decisonRecords = new ArrayList<DecisionRecord>();
	protected String orderNum ="";
	protected Node nextNode;
	
	private String creator;
	private Date createTime;
	private String updator;
	private Date updateTime;

	@Transient
	public String getUrl() {
		return uriKey;
	}

	/**
	 * @return the nodeType
	 */
	@Transient
	public NodeType getNodeType() {
		return NodeType.NODE;
	}

	public void write(Element element) {

	}

	public void read(Element element, AsfXmlReader reader) {
	}
	@Column(length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="uri_key_name",length=50)
	public String getUriKey() {
		return uriKey;
	}

	public void setUriKey(String uriKey) {
		this.uriKey = uriKey;
	}
	@Column(name="uri_key_id",length=20)
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	@Column(length=200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "process_definition_id")
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	@Column(length=50)
	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	@Transient
	public char getIsStartNode() {
		return isStartNode;
	}

	public void setIsStartNode(char isStartNode) {
		this.isStartNode = isStartNode;
	}

	@ManyToOne
	@JoinColumn(name = "node_group_id")
	public NodeGroup getNodeGroup() {
		return nodeGroup;
	}

	public void setNodeGroup(NodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "node", fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@OrderBy("priority desc")
	public List<DecisionRecord> getDecisonRecords() {
		return decisonRecords;
	}

	public void setDecisonRecords(List<DecisionRecord> decisonRecords) {
		this.decisonRecords = decisonRecords;
	}

	@Transient
	public String getDecisionName() {
		if (this.decisonRecords.isEmpty())
			return null;
		else
			return this.decisonRecords.get(0).getDecisionName();
	}

	@Column(name = "order_num")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * @return the nextNode
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "next_node_id")
	public Node getNextNode() {
		return nextNode;
	}

	/**
	 * @param nextNode
	 *            the nextNode to set
	 */
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	@Column(length=50)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(length=50)
	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
