package com.ambow.studyflow.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ProcessDefinitionDTO.java:本类功能简介
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
public class ProcessDTO implements NodeDTOCollection {
	private long processDefinitionId;
	private long processInstanceId;
	private String name;
	private List<NodeDTO> nodes;

	public NodeDTO addNode(NodeDTO node) {
		if(nodes==null)
			nodes=new ArrayList<NodeDTO>();
		nodes.add(node);
		return node;
	}

	public long getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NodeDTO> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeDTO> nodes) {
		this.nodes = nodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (processInstanceId ^ (processInstanceId >>> 32));
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
		final ProcessDTO other = (ProcessDTO) obj;
		if (processInstanceId != other.processInstanceId)
			return false;
		return true;
	}


	
	
}
