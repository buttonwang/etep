package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.dto.NodeDTO;

public class AdminService  extends HibernateGenericDao {
	
		public static boolean isGroup(NodeDTO.NodeType n){
			try{
				return n==NodeDTO.NodeType.GROUP;
			}catch(Exception e){
				
			}
			return false;
		}
		
		public static boolean is(String type ,NodeDTO.NodeType n){
			try{
				return type.toLowerCase().equals(n.toString().toLowerCase());
			}catch(Exception e){				
			}
			return false;
		}
		
		public static boolean isNode(NodeDTO.NodeType n){
			try{
				return n==NodeDTO.NodeType.NODE;
			}catch(Exception e){
			}
			return false;
		}
		
		private static final long serialVersionUID = 2955776319264174735L;
		public List<String> addProcess(ProcessDefinition process ,String creator){
			List errorList = new ArrayList(0);
			process.setCreateTime(new Date());
			process.setCreator(creator);
			if(true){
				errorList.add("不能保存");
			}else{
				
			}
			save(process);
			return errorList;
		}
		
		
		public List<String> addObject(Object obj,String creator){
			List errorList = new ArrayList(0);
			//TODO 加上只针对 Node ProcessDefinition Policy的通过代理加上createTime creator
			/*id只能由hibernate，如果设定请调用edit方法。 设定，打印日志
			if(obj.getId()>0){
				errorList.add("非法增加对象，本操作报告失败");
				logger.debug("id只能由hibernate，如果设定请调用edit方法。")				
			}
			obj.setCreateTime(new Date());
			obj.setCreator(creator);
			*/
			save(obj);
			return errorList;
		}
		
		//取得具体类的对象
		public Object get(Object obj,long id){			

			
			
			/*
			 是否要加上只针对 Node ProcessDefinition Policy的判断
			 if(obj.getId()!=0&&(obj instanceof Node ProcessDefinition Policy)){
			 	
			 }else{
			 	logger.debug("对象的id 不存在,");
			 }
			 */
			Object item=null ; 
			if(obj!=null){
				Class clazz = obj.getClass();
				item = super.get(obj.getClass(),id);
				//流程，组装流程相关节点 
				if(clazz==ProcessDefinition.class){
					/*ProcessDefinition t =(ProcessDefinition)item;
					if(t!=null){
						String hqlNodes= "from Node n where n.processDefinition.id= "+t.getId();
						List nodes = super.find(hqlNodes , null); 
						t.setNodes(nodes);
						return t;
					}*/
				}
			}
			return item; 
		}
		

}
