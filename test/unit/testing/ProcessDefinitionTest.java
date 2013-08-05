package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.trainingengine.studyflow.util.JSONLeanW;
import com.ambow.trainingengine.studyflow.util.ProcessNodeJson;
import com.ambow.trainingengine.studyflow.web.data.MzTreeView;
import com.ambow.trainingengine.studyflow.web.data.NodeForMzTreeView;

public class ProcessDefinitionTest extends Zhu_BaseTest{
	public void xxx(){
		ProcessDefinitionTest processDefinitionTest = new ProcessDefinitionTest();
		processDefinitionTest.setUp();
		ProcessDefinition pd = processDefinitionTest.getGenService().get( ProcessDefinition.class,2L);
		ProcessNodeJson json = new ProcessNodeJson();
		System.out.println(json.getStr(pd));
	}
	public void test(){
		 
		Long processId = 3L;
		List<Node> nodes = genService.find("from Node where processDefinition.id=?",processId);
		List nodesMzTreeViewLst = new ArrayList();
		
		Map map = new HashMap();
		
		//创建根节点
		Long rootId = -1L;
		Node _node = new Node();
			_node.setId(rootId);
			_node.setName("流程所有节点");
		NodeGroup _ng=new NodeGroup();
			_ng.setId(0);
		_node.setNodeGroup(_ng);
		NodeForMzTreeView nodeForMzTreeViewRoot = new NodeForMzTreeView(_node); 
		
		
		Map classProps = new HashMap();
		String outPrototype = "id,name,orderNum,updator,creator";
		classProps.put(Node.class.getName(), outPrototype);
		classProps.put(MzTreeView.class.getName(), "pid_id,obj");
		
		classProps.put(PhaseTestNode.class.getName(), outPrototype);
		classProps.put(PracticeNode.class.getName(), outPrototype);
		classProps.put(UnitTestNode.class.getName(), outPrototype);
		classProps.put(ExamNode.class.getName(), outPrototype);
		classProps.put(EvaluateNode.class.getName(), outPrototype);
		classProps.put(NodeGroup.class.getName(), outPrototype);
		classProps.put(ProcessDefinition.class.getName(), "id");
		JSONLeanW json = new JSONLeanW();
		json.setClassProps(classProps);
		
		
		nodesMzTreeViewLst.add(nodeForMzTreeViewRoot);
		for (Node node : nodes) {
			NodeForMzTreeView nodeForMzTreeView = new NodeForMzTreeView(node); 
			nodesMzTreeViewLst.add(nodeForMzTreeView);
		}
		System.out.println(json.write(nodesMzTreeViewLst)); 
		

		
		/*
		map.put(nodeRoot.getPid_id(), nodeRoot); 
		for (Node node : nodes) {
			NodeForMzTreeView _nf = new NodeForMzTreeView(node); 
			map.put(_nf.getPid_id(), _nf);
		}
		System.out.println(json.write(map)); 
		*/
		/*for (Node node : nodes) {
			NodeForMzTreeView mz = new NodeForMzTreeView(node);
			nodesMzTreeViewLst.add(mz);
			System.out.println(mz.getTreeN("tree"));
		}*/
		
		
	}
	public static void main(String[] args) {
	  String s1 = String.format("%1$s, ","test");
	  System.out.println(s1 );
	}
}
