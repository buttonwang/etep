package com.ambow.trainingengine.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ambow.trainingengine.web.data.UserDataVO;

public class StudyInfoUtil {
	/**
	 * 获取距离完成训练的日期
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getFinishDate(UserDataVO userData){
		//计算完成日期
		float learningProcessRate=userData.getLearningProcessRate();
		if(learningProcessRate>0){
			long time =System.currentTimeMillis()-userData.getFirstTrainingTime().getTime();
		//	int days=(int)Math.ceil((double)time/(double)(24*60*60*1000));
			double tempTimes=(((double)100-learningProcessRate)/(double)(learningProcessRate))*((double)time);
			int days2= (int)Math.ceil((double)tempTimes/(double)(24*60*60*1000));
			Calendar C = Calendar.getInstance();// 得到当前时间 
			if(tempTimes+time>24*60*60*1000)
				C.add(C.DATE, days2);	
			Date temp_date = C.getTime(); 
			SimpleDateFormat date=new SimpleDateFormat("yyyy年MM月dd日");
			return date.format(temp_date);
		}
		return null;
	}
}