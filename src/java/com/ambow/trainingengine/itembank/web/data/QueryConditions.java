package com.ambow.trainingengine.itembank.web.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ambow.trainingengine.util.DateUtil;

public class QueryConditions {	
	private String code="";//试题编码
	private String content="";// 题干
	private String source="";//试题来源	
	private String year="";//试题年份
	private String difficultyValue="";//试题难度	
	private String validityValue="";//试题效度
	private String yearUp="";//试题年份上限
	private String yearDown="";//试题年份下限
	private String difficultyValueUp="";//试题难度上限
	private String difficultyValueDown="";//试题难度下限
	private String validityValueUp="";//试题效度上限
	private String validityValueDown="";//试题效度下限
	private String applicableObject="";//适用对象
	private String[] applicableObjects=new String[]{};//适用对象们
	private String originalPaperCode=""; //原始套卷编码
	private String status="9";//状态
	private String[] courseVersions=new String[]{};
	private String opinion="";//直观评价
	private String opinionUp="";//直观评价上线
	private String opinionDown="";//直观评价下线
 
	private Integer answeringTimeByMinDown;
	private Integer answeringTimeByMinUp;
	private Float scoreDown;
	private Float scoreUp;
	
	private String region="";//适用地区
	private String subject="";//所属学科
	private String subject_code ="";
	private String grade="";//适用学级
	private String itemType="";//题型
	private String knowledgePoint="";//知识点
	private String itemTheme="";//题材
	private String sourceFile="";//来源书目
	private String importFile="";//导入文件
	private String typicalExample=""; //典型例题
	private String abilityValue=""; //能力要求

	private Integer amount; //试题数量
	private String reviewRound="";//轮次复习
	
	private String createdTime=""; //创建时间
	private String correctAnswer=""; //正确答案

	private String field;
	private String value;
	private String hint;
	private String analysisAtLarge1;
	public String hql;
	public List<Object> queryConditionList=new ArrayList<Object>();
	
