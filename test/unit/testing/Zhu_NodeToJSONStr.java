package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.trainingengine.studyflow.util.JSONLeanW;
import com.ambow.trainingengine.studyflow.web.data.MzTreeView;

public class Zhu_NodeToJSONStr extends Zhu_BaseTest{
	static JSONLeanW json = new JSONLeanW();
	static{
		Map classProps = new HashMap();
		classProps.put(Node.class.getName(), "id,name");
		classProps.put(PhaseTestNode.class.getName(), "id,name");
		classProps.put(PracticeNode.class.getName(), "id,name");
		classProps.put(UnitTestNode.class.getName(), "id,name");
		classProps.put(ExamNode.class.getName(), "id,name");
		classProps.put(EvaluateNode.class.getName(), "id,name");
		classProps.put(NodeGroup.class.getName(), "id,name");		
		json.setClassProps(classProps);
	}
	 public void testXXX(){
		 System.out.println("sdfsdfsd");
	 }
	 public static void main(String[] args) {
		 Zhu_NodeToJSONStr z = new Zhu_NodeToJSONStr();
		 z.setUp();
		 HibernateGenericDao dao =  z.getGenService();
		 List<Node> lst =dao.find("from Node where processDefinition.id = ?", 2L);
		 System.out.println(lst.size());
		 List<MzTreeView> lstMz= new ArrayList<MzTreeView>();
		 for (Node node : lst) {
			 MzTreeView mz = new MzTreeView();
			 mz.setId(String.valueOf(node.getId()));
			 if(node.getNodeGroup()!=null){
				 mz.setPid(String.valueOf(node.getNodeGroup().getId()));
			 }else{
				 mz.setPid("-1");
			 }
			 mz.setObj(node);
			 lstMz.add(mz);
		}
		System.out.println( json.write(lstMz));
	}
}
