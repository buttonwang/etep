package testing;

import java.util.List;

import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
/**
 * 更新 知识点的父亲  
 * @author zhujianmin
 *
 */
public class UpdateKnowledgePointParentTest  extends Zhu_BaseTest{ 
	/**
	 * 更新 知识点的父亲   只针对 subject.code 分别为 C(化学) M(数学) P(物理) 的知识点
	 * 例如
	 * J4P 08 01 的父亲为 J4P 08 00<br>
	 * J4P 08 01 的父亲为 J4P 08 00<br>
	 * J4P 17 04 的父亲为 J4P 17 00<br>
	 */
	public void test_setKnowledgePointParent(){
		
		List<KnowledgePoint> kpLst = genService.find("from KnowledgePoint where code like 'S4MF%'");
		int i=1,j=1;
		int len = 20;
		System.out.println("开始更新parent_node parentLevel"+kpLst.size());
		for (KnowledgePoint knowledgePoint : kpLst) {
			String code  = knowledgePoint.getCode() ;
			//System.out.println(code+":"+code.substring(code.length()-2, code.length()));
			String parentCode = code.replaceAll("\\d{2,2}$", "00");
			String sc = knowledgePoint.getSubject().getCode().toUpperCase();
			if(sc.equals("C")||sc.equals("M")||sc.equals("P")){
				if(!code.equals(parentCode)){
					j++;
					knowledgePoint.setParentKnowledgePoint((KnowledgePoint)genService.get(KnowledgePoint.class,parentCode));
				}
			}
			knowledgePoint.setParentLevel(getParentLevel(knowledgePoint,"","_"));
			System.out.print("...|"+((i++%len==0)?"\n已更新"+(j-1)+"条 父亲,"+(i-1)+"条 层次 ":""));
			genService.save(knowledgePoint);
		}
		System.out.println("\n共更新 "+(j-1)+"条 parent_node , "+(i-1)+"条 parentLevel已经全部更新成功！");
	}
	public String getParentLevel(KnowledgePoint knowledgePoint,String parentLevel,String split){
		if(knowledgePoint!=null){
			parentLevel  =  knowledgePoint.getCode()+(parentLevel.length()>0?"_":"")+parentLevel;
			KnowledgePoint parentKnowledgePoint= knowledgePoint.getParentKnowledgePoint();
			if(parentKnowledgePoint!=null){
				parentLevel  = getParentLevel(knowledgePoint.getParentKnowledgePoint(),parentLevel,split);
			} 
		}
		return parentLevel;
	}
	@Override
	protected void setPath() {
		// TODO Auto-generated method stub
		
	}
}