	public void genHQLandValues(String queryType) {
		hql = "select a from Item a";
		
		if(queryType.equals("0")){
			if(getValue()!=null&&!"".equals(getValue())){
				if(getField().equals("code")
				 ||getField().equals("year")){
					hql+=" where a."+getField() + "=?";
					queryConditionList.add(getValue());
				}
				else if(getField().equals("difficultyValue")
				  	  ||getField().equals("validityValue")){
					hql+=" where a."+getField()+"=?";
					queryConditionList.add(getFValue(this.value));
				}
				else if(getField().equals("content")){
					hql+=" where a.content like ?";
					queryConditionList.add("%"+getValue()+"%");
				}
				else if(getField().equals("source")){
					hql+=" where a.source=?";
					if(getValue().equals("真题"))
						queryConditionList.add("1");
					else if(getValue().equals("模拟"))
						queryConditionList.add("2");
					else if(getValue().equals("自编"))
						queryConditionList.add("3");
					else if(getValue().equals("专项"))
						queryConditionList.add("4");
					else queryConditionList.add("-100");
				}
				else if(getField().equals("applicableObject")){
					hql+=" where a.applicableObject=?";
					if(getValue().equals("文科"))
						queryConditionList.add(1);
					else if(getValue().equals("理科"))
						queryConditionList.add(2);
					else queryConditionList.add(-100);
				}
				else if(getField().equals("status")){
					hql+=" where a.status=?";
					//0-未审核;1-已审核;2-已组卷;3-已使用;-1-已废弃
					if(getValue().equals("未审核"))
						queryConditionList.add(0);
					else if(getValue().equals("已审核"))
						queryConditionList.add(1);
					else if(getValue().equals("已组卷"))
						queryConditionList.add(2);
					else if(getValue().equals("已使用"))
						queryConditionList.add(3);
					else if(getValue().equals("已废弃"))
						queryConditionList.add(-1);
					else queryConditionList.add(-100);
				}
				else if(getField().equals("regionName")
						||getField().equals("subjectName")
						||getField().equals("itemTypeName")){
					hql+=" where a."+getField().replace("Name", "")+".name like ?";
					queryConditionList.add("%"+getValue()+"%");
				}
				else if(getField().equals("gradeName")
						||getField().equals("knowledgePointName")
						||getField().equals("itemThemeName")){
					hql+=" left join a."+getField().replace("Name", "")+"s b where b.name like ? ";
					queryConditionList.add("%"+getValue()+"%");
				}
			}	
		}else if(queryType.indexOf("1")>-1){
			if(!getGrade().equals(""))
				hql+=" left join a.grades b";
			if(!getItemTheme().equals(""))
				hql+=" left join a.itemThemes c";
			if(!getTypicalExample().equals(""))
				hql+=" join a.typicalExample t";
			//if(!getKnowledgePoint().equals(""))
			//	hql+=" left join a.knowledgePoints d";
			
			hql+=" where 1=1 ";

			if(!getCode().equals("")){
				hql+=" and a.code=?";
				queryConditionList.add(getCode());
			}
			if(!getContent().equals("")){
				hql+=" and a.content like ?";
				queryConditionList.add("%"+getContent()+"%");
			}
			if(!getSource().equals("")){
				hql+=" and a.source in (" + getSource() + ")";
				//queryConditionList.add(getSource());
			}
			if(!getYear().equals("")){
				hql+=" and a.year = ?";
				queryConditionList.add(getYear());
			}
			if(!getYearDown().equals("")){
				hql+=" and a.year >= ?";
				queryConditionList.add(getYearDown());
			}
			if(!getYearUp().equals("")){
				hql+=" and a.year <= ?";
				queryConditionList.add(getYearUp());
			}
			if(!getDifficultyValue().equals("")){
				hql+=" and a.difficultyValue = ?";
				queryConditionList.add(getFValue(getDifficultyValue()));
			}
			if(!getDifficultyValueDown().equals("")){
				hql+=" and a.difficultyValue >= ?";
				queryConditionList.add(getFValue(getDifficultyValueDown()));
			}
			if(!getDifficultyValueUp().equals("")){
				hql+=" and a.difficultyValue <= ?";
				queryConditionList.add(getFValue(getDifficultyValueUp()));
			}
			if(!getValidityValue().equals("")){
				hql+=" and a.validityValue = ?";
				queryConditionList.add(getFValue(getValidityValue()));
			}
			if(!getValidityValueDown().equals("")){
				hql+=" and a.validityValue >= ?";
				queryConditionList.add(getFValue(getValidityValueDown()));
			}
			if(!getValidityValueUp().equals("")){
				hql+=" and a.validityValue <= ?";
				queryConditionList.add(getFValue(getValidityValueUp()));
			}
			if(getAnsweringTimeByMinDown()!=null){
				hql+=" and a.answeringTime >= ?";
				queryConditionList.add(getAnsweringTimeByMinDown()*60);
			}
			if(getAnsweringTimeByMinUp()!=null){
				hql+=" and a.answeringTime <= ?";
				queryConditionList.add(getAnsweringTimeByMinUp()*60);
			}
			if(getScoreDown()!=null){
				hql+=" and a.score >= ?";
				queryConditionList.add(getScoreDown());
			}
			if(getScoreUp()!=null){
				hql+=" and a.score <= ?";
				queryConditionList.add(getScoreUp());
			}
			if(!this.getOpinionDown().equals("")){
				hql+=" and a.opinion >= ?";
				queryConditionList.add(getFValue(getOpinionDown()));
			}
			if(!getOpinionUp().equals("")){
				hql+=" and a.opinion <= ?";
				queryConditionList.add(getFValue(getOpinionUp()));
			}
			if(!getApplicableObject().equals("")){
				hql+=" and a.applicableObject in (" + getApplicableObject() + ")";
				//hql+=" and a.applicableObject=?";
				//queryConditionList.add(new Integer(getApplicableObject()));
			}
			
			if( getApplicableObjects()!=null&&getApplicableObjects().length>0){
				int i =0;
				
				String applicableObjectStr = "";
				for (String str : getApplicableObjects()) {
					if(++i>1){
						applicableObjectStr+=",";
					}
					applicableObjectStr+= str ;
				}
				hql+=" and a.applicableObject in (" + applicableObjectStr + ")";				 
			}
			
			if( getCourseVersions()!=null&&getCourseVersions().length>0&&!getCourseVersions()[0].equals("")){
				int i =0;
				String courseVersionsStr = "";
				for (String str : getCourseVersions()) {
					if(!"".equals(str.trim())){
						if(++i>1){
							courseVersionsStr+=",";
						}
						courseVersionsStr+= str ;
					}
				}
				hql+=" and a.courseVersion in (" + courseVersionsStr + ")";
			}
			
			if(!getOriginalPaperCode().equals("")){
				hql+=" and a.originalPaperCode =?";
				queryConditionList.add(getOriginalPaperCode());
			}
			if(!getStatus().equals("9")&&!"".equals(getStatus().trim())){
				hql+=" and a.status=?";
				queryConditionList.add(Integer.valueOf(getStatus().trim()));
			}
			if(!getRegion().equals("")){
				hql+=" and a.region.code=?";
				queryConditionList.add(getRegion());
			}
			if(!getSubject().equals("")){
				hql+=" and a.subject.code=?";
				queryConditionList.add(getSubject());
			}
			if(!getItemType().equals("")){
				if(getItemType().equals("feiXuanTi")){
					//非选题特殊逻辑 数学,化学,物理 相同
					hql+= "and substring(a.itemType.code, -2, 1) > 1";
					/*if(getSubject_code().toLowerCase().equals("m")){
						hql+= "and substring(a.itemType.code, -2, 1) > 1";						 
					}else if(getSubject_code().toLowerCase().equals("p") ){
						hql+= "and substring(a.itemType.code, -2, 1) > 1";
					}else if(getSubject_code().toLowerCase().equals("c") ){
						hql+= "and substring(a.itemType.code, -2, 1) > 1";
					}*/
				}else{
					hql+=" and a.itemType.code in('";
					hql+=getItemType().replaceAll(",", "','");
					hql+="')";
				}/**/
			}
			if(!getGrade().equals("")){
				hql+=" and b.code=? ";
				queryConditionList.add(getGrade());
			}
			if(!getItemTheme().equals("")){
				hql+=" and c.code=?";
				queryConditionList.add(getItemTheme());
			}
			if(!getKnowledgePoint().equals("")){
				//hql+=" and d.code=?";
				//queryConditionList.add(getKnowledgePoint());
				String[] knowledgePoints=getKnowledgePoint().split(",");
				if(knowledgePoints.length>0)
					hql+=" and (";
				int i=0;
				for(String knowledgePoint:knowledgePoints){
					if(i>0)
						hql+=" or";
					hql+=" '"+knowledgePoint+"'in elements(a.knowledgePoints)";
					i++;
				}
				if(knowledgePoints.length>0)
					hql+=" )";
			}
			if(!getSourceFile().equals("")){
				hql+=" and a.sourceFile like ? ";
				queryConditionList.add("%"+getSourceFile()+"%");
			}
			if(!getImportFile().equals("")){
				hql+=" and a.importFile like ? ";
				queryConditionList.add("%"+getImportFile()+"%");
			}
			if(getHint()!=null&&!"".equals(getHint().trim())){
				hql+="and a.hint like ? ";
				queryConditionList.add("%"+getHint()+"%");
			}
			if(getAnalysisAtLarge1()!=null&&!"".equals(getAnalysisAtLarge1().trim())){
				hql+="and a.analysisAtLarge1 like ? ";
				queryConditionList.add("%"+getAnalysisAtLarge1()+"%");
			}
			if(!getAbilityValue().equals("")){
				hql+=" and a.abilityValue is not null and trim(a.abilityValue) != '' ";				
			}
			if(!getCreatedTime().equals("")){
				hql+=" and DATE(a.createdTime) = ? ";
				queryConditionList.add(getCreatedDate());
			}
			if(!getCorrectAnswer().equals("")){
				hql+=" and a.correctAnswer like ? ";
				queryConditionList.add("%"+getCorrectAnswer()+"%");
			}
			if(!getReviewRound().equals("")){
				hql+=" and a.reviewRound=?";
				queryConditionList.add(getReviewRound());
			}
		}
		if(getSubject_code()!=null&&!"".equals(getSubject_code())){
			if(hql.indexOf("where")==-1){
				hql+=" where 1=1 ";
			}
			hql+=" and a.subject.code = ? ";
			queryConditionList.add(getSubject_code());
		}
		//hql += " order by a.itemType.code ";
	}
	
