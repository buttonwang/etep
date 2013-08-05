package com.ambow.studyflow.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.xml.sax.InputSource;

import com.ambow.studyflow.common.ProcessReleaseStatus;
import com.ambow.studyflow.xml.AsfXmlReader;
import com.ambow.util.ClassLoaderUtil;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "asf_process_definition")
public class ProcessDefinition implements NodeCollection {
	private long id;
	private String name;
	private String description;
	/**
	 * 起始节点
	 */
	private Node startNode;
	/**
	 * 流程定义的版本号
	 */
	private int defVersion=0;
	/**
	 * 节点组
	 */
	private List<Node> nodes;
	
	/** 流程定义分类Id* */
	
	private Long categoryId;
	
	private String creator;
	private Date createTime;
	private String updator;
	private Date updateTime;
	private ProcessReleaseStatus releaseStatus;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	@Column(length=50)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public static ProcessDefinition createNewProcessDefinition() {
		return new ProcessDefinition();
	}

	/**
	 * parse a process definition from an xml string.
	 */
	public static ProcessDefinition parseXmlString(String xml) {
		StringReader stringReader = new StringReader(xml);
		AsfXmlReader asfReader = new AsfXmlReader(new InputSource(stringReader));
		return asfReader.readProcessDefinition();
	}

	/**
	 * parse a process definition from an xml resource file.
	 */
	public static ProcessDefinition parseXmlResource(String xmlResource) {
		InputStream resourceStream = ClassLoaderUtil.getStream(xmlResource);
		// this.getClass().getResourceAsStream
		try {
			return parseXmlInputStream(resourceStream);
		} finally {
			if (resourceStream != null) {
				try {
					resourceStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * parse a process definition from an xml input stream.
	 */
	public static ProcessDefinition parseXmlInputStream(InputStream inputStream) {
		AsfXmlReader asfReader = new AsfXmlReader(new InputSource(inputStream));
		return asfReader.readProcessDefinition();
	}

	/**
	 * parse a process definition from an xml reader.
	 */
	public static ProcessDefinition parseXmlReader(Reader reader) {
		AsfXmlReader asfReader = new AsfXmlReader(new InputSource(reader));
		return asfReader.readProcessDefinition();
	}

	public ProcessInstance createProcessInstance(Map variables) {
		return new ProcessInstance(variables, this);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public void setDefVersion(int version) {
		this.defVersion = version;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "start_node_id")
	public Node getStartNode() {
		return startNode;
	}

	@Column(name = "def_version")
	public int getDefVersion() {
		return defVersion;
	}

	@OneToMany(mappedBy = "processDefinition", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@Where(clause = "node_group_id is null")
	@OrderBy("orderNum")
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Node addNode(Node node) {

		if (node == null)
			throw new IllegalArgumentException(
					"can't add a null node to a process definition");
		if (nodes == null)
			nodes = new ArrayList();
		nodes.add(node);
		node.processDefinition = this;
		return node;
	}

	@Column(name = "category_id",nullable=false)
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name= "release_status", columnDefinition="integer")
	    @Type(
	        type = "com.ambow.studyflow.common.GenericEnumUserType",
	        parameters = {
	                @Parameter(
	                    name  = "enumClass",                      
	                    value = "com.ambow.studyflow.common.ProcessReleaseStatus"),
	                @Parameter(
	                    name  = "identifierMethod",
	                    value = "toInt"),
	                @Parameter(
	                    name  = "valueOfMethod",
	                    value = "fromInt")
	                }
	    )
	public ProcessReleaseStatus getReleaseStatus() {
		return releaseStatus;
	}
	 
	public void setReleaseStatus(ProcessReleaseStatus releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
}
