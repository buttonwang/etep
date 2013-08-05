package com.ambow.trainingengine.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
import com.ambow.trainingengine.web.util.StudyInfoUtil;




public class StudyInfoAction extends WebBaseAction {

	private static final long serialVersionUID = 1L;

	private MainPageService mainPageService;
	
	private String datepicker;
	
	private int isError=0;//0是日期格式正确 1是日期格式不正确
	
	private String studyInfoFirst;
	
	private String studyInfoSecond;
	
	public String execute() {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date inputDate=null;		
		try {
			inputDate=sdf.parse(datepicker);
		} catch (ParseException e) {
			isError=1;
			return SUCCESS;
		}
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		this.mainPageService.updateExamTime(userData, inputDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);
		//算据考研日期
		long days=0;
		days=(inputDate.getTime()-new Date().getTime())/(1000*3600*24);
		String tempStr="还有";
		if(days<0&&days>-1)
			days=0;
		else if(days<=-1)
			tempStr="已过";
		
		if(days<0)
			days=-days;
		
		this.setStudyInfoFirst(
				"距离"+(calendar.get(Calendar.YEAR)-2000)+"'"+userData.getProcessPolicy().getStudyInfo()
				+tempStr+"<span>"+days+"</span>天");
	
		//获取完成训练的日期
		String finishDate=StudyInfoUtil.getFinishDate(userData);
		
		if(finishDate==null){			
			this.setStudyInfoSecond("考试时间为:"+sdf.format(inputDate));
		}
		else
			this.setStudyInfoSecond("以你目前的平均学习速度计，你将于"+finishDate+"学完本课程。");
		return SUCCESS;
	}
	
	public String getDatepicker() {
		return datepicker;
	}

	public void setDatepicker(String datepicker) {
		this.datepicker = datepicker;
	}

	public int getIsError() {
		return isError;
	}

	public void setIsError(int isError) {
		this.isError = isError;
	}

	public String getStudyInfoFirst() {
		return studyInfoFirst;
	}

	public void setStudyInfoFirst(String studyInfoFirst) {
		this.studyInfoFirst = studyInfoFirst;
	}

	public String getStudyInfoSecond() {
		return studyInfoSecond;
	}

	public void setStudyInfoSecond(String studyInoSecond) {
		this.studyInfoSecond = studyInoSecond;
	}

	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}


	

}