	public void setStatusRang(String status) {
		hql += " and a.status >= ? ";
		queryConditionList.add(new Integer(status));
	}
	
	public void setOrderby(String orderstr) {
		hql += " order by " + orderstr;		
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}	
	public String getApplicableObject() {
		return applicableObject;
	}
	public void setApplicableObject(String applicableObject) {
		this.applicableObject = applicableObject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value != null? value.trim(): "";
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDifficultyValueUp() {
		return difficultyValueUp;
	}
	public void setDifficultyValueUp(String difficultyValueUp) {
		this.difficultyValueUp = difficultyValueUp;
	}
	public String getDifficultyValueDown() {
		return difficultyValueDown;
	}
	public void setDifficultyValueDown(String difficultyValueDown) {
		this.difficultyValueDown = difficultyValueDown;
	}
	public String getValidityValueUp() {
		return validityValueUp;
	}
	public void setValidityValueUp(String validityValueUp) {
		this.validityValueUp = validityValueUp;
	}
	public String getValidityValueDown() {
		return validityValueDown;
	}
	public void setValidityValueDown(String validityValueDown) {
		this.validityValueDown = validityValueDown;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getKnowledgePoint() {
		return knowledgePoint;
	}
	public void setKnowledgePoint(String knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}
	public String getItemTheme() {
		return itemTheme;
	}
	public void setItemTheme(String itemTheme) {
		this.itemTheme = itemTheme;
	}
	public String getYearUp() {
		return yearUp;
	}
	public void setYearUp(String yearUp) {
		this.yearUp = yearUp;
	}
	public String getYearDown() {
		return yearDown;
	}
	public void setYearDown(String yearDown) {
		this.yearDown = yearDown;
	}
	public String getDifficultyValue() {
		return difficultyValue;
	}
	public void setDifficultyValue(String difficultyValue) {
		this.difficultyValue = difficultyValue;
	}
	public String getValidityValue() {
		return validityValue;
	}
	public void setValidityValue(String validityValue) {
		this.validityValue = validityValue;
	}
	public String getOriginalPaperCode() {
		return originalPaperCode;
	}
	public void setOriginalPaperCode(String originalPaperCode) {
		this.originalPaperCode = originalPaperCode;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	public Integer getIValue(String value){
		int r = -100;
		try {
			r = Integer.valueOf(value);
		} catch (Exception e ){			
		}				
		return r;
	}
	public Float getFValue(String value){
		float r = -100;
		try {
			r = Float.valueOf(value);
		} catch (Exception e ){
		}				
		return r;
	}

	public String getOpinionUp() {
		return opinionUp;
	}

	public void setOpinionUp(String opinionUp) {
		this.opinionUp = opinionUp;
	}

	public String getOpinionDown() {
		return opinionDown;
	}

	public void setOpinionDown(String opinionDown) {
		this.opinionDown = opinionDown;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}

	public String getImportFile() {
		return importFile;
	}

	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}

	public String[] getApplicableObjects() {
		return applicableObjects;
	}

	public void setApplicableObjects(String[] applicableObjects) {
		this.applicableObjects = applicableObjects;
	}

	public String[] getCourseVersions() {
		return courseVersions;
	}

	public void setCourseVersions(String[] courseVersions) {
		this.courseVersions = courseVersions;
	}

	public Integer getAnsweringTimeByMinDown() {
		return answeringTimeByMinDown;
	}

	public void setAnsweringTimeByMinDown(Integer answeringTimeByMinDown) {
		this.answeringTimeByMinDown = answeringTimeByMinDown;
	}

	public Integer getAnsweringTimeByMinUp() {
		return answeringTimeByMinUp;
	}

	public void setAnsweringTimeByMinUp(Integer answeringTimeByMinUp) {
		this.answeringTimeByMinUp = answeringTimeByMinUp;
	}

	public Float getScoreDown() {
		return scoreDown;
	}

	public void setScoreDown(Float scoreDown) {
		this.scoreDown = scoreDown;
	}

	public Float getScoreUp() {
		return scoreUp;
	}

	public void setScoreUp(Float scoreUp) {
		this.scoreUp = scoreUp;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getAnalysisAtLarge1() {
		return analysisAtLarge1;
	}

	public void setAnalysisAtLarge1(String analysisAtLarge1) {
		this.analysisAtLarge1 = analysisAtLarge1;
	}

	public String getTypicalExample() {
		return typicalExample;
	}

	public void setTypicalExample(String typicalExample) {
		this.typicalExample = typicalExample;
	}
	
	public String getAbilityValue() {
		return abilityValue;
	}

	public void setAbilityValue(String abilityValue) {
		this.abilityValue = abilityValue;
	}

	public String getReviewRound() {
		return reviewRound;
	}

	public void setReviewRound(String reviewRound) {
		this.reviewRound = reviewRound;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}

	public Date getCreatedDate() {
		return DateUtil.parse(this.createdTime);
	}
	
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
}
