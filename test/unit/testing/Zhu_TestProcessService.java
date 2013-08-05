package testing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.service.impl.ProcessService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.report.domain.Adage;

public class Zhu_TestProcessService  extends Zhu_BaseTest{
	long processInstanceId=2;
	/*public void testInsertProcessInstanceTest(){
		ProcessInstance pi = genService.get(ProcessInstance.class,1l);
		if(pi==null){
			pi= new ProcessInstance();
			pi.setActor("testor");
			pi.setProcessStatus(ProcessStatus.RUNNING);
			pi.setStartTime(new Date());
			pi.setEnd_Time(new Date());
			pi.setProcessDefinition(genService.get(ProcessDefinition.class, 1l));
			genService.save(pi);
			processInstanceId = pi.getId();
		}
	}*/
	
	public void test_runProcessInstance(){
		ExamItemService examItemService =(ExamItemService)getBean("examItemService");
		/*NodeStatus nodeStatus = NodeStatus.NOPASS;
		//NodeStatus nodeStatus = NodeStatus.PASSED;
		Map param = new HashMap();
		流程实例 id ,流程状态,其它参数
		processService.runProcessInstance(processInstanceId,nodeStatus,param);
		ProcessInstance p = genService.get(ProcessInstance.class, processInstanceId);
		Node tmp = p.getNode();
		if(tmp!=null){
			System.out.println(tmp.getId()+":"+tmp.getName());
		}else{
			System.out.println("没有 了");
		}
		System.out.println("================================end=============================");*/
		//examItemService.sendMail("SSSSSSSSSSSSSSSSSS");
		Adage adage=null;
		List<Adage> adageList = examItemService.getAll(Adage.class);		
		if(adageList.size() > 0 ){
			Random random = new Random();
			int k = Math.abs(random.nextInt()) % adageList.size();
			adage = adageList.get(k);
		}
		System.out.println(">>>"+adage.getEn());
		System.out.println(">>>"+adage.getCn());
	}
	
	@Override
	protected void setPath() {
		// TODO Auto-generated method stub
	}

}
