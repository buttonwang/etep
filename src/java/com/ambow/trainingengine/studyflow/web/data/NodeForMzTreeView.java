package com.ambow.trainingengine.studyflow.web.data;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.ambow.studyflow.domain.Node;

public class NodeForMzTreeView extends MzTreeView {
private String type;
	private Map map=new HashMap();
	
	public NodeForMzTreeView(Node node){
		super();
		if(node!=null){
			long pid=-1;
			if(node.getNodeGroup()!=null){
				pid=node.getNodeGroup().getId();
			}
			this.setPid_id(pid+"_"+node.getId());
			this.setShow(1);
			this.setChecked(1);
			
			
			String prid = node.getProcessDefinition()!=null?node.getProcessDefinition().getId()+"":"";
			map.put("prid", prid);
			//map.put("createTime", String.format("%1$tF",node.getCreateTime()));
			//map.put("updateTime", String.format("%1$tF",node.getUpdateTime()));
			map.put("type",node.getNodeType().toString().toLowerCase());
			map.put("n", node);
			this.setObj(map);
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
