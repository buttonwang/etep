package com.ambow.studyflow.dto;

import java.util.ArrayList;
import java.util.List;

import com.ambow.studyflow.common.NodeStatus;
/**
 * 
 * NodeDTO.java:本类功能简介
 * 
 * Created on 2008-5-21
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
public class NodeDTO implements NodeDTOCollection {

	public enum NodeType
	{
		NODE,
		PRACTICE,
		EXAM,
		GROUP,
		
		EVALUATE,
		PHASETEST,
		UNITTEST,
		
		EXTPRACTICE
	}
	private long id;
	private String name;
	private NodeStatus nodeStatus;
	private NodeType nodeType;
	private List<NodeDTO> nodes;
	private boolean isCurrent=false;

	public NodeDTO addNode(NodeDTO node) {
		if(nodes==null)
			nodes=new ArrayList<NodeDTO>();
		nodes.add(node);
		return node;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NodeStatus getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(NodeStatus nodeStauts) {
		this.nodeStatus = nodeStauts;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public List<NodeDTO> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeDTO> nodes) {
		this.nodes = nodes;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final NodeDTO other = (NodeDTO) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String toString() {
		return "id:" + this.id + ",name:" + this.name + ",node status:"
				+ this.nodeStatus + ",nodeType:" + this.nodeType;
	}


}
