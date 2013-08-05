/**
 * 
 */
package com.ambow.studyflow.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.ambow.studyflow.dto.NodeDTO.NodeType;

/**
 * 节点组 在流程定义中加入节点组的概念，便于节点的组织和管理，相当于评测中的单元
 * 
 * 例如我们可以加入这样的规则： 节点组中所有练习节点通过，才能进入测试节点 或者 节点组中测试节点未通过，则节点组中所有练习节点置为空
 * 
 * 节点组自关联，可无限嵌套
 * 
 * @author suxiaoyong
 * 
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@DiscriminatorValue("GROUP")
public class NodeGroup extends Node implements NodeCollection{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Node> nodes;
	@OneToMany(mappedBy="nodeGroup",
            cascade=CascadeType.PERSIST, 
            fetch=FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
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
					"can't add a null node to a node group");
		if (nodes == null)
			nodes = new ArrayList();
		nodes.add(node);
		node.processDefinition = this.processDefinition;
		return node;
	}

	@Override
	@Transient
	public String getDecisionName() {
		if(this.decisonRecords.isEmpty())
			return "groupDecision";
		else
			return this.decisonRecords.get(0).getDecisionName();
	}
	
	@Override
	@Transient
	public NodeType getNodeType() {
		return NodeType.GROUP;
	}
}